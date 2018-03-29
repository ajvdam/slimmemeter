package smarthome.slimmemeter.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Service
@Getter
@Log4j2
public class HeatRepository {

	private static final String DEVICE_ID_HEAT_EXCHANGE_IN = "28-0316a2e999ff";
	private static final String DEVICE_ID_HEAT_EXCHANGE_OUT = "28-0316a2e339ff";
	private static final String DEVICE_ID_HOT_WATER_IN = "28-0316a2e2adff";
	private static final String DEVICE_ID_HEATING_IN = "28-0416a26655ff";

	private double tempHeatExchangeIn = 0.0;
	private double tempHeatExchangeOut = 0.0;
	private double tempHotWater = 0.0;
	private double tempHeatingIn = 0.0;

	public void readValues() {
		W1Master master = new W1Master();
		List<W1Device> tempDevices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
		for (W1Device device : tempDevices) {
			switch (device.getId()) {
			case DEVICE_ID_HEAT_EXCHANGE_IN:
				tempHeatExchangeIn = ((TemperatureSensor) device).getTemperature();
				log.error("T_ExchangeIn = {} degree Celsius.", tempHeatExchangeIn);
				break;
			case DEVICE_ID_HEAT_EXCHANGE_OUT:
				tempHeatExchangeOut = ((TemperatureSensor) device).getTemperature();
				log.error("T_ExchangeOut = {} degree Celsius.", tempHotWater);
				break;
			case DEVICE_ID_HOT_WATER_IN:
				tempHotWater = ((TemperatureSensor) device).getTemperature();
				log.error("T_HotWater = {} degree Celsius.", tempHeatExchangeOut);				
				break;
			case DEVICE_ID_HEATING_IN:
				tempHeatingIn = ((TemperatureSensor) device).getTemperature();
				log.error("T_HeatingIn = {} degree Celsius.", tempHeatingIn);				
			}
		}
	}

}
