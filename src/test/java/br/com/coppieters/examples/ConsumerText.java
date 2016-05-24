package br.com.coppieters.examples;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsumerText {

	private final String FILA_NOME = "financeiro";
	private InitialContext contexto = null;

	@Before
	public void init() throws NamingException {
		// criando o contexto
		contexto = new InitialContext();
	}

	@Test
	public void consumirMensagem() {
		Connection conexao = null;
		Session sessao = null;
		try {
			// Acessando instância da fabrica do pacote desejado
			/*
			 * O lookup é uma operação muito comum em ambientes JavaEE para
			 * descobrir ou pegar um recurso que o servidor disponibiliza.
			 * Dependendo do servidor os recursos podem variar muito. Por
			 * exemplo, usando um servidor de aplicação, podemos pegar
			 * configurações de segurança, pool de conexão, gerenciadores de
			 * transação ou caches, etc.
			 */
			ConnectionFactory lookup = (ConnectionFactory) contexto.lookup("ConnectionFactory");
			// Criamos então a conexão...
			conexao = lookup.createConnection();
			// e damos start nela.
			conexao.start();
			// Agora abrimos a sessão, da maneira mais simples. É possível
			// realizarmos algo semelhante a transação de um banco de dados
			// relacional
			sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination fila = (Destination) contexto.lookup(FILA_NOME);// <--- nome da fila no arquivo  de configuração(main/resoureces/jndi.properties)
			MessageConsumer consumidorDeMensagem = sessao.createConsumer(fila);
			// Recebemos então a mensagem da fila selecionada acima através do contexto.
			Message messagem = consumidorDeMensagem.receive();
			// Por fim, imprimimos a mensagem recebida
			System.out.println("Recebendo msg: " + messagem);
		} catch (JMSException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != sessao) sessao.close();
				if (null != conexao) conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
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

	@After
	public void finalizar() {
		try {
			if (null != contexto) contexto.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
