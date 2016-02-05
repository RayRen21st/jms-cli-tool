package org.genjms.client.cli;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.util.ByteSequence;

public class VirtualTopicMessageListener implements MessageListener {
	
	private String clientId;

	public VirtualTopicMessageListener(String clientId) {
		this.clientId = clientId;
	}

	public void onMessage(Message message) {
		ByteSequence bs = ((ActiveMQMessage)message).getContent();
		System.out.println("ClientId " + clientId + " received message:" + new String(bs.data, bs.offset, bs.length));
		try {
			Thread.sleep(2000);
			message.acknowledge();
		} catch (JMSException e) {
			throw new IllegalStateException(e);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
