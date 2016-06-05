package br.com.coppieters.examples.utils;

import static org.junit.Assert.fail;

import java.util.function.Consumer;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class DefaultProducer implements Consumer<Session>{
	
	private final String defaultMessage;
	private final InitialContext context;
	private final String destinationName;

	public DefaultProducer(String defaultMessage, InitialContext context, String destinationName) {
		this.defaultMessage = defaultMessage;
		this.context = context;
		this.destinationName = destinationName;
	}

	@Override
	public void accept(Session session) {
		Destination destino;
		for (int i = 0; i < 100; i++) {
			try {
				destino = (Destination) this.context.lookup(this.destinationName);
				Message message = session.createTextMessage(String.format(this.defaultMessage, i));
				MessageProducer producer = session.createProducer(destino);
				producer.send(message);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Erro na iteração "+i);
			}
		}		
	}
	
	

}
