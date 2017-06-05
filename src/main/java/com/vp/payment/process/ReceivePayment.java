package com.vp.payment.process;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.vp.api.messaging.QProcessor;
import com.vp.api.messaging.QueueMessageProcessing;

public class ReceivePayment {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//		ReceivePayment receiver = new ReceivePayment();

		QProcessor qp = new QProcessor();
		QueueMessageProcessing.registerQueueProcessor(qp.getTopic(), qp);
		while (true) {
		}

		//	receiver.receiveMessage();
	}

	//	private ConnectionFactory factory = null;
	//	private Connection connection = null;
	//	private Session session = null;
	//	private Destination destination = null;
	//
	//	private MessageConsumer consumer = null;

	public ReceivePayment() {

	}

	//	public void receiveMessage() throws ClientProtocolException, IOException {
	//		try {
	////			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
	////			connection = factory.createConnection();
	////			connection = factory.createConnection();
	////			connection.start();
	////			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	////			destination = session.createQueue("DOMESTICPYMTQUEUE");
	////			consumer = session.createConsumer(destination);
	//			//Message message = consumer.receive();
	//
	//			if (message instanceof TextMessage) {
	//				TextMessage text = (TextMessage) message;
	//				System.out.println("Message is : " + text.getText());
	//			}
	//
	//			HttpClient client = new DefaultHttpClient();
	//			HttpGet request = new HttpGet("http://127.0.0.1:4567/processpayment");
	//			System.out.println(request);
	//			HttpResponse response = client.execute(request);
	//			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	//			String line = "";
	//			while ((line = rd.readLine()) != null) {
	//				System.out.println(line);
	//
	//			}
	//
	//		} catch (JMSException e) {
	//			e.printStackTrace();
	//		}
	//	}
}
