package smarthome.slimmemeter.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smarthome.slimmemeter.io.PortReaderService;
import smarthome.slimmemeter.model.Telegram;

@Service
@AllArgsConstructor
public class ElectricityReadingService {

	private PortReaderService slimmeMeterReader;
	
	public Telegram getTelegram() {
		return slimmeMeterReader.getTelegram();
	}

}
