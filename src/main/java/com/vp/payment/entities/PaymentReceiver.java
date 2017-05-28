package com.vp.payment.entities;

public class PaymentReceiver implements java.io.Serializable{
	private String receiverName =null;
	private String receiverCryptoAddress = null;
	private String receiverKYCRating = null;
	private String receiverBankID = null;
	private String accountID = null;
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverCryptoAddress() {
		return receiverCryptoAddress;
	}
	public void setReceiverCryptoAddress(String receiverCryptoAddress) {
		this.receiverCryptoAddress = receiverCryptoAddress;
	}
	public String getReceiverKYCRating() {
		return receiverKYCRating;
	}
	public void setReceiverKYCRating(String receiverKYCRating) {
		this.receiverKYCRating = receiverKYCRating;
	}
	public String getReceiverBankID() {
		return receiverBankID;
	}
	public void setReceiverBankID(String receiverBankID) {
		this.receiverBankID = receiverBankID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
}
