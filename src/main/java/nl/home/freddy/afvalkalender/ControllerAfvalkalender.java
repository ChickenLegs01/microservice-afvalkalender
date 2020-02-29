package nl.home.freddy.afvalkalender;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ControllerAfvalkalender {

	private final static Logger LOG = LoggerFactory.getLogger(ControllerAfvalkalender.class);

	@Autowired
    private ServiceAfvalkalender service;

	@RequestMapping(method=RequestMethod.GET, value="/hello")
	public String getHello() {
		return "Hola!!";
	}

	@RequestMapping(method=RequestMethod.GET, value="/addressdata")
	public ResponseEntity<JSONObject> getAddressData() {
		return ResponseEntity.ok(service.getAddressData());
	}

	@RequestMapping(method=RequestMethod.GET, value="/ophaaldata")
	public ResponseEntity<List<ModelAfvalkalender>> getOphaalData() {
		return ResponseEntity.ok(service.getOphaalData());
	}

	@RequestMapping(method=RequestMethod.GET, value="/lastophaaldata")
	public ResponseEntity<List<ModelAfvalkalender>> getLastOphaalData() {
		return ResponseEntity.ok(service.getLastOphaalData());
	}

	@RequestMapping(method=RequestMethod.GET, value="/whichafval")
	public String getAfval() {
		return service.getAfval();
	}

	// ------------------------------------------------------------------------
	// endpoints for testing
	// ------------------------------------------------------------------------

	String testAfalId;

	// this should be a post...
	@RequestMapping(method=RequestMethod.GET, value="/testsetafval/{afalId}")
	public String testSetAfval(@PathVariable String afalId) {
		this.testAfalId = afalId;
		return afalId;
	}

	@RequestMapping(method=RequestMethod.GET, value="/testwhichafval")
	public String testGetAfval() {
		if(testAfalId==null) return "0";
		return testAfalId;
	}

	// ------------------------------------------------------------------------
	// endpoints for testing
	// ------------------------------------------------------------------------

}
