package com.vp.payment.entities;

public class PaymentSender implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2040888492345792020L;
	private String senderName =null;
	private String senderCryptoAddress = null;
	private String senderKYCRating = null;
	private String senderBankID = null;
	private String accountID = null;
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderCryptoAddress() {
		return senderCryptoAddress;
	}
	public void setSenderCryptoAddress(String senderCryptoAddress) {
		this.senderCryptoAddress = senderCryptoAddress;
	}
	public String getSenderKYCRating() {
		return senderKYCRating;
	}
	public void setSenderKYCRating(String senderKYCRating) {
		this.senderKYCRating = senderKYCRating;
	}
	public String getSenderBankID() {
		return senderBankID;
	}
	public void setSenderBankID(String senderBankID) {
		this.senderBankID = senderBankID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

}
