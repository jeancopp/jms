package br.com.coppieters.examples;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.naming.NamingException;

import org.junit.Test;

public class MessageriaProducerTest extends MessageriaTest {
	
	private ManipulaJMS consumerJMS;

	@Override
	public void init() throws NamingException {
		super.init();
		consumerJMS = new ManipulaJMS(this.contexto);
	}
	
	@Test
	public void testaProducer(){
		consumerJMS.accept(sessao -> {
			Destination fila;
			try {
				fila = (Destination) contexto.lookup(FILA_NOME);
				Message message = sessao.createTextMessage("<pedido><id>123</id></pedido>");
				MessageProducer producer = sessao.createProducer(fila);
				producer.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
