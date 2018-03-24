package smarthome.slimmemeter.repository;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.springframework.stereotype.Service;

import lombok.Setter;

@Singleton
@Service
@Setter
public class HeatRepository {
	
	// all in degrees Celsius
	private double tempHeatExchangeIn;
	private double tempHeatExchangeOut;
	private double tempHotWater;
	private double tempHeatingWater;	
	
	@PostConstruct
	private void init() {
		setTempHeatExchangeIn(60);
		setTempHeatExchangeOut(30);
		setTempHotWater(40);
		setTempHeatingWater(55);
	}

	public double getTempHeatExchangeIn() {
		return tempHeatExchangeIn;
	}

	public double getTempHeatExchangeOut() {
		return tempHeatExchangeOut+1;
	}

	public double getTempHotWater() {
		return tempHotWater-1;
	}

	public double getTempHeatingWater() {
		return tempHeatingWater+1;
	}
	
}
