package br.com.coppieters.examples.queue;

import org.junit.Test;

import br.com.coppieters.examples.MessageriaDefaultTest;
import br.com.coppieters.examples.utils.DefaultProducer;

public class MessageriaProducerForQueueTest extends MessageriaDefaultTest {

	@Test
	public void testaProducer() {
		this.executarAcaoAposCriarSessao( new DefaultProducer("<pedido><id>%d</id></pedido>", this.contexto, FILA_NOME));
	}

}
