package com.vp.payment.entities;

import com.vp.payment.process.PaymentExecutionVenue;

public class PaymentMessage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PaymentSender sender = null;
	private PaymentReceiver reciever = null;
	private PaymentExecutionVenue venue = null;
	private String paymentType =null ;
	private java.util.Date executionDate = null;
	private double amount =0;
	private String sourceCurrency = null;
	private String targetCurrency = null;
	private String currency = null;
	private String AMLScore = null;
	private String paymentStatus = null;
	
	
	
	
	public PaymentSender getSender() {
		return sender;
	}
	public void setSender(PaymentSender sender) {
		this.sender = sender;
	}
	public PaymentReceiver getReciever() {
		return reciever;
	}
	public void setReciever(PaymentReceiver reciever) {
		this.reciever = reciever;
	}
	public PaymentExecutionVenue getVenue() {
		return venue;
	}
	public void setVenue(PaymentExecutionVenue venue) {
		this.venue = venue;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public java.util.Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(java.util.Date executionDate) {
		this.executionDate = executionDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSourceCurrency() {
		return sourceCurrency;
	}
	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAMLScore() {
		return AMLScore;
	}
	public void setAMLScore(String aMLScore) {
		AMLScore = aMLScore;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
