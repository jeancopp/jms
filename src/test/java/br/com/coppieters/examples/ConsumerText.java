package br.com.coppieters.examples;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

public class ConsumerText {

	@Test
	public void consumirMensagem() {
		InitialContext contexto = null;
		Connection conexao = null;
		Session sessao = null;
		try {
			//criando o contexto
			contexto = new InitialContext();
			//Acessando inst�ncia da fabrica do pacote desejado
			ConnectionFactory lookup = (ConnectionFactory) contexto.lookup("ConnectionFactory");
			//Criamos ent�o a conex�o...
			conexao = lookup.createConnection();
			// e damos start nela.
			conexao.start();
			//Agora abrimos a sess�o, da maneira mais simples. � poss�vel realizarmos algo semelhante a transa��o de um banco de dados relacional
			sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination fila = (Destination) contexto.lookup("financeiro");//<--- nome da fila no arquivo de configura��o(main/resoureces/jndi.properties)
			MessageConsumer consumidorDeMensagem = sessao.createConsumer(fila);
			//Recebemos ent�o a mensagem da fila selecionada acima atrav�s do contexto.
			Message messagem = consumidorDeMensagem.receive();
			// Por fim, imprimimos a mensagem recebida
			System.out.println("Recebendo msg: " + messagem);
		} catch (JMSException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != sessao ) sessao.close();
				if( null != conexao ) conexao.close();
				if( null != contexto ) contexto.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
