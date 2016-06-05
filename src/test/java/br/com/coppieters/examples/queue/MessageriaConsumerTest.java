package br.com.coppieters.examples.queue;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;

import org.junit.Test;

import br.com.coppieters.examples.MessageriaDefaultTest;

public class MessageriaConsumerTest extends MessageriaDefaultTest{
	
	@Test
	public void consumirMensagem() {
		this.executarAcaoAposCriarSessao( sessao -> {
			try {
				Destination fila = (Destination) contexto.lookup(FILA_NOME);// <--- nome da fila no arquivo  de configuração(main/resoureces/jndi.properties)
				MessageConsumer consumidorDeMensagem;
				consumidorDeMensagem = sessao.createConsumer(fila);
				// Recebemos então a mensagem da fila selecionada acima através do contexto.
				Message messagem = consumidorDeMensagem.receive();
				// Por fim, imprimimos a mensagem recebida
				System.out.println("Recebendo msg: " + messagem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
