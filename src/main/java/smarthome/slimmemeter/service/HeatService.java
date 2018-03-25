package smarthome.slimmemeter.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import smarthome.slimmemeter.repository.HeatRepository;

@Service
public class HeatService {

	/*
 	* Q = m * c * deltaT
 	* 
 	* kg/s * kJ/(kg*K) * K = kJ/s = kW.
	 */
	
	private static final double FLOW_HOT_WATER = 0.222; // = 800 l/h
	private static final double FLOW_HEATING = 0.083; // = 300 l/h
	private static final double SPECIFIC_HEAT = 4.18 ; // kJ/(kg*K)
	private static final double TRESHOLD_DELTA_TEMP_HEATING_WATER_ON = 30 ; // K
	private static final double TRESHOLD_DELTA_TEMP_HOT_WATER_ON = 30 ; // K
	
	@Inject
	private HeatRepository heatRepo;

	// in kW.
	public double getHeatingPower() {
		double deltaTempHeatExchanger = heatRepo.getTempHeatExchangeIn() - heatRepo.getTempHeatExchangeOut();
		return isHeatingOn() ? FLOW_HEATING * SPECIFIC_HEAT * deltaTempHeatExchanger : 0;		
	}


	// in kW.
	public double getHotWaterPower() {
		double deltaTempHeatExchanger = heatRepo.getTempHeatExchangeIn() - heatRepo.getTempHeatExchangeOut();
		return isHotWaterOn() ? FLOW_HOT_WATER * SPECIFIC_HEAT * deltaTempHeatExchanger : 0;
	}
	
	public boolean isHeatingOn() {
		return heatRepo.getTempHeatingIn() > TRESHOLD_DELTA_TEMP_HEATING_WATER_ON;		
	}

	public boolean isHotWaterOn() {
		return heatRepo.getTempHotWaterIn() > TRESHOLD_DELTA_TEMP_HOT_WATER_ON;
	}
	
}
