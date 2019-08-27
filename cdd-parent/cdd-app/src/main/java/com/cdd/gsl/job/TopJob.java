package com.cdd.gsl.job;

import com.cdd.gsl.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Lazy(false)
@EnableScheduling
@Component
public class TopJob {
    private final static Logger logger = LoggerFactory.getLogger(TopJob.class);

    @Autowired
    private HouseService houseService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void expireTopHouse(){
        houseService.delayTopHouse();
    }

}
