package smarthome.slimmemeter.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Setter
@Log4j2
public class HeatRepository {

	private static final String DEVICE_ID_HEAT_EXCHANGE_IN = "28-0316a2e999ff";
	private static final String DEVICE_ID_HEAT_EXCHANGE_OUT = "28-0316a2e339ff";
	private static final String DEVICE_ID_HOT_WATER_IN = "28-0316a2e2adff";
	private static final String DEVICE_ID_HEATING_IN = "28-0416a26655ff";

	private double getTempValue(String id, String logString) {
		double temp = 0;
		W1Master master = new W1Master();
		List<W1Device> tempDevices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
		for (W1Device device : tempDevices) {
			log.error("Temp {} is {}.", device.getId(), ((TemperatureSensor) device).getTemperature());
			if (id.equals(device.getId())) {
				temp = ((TemperatureSensor) device).getTemperature();
			}
		}
		log.error(logString, temp);
		return temp;
	}

	public double getTempHeatExchangeIn() {
		return getTempValue(DEVICE_ID_HEAT_EXCHANGE_IN, "T_ExchangeIn = {} degree Celsius.");
	}

	public double getTempHeatExchangeOut() {
		return getTempValue(DEVICE_ID_HEAT_EXCHANGE_OUT, "T_ExchangeOut = {} degree Celsius.");
	}

	public double getTempHotWaterIn() {
		return getTempValue(DEVICE_ID_HOT_WATER_IN, "T_HotWater = {} degree Celsius.");
	}

	public double getTempHeatingIn() {
		return getTempValue(DEVICE_ID_HEATING_IN, "T_HeatingIn = {} degree Celsius.");
	}

}
