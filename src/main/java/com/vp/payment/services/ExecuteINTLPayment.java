package com.vp.payment.services;

import com.vp.payment.entities.PaymentMessage;
import com.vp.payment.entities.PaymentReceiver;
import com.vp.payment.entities.PaymentSender;
import com.vp.payment.process.PaymentException;
import com.vp.payment.process.PaymentGateway;

import redis.clients.jedis.Jedis;

public class ExecuteINTLPayment {

	public static String executePayment() throws PaymentException {

		// Connecting to Redis server on localhost
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		// set the data in redis string

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
		FraudCheck frd = new FraudCheck(msg);
		int fraudResult = frd.getFraudScore();

		FundCheck fnd = new FundCheck(msg);
		String fundResult = fnd.areEnoughFundsAvailable();
		fundResult = "YES";
		String set = null;
		String paymentStatus = null;

		if ((fraudResult == 0) && (fundResult.equals("YES"))) {

			ExecuteINTLPayment agent = new ExecuteINTLPayment();
			try {

				// agent.executePayment(sender, receiver, msg);
				PaymentGateway pGate = new PaymentGateway();
				// System.out.println(pStore.insertPayment(msg));

				jedis.set("PYMT","SD");
				// Get the stored data and print it
				System.out.println("Stored string in redis:: " + jedis.get("PYMT"));
				paymentStatus = pGate.insertPayment(msg);
				;
				return paymentStatus;

			} catch (Exception p) {
				System.out.println(p.toString());

			}
		}
		return null;

	}

	public static void main(String args[]) {
		
		try {
			System.out.println(ExecuteINTLPayment.executePayment());
		} catch (PaymentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
