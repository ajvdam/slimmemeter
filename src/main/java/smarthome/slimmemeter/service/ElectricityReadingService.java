package smarthome.slimmemeter.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import smarthome.slimmemeter.io.SerialPortReader;
import smarthome.slimmemeter.model.Telegram;

@Service
@AllArgsConstructor
public class ElectricityReadingService {

	private SerialPortReader slimmeMeterReader;
	
	public Telegram getTelegram() {
		return slimmeMeterReader.getTelegram();
	}

}
