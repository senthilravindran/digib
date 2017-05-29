package com.vp.api.messaging;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vp.api.messaging.utilities.ConfigurationFileWrapper;

public class QueueMessagePublisher {
	private volatile static QueueMessagePublisher _instance;

	public static QueueMessagePublisher getInstance() {
		if (_instance == null) {
			synchronized (QueueMessagePublisher.class) {
				if (_instance == null) {
					_instance = new QueueMessagePublisher();
				}
			}
		}
		return _instance;
	}

	public static void main(String[] args) {
		QueueMessagePublisher tmp = new QueueMessagePublisher();
		tmp.publish("test", "test", "this is a test");
	}

	protected Hashtable<String, MessageProducer> publishers;

	Logger log = LoggerFactory.getLogger(QueueMessagePublisher.class);

	protected Connection connection;
	protected Destination destination;
	protected Session session;
	protected int deliveryMode;

	protected int count = 1;

	private String username = "admin";

	private String password = "password";

	private QueueConnectionFactory connectionFactory;

	private QueueMessagePublisher() {

		publishers = new Hashtable<String, MessageProducer>();
		try {

			if (ConfigurationFileWrapper.contains("ACTIVEMQ_USER") && //
			        ConfigurationFileWrapper.contains("ACTIVEMQ_PASSWORD")) { //

				username = ConfigurationFileWrapper.getPropValue("ACTIVEMQ_USER");
				password = ConfigurationFileWrapper.getPropValue("ACTIVEMQ_PASSWORD");
			} else {
				log.error("Configuration ACTIVEMQ_USER and ACTIVEMQ_PASSWORD are missing.");
				log.warn("Will user default values to start");
			}

			if (ConfigurationFileWrapper.contains("primary-broker-protocol") && //
			        ConfigurationFileWrapper.contains("primary-broker-host") && //
			        ConfigurationFileWrapper.contains("primary-broker-port")) { //

				String connectionURI = //
				        ConfigurationFileWrapper.getPropValue("primary-broker-protocol") + "://" + //
				                ConfigurationFileWrapper.getPropValue("primary-broker-host") + ":" + //
				                ConfigurationFileWrapper.getPropValue("primary-broker-port");

				connectionFactory = new JmsConnectionFactory("failover:(" + connectionURI + ")");
			} else {
				log.warn(
				        "Configuration name: primary-broker-protocol, primary-broker-host, and/or primary-broker-port are missing.");
				log.warn("Connecting to amqp://localhost:61616 for the primary broker");
				connectionFactory = new JmsConnectionFactory("amqp://localhost:61616");
			}

			log.debug("JMS queue producer created.");

			// Create a new JMS Connection using the ConnectionFactory
			connection = null;
			try {

				//connection = connectionFactory.createQueueConnection();
				connection = connectionFactory.createConnection(username, password);
				log.debug("JMS Queue connection established.");
			} catch (JMSSecurityException secException) {
				log.error("Authentiation failed: " + secException);
				return;
			}
			deliveryMode = DeliveryMode.PERSISTENT;

			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

			log.debug("JMS session created.");

		} catch (JMSException e) {
			e.printStackTrace();
			log.error("JMSException occurred: " + e);
		} catch (Exception e) {
			log.error("Exception occurred: " + e);
		}

	}

	public void endPublish() {

		try {

			log.debug("Message producers closed.");

			// Close the session
			session.close();
			log.debug("JMS session closed.");

			// Close the connection
			connection.close();
			log.debug("JMS connection closed.");

		} catch (JMSException e) {
			e.printStackTrace();
			log.debug("JMSException occurred: " + e);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Exception occurred: " + e);
		}
		QueueMessagePublisher._instance = null;

	}

	private MessageProducer getPublisher(String topic) {
		MessageProducer pub = publishers.get(topic);

		if (pub == null) {

			try {
				destination = session.createQueue(topic);
			} catch (JMSException e1) {
				log.error("Wast not able to create a topic! please check permissions.");
			}
			log.debug("Topic destination created for topic name '" + topic + "'.");
			/*
			 * Create a new JMS Session
			 */
			try {
				// Create a new JMS queue consumer for the given destination
				pub = session.createProducer(destination);
				// Set the delivery mode to non-persistent
				pub.setDeliveryMode(deliveryMode);

				publishers.put(topic, pub);

			} catch (JMSException e) {
				log.error("Could not create a publisher for topic " + topic, e);
			}
			return pub;
		} else {
			return pub;
		}

	}

	public void publish(String source, String topic, String data) {

		MessageProducer mp = getPublisher(topic);
		try {
			sendSerializedData(session, mp, source, data);
		} catch (JMSException e) {
			log.error("Could not Send the Message", e);
			log.error("Message data: " + data);
			endPublish();
			_instance = null;
		}
	}

	/**
	 * Sample creates a JMS TextMessage Message for delivery to a JMS
	 * Destination. Number of Messages sent are specified on the command line
	 * and defaults to 1 if not specified.
	 *
	 * The Message body is filled by appending the index number of the message
	 * to a fixed length String. Then calls send on the JMS MessageProducer to
	 * publish.
	 */
	private void sendSerializedData(Session session, MessageProducer producer, String source, String data)
	        throws JMSException {
		// Create a text message
		TextMessage message = session.createTextMessage();

		message.setJMSCorrelationID(source);

		message.setText(data);
		// Send message
		producer.send(message);
		log.debug("Text message sent: " + message.getText());

	}

}
