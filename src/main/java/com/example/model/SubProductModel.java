package com.example.model;


import com.opencsv.bean.CsvBindByPosition;

public class SubProductModel {

    @CsvBindByPosition(position = 0)
    private String operation;

    @CsvBindByPosition(position = 1)
    private String productCode;

    @CsvBindByPosition(position = 2)
    private String productTitle;

    @CsvBindByPosition(position = 3)
    private String productType;

    @CsvBindByPosition(position = 4)
    private String productUsage;

    @CsvBindByPosition(position = 5)
    private String price;

    @CsvBindByPosition(position = 6)
    private String isActive;

    @CsvBindByPosition(position = 7)
    private String parentProductCode;

    @CsvBindByPosition(position = 8)
    private String shortDescription;

    @CsvBindByPosition(position = 9)
    private String LongDescription;

    @CsvBindByPosition(position = 10)
    private String productImages;

    @CsvBindByPosition(position = 11)
    private String taxable;

    @CsvBindByPosition(position = 12)
    private String packageWeight;

    @CsvBindByPosition(position = 13)
    private String packageLength;

    @CsvBindByPosition(position = 14)
    private String packagetWidth;

    @CsvBindByPosition(position = 15)
    private String packageHeight;

    @CsvBindByPosition(position = 16)
    private String upc;

    @CsvBindByPosition(position = 17)
    private String metaTitle;

    @CsvBindByPosition(position = 18)
    private String metaDescription;

    @CsvBindByPosition(position = 19)
    private String metaKeywords;

    @CsvBindByPosition(position = 20)
    private String categoryCodes;

    @CsvBindByPosition(position = 21)
    private String attr1;

    @CsvBindByPosition(position = 22)
    private String attr2;

    @CsvBindByPosition(position = 23)
    private String attr3;

    @CsvBindByPosition(position = 24)
    private String attr4;

    @CsvBindByPosition(position = 25)
    private String attr5;

    @CsvBindByPosition(position = 26)
    private String attr6;

    @CsvBindByPosition(position = 27)
    private String attr7;

    @CsvBindByPosition(position = 28)
    private String attr8;

    @CsvBindByPosition(position = 29)
    private String attr9;

    @CsvBindByPosition(position = 30)
    private String attr10;

    @CsvBindByPosition(position = 31)
    private String attr11;

    @CsvBindByPosition(position = 32)
    private String attr12;

    @CsvBindByPosition(position = 33)
    private String attr13;

    @CsvBindByPosition(position = 34)
    private String attr14;

    @CsvBindByPosition(position = 35)
    private String attr15;

    @CsvBindByPosition(position = 36)
    private String attr16;

    @CsvBindByPosition(position = 37)
    private String attr17;

    @CsvBindByPosition(position = 38)
    private String attr18;

    @CsvBindByPosition(position = 39)
    private String attr19;

    @CsvBindByPosition(position = 40)
    private String attr20;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    public String getParentProductCode() {
        return parentProductCode;
    }

    public void setParentProductCode(String parentProductCode) {
        this.parentProductCode = parentProductCode;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return LongDescription;
    }

    public void setLongDescription(String longDescription) {
        LongDescription = longDescription;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(String packageWeight) {
        this.packageWeight = packageWeight;
    }

    public String getPackageLength() {
        return packageLength;
    }

    public void setPackageLength(String packageLength) {
        this.packageLength = packageLength;
    }

    public String getPackagetWidth() {
        return packagetWidth;
    }

    public void setPackagetWidth(String packagetWidth) {
        this.packagetWidth = packagetWidth;
    }

    public String getPackageHeight() {
        return packageHeight;
    }

    public void setPackageHeight(String packageHeight) {
        this.packageHeight = packageHeight;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getCategoryCodes() {
        return categoryCodes;
    }

    public void setCategoryCodes(String categoryCodes) {
        this.categoryCodes = categoryCodes;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public String getAttr6() {
        return attr6;
    }

    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    public String getAttr7() {
        return attr7;
    }

    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    public String getAttr8() {
        return attr8;
    }

    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    public String getAttr9() {
        return attr9;
    }

    public void setAttr9(String attr9) {
        this.attr9 = attr9;
    }

    public String getAttr10() {
        return attr10;
    }

    public void setAttr10(String attr10) {
        this.attr10 = attr10;
    }

    public String getAttr11() {
        return attr11;
    }

    public void setAttr11(String attr11) {
        this.attr11 = attr11;
    }

    public String getAttr12() {
        return attr12;
    }

    public void setAttr12(String attr12) {
        this.attr12 = attr12;
    }

    public String getAttr13() {
        return attr13;
    }

    public void setAttr13(String attr13) {
        this.attr13 = attr13;
    }

    public String getAttr14() {
        return attr14;
    }

    public void setAttr14(String attr14) {
        this.attr14 = attr14;
    }

    public String getAttr15() {
        return attr15;
    }

    public void setAttr15(String attr15) {
        this.attr15 = attr15;
    }

    public String getAttr16() {
        return attr16;
    }

    public void setAttr16(String attr16) {
        this.attr16 = attr16;
    }

    public String getAttr17() {
        return attr17;
    }

    public void setAttr17(String attr17) {
        this.attr17 = attr17;
    }

    public String getAttr18() {
        return attr18;
    }

    public void setAttr18(String attr18) {
        this.attr18 = attr18;
    }

    public String getAttr19() {
        return attr19;
    }

    public void setAttr19(String attr19) {
        this.attr19 = attr19;
    }

    public String getAttr20() {
        return attr20;
    }

    public void setAttr20(String attr20) {
        this.attr20 = attr20;
    }

}
