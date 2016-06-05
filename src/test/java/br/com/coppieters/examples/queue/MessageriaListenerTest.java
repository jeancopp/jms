package br.com.coppieters.examples.queue;

import javax.jms.TextMessage;

import org.junit.Test;

import br.com.coppieters.examples.MessageriaDefaultTest;
/**
 * Teste de messageria usando MessageListener
 * @author Jean
 */
public class MessageriaListenerTest extends MessageriaDefaultTest{
	
	@Test
	public void consumirMensagemComMessageListenerPadrao() {
		executarAcaoAposCriarSessao( sessao -> {
			try {
				sessao.setMessageListener( System.out::println );
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	@Test
	public void consumirMensagemComMessageListenerText() {
		executarAcaoAposCriarSessao( sessao -> {
			try {
				sessao.setMessageListener( mensagem -> {
					TextMessage textMessage = (TextMessage) mensagem;
					try {
						System.out.println(textMessage.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} );
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
