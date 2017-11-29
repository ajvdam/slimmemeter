package smarthome.slimmemeter.io;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import smarthome.slimmemeter.model.Telegram;

//@Service
public class DummyPortReader implements PortReaderService {
	
	private Telegram telele;

	@PostConstruct
	private void init() {
		telele = new Telegram();
		telele.setVermogen(1.0);
		telele.setVerbruikTarief1(3456.0);
		telele.setVerbruikTarief2(8564.0);
	}
	
	@Override
	public Telegram getTelegram() {
		telele.setVermogen(telele.getVermogen() + 0.123);
		telele.setVerbruikTarief1(telele.getVerbruikTarief1() + 1.0);
		telele.setVerbruikTarief2(telele.getVerbruikTarief2() + 1.0);		
		return telele;
	}

}
