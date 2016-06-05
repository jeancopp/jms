package br.com.coppieters.examples;

import java.util.function.Consumer;

import javax.jms.Session;
import javax.naming.NamingException;

import br.com.coppieters.examples.utils.ManipulaJMS;

public class MessageriaDefaultTest extends MessageriaTest{
	
	
	private ManipulaJMS manipulaJMS;

	@Override
	public void init() throws NamingException {
		super.init();
		this.manipulaJMS = new ManipulaJMS(this.contexto);
	}

	public ManipulaJMS getManipulaJMS() {
		return manipulaJMS;
	}
	
	public void executarAcaoAposCriarSessao(Consumer<Session> acao){
		this.manipulaJMS.accept(acao);
	}
	

}
