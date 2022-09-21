package com.example;

import com.example.kibo.KiboResourceManager;
import com.example.model.Context;
import com.example.model.SubProductModel;
import com.mozu.api.contracts.core.Measurement;
import com.mozu.api.contracts.productadmin.Product;
import com.mozu.api.contracts.productadmin.ProductLocalizedContent;
import com.mozu.api.contracts.productadmin.ProductPrice;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class AddProduct implements Function<Context, Product> {

    Map<String, ProductType> productTypeMap;

    @Autowired
    KiboResourceManager kiboResourceManager;

    @SneakyThrows
    @Override
    public Product apply(Context context) {
        String path = "/home/satyam/Downloads/Products.1234.csv";
        List<SubProductModel> beans = new CsvToBeanBuilder(new FileReader(path))
                .withType(SubProductModel.class).build().parse();
        SubProductModel product = beans.get(1);
        productTypeMap = kiboResourceManager.getProductTypesForContext(context);
        ProductType productType = null;
        if (product.getProductType() != null) {
            productType = productTypeMap.get(product.getProductType());
        }
        ProductResource productResource = kiboResourceManager.getProductResource(context);
        Product kiboProduct = new Product();
        setGeneralProductData(kiboProduct, productType, product);
        Product product1 = productResource.addProduct(kiboProduct);
        return product1;
    }

    protected void setGeneralProductData(Product kiboProduct, ProductType productType, SubProductModel product) throws Exception {
        //Fix for SU-61

        kiboProduct.setProductCode(product.getProductCode().trim());

        kiboProduct.setProductTypeId(productType.getId());

        //Fix for SU-60
        if(!StringUtils.isEmpty(product.getPackageHeight())) {
            Measurement packageHeight = new Measurement();
            packageHeight.setUnit("in");
            packageHeight.setValue(Double.valueOf(product.getPackageHeight()));
            kiboProduct.setPackageHeight (packageHeight);
        }

        if(!StringUtils.isEmpty(product.getPackageLength())) {
            Measurement packageLength = new Measurement();
            packageLength.setUnit("in");
            packageLength.setValue(Double.valueOf(product.getPackageLength()));
            kiboProduct.setPackageLength(packageLength);
        }

        if(!StringUtils.isEmpty(product.getPackagetWidth())) {
            Measurement packageWidth = new Measurement();
            packageWidth.setUnit("in");
            packageWidth.setValue(Double.valueOf(product.getPackagetWidth()));
            kiboProduct.setPackageWidth(packageWidth);
        }
        Double weight = 0.0;
        if(!StringUtils.isEmpty(product.getPackageWeight())) {
            weight = Double.valueOf(product.getPackageWeight());
        }
        if(weight <= 0.0) {
            // Kibo doesn't accept 0 weighted products
            // Default if it's not sent in the file
            weight = 1.0;
        }

        Measurement packageWeight = new Measurement();
        packageWeight.setUnit("lbs");
        packageWeight.setValue(weight);
        kiboProduct.setPackageWeight(packageWeight);

        //Fix for SU-61
        kiboProduct.setProductUsage(product.getProductUsage().trim());

        if (product.getProductUsage().equalsIgnoreCase("Configurable")) {
            kiboProduct.setIsVariation(false);
            kiboProduct.setHasConfigurableOptions(true);
        }

        ProductLocalizedContent content = new ProductLocalizedContent();
        content.setProductName(!StringUtils.isEmpty(product.getProductTitle()) ? product.getProductTitle()
                : product.getShortDescription());

        //Fix for SU-45
        content.setProductShortDescription(product.getShortDescription());
        content.setProductFullDescription(product.getLongDescription());
        content.setLocaleCode("en-US");
        kiboProduct.setContent(content);

        ProductPrice price = new ProductPrice();
        if (!StringUtils.isEmpty(product.getPrice())) {
            Double priceValue = Double.valueOf(product.getPrice());
            //SU-70 fix
            if(priceValue<0) {
                throw new Exception("Validation Error: Price is negative.");
            }
            price.setPrice(priceValue);
        } else {
            price.setPrice(0.0);
        }
        kiboProduct.setPrice(price);

			/*ProductPricingBehaviorInfo pricingBehavior = new ProductPricingBehaviorInfo();
			// Fix for SU-56
			pricingBehavior.setDiscountsRestricted(util.getBooleanValue(product.getRestrictDiscountOnProduct()));
			pricingBehavior.setVariationPricingMethod("Fixed");
			// Fix for SU-76
			final DateTimeFormatter formatter = DateTimeFormat.forPattern(productDateFormat);
			if (product.getRestrictionStartDate() != null && !product.getRestrictionStartDate().trim().equalsIgnoreCase("")) {
				try {
					final DateTime startDate = formatter
							.parseDateTime(product.getRestrictionStartDate().concat(" ").concat(timezone));
					LOGGER.debug("Restriction Start Date: " + startDate);
					pricingBehavior.setDiscountsRestrictedStartDate(startDate);
				} catch (final IllegalArgumentException ex) {
					LOGGER.error("Restriction Start Date format is incorrect.");
					throw new Exception("Validation Error: Restriction Start Date format is incorrect.");
				}
			}

			if (product.getRestrictionEndDate() != null && !product.getRestrictionEndDate().trim().equalsIgnoreCase("")) {
				try {
					final DateTime endDate = formatter.parseDateTime(product.getRestrictionEndDate().concat(" ").concat(timezone));
					LOGGER.debug("Restriction End Date:" + endDate);
					pricingBehavior.setDiscountsRestrictedEndDate(endDate);
				} catch (final IllegalArgumentException ex) {
					LOGGER.error("Restriction End Date format is incorrect.");
					throw new Exception("Validation Error: Restriction End Date format is incorrect.");
				}
			}

			kiboProduct.setPricingBehavior(pricingBehavior);*/
        // Fix for SU-44
        kiboProduct.setIsTaxable(Boolean.parseBoolean(product.getTaxable()));
        // Fix for SU-81
        kiboProduct.setUpc(product.getUpc());

    }
}
