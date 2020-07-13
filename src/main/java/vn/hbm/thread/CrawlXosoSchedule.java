package vn.hbm.thread;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.hbm.core.common.*;
import vn.hbm.core.service.CommonService;
import vn.hbm.jpa.XsplDailyResult;

import java.sql.Timestamp;
import java.util.List;

//@Component
@Slf4j
public class CrawlXosoSchedule implements IGsonBase {
    CommonService commonService = (CommonService) AppContext.getBean("commonService");


    //second, minute, hour, day of month, month, day(s) of week
    //Run crawl data every 30 second in 18h
    @Scheduled(cron = "*/30 15-50 18 * * ?")
    public void crawlOnlineXSMB(){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        log.info(transId + "::BEGIN::crawlOnlineXSMB");
        String REGION_CODE = "MB";
        String PROVINCE_CODE = "ALL";
        String DATE_RESULT = "";
        try {
            DateTime jdCurrentDate = new DateTime();
            DATE_RESULT = Common.dateToString(jdCurrentDate.toDate(), Common.KQ_NET_DDMMYYYY);
            String url = "http://ketqua.net/xo-so-mien-bac?ngay=" + DATE_RESULT;


            Document document = Jsoup.connect(url).get();
            Element element = document.select("#result_tab_mb").first();

            if(element != null && element.hasText()) {
                Elements arrTagResult = element.select("td[id*=rs]");
                String resultCode = "";
                if (arrTagResult != null && arrTagResult.size() > 0) {

                    for (int i = 0; i < arrTagResult.size(); i++) {
                        Element tdElement = arrTagResult.get(i);
                        if (tdElement != null) {
                            String resultLevel = StringUtils.nvl(tdElement.attr("id"), "").replace("rs_", "");
                            String resultValue = StringUtils.nvl(tdElement.text().trim(), "");
                            resultCode = REGION_CODE.concat("_").concat(PROVINCE_CODE).concat("_").concat(Common.dateToString(jdCurrentDate.toDate(), Common.DATE_YYYYMMDD));

                            XsplDailyResult beanResult = new XsplDailyResult();
                            beanResult.setRegionCode(REGION_CODE);
                            beanResult.setProviceCode(Common.upper(PROVINCE_CODE));
                            beanResult.setResultCode(Common.upper(resultCode));
                            beanResult.setResultValue(resultValue);
                            beanResult.setResultLevel(resultLevel);
                            beanResult.setStatus(Constants.G_STATUS_ACTIVE);
                            beanResult.setCreatedTime(new Timestamp(jdCurrentDate.toDate().getTime()));
                            beanResult.setResultDate(jdCurrentDate.toDate());
                            logXsplDailyResult(transId, beanResult);
                        }
                    }
                }
            } else {
                log.warn(transId + "::NO DATA LOAD");
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        } finally {
            log.info(transId + "::END::crawlOnlineXSMB");
        }
    }

    //second, minute, hour, day of month, month, day(s) of week
    //Run crawl data every 30 second in 17h
    @Scheduled(cron = "*/30 15-50 17 * * ?")
    public void crawlOnlineXSMT(){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        log.info(transId + "::BEGIN::crawlOnlineXSMT");
        String REGION_CODE = "MT";
        String PROVINCE_CODE = "";
        String DATE_RESULT = "";
        try {
            DateTime jdCurrentDate = new DateTime();
            DATE_RESULT = Common.dateToString(jdCurrentDate.toDate(), Common.KQ_NET_DDMMYYYY);
            String url = "http://ketqua.net/xo-so-mien-trung?ngay=" + DATE_RESULT;


            Document document = Jsoup.connect(url).get();
            Element element = document.select("#region_table").first();

            if(element != null && element.hasText()) {
                Elements arrTagResult = element.select("td[id*=rs]");
                String resultCode = "";
                if (arrTagResult != null && arrTagResult.size() > 0) {
                    for (int i = 0; i < arrTagResult.size(); i++) {
                        Element tdElement = arrTagResult.get(i);
                        if (tdElement != null) {
                            String provinceData = StringUtils.nvl(tdElement.attr("id"), "").replace("rs_", "");
                            PROVINCE_CODE = provinceData.substring(0, provinceData.indexOf("_"));
                            String resultLevel = provinceData.replace(PROVINCE_CODE.concat("_"), "");
                            String resultValue = StringUtils.nvl(tdElement.text().trim(), "");
                            resultCode = REGION_CODE.concat("_").concat(PROVINCE_CODE).concat("_").concat(Common.dateToString(jdCurrentDate.toDate(), Common.DATE_YYYYMMDD));

                            XsplDailyResult beanResult = new XsplDailyResult();
                            beanResult.setRegionCode(REGION_CODE);
                            beanResult.setProviceCode(Common.upper(PROVINCE_CODE));
                            beanResult.setResultCode(Common.upper(resultCode));
                            beanResult.setResultValue(resultValue);
                            beanResult.setResultLevel(resultLevel);
                            beanResult.setStatus(Constants.G_STATUS_ACTIVE);
                            beanResult.setCreatedTime(new Timestamp(jdCurrentDate.toDate().getTime()));
                            beanResult.setResultDate(jdCurrentDate.toDate());
                            logXsplDailyResult(transId, beanResult);
                        }
                    }
                }
            } else {
                log.warn(transId + "::NO DATA LOAD");
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        } finally {
            log.info(transId + "::END::crawlOnlineXSMT");
        }
    }

    //second, minute, hour, day of month, month, day(s) of week
    //Run crawl data every 30 second in 16h
    @Scheduled(cron = "*/30 15-50 16 * * ?")
    public void crawlOnlineXSMN(){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        log.info(transId + "::BEGIN::crawlOnlineXSMN");
        String REGION_CODE = "MN";
        String PROVINCE_CODE = "";
        String DATE_RESULT = "";
        try {
            DateTime jdCurrentDate = new DateTime();
            DATE_RESULT = Common.dateToString(jdCurrentDate.toDate(), Common.KQ_NET_DDMMYYYY);
            String url = "http://ketqua.net/xo-so-mien-nam?ngay=" + DATE_RESULT;


            Document document = Jsoup.connect(url).get();
            Element element = document.select("#region_table").first();

            if(element != null && element.hasText()) {
                Elements arrTagResult = element.select("td[id*=rs]");
                String resultCode = "";
                if (arrTagResult != null && arrTagResult.size() > 0) {
                    for (int i = 0; i < arrTagResult.size(); i++) {
                        Element tdElement = arrTagResult.get(i);
                        if (tdElement != null) {
                            String provinceData = StringUtils.nvl(tdElement.attr("id"), "").replace("rs_", "");
                            PROVINCE_CODE = provinceData.substring(0, provinceData.indexOf("_"));
                            String resultLevel = provinceData.replace(PROVINCE_CODE.concat("_"), "");
                            String resultValue = StringUtils.nvl(tdElement.text().trim(), "");
                            resultCode = REGION_CODE.concat("_").concat(PROVINCE_CODE).concat("_").concat(Common.dateToString(jdCurrentDate.toDate(), Common.DATE_YYYYMMDD));

                            XsplDailyResult beanResult = new XsplDailyResult();
                            beanResult.setRegionCode(REGION_CODE);
                            beanResult.setProviceCode(Common.upper(PROVINCE_CODE));
                            beanResult.setResultCode(Common.upper(resultCode));
                            beanResult.setResultValue(resultValue);
                            beanResult.setResultLevel(resultLevel);
                            beanResult.setStatus(Constants.G_STATUS_ACTIVE);
                            beanResult.setCreatedTime(new Timestamp(jdCurrentDate.toDate().getTime()));
                            beanResult.setResultDate(jdCurrentDate.toDate());
                            logXsplDailyResult(transId, beanResult);
                        }
                    }
                }
            } else {
                log.warn(transId + "::NO DATA LOAD");
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        } finally {
            log.info(transId + "::END::crawlOnlineXSMN");
        }
    }

    private void logXsplDailyResult(String transId, XsplDailyResult bean) {
        try {
            XsplDailyResult xsResult = getXsplDailyResult(transId, bean.getResultCode(), bean.getResultLevel());
            if (xsResult == null) {
                commonService.create(transId, bean);
            } else {
                if (!Common.isBlank(bean.getResultValue())) {
                    XsplDailyResult beanUpdate = new XsplDailyResult();
                    beanUpdate.setResultValue(bean.getResultValue());
                    if (("*".equalsIgnoreCase(xsResult.getResultValue())) && !"*".equalsIgnoreCase(bean.getResultValue())
                            && !"-".equalsIgnoreCase(bean.getResultValue())) {
                        beanUpdate.setModifyTime(new Timestamp(new DateTime().toDate().getTime()));
                    }
                    commonService.update(transId, beanUpdate, "WHERE result_code = ? AND result_level = ? ",
                            new String[]{bean.getResultCode(), bean.getResultLevel()}, false, false);
                }
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        }
    }

    private XsplDailyResult getXsplDailyResult(String transId, String resultCode, String resultLevel) throws Exception {
        XsplDailyResult bean = null;
        try {
            List<Object[]> lstObj = commonService.findBySQL(transId, "SELECT * FROM xspl_daily_result WHERE result_code = ? AND result_level = ? ", new String[]{resultCode, resultLevel});
            if(Common.isEmpty(lstObj)) {
                return null;
            } else {
                bean = new XsplDailyResult();
                bean = Common.convertToBean(lstObj.get(0), bean);
                return bean;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
