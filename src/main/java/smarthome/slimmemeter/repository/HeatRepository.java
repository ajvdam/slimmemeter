package smarthome.slimmemeter.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import lombok.Setter;

@Service
@Setter
public class HeatRepository {

	private static final String DEVICE_ID_HEAT_EXCHANGE_IN = "28-0316a2e999ff";
	private static final String DEVICE_ID_HEAT_EXCHANGE_OUT = "28-0316a2e339ff";
	private static final String DEVICE_ID_HOT_WATER_IN = "28-0316a2e2adff";
	private static final String DEVICE_ID_HEATING_IN = "28-0416a26655ff";
	
	private List<W1Device> tempDevices;
	
	@PostConstruct
	private void init() {
		W1Master master = new W1Master();
		tempDevices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
	}

	public double getTempHeatExchangeIn() {
		for (W1Device device : tempDevices) {
			if (DEVICE_ID_HEAT_EXCHANGE_IN.equals(device.getId())) {
				return ((TemperatureSensor) device).getTemperature();
			}
		}
		return 0;
	}

	public double getTempHeatExchangeOut() {
		for (W1Device device : tempDevices) {
			if (DEVICE_ID_HEAT_EXCHANGE_OUT.equals(device.getId())) {
				return ((TemperatureSensor) device).getTemperature();
			}
		}
		return 0;
	}

	public double getTempHotWaterIn() {
		for (W1Device device : tempDevices) {
			if (DEVICE_ID_HOT_WATER_IN.equals(device.getId())) {
				return ((TemperatureSensor) device).getTemperature();
			}
		}
		return 0;
	}

	public double getTempHeatingIn() {
		for (W1Device device : tempDevices) {
			if (DEVICE_ID_HEATING_IN.equals(device.getId())) {
				return ((TemperatureSensor) device).getTemperature();
			}
		}
		return 0;
	}
	
}
