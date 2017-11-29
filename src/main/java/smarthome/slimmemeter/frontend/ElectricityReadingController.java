package smarthome.slimmemeter.frontend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.MeterGaugeChartModel;

import lombok.Getter;
import smarthome.slimmemeter.model.Telegram;
import smarthome.slimmemeter.service.ElectricityReadingService;

@Named
@ViewScoped
public class ElectricityReadingController implements Serializable{

	private static final long serialVersionUID = 5992372568060110227L;
	private static final int MAX_WAARDE_KM_TELLER = 5;
	@Inject
	private ElectricityReadingService slimmeMeterService;
	@Getter
	private MeterGaugeChartModel meterGaugeModel;
	private Telegram telegram;
	private Integer watt;
	private Double kW;
	private Double counter1;
	private Double counter2;

	public void updateValues() {
		telegram = slimmeMeterService.getTelegram();
		kW = telegram.getVermogen();
		counter1 = telegram.getVerbruikTarief1();
		counter2 = telegram.getVerbruikTarief2();		
		watt = new Double(kW * 1000.0).intValue();
		meterGaugeModel.setValue(kW > MAX_WAARDE_KM_TELLER ? MAX_WAARDE_KM_TELLER : kW);
	}
	
    @PostConstruct
    private void init() {
        createMeterGaugeModel();
    	updateValues();
    }
 
    private MeterGaugeChartModel initTresholdsMeterGaugeModel() {
        List<Number> intervals = new ArrayList<Number>(){
			private static final long serialVersionUID = 1L;
		{
            add(1);
            add(2);
            add(3);
            add(MAX_WAARDE_KM_TELLER);
        }};
         
        return new MeterGaugeChartModel(0, intervals);
    }
 
    private void createMeterGaugeModel() {
        meterGaugeModel = initTresholdsMeterGaugeModel();
        meterGaugeModel.setLabelHeightAdjust(20);
        meterGaugeModel.setGaugeLabel("kW");        
        meterGaugeModel.setSeriesColors("66cc66,eef442,ff6600,ff0000");
    }

	public String getPower() {
		return watt + " W";
	}

	public String getCounter1() {
		return counter1 + " kWh";
	}

	public String getCounter2() {
		return counter2 + " kWh";
	}
	
}
