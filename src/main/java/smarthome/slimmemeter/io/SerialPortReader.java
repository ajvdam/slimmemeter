package smarthome.slimmemeter.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import purejavacomm.CommPortIdentifier;
import purejavacomm.NoSuchPortException;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.UnsupportedCommOperationException;
import smarthome.slimmemeter.model.Telegram;

@Slf4j
@Service
public class SerialPortReader implements PortReaderService {

	@Value("${smarthome.slimmemeter.telegram.datetime.line.format}")
	private String dateTimeLineFormat;
	@Value("${smarthome.slimmemeter.telegram.datetime.format}")
	private String dateTimeFormat;
	@Value("${smarthome.slimmemeter.telegram.datetime.dateformatter.format}")
	private String dateTimeDateFormattterFormat;
	@Value("${smarthome.slimmemeter.telegram.electricity.received.tariff1.power.line.format}")
	private String electricityTariff1PowerFormat;
	@Value("${smarthome.slimmemeter.telegram.electricity.received.tariff2.power.line.format}")
	private String electricityTariff2PowerFormat;
	@Value("${smarthome.slimmemeter.telegram.electricity.received.power.format}")
	private String electricityPowerFormat;
	@Value("${smarthome.slimmemeter.telegram.electricity.received.tariff1.counter.line.format}")
	private String electricityTariff1CounterFormat;
	@Value("${smarthome.slimmemeter.telegram.electricity.received.tariff2.counter.line.format}")
	private String electricityTariff2CounterFormat;
	@Value("${smarthome.slimmemeter.telegram.electricity.received.counter.format}")
	private String electricityCounterFormat;
	@Value("${smarthome.slimmemeter.telegram.end.line.format}")
	private String endLineFormat;
	@Value("${smarthome.slimmemeter.minimum.storing.delay}")
	private String minimumDelay;
	@Value("${smarthome.slimmemeter.serial.port}")
	private String serialPort;
	@Value("${smarthome.slimmemeter.serial.port.baudRate}")
	private String baudRate;
	@Value("${smarthome.slimmemeter.serial.port.dataBits}")
	private String dataBits;
	@Value("${smarthome.slimmemeter.serial.port.stopBits}")
	private String stopBits;
	@Value("${smarthome.slimmemeter.serial.port.parity}")
	private String parity;
	private Pattern electricityPowerPattern;
	private Pattern electricityCounterPattern;
	private Pattern dateTimePattern;
	private DateTimeFormatter dateTimeFormatter;
	private BufferedReader portReader;
	private boolean keepReading;
	private Telegram telegram;
	private SerialPort port;

	@PostConstruct
	private void initSerialPortReader() {
		log.debug("Initialize serial port reader");
		initPatterns();
		initSerialPortReading();
	}

	@PreDestroy
	private void stopReading() {
		log.debug("Stop serial port reader");
		keepReading = false;
		try {
			portReader.close();
			port.close();
		} catch (IOException e) {
			log.error("Unable to close serial port");
		}
	}

	private void initSerialPortReading() {
		log.debug("Initialize reading from port {}", serialPort);
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(serialPort);
			port = (SerialPort) portId.open("smarthome.slimmemeter", 1000);
			port.setSerialPortParams(Integer.parseInt(baudRate), Integer.parseInt(dataBits), Integer.parseInt(stopBits),
					Integer.parseInt(parity));
			InputStream inStream = port.getInputStream();
			portReader = new BufferedReader(new InputStreamReader(inStream));
			telegram = new Telegram();
			startReading();
		} catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException | IOException e) {
			log.error("Not able to open serial port for reading {}", serialPort, e);
		}
	}

	private void startReading() {
		keepReading = true;
		new Thread() {
			@Override
			public void run() {
				log.info("Started reading from serial port");
				while (keepReading) {
					try {
						String line = portReader.readLine();
						log.trace(line);
						parseReadLine(line);
					} catch (IOException e) {
						log.error("Error reading line from serial port", e);
					}
				}
			}
		}.start();
	}

	private void initPatterns() {
		dateTimePattern = Pattern.compile(dateTimeFormat);
		dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeDateFormattterFormat);
		electricityPowerPattern = Pattern.compile(electricityPowerFormat);
		electricityCounterPattern = Pattern.compile(electricityCounterFormat);
	}

	private void parseReadLine(String line) {
		try {
			if (isDateTimeLine(line)) {
				Matcher dateTimeMatcher = dateTimePattern.matcher(line);
				if (dateTimeMatcher.find()) {
					LocalDateTime dateTime = LocalDateTime.parse(dateTimeMatcher.group(), dateTimeFormatter);
					telegram.setDateTime(dateTime);
				}
			}
			if (isElectricityReadingTariff1CounterLine(line) || isElectricityReadingTariff2CounterLine(line)) {
				Matcher electricityMatcher = electricityCounterPattern.matcher(line);
				if (electricityMatcher.find()) {
					double electricityReading = Double.parseDouble(electricityMatcher.group());
					if (isElectricityReadingTariff1CounterLine(line)) {
						telegram.setVerbruikTarief1(electricityReading);
					}
					if (isElectricityReadingTariff2CounterLine(line)) {
						telegram.setVerbruikTarief2(electricityReading);
					}
				}
			}
			if (isElectricityReadingTariff1PowerLine(line) || isElectricityReadingTariff2PowerLine(line)) {
				Matcher electricityMatcher = electricityPowerPattern.matcher(line);
				if (electricityMatcher.find()) {
					int electricityReading = Integer.valueOf(electricityMatcher.group());
					if (electricityReading > 0) {
						telegram.setVermogen(electricityReading);
					}
				}
			}
		} catch (RuntimeException e) {
			log.error("Unable to parse line correctly: " + line, e);
		}
	}

	private boolean isElectricityReadingTariff1PowerLine(String line) {
		return line.matches(electricityTariff1PowerFormat);
	}

	private boolean isElectricityReadingTariff2PowerLine(String line) {
		return line.matches(electricityTariff2PowerFormat);
	}

	private boolean isElectricityReadingTariff1CounterLine(String line) {
		return line.matches(electricityTariff1CounterFormat);
	}

	private boolean isElectricityReadingTariff2CounterLine(String line) {
		return line.matches(electricityTariff2CounterFormat);
	}

	private boolean isDateTimeLine(String line) {
		return line.matches(dateTimeLineFormat);
	}

	private boolean isEndLine(String line) {
		return endLineFormat.equals(line);
	}

	@Override
	public Telegram getTelegram() {
		return telegram;
	}
}
