package vn.hbm.thread;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import vn.hbm.core.common.*;
import vn.hbm.core.service.CommonService;
import vn.hbm.jpa.XsplDailyResult;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CrawlXosoProcess implements IGsonBase {
    CommonService commonService = (CommonService) AppContext.getBean("commonService");

    //second, minute, hour, day of month, month, day(s) of week
    //Run crawl data every 30 second in 18h
//    @Scheduled(cron = "*/30 15-50 18 * * ?")
    public void crawlOnlineXSMB(String strDate){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        log.info(transId + "::BEGIN::crawlOnlineXSMB::" + strDate);
        String REGION_CODE = "MB";
        String PROVINCE_CODE = "ALL";
        String DATE_RESULT = "";
        try {
            Date dt = Common.strToDate(strDate, Common.DATE_YYYYMMDD);

            DateTime jdDate = new DateTime(dt.getTime());
            DATE_RESULT = Common.dateToString(jdDate.toDate(), Common.KQ_NET_DDMMYYYY);
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
                            String resultCodeNew = REGION_CODE.concat("_").concat(PROVINCE_CODE).concat("_").concat(Common.dateToString(jdDate.toDate(), Common.DATE_YYYYMMDD));

                            XsplDailyResult beanResult = new XsplDailyResult();
                            beanResult.setRegionCode(REGION_CODE);
                            beanResult.setProviceCode(Common.upper(PROVINCE_CODE));
                            beanResult.setResultCode(Common.upper(resultCodeNew));
                            beanResult.setResultValue(resultValue);
                            beanResult.setResultLevel(resultLevel);
                            beanResult.setStatus(Constants.G_STATUS_ACTIVE);
                            beanResult.setCreatedTime(new Timestamp(jdDate.toDate().getTime()));
                            beanResult.setResultDate(jdDate.toDate());
                            logXsplDailyResult(transId, beanResult);

                        }
                    }
                }
            } else {
                log.warn(transId, "NO DATA LOAD");
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        } finally {
            log.info(transId + "::END::crawlOnlineXSMB::" + strDate);
        }
    }

    //second, minute, hour, day of month, month, day(s) of week
    //Run crawl data every 30 second in 17h
//    @Scheduled(cron = "*/30 15-50 17 * * ?")
    public void crawlOnlineXSMT(String strDate){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        log.info(transId + "::BEGIN::crawlOnlineXSMT::" + strDate);
        String REGION_CODE = "MT";
        String PROVINCE_CODE = "";
        String DATE_RESULT = "";
        try {
            Date dt = Common.strToDate(strDate, Common.DATE_YYYYMMDD);

            DateTime jdDate = new DateTime(dt.getTime());
            DATE_RESULT = Common.dateToString(jdDate.toDate(), Common.KQ_NET_DDMMYYYY);
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
                            String resultCodeNew = REGION_CODE.concat("_").concat(PROVINCE_CODE).concat("_").concat(Common.dateToString(jdDate.toDate(), Common.DATE_YYYYMMDD));

                            XsplDailyResult beanResult = new XsplDailyResult();
                            beanResult.setRegionCode(REGION_CODE);
                            beanResult.setProviceCode(Common.upper(PROVINCE_CODE));
                            beanResult.setResultCode(Common.upper(resultCodeNew));
                            beanResult.setResultValue(resultValue);
                            beanResult.setResultLevel(resultLevel);
                            beanResult.setStatus(Constants.G_STATUS_ACTIVE);
                            beanResult.setCreatedTime(new Timestamp(jdDate.toDate().getTime()));
                            beanResult.setResultDate(jdDate.toDate());
                            logXsplDailyResult(transId, beanResult);
                        }
                    }
                }
            } else {
                log.warn(transId, "NO DATA LOAD");
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        } finally {
            log.info(transId + "::END::crawlOnlineXSMT::" + strDate);
        }
    }

    //second, minute, hour, day of month, month, day(s) of week
    //Run crawl data every 30 second in 16h
//    @Scheduled(cron = "*/30 15-50 16 * * ?")
    public void crawlOnlineXSMN(String strDate){
        String transId = Common.randomString(Constants.TRANS_ID_LENGTH);
        log.info(transId + "::BEGIN::crawlOnlineXSMN::" + strDate);
        String REGION_CODE = "MT";
        String PROVINCE_CODE = "";
        String DATE_RESULT = "";
        try {
            Date dt = Common.strToDate(strDate, Common.DATE_YYYYMMDD);

            DateTime jdDate = new DateTime(dt.getTime());
            DATE_RESULT = Common.dateToString(jdDate.toDate(), Common.KQ_NET_DDMMYYYY);
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
                            String resultCodeNew = REGION_CODE.concat("_").concat(PROVINCE_CODE).concat("_").concat(Common.dateToString(jdDate.toDate(), Common.DATE_YYYYMMDD));

                            XsplDailyResult beanResult = new XsplDailyResult();
                            beanResult.setRegionCode(REGION_CODE);
                            beanResult.setProviceCode(Common.upper(PROVINCE_CODE));
                            beanResult.setResultCode(Common.upper(resultCodeNew));
                            beanResult.setResultValue(resultValue);
                            beanResult.setResultLevel(resultLevel);
                            beanResult.setStatus(Constants.G_STATUS_ACTIVE);
                            beanResult.setCreatedTime(new Timestamp(jdDate.toDate().getTime()));
                            beanResult.setResultDate(jdDate.toDate());
                            logXsplDailyResult(transId, beanResult);
                        }
                    }
                }
            } else {
                log.warn(transId, "NO DATA LOAD");
            }
        } catch (Exception ex) {
            log.error(transId, ex);
        } finally {
            log.info(transId + "::END::crawlOnlineXSMN::" + strDate);
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
                    if (("*".equalsIgnoreCase(xsResult.getResultValue())) && !"*".equalsIgnoreCase(bean.getResultValue())) {
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

//    public static void main(String[] args) {
//        try {
//            CrawlXosoProcess xosoProcess = new CrawlXosoProcess();
//
//            String strFromDate = "01/01/2020";
//            String strToDate = "24/04/2020";
//
//            Date fromDate = Common.strToDate(strFromDate, Common.DATE_DDMMYYYY);
//            DateTime jdFromDate = new DateTime(fromDate.getTime());
//            Date toDate = Common.strToDate(strToDate, Common.DATE_DDMMYYYY);
//            DateTime jdToDate = new DateTime(fromDate.getTime());
//
//            long diff = toDate.getTime() - fromDate.getTime();
//
//            long totalDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
////            log.info("Total days: " + totalDay);
//            for (int i = 0; i < totalDay; i++) {
//                DateTime dt = jdFromDate.plusDays(i);
//                String strDate = Common.dateToString(dt.toDate(), Common.DATE_YYYYMMDD);
//
//
//            }
//        } catch (Exception ex) {
//            log.error("", ex);
//        }
//    }
}
