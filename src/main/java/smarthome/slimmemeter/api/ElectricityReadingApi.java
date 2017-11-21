package smarthome.slimmemeter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smarthome.slimmemeter.model.Telegram;
import smarthome.slimmemeter.service.ElectricityReadingService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/electricity")
public class ElectricityReadingApi{

	private ElectricityReadingService slimmeMeterService;
	
	@GetMapping
	public Telegram get() {
		return slimmeMeterService.getTelegram();
	}

}
