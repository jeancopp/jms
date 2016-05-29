package br.com.coppieters.examples;

import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.junit.Test;
/**
 * Teste de messageria usando MessageListener
 * @author Jean
 *
 */
public class MessageriaListenerTest extends MessageriaTest{
	
	private ManipulaJMS consumerJMS;
	@Override
	public void init() throws NamingException {
		super.init();
		this.consumerJMS = new ManipulaJMS(this.contexto);
	}

	@Test
	public void consumirMensagemComMessageListenerPadrao() {
		consumerJMS.accept(sessao -> {
			try {
				sessao.setMessageListener( System.out::println );
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	@Test
	public void consumirMensagemComMessageListenerText() {
		consumerJMS.accept(sessao -> {
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
