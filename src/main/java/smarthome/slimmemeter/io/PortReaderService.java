package smarthome.slimmemeter.io;

import smarthome.slimmemeter.model.Telegram;

public interface PortReaderService {
	Telegram getTelegram();
}
