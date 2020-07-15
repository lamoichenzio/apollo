package it.univaq.disim.mwt.apollo.domain.questions;

public enum InputType{
	TEXT("Text"), 
	TEXTAREA("Textarea"), 
	NUMBER("Number"), 
	DATE("Date");
	
	private String displayValue;
	
	private InputType(String value) {
		this.displayValue = value;
	}
	
	public String getDisplayValue() {
		return this.displayValue;
	}
}
