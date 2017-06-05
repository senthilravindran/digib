package com.vp.api.messaging;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

@SuppressWarnings("deprecation")
public class QProcessor extends QueueMessageProcessing {

	public static void main(String[] argv) {
		QProcessor qp = new QProcessor();
		QueueMessageProcessing.registerQueueProcessor(qp.getTopic(), qp);
		while (true) {
		}

	}

	public QProcessor() {
		super();
	}

	@Override
	public String getTopic() {
		return "DOMESTICPYMTQUEUE";
	}

	@SuppressWarnings({ "resource" })
	@Override
	public void process(Message message) {
		// TODO Auto-generated method stub

		int i = 0;
		if (message != null) {
			if (message instanceof TextMessage) {
				String text = null;
				try {
					text = ((TextMessage) message).getText();

					System.out.println("Message is : " + text);

					HttpGet request = new HttpGet("http://127.0.0.1:4567/processpayment");
					System.out.println(request);
					HttpResponse response;
					response = new DefaultHttpClient().execute(request);
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = "";
					while ((line = rd.readLine()) != null) {
						System.out.println(line);

					}

				} catch (Exception e) {

					log.error("Was not able to process message", e);
					e.printStackTrace();
				}
				System.out.println("Got " + i++ + ". message: " + text);
			}
		}

	}
}
