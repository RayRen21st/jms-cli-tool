package org.genjms.client.cli;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class VirtualTopicSubscriber {
	
	public static void createConsumers(final String destination, String jmsUri, int clientNumber, String clientName,
			List<ActiveMQConnection> connectionList, List<Session> sessionList, List<MessageConsumer> consumerList)
					throws JMSException {
		for (int i = 0; i < clientNumber; i++) {

			ActiveMQConnection activeMQConnection = (ActiveMQConnection) new ActiveMQConnectionFactory(jmsUri)
					.createConnection();
			// MUST set to true to receive retained messages
			// activeMQConnection.setUseRetroactiveConsumer(true);
			String clientId = clientName + "_" + i;
			activeMQConnection.setClientID(clientId);
			activeMQConnection.start();
			connectionList.add(activeMQConnection);

			Session s = activeMQConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sessionList.add(s);
			Queue jmsQueue = s.createQueue(destination);
			MessageConsumer consumer = s.createConsumer(jmsQueue);
			consumerList.add(consumer);
			consumer.setMessageListener(new VirtualTopicMessageListener(clientId));
		}
	}
}
