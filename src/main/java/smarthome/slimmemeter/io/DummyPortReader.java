package smarthome.slimmemeter.io;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import smarthome.slimmemeter.model.Telegram;

//@Service
public class DummyPortReader implements PortReaderService {
	
	private Telegram telele;

	@PostConstruct
	private void ini() {
		telele = new Telegram();
		telele.setVermogen(0.0);
	}
	
	@Override
	public Telegram getTelegram() {
		telele.setVermogen(telele.getVermogen() + 100.0);
		return telele;
	}

}
