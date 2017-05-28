package com.vp.payment.process;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.google.gson.Gson;
import com.vp.payment.entities.PaymentMessage;
import com.vp.payment.entities.PaymentReceiver;
import com.vp.payment.entities.PaymentSender;
public class SubmitPayment {

	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;

	public SubmitPayment() {

	}

	public void sendMessage(String paymentMessage) {

		try {
			factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("DOMESTICPYMTQUEUE");
			producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText("Hello ...This is a sample message..sending from FirstClient" + paymentMessage
);
			message.setText(paymentMessage);
			producer.send(message);
			System.out.println("Sent: " + message.getText());

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

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
}
