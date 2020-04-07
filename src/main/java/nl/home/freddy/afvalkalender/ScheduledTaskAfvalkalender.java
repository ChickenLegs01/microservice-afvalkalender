package nl.home.freddy.afvalkalender;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTaskAfvalkalender {

	private final static Logger LOG = LoggerFactory.getLogger(ScheduledTaskAfvalkalender.class);

	@Autowired
    private ServiceAfvalkalender service;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    LOG.info("hello world, I have just started up");
	    service.getOphaalData();
	    LOG.info("Ready to rock!! ");
	}

	// should run every sat at 23
	@Scheduled(cron="0 0 23 * * SAT")
    public void refreshOphaalData() {
        LOG.info("refresh ophaaldata via schedule @ {}", new Date());
        service.getOphaalData();
    }


}
