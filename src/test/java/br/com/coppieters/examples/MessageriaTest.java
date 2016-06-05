package br.com.coppieters.examples;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;

public class MessageriaTest {

	protected final String FILA_NOME = "financeiro";
	protected final String TOPICO_NOME = "loja";
	
	protected InitialContext contexto = null;

	@Before
	public void init() throws NamingException {
		contexto = new InitialContext();
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
