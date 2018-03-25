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
import smarthome.slimmemeter.service.HeatService;

@Named
@ViewScoped
public class HeatController implements Serializable{

	private static final long serialVersionUID = 6307292393448463495L;
	private static final int MAX_VALUE_HEATING_METER = 20; //kW
	private static final int MAX_VALUE_HOTWATER_METER = 30;	//kW
	@Inject
	private HeatService heatService;
	@Getter
	private MeterGaugeChartModel heatingMeter;
	private double heatingPower;
	@Getter
	private MeterGaugeChartModel hotWaterMeter;
	private double hotWaterPower;

	public void updateHeatingValues() {
		heatingPower = heatService.getHeatingPower();
		heatingMeter.setValue(heatingPower > MAX_VALUE_HEATING_METER 
				? MAX_VALUE_HEATING_METER : heatingPower);
	}

	public void updateHotWaterValues() {
		hotWaterPower = heatService.getHotWaterPower();
		hotWaterMeter.setValue(hotWaterPower > MAX_VALUE_HOTWATER_METER 
				? MAX_VALUE_HOTWATER_METER : hotWaterPower);
	}
	
    @PostConstruct
    private void init() {
        createHeatingMeter();
        createHotWaterMeter();
        updateHeatingValues();
        updateHotWaterValues();
    }
 
    private MeterGaugeChartModel initHeatingMeter() {
        List<Number> intervals = new ArrayList<Number>(){
			private static final long serialVersionUID = 1L;
		{
            add(5);
            add(10);
            add(15);
            add(MAX_VALUE_HEATING_METER);
        }};
         
        return new MeterGaugeChartModel(0, intervals);
    }
 
    private void createHeatingMeter() {
        heatingMeter = initHeatingMeter();
        heatingMeter.setLabelHeightAdjust(20);
        heatingMeter.setGaugeLabel("kW");        
        heatingMeter.setSeriesColors("66cc66,eef442,ff6600,ff0000");
    }

	public String getHeatingPower() {
		return heatingPower + " kW";		
	}

    private MeterGaugeChartModel initHotWaterMeter() {
        List<Number> intervals = new ArrayList<Number>(){
			private static final long serialVersionUID = 1L;
		{
            add(10);
            add(20);
            add(MAX_VALUE_HOTWATER_METER);
        }};
         
        return new MeterGaugeChartModel(0, intervals);
    }
 
    private void createHotWaterMeter() {
    	hotWaterMeter = initHotWaterMeter();
    	hotWaterMeter.setLabelHeightAdjust(20);
    	hotWaterMeter.setGaugeLabel("kW");        
    	hotWaterMeter.setSeriesColors("66cc66,eef442,ff0000");
    }

	public String getHotWaterPower() {
		return hotWaterPower + " kW";
	}

	
}
