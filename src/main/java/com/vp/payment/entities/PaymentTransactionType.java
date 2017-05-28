package com.vp.payment.entities;

public enum PaymentTransactionType {

	ANNUITY("ANN"),
	BUSINESS_COMMERCIAL("BUS"),
	DEPOSIT("DEP"),
	LOAN("LOA"),
	MISCELLANEOUS("MIS"),
	MORTGAGE("MOR"),
	PENSION("PEN"),
	RENT_LEASE("RLS"),
	SALARY_PAYROLL("SAL"),
	TAX("TAX");
	
	private final String value;
 
	private PaymentTransactionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
