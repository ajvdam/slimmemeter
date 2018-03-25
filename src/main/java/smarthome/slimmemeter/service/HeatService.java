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
	private static final double SPECIFIC_HEAT = 4.18; // kJ/(kg*K)
	private static final double TRESHOLD_TEMP_HEATING_WATER_ON = 55; // K
	private static final double TRESHOLD_TEMP_HOT_WATER_ON = 40; // K
	private static final double TRESHOLD_DELTA_TEMP_EXCHANGER_ON = 35; // K

	private double deltaT;

	@Inject
	private HeatRepository heatRepo;

	// in kW.
	public double getHeatingPower() {
		return isHeatingOn() ? FLOW_HEATING * SPECIFIC_HEAT * deltaT : 0;
	}

	// in kW.
	public double getHotWaterPower() {
		return isHotWaterOn() ? FLOW_HOT_WATER * SPECIFIC_HEAT * deltaT : 0;
	}

	private boolean isExchangerOn() {
		return getDeltaT() > TRESHOLD_DELTA_TEMP_EXCHANGER_ON;
	}


	private boolean isHeatingOn() {
		return isExchangerOn()
				&& heatRepo.getTempHeatingIn() > TRESHOLD_TEMP_HEATING_WATER_ON;
	}
	
	private boolean isHotWaterOn() {
		return isExchangerOn()
				&& heatRepo.getTempHotWaterIn() > TRESHOLD_TEMP_HOT_WATER_ON;
	}

	private double getDeltaT() {
		deltaT = heatRepo.getTempHeatExchangeIn() - heatRepo.getTempHeatExchangeOut();
		return deltaT;
	}
}
