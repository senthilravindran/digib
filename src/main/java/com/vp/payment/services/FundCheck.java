package com.vp.payment.services;

import com.vp.payment.entities.PaymentMessage;

public class FundCheck {
	private PaymentMessage msg = null;

	public FundCheck(PaymentMessage msg) {

		this.msg = msg;
	}
	
	public String areEnoughFundsAvailable(){
		
		return "YES";
	}

}
