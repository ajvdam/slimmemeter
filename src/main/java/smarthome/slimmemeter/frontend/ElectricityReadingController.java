package smarthome.slimmemeter.frontend;

import javax.annotation.ManagedBean;
import javax.faces.view.ViewScoped;

import smarthome.slimmemeter.service.ElectricityReadingService;

@ManagedBean
@ViewScoped
public class ElectricityReadingController{

	private ElectricityReadingService slimmeMeterService;
	
	public double getPower() {
		return slimmeMeterService.getTelegram().getVermogen();
	}
	
}
