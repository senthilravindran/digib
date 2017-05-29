package com.vp.payment.process;

import com.google.gson.Gson;
import com.vp.api.messaging.QueueMessagePublisher;
import com.vp.payment.entities.PaymentMessage;
import com.vp.payment.entities.PaymentReceiver;
import com.vp.payment.entities.PaymentSender;

public class SubmitPayment {

	public static void main(String[] args) {
		SubmitPayment queueSender = new SubmitPayment();
		PaymentSender sender = new PaymentSender();
		PaymentReceiver receiver = new PaymentReceiver();
		PaymentMessage msg = new PaymentMessage();

		sender.setSenderBankID("HSBC");
		sender.setSenderCryptoAddress("NONE");
		sender.setSenderKYCRating("GOOD");
		sender.setSenderName("Peter");
		sender.setAccountID("A001");

		receiver.setReceiverBankID("M&T");
		receiver.setReceiverCryptoAddress("NONE");
		receiver.setReceiverName("Parker");
		receiver.setReceiverKYCRating("UNKNOWN");
		receiver.setAccountID("A002");

		msg.setSourceCurrency("USD");
		msg.setTargetCurrency("GBP");
		msg.setAmount(10000);

		msg.setSender(sender);
		msg.setReciever(receiver);
		Gson gson = new Gson();

		queueSender.sendMessage(gson.toJson(msg));
		System.out.println("send msg" + gson.toJson(msg));
	}

	public SubmitPayment() {

	}

	public void sendMessage(String paymentMessage) {

		try {

			StringBuffer sb = new StringBuffer();
			sb.append(paymentMessage);

			QueueMessagePublisher.getInstance().publish("FIRSTCLIENT", "DOMESTICPYMTQUEUE", sb.toString());
			System.out.println("Sent: " + sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
