package com.vp.api.messaging;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vp.api.messaging.utilities.ConfigurationFileWrapper;

public class QueueMessageConsumer implements Runnable {

	private volatile static QueueMessageConsumer _instance;

	public static QueueMessageConsumer getInstance() {
		if (_instance == null) {
			synchronized (QueueMessageConsumer.class) {
				if (_instance == null) {
					_instance = new QueueMessageConsumer();
					Thread t = new Thread(_instance);
					t.start();

				}
			}
		}
		return _instance;
	}

	public static void main(String[] args) {
		QueueMessageConsumer tmc = new QueueMessageConsumer();
		tmc.run();
	}

	Logger log = LoggerFactory.getLogger(QueueMessageConsumer.class);

	protected boolean isDurable = false;
	protected String subscriptionName = null;
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;

	private MessageConsumer consumer;

	private String username = "admin";

	private String password = "password";

	private QueueMessageConsumer() {
		log.debug("Starting Message Consumer ...");

		try {

			if (ConfigurationFileWrapper.contains("ACTIVEMQ_USER") && //
			        ConfigurationFileWrapper.contains("ACTIVEMQ_PASSWORD")) { //

				username = ConfigurationFileWrapper.getPropValue("ACTIVEMQ_USER");
				password = ConfigurationFileWrapper.getPropValue("ACTIVEMQ_PASSWORD");
			} else {
				log.error("Configuration ACTIVEMQ_USER and ACTIVEMQ_PASSWORD are missing.");
				log.warn("Will user default values to start");
			}
			/*
			 * Create a ConnectionFactory instance. TervelaFactory factory class
			 * is used to create a ConnectionFactory
			 */

			/**
			 *
			 * subscriber-primary-broker-protocol=tcp
			 * subscriber-primary-broker-host= subscriber-primary-broker-port=
			 * subscriber-secondary-broker-protocol=//
			 * subscriber-secondary-broker-host=
			 * subscriber-secondary-broker-port=
			 * subscriber-tertiary-broker-protocol=
			 * subscriber-tertiary-broker-host= //
			 * subscriber-tertiary-broker-port=
			 */
			if (ConfigurationFileWrapper.contains("subscriber-primary-broker-protocol") && //
			        ConfigurationFileWrapper.contains("subscriber-primary-broker-host") && //
			        ConfigurationFileWrapper.contains("subscriber-primary-broker-port")) { //

				String primaryconnectionURI = //
				        ConfigurationFileWrapper.getPropValue("subscriber-primary-broker-protocol") + "://" + //
				                ConfigurationFileWrapper.getPropValue("subscriber-primary-broker-host") + ":" + //
				                ConfigurationFileWrapper.getPropValue("subscriber-primary-broker-port");

				String secondaryconnectionURI = null;
				String tertiaryconnectionURI = null;

				if (ConfigurationFileWrapper.contains("subscriber-secondary-broker-protocol") && //
				        ConfigurationFileWrapper.contains("subscriber-secondary-broker-host") && //
				        ConfigurationFileWrapper.contains("subscriber-secondary-broker-port")) { //

					secondaryconnectionURI = //
					        ConfigurationFileWrapper.getPropValue("subscriber-secondary-broker-protocol") + "://" + //
					                ConfigurationFileWrapper.getPropValue("subscriber-secondary-broker-host") + ":" + //
					                ConfigurationFileWrapper.getPropValue("subscriber-secondary-broker-port");

				} else {
					log.warn("Configuration for secondary broker is missing");
				}

				if (ConfigurationFileWrapper.contains("subscriber-tertiary-broker-protocol") && //
				        ConfigurationFileWrapper.contains("subscriber-tertiary-broker-host") && //
				        ConfigurationFileWrapper.contains("subscriber-tertiary-broker-port")) { //

					tertiaryconnectionURI = //
					        ConfigurationFileWrapper.getPropValue("subscriber-tertiary-broker-protocol") + "://" + //
					                ConfigurationFileWrapper.getPropValue("subscriber-tertiary-broker-host") + ":" + //
					                ConfigurationFileWrapper.getPropValue("subscriber-tertiary-broker-port");

				} else {
					log.warn("Configuration for tertiary broker is missing");
				}

				if (secondaryconnectionURI != null && tertiaryconnectionURI == null) {
					connectionFactory = new JmsConnectionFactory(
					        "failover:(" + primaryconnectionURI + "," + secondaryconnectionURI + ")");

				} else if (secondaryconnectionURI != null && tertiaryconnectionURI != null) {
					connectionFactory = new JmsConnectionFactory("failover:(" + primaryconnectionURI + ","
					        + secondaryconnectionURI + "," + tertiaryconnectionURI + ")");

				} else {
					connectionFactory = new JmsConnectionFactory("failover:(" + primaryconnectionURI + ")");

				}

			} else {
				log.warn(
				        "Configuration name: subscriber-primary-broker-protocol, subscriber-primary-broker-host, and/or subscriber-primary-broker-port are missing.");
				log.warn("Connecting to tcp://localhost:61616 for the subscriber primary broker");

				connectionFactory = new JmsConnectionFactory("tcp://localhost:61616");
			}

			log.debug("JMS message consumer created.");

			// Create a new JMS Connection using the ConnectionFactory
			connection = null;
			try {
				connection = connectionFactory.createConnection(username, password);
				connection.start();
				log.debug("JMS connection established.");

			} catch (JMSSecurityException secException) {
				log.error("Authentiation failed: " + secException);
				return;
			}
			/*
			 * Create a new JMS Session
			 */

			/**
			 * @TODO: We need or should create one session per subscriber. for
			 *        now, 1 session is shared
			 */

			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			log.debug("JMS session created.");

		} catch (JMSException e) {
			log.error("JMSException occurred", e);
		} catch (Exception e) {
			log.error("Exception occurred: ", e);
			e.printStackTrace();
		}
	}

	public void endConsumer() {
		// Close the message consumer
		try {
			consumer.close();
			log.debug("Message consumer closed.");

			// Close the session
			session.close();
			log.debug("JMS session closed.");

			// Close the connection
			connection.close();
			log.debug("JMS connection closed.");
		} catch (JMSException e) {
			log.error("Error closing connection" + e);
		}
		QueueMessageConsumer._instance = null;

	}

	public Session getSession() {
		return session;
	}

	@Override
	public void run() {

		System.out.println("Queue consumer thread started");

		while (true) {

			try {
				if (ConfigurationFileWrapper.contains("subscriber-sleep-timer-ms")) {
					Thread.sleep(ConfigurationFileWrapper.getLongPropValue("subscriber-sleep-timer-ms"));
				} else {
					Thread.sleep(500);
				}

			} catch (InterruptedException e) {
				log.error("Thread exception in healer thread", e);
				QueueMessageConsumer._instance = null;
				break;
			} catch (IOException e) {
				log.error("Error reading configuration file", e);
				QueueMessageConsumer._instance = null;
				break;

			}
		}

	}

}
