package br.com.coppieters.examples.utils;

import java.util.function.Consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ManipulaJMS implements Consumer<Consumer<Session>> {
	private final InitialContext contexto;
	
	public ManipulaJMS() throws NamingException {
		contexto = new InitialContext();
	}
	
	public ManipulaJMS(InitialContext contexto) {
		this.contexto = contexto;
	}


	@Override
	public void accept(Consumer<Session> acaoAposCriarSessao ){
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
			acaoAposCriarSessao.accept(sessao);
			
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
}
