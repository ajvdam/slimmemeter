package smarthome.slimmemeter.frontend;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.MeterGaugeChartModel;

import lombok.Getter;
import smarthome.slimmemeter.service.ElectricityReadingService;

@Named
@ViewScoped
public class ElectricityReadingController{

	private static final int MAX_WAARDE_KM_TELLER = 3000;
	@Inject
	private ElectricityReadingService slimmeMeterService;
	@Getter
	private MeterGaugeChartModel meterGaugeModel;

	public Double getPower() {
		Double power = getReading();
		Double maxTeller = power;
		if (maxTeller > MAX_WAARDE_KM_TELLER) {
			maxTeller = Double.valueOf(MAX_WAARDE_KM_TELLER);
		}
		meterGaugeModel.setValue(maxTeller);
		return power;
	}
	
    @PostConstruct
    private void init() {
        createMeterGaugeModel();
    }
 
    private MeterGaugeChartModel initTresholdsMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){
			private static final long serialVersionUID = 1L;
		{
            add(1000);
            add(2000);
            add(MAX_WAARDE_KM_TELLER);
        }};
         
        return new MeterGaugeChartModel(0, intervals);
    }
 
    private void createMeterGaugeModel() {
        meterGaugeModel = initTresholdsMeterGaugeModel();
        meterGaugeModel.setTitle("Actuele Vermogen in [Watt]");
        meterGaugeModel.setGaugeLabel("Watt");
        meterGaugeModel.setSeriesColors("66cc66,ff6600,ff0000");
    }
    
	private double getReading() {
		return slimmeMeterService.getTelegram().getVermogen() * 1000;
	}
	
}
