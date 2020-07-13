package vn.hbm.core.service;

import lombok.extern.slf4j.Slf4j;
//import org.joda.time.DateTime;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
//import vn.hbm.core.common.Common;
import vn.hbm.core.thread.ThreadManager;
//import vn.hbm.thread.CrawlXosoProcess;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class InitService {
    @Resource
    ThreadManager threadManager;

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        if (threadManager != null) {
            threadManager.setEnvLoaded(true);
        }
    }

    @PostConstruct
    public void init(){
        log.info("BEGIN::init");
        try {
//            String strFromDate = "20200301";
//            String strToDate = "20200424";
//            Date dtFromDate = Common.strToDate(strFromDate, Common.DATE_YYYYMMDD);
//            Date dtToDate = Common.strToDate(strToDate, Common.DATE_YYYYMMDD);
//            DateTime jdFromDate = new DateTime(dtFromDate.getTime());
//
//            long diffInMillies = Math.abs(dtToDate.getTime() - dtFromDate.getTime());
//            long numDate = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//            CrawlXosoProcess clazz = new CrawlXosoProcess();
//            for (int i=0; i <= numDate; i++) {
//                DateTime jdDateRun = jdFromDate.plusDays(i);
//                clazz.crawlOnlineXSMN(Common.dateToString(jdDateRun.toDate(), Common.DATE_YYYYMMDD));
//                clazz.crawlOnlineXSMT(Common.dateToString(jdDateRun.toDate(), Common.DATE_YYYYMMDD));
//                clazz.crawlOnlineXSMB(Common.dateToString(jdDateRun.toDate(), Common.DATE_YYYYMMDD));
//            }
        } catch (Exception ex) {
            log.error("", ex);
        } finally {
            log.info("END::init");
        }
    }
}
