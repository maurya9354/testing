package com.example.kibo;

import com.example.model.Context;
import com.example.model.ProductAttributeData;
import com.mozu.api.ApiContext;
import com.mozu.api.DataViewMode;
import com.mozu.api.Headers;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationCollection;
import com.mozu.api.contracts.location.LocationGroup;
import com.mozu.api.contracts.productadmin.*;
import com.mozu.api.resources.commerce.admin.LocationGroupResource;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.commerce.catalog.admin.CategoryResource;
import com.mozu.api.resources.commerce.catalog.admin.LocationInventoryResource;
import com.mozu.api.resources.commerce.catalog.admin.PriceListResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.AttributeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.ProductTypeResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.attributes.AttributeVocabularyValueResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.producttypes.ProductTypeExtraResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.producttypes.ProductTypeOptionResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.producttypes.ProductTypePropertyResource;
import com.mozu.api.resources.commerce.catalog.admin.pricelists.PriceListEntryResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductExtraResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductVariationResource;
import com.mozu.api.resources.content.DocumentDraftSummaryResource;
import com.mozu.api.resources.content.documentlists.DocumentResource;
import com.mozu.api.resources.platform.EntityListResource;
import com.mozu.api.resources.platform.TenantResource;
import com.mozu.api.resources.platform.entitylists.EntityResource;
import com.thoughtworks.xstream.InitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KiboResourceManagerImpl implements KiboResourceManager {

    private static final String CONTEXT_CACHE_KEY_PATTERN = "%s%s";

    @Autowired
    private KiboApiContextManager kiboApiContextManager;

    private int tenantId = 34618;

    private int siteIdUs = 56036;

    private int siteIdCa = 52904;

    Map<String, Context> contextCache = new HashMap<>();
    Map<Context, ProductTypeResource> productTypeResourceCache = new HashMap<>();
    Map<Context, TenantResource> tenantResourceCache = new HashMap<>();
    Map<Context, AttributeResource> attributeResourceCache = new HashMap<>();
    Map<Context, ProductResource> productResourceCache = new HashMap<>();
    Map<Context, CategoryResource> categoryResourceCache = new HashMap<>();
    Map<Context, ProductPropertyResource> productPropertyResourceCache = new HashMap<>();
    Map<Context, DocumentResource> documentResourceCache = new HashMap<>();
    Map<Context, EntityResource> entityResourceCache = new HashMap<>();
    Map<Context, EntityListResource> entityListResourceCache = new HashMap<>();
    Map<Context, PriceListResource> priceListResourceCache = new HashMap<>();
    Map<Context, PriceListEntryResource> priceListEntryResourceCache = new HashMap<>();
    Map<Context, LocationResource> locationResourceCache = new HashMap<>();
    Map<Context, LocationInventoryResource> locationInventoryResourceCache = new HashMap<>();
    Map<Context, ProductExtraResource> productExtraResourceCache = new HashMap<>();
    Map<Context, DocumentDraftSummaryResource> documentDraftSummaryResourceCache = new HashMap<>();
    Map<Context, AttributeVocabularyValueResource> attributeVocabularyValueResourceCache = new HashMap<>();
    Map<Context, ProductTypePropertyResource> productTypePropertyResourceCache = new HashMap<>();
    Map<Context, ProductTypeOptionResource> productTypeOptionResourceCache = new HashMap<>();
    Map<Context, ProductTypeExtraResource> productTypeExtraResourceCache = new HashMap<>();
    Map<Context, ProductVariationResource> productVariationResourceCache = new HashMap<>();
    Map<Context, LocationGroupResource> locationGroupResourceCache = new HashMap<>();
    Map<String, ProductAttributeData> productTypeAttrMasterCache = new HashMap<>();
    Map<Context, Map<String, List<String>>> attrMasterCache = new HashMap<>();
    Map<Context, Map<String, ProductType>> productTypeMasterCache = new HashMap<>();
    Map<Context, Map<String, Integer>> categoriesMasterCache = new HashMap<>();
    Map<String, LocationGroup> locationGroupCache = new HashMap<>();
    List<String> locationCodeCache = new ArrayList<>();

    private Context getCachedOrCreateContext(int tenantId, int siteId) throws InitializationException {
        Context cachedContext = null;
        cachedContext = getContextCreateIfAbsent(tenantId, siteId);
        return cachedContext;
    }

    private Context getContextCreateIfAbsent(int tenantId, int siteId) throws InitializationException {
        Context context = null;
        String contextKey = String.format(CONTEXT_CACHE_KEY_PATTERN, tenantId, siteId);
        if ((context = contextCache.get(contextKey)) == null) {
            synchronized (contextCache) {
                if ((context = contextCache.get(contextKey)) == null) {
                    try {
                        context = new Context(tenantId, siteId);
                    } catch (NumberFormatException e) {
                        throw new InitializationException("Error.INVALID_TENANT_ID_SITE_ID");
                    }
                    contextCache.put(contextKey, context);
                }
            }
        }
        return context;
    }

    private ApiContext getKiboApiContext(final Context context) throws InitializationException {
        ApiContext cahcedApiContext = null;
        cahcedApiContext = getKiboApiContextCreateIfAbsent(context);
        return cahcedApiContext;
    }

    private ApiContext getKiboApiContextCreateIfAbsent(Context context) throws InitializationException {
        ApiContext apiContext = null;
        apiContext = kiboApiContextManager.getApiContext(context);
        if (apiContext == null) {
            apiContext = kiboApiContextManager.newBuilder().setContext(context)
                    .setKiboApiContextExpireListner(new KiboApiContextExpireListener() {
                        @Override
                        public boolean contextExpired() {
                            synchronized (productTypeResourceCache) {
                                productTypeResourceCache.clear();
                            }
                            synchronized (tenantResourceCache) {
                                tenantResourceCache.clear();
                            }
                            synchronized (attributeResourceCache) {
                                attributeResourceCache.clear();
                            }
                            synchronized (productResourceCache) {
                                productResourceCache.clear();
                            }
                            synchronized (categoryResourceCache) {
                                categoryResourceCache.clear();
                            }
                            synchronized (productPropertyResourceCache) {
                                productPropertyResourceCache.clear();
                            }
                            synchronized (documentResourceCache) {
                                documentResourceCache.clear();
                            }
                            synchronized (entityResourceCache) {
                                entityResourceCache.clear();
                            }
                            synchronized (entityListResourceCache) {
                                entityListResourceCache.clear();
                            }
                            synchronized (priceListResourceCache) {
                                priceListResourceCache.clear();
                            }
                            synchronized (priceListEntryResourceCache) {
                                priceListEntryResourceCache.clear();
                            }
                            synchronized (locationInventoryResourceCache) {
                                locationInventoryResourceCache.clear();
                            }
                            synchronized (productExtraResourceCache) {
                                productExtraResourceCache.clear();
                            }
                            synchronized (locationResourceCache) {
                                locationResourceCache.clear();
                            }
                            synchronized (documentDraftSummaryResourceCache) {
                                documentDraftSummaryResourceCache.clear();
                            }
                            synchronized (attributeVocabularyValueResourceCache) {
                                attributeVocabularyValueResourceCache.clear();
                            }
                            synchronized (productTypePropertyResourceCache) {
                                productTypePropertyResourceCache.clear();
                            }
                            return true;
                        }
                    }).build();
        }
        return apiContext;
    }

    @Override
    public TenantResource getTenantResource(Context context) throws InitializationException {
        TenantResource tenantResource = null;
        Context cachedContext = getCachedOrCreateContext(context.getTenantId(), context.getSiteId());
        tenantResource = getTenantResourceCreateIfAbsent(cachedContext);
        return tenantResource;
    }

    private TenantResource getTenantResourceCreateIfAbsent(Context context) throws InitializationException {
        TenantResource cachedResource = null;
        if ((cachedResource = tenantResourceCache.get(context)) == null) {
            synchronized (tenantResourceCache) {
                if ((cachedResource = tenantResourceCache.get(context)) == null) {
                    ApiContext apiContext = getKiboApiContext(context);
                    cachedResource = new TenantResource(apiContext);
                    tenantResourceCache.put(context, cachedResource);
                }
            }
        }
        return cachedResource;
    }

    @Override
    public ProductResource getProductResource(Context context) throws InitializationException {
        ProductResource productResource = null;
        Context cachedContext = getCachedOrCreateContext(context.getTenantId(), context.getSiteId());
        productResource = getProductResourceCreateIfAbsent(cachedContext);
        return productResource;
    }

    private ProductResource getProductResourceCreateIfAbsent(Context context) throws InitializationException {
        ProductResource cachedResource = null;
        if ((cachedResource = productResourceCache.get(context)) == null) {
            synchronized (productResourceCache) {
                if ((cachedResource = productResourceCache.get(context)) == null) {
                    ApiContext apiContext = getKiboApiContext(context);
                    cachedResource = new ProductResource(apiContext);
                    productResourceCache.put(context, cachedResource);
                }
            }
        }
        return cachedResource;
    }

    @Override
    public Map<String, ProductType> getProductTypesForContext(Context context) throws InitializationException {
        if(productTypeMasterCache.get(context) != null && productTypeMasterCache.get(context).size() > 0) {
            return productTypeMasterCache.get(context);
        }

        Map<String, ProductType> productTypeMap = new HashMap<>();
        ProductTypeResource resource = getProductTypeResource(context);

        ProductTypeCollection productTypes = null;
        try {
            do {
                int start = 0;
                productTypes = resource.getProductTypes(start, 200, null, null, null);
                start += productTypes.getPageSize();
                if(productTypes != null && productTypes.getItems() != null && productTypes.getItems().size() > 0) {
                    for(ProductType productType: productTypes.getItems()) {
                        productTypeMap.put(productType.getName(), productType);
                        prepareProductTypeAttrMap(productType, context);
                    }
                }
            }
            while(productTypes.getStartIndex() + productTypes.getPageSize() < productTypes.getTotalCount());
        }
        catch(Exception ex) {
        }

        synchronized (productTypeMasterCache) {
            productTypeMasterCache.put(context, productTypeMap);
        }
        return productTypeMap;
    }

    public ProductTypeResource getProductTypeResource(Context context) throws InitializationException {
        ProductTypeResource productTypeResource = null;
        Context cachedContext = getCachedOrCreateContext(context.getTenantId(), context.getSiteId());
        productTypeResource = getProductTypeResourceCreateIfAbsent(cachedContext);
        return productTypeResource;
    }

    private ProductTypeResource getProductTypeResourceCreateIfAbsent(final Context context)
            throws InitializationException {
        ProductTypeResource cachedResource = null;
        if ((cachedResource = productTypeResourceCache.get(context)) == null) {
            synchronized (productTypeResourceCache) {
                if ((cachedResource = productTypeResourceCache.get(context)) == null) {
                    ApiContext apiContext = getKiboApiContext(context);
                    cachedResource = new ProductTypeResource(apiContext);
                    productTypeResourceCache.put(context, cachedResource);
                }
            }
        }
        return cachedResource;
    }


    private void prepareProductTypeAttrMap(ProductType productType, Context context) {
        ProductAttributeData mapValue = null;
        synchronized (productTypeAttrMasterCache) {
            for(AttributeInProductType attr : productType.getProperties()) {
                mapValue = new ProductAttributeData();
                String key = productType.getId() + "#";
                mapValue.setProperty(true);
                mapValue.setAttributeFQN(attr.getAttributeFQN());
                mapValue.setAttribute(attr);
                populateProductAttributeData(mapValue, attr);
                productTypeAttrMasterCache.put(key.concat(attr.getAttributeDetail().getAttributeCode()), mapValue);
            }
            for(AttributeInProductType attr : productType.getOptions()) {
                mapValue = new ProductAttributeData();
                String key = productType.getId() + "#";
                mapValue.setOption(true);
                mapValue.setAttributeFQN(attr.getAttributeFQN());
                mapValue.setAttribute(attr);
                populateProductAttributeData(mapValue, attr);
                productTypeAttrMasterCache.put(key.concat(attr.getAttributeDetail().getAttributeCode()), mapValue);
            }
            for(AttributeInProductType attr : productType.getExtras()) {
                mapValue = new ProductAttributeData();
                String key = productType.getId() + "#";
                mapValue.setExtra(true);
                mapValue.setAttributeFQN(attr.getAttributeFQN());
                mapValue.setAttribute(attr);
                populateProductAttributeData(mapValue, attr);
                productTypeAttrMasterCache.put(key.concat(attr.getAttributeDetail().getAttributeCode()), mapValue);
            }
            for(AttributeInProductType attr : productType.getVariantProperties()) {
                mapValue = new ProductAttributeData();
                String key = productType.getId() + "#";
                mapValue.setVariantProperty(true);
                mapValue.setAttributeFQN(attr.getAttributeFQN());
                mapValue.setAttribute(attr);
                populateProductAttributeData(mapValue, attr);
                productTypeAttrMasterCache.put(key.concat(attr.getAttributeDetail().getAttributeCode()), mapValue);
            }
        }
    }

    private void populateProductAttributeData(ProductAttributeData mapValue, AttributeInProductType attr) {
        if("YesNo".equalsIgnoreCase(attr.getAttributeDetail().getInputType())) {
            mapValue.setBooleanType(true);
        }
        else if("List".equalsIgnoreCase(attr.getAttributeDetail().getInputType())) {
            mapValue.setListType(true);
            populateListValues(mapValue, attr);
        }
        else if(attr.getAttributeDetail().getInputType().toLowerCase().contains("text")) {
            mapValue.setTextType(true);
        }
    }

    private void populateListValues(ProductAttributeData mapValue, AttributeInProductType attr) {
        List<AttributeVocabularyValueInProductType> vocabularyValues = attr.getVocabularyValues();
        List<String> valuelist = new ArrayList<>();
        if(vocabularyValues != null && vocabularyValues.size() > 0) {
            for(AttributeVocabularyValueInProductType values: vocabularyValues) {
                valuelist.add(String.valueOf(values.getValue()).toLowerCase());
            }
        }
        mapValue.setListValidValues(valuelist);
    }
}