package nl.home.freddy.afvalkalender;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceAfvalkalender {

	private final static Logger LOG = LoggerFactory.getLogger(ServiceAfvalkalender.class);

	// load variables from the application.properties
	@Value("${afvalkalender.ophaaldata.url}")
	private String ophaaldataUrl;

	@Value("${afvalkalender.adressdata.url}")
	private String addressdataUrl;

	RestTemplate restTemplate = new RestTemplate();

	Date dateOfLastGet;
	List<ModelAfvalkalender> ophaalList;
	ObjectMapper mapper = new ObjectMapper();

	public JSONObject getAddressData() {
		LOG.info("START getAddressData()");
		String jsonStr = restTemplate.getForObject(addressdataUrl, String.class);
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonAr = (JSONArray)parser.parse(jsonStr);
			JSONObject addData = (JSONObject) jsonAr.get(0);
			return addData;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}

	public List<ModelAfvalkalender> getOphaalData() {
		LOG.info("START getOphaalData()");
		String jsonStr = restTemplate.getForObject(ophaaldataUrl, String.class);

		dateOfLastGet = new Date();
		try {
			ophaalList = Arrays.asList(mapper.readValue(jsonStr, ModelAfvalkalender[].class));
			LOG.info("Number of items in ophaaldata {} ", ophaalList.size());
			return ophaalList;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<ModelAfvalkalender>();
	}

	public List<ModelAfvalkalender> getLastOphaalData() {
		return ophaalList;
	}

	// 0=none
	// 5=Glas papier BEST tas
	// 11=PMD (big bin)
	// 55=GFT (green bin)
	// -1=error
	public String getAfval() {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());

		// dont need this any more
		//if(!isTimeOkForReturn(cal)) return "0";

		for(ModelAfvalkalender mod: ophaalList) {
			long milli = mod.getOphaaldatum().getTime() - cal.getTimeInMillis();
			long hours = milli/1000/60/60;

			if(hours >0 && hours < 15  ) {
				LOG.info("Found date match " + mod.getOphaaldatum() + " for afval: " + mod.getAfvalstroomId());
				return ""+mod.getAfvalstroomId();
			}
		}
		return "0";
	}

	/*
[{
	"afvalstroom_id": 5,
	"ophaaldatum": "2019-12-31"
}, {
	"afvalstroom_id": 55,
	"ophaaldatum": "2020-01-02"
}, {
	"afvalstroom_id": 11,
	"ophaaldatum": "2020-01-09"
}]
	 */

	public boolean isTimeOkForReturn(GregorianCalendar cal) {
		int currentHour = cal.get(Calendar.HOUR_OF_DAY);
		if(currentHour>=13 && currentHour<=23) return true;
		if(currentHour>=0 && currentHour<=7) return true;

		return false;
	}
}
