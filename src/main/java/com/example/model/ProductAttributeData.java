package com.example.model;

import com.mozu.api.contracts.productadmin.AttributeInProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductAttributeData {
	private boolean isExtra = false;
	private boolean isProperty = false;
	private boolean isOption = false;
	private boolean isVariantProperty = false;
	
	private boolean isBooleanType = false;
	private boolean isTextType = false;
	private boolean isListType = false;
	private AttributeInProductType attribute;
	
	private String attributeFQN;
	private List<String> listValidValues;

	public boolean isExtra() {
		return isExtra;
	}

	public void setExtra(boolean isExtra) {
		this.isExtra = isExtra;
	}

	public boolean isProperty() {
		return isProperty;
	}

	public void setProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}

	public boolean isOption() {
		return isOption;
	}

	public void setOption(boolean isOption) {
		this.isOption = isOption;
	}

	public boolean isVariantProperty() {
		return isVariantProperty;
	}

	public void setVariantProperty(boolean isVariantProperty) {
		this.isVariantProperty = isVariantProperty;
	}

	public boolean isBooleanType() {
		return isBooleanType;
	}

	public void setBooleanType(boolean isBooleanType) {
		this.isBooleanType = isBooleanType;
	}

	public boolean isTextType() {
		return isTextType;
	}

	public void setTextType(boolean isTextType) {
		this.isTextType = isTextType;
	}

	public boolean isListType() {
		return isListType;
	}

	public void setListType(boolean isListType) {
		this.isListType = isListType;
	}

	public AttributeInProductType getAttribute() {
		return attribute;
	}

	public void setAttribute(AttributeInProductType attribute) {
		this.attribute = attribute;
	}

	public String getAttributeFQN() {
		return attributeFQN;
	}

	public void setAttributeFQN(String attributeFQN) {
		this.attributeFQN = attributeFQN;
	}

	public List<String> getListValidValues() {
		return listValidValues == null ? new ArrayList<>() : listValidValues;
	}

	public void setListValidValues(List<String> listValidValues) {
		this.listValidValues = listValidValues;
	}
}
