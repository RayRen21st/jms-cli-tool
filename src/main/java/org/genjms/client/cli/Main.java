package org.genjms.client.cli;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;

/**
 * Hello world!
 *
 */
public class Main {
	public static void main(String[] args) throws JMSException, InterruptedException, UnknownHostException {
		
		CmdProcessor processor = new CmdProcessor();
		if(1 == processor.process(args)) {
			return;
		}

		final String destination = processor.getDestination();
		String jmsUri = processor.getJmsUri();
		int clientNumber = processor.getClientNumber();
		String clientName = processor.getClientName();

		if(clientName == null) {
			clientName = InetAddress.getLocalHost().getHostAddress();
		}
		List<ActiveMQConnection> connectionList = new ArrayList<ActiveMQConnection>();
		List<Session> sessionList = new ArrayList<Session>();
		List<MessageConsumer> consumerList = new ArrayList<MessageConsumer>();

		try {

			VirtualTopicSubscriber.createConsumers(destination, jmsUri, clientNumber, clientName, connectionList, sessionList, consumerList);

			while (true) {
				Thread.sleep(100);
				
			}
		} finally {
			for (MessageConsumer consumer : consumerList) {
				consumer.close();
			}
			for (Session s : sessionList) {
				s.close();
			}
			for (ActiveMQConnection c : connectionList) {
				c.close();
			}
		}
	}

}
