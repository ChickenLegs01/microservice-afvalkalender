package nl.home.freddy.afvalkalender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class AfvalkalenderInfoContributor implements InfoContributor {

	@Autowired
    private ServiceAfvalkalender service;

	@Override
	public void contribute(Builder builder) {

	//	Map<String, Integer> details = new HashMap<>();

		builder.withDetail("yayay", "Donkey");
		builder.withDetail("NumberOfOphaal", service.getLastOphaalData().size());
		builder.withDetail("DateOfLastGet", service.dateOfLastGet);

		int counter=0;
		for(ModelAfvalkalender mod: service.getLastOphaalData()) {
			builder.withDetail("ophaaldata"+ counter++, mod.getAfvalstroomId() +"-"+ mod.getOphaaldatum());
		}

		Map<String, String> details = new HashMap<>();
		details.put("5", "Glas papier BEST tas");
		details.put("11", "PMD (big bin)");
		details.put("55", "GFT (green bin)");

		builder.withDetail("Ophaal", details);

	}

}
