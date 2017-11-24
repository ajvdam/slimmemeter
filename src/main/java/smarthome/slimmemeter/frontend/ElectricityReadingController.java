package smarthome.slimmemeter.frontend;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import smarthome.slimmemeter.service.ElectricityReadingService;

@Named
@ViewScoped
public class ElectricityReadingController{

	@Inject
	private ElectricityReadingService slimmeMeterService;
	
	public double getPower() {
		return slimmeMeterService.getTelegram().getVermogen();
	}
	
}
