package br.com.coppieters.examples;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.junit.Test;

public class MessageriaConsumerQueueTest extends MessageriaTest {
	
	@Test
	public void consomeMensagemQueue() {
		QueueSession sessao = null;
		QueueConnection conexao = null;
		try {
			// Acessando instância da fabrica do pacote desejado, desta vez enxergando como sendo um fabrica de QueueConnection
			QueueConnectionFactory cf = (QueueConnectionFactory) contexto.lookup("ConnectionFactory");
			conexao = cf.createQueueConnection();// criando a conexão
			conexao.start();//inicializando-a
			
			sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue fila = (Queue) contexto.lookup(FILA_NOME);
			QueueReceiver receiver = (QueueReceiver) sessao.createReceiver(fila);
			
			Message messagem = receiver.receive();
			System.out.println("Receebendo msg : " + messagem);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (null != sessao) sessao.close();
				if (null != conexao) conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}
}
