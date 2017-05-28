package com.vp.payment.services;

import com.vp.payment.entities.PaymentMessage;

public class FraudCheck {
	private PaymentMessage msg = null;

	public FraudCheck(PaymentMessage msg) {

		this.msg = msg;
	}

	public int getFraudScore() {

		return 0;

	}

}
