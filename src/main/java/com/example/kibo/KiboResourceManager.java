package com.example.kibo;

import com.example.model.Context;
import com.mozu.api.contracts.location.LocationGroup;
import com.mozu.api.contracts.productadmin.ProductType;
import com.mozu.api.resources.commerce.admin.LocationGroupResource;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.commerce.catalog.admin.CategoryResource;
import com.mozu.api.resources.commerce.catalog.admin.LocationInventoryResource;
import com.mozu.api.resources.commerce.catalog.admin.PriceListResource;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.attributes.AttributeVocabularyValueResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.producttypes.ProductTypeExtraResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.producttypes.ProductTypeOptionResource;
import com.mozu.api.resources.commerce.catalog.admin.attributedefinition.producttypes.ProductTypePropertyResource;
import com.mozu.api.resources.commerce.catalog.admin.pricelists.PriceListEntryResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductExtraResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductPropertyResource;
import com.mozu.api.resources.commerce.catalog.admin.products.ProductVariationResource;
import com.mozu.api.resources.content.documentlists.DocumentResource;
import com.mozu.api.resources.platform.EntityListResource;
import com.mozu.api.resources.platform.TenantResource;
import com.thoughtworks.xstream.InitializationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface KiboResourceManager {

    public TenantResource getTenantResource(Context context) throws InitializationException;

    public ProductResource getProductResource(Context context) throws InitializationException;

    public Map<String, ProductType> getProductTypesForContext(Context context) throws InitializationException;
}