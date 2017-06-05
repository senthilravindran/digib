package com.vp.api.messaging;

import java.util.Hashtable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class QueueMessageProcessing implements MessageListener {

	private static Session session;

	protected static Hashtable<String, MessageConsumer> listeners = new Hashtable<String, MessageConsumer>();

	public static MessageConsumer getProcessor(String topic) {
		if (listeners.contains(topic)) {
			return listeners.get(topic);
		}
		return null;
	}

	//@Override
	public static MessageConsumer registerQueueProcessor(String topic, QueueMessageProcessing processor) {

		MessageConsumer consumer = null;
		if (session == null) {

			session = QueueMessageConsumer.getInstance().getSession();
		}
		// Create a new JMS message consumer for the given destination

		try {
			Destination destination = session.createQueue(topic);
			consumer = session.createConsumer(destination);
			processor.setConsumer(consumer);
			consumer.setMessageListener(processor);

			listeners.put(processor.getTopic(), consumer);
			return consumer;

		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected MessageConsumer consumer;

	protected String topic;

	Logger log = LoggerFactory.getLogger(QueueMessageProcessing.class);

	public abstract String getTopic();

	@Override
	public void onMessage(Message message) {

		if (consumer == null) {

		} else {
			//Message message;
			try {
				process(message);
				message.acknowledge();

			} catch (JMSException e) {
				log.error("Was not able to process incoming message ", e);
			}

		}

	}

	public abstract void process(Message message);

	public void setConsumer(MessageConsumer con) {
		consumer = con;
	}
}
