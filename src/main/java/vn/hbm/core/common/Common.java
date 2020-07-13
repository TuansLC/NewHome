package vn.hbm.core.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import vn.hbm.core.annotation.AntColumn;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
public class Common {
//    private static final Logger LOG = LoggerFactory.getLogger(Common.class);

    public static final String EMAIL_PATTERN = "(^$)|(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)";
    public static final String IDENTIFY_PATTERN = "^[0-9]{7,12}$";
    public static final String FULL_NAME_PATTERN = "/^[a-z A-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$/";
    public static SimpleDateFormat sdf = new SimpleDateFormat();
    public static final String DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATE_YYYYMMDDHHMMSS_WP = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_YYYYMM = "yyyyMM";
    public static final String DATE_DDMM = "ddMM";
    public static final String CSP_DATE_DDMMYYYY = "ddMM";
    public static final String CSP_DATE_DDMMYYYYHHmmss= "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_DDMMYYYY= "dd/MM/yyyy";
    public static final String FORM_DATE_MMYYYY= "MM/yyyy";
    public static final String DATE_HHmmss= "HH:mm:ss";
    public static final String MPS_DATE_YYYYMMDDHHmmss= "yyyyMMddHHmmss";
    public static final String KQ_NET_DDMMYYYY= "dd-MM-yyyy";
    private static final Random random = new Random();
    private static final char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] charsDigit = "0123456789".toCharArray();
    private static final String uniChars = "àáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệđ"
            + "îìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵÀÁẢÃẠÂẦẤẨẪẬĂẰẮẲ"
            + "ẴẶÈÉẺẼẸÊỀẾỂỄỆĐÌÍỈĨỊÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲÝỶỸỴÂĂĐÔƠƯ";


    private static final String noneChars = "aaaaaaaaaaaaaaaaaeeeeeeeeeeediiiiiio"
            + "oooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAA"
            + "AAAEEEEEEEEEEEDIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYAADOOU";

    public static boolean checkEmail(String email) { // check định dạng email
        if (checkLength(email)) {
            return false;
        } else {
            return email.matches(EMAIL_PATTERN);
        }
    }

    public static boolean checkIdentifyCode(String identify) { // check chứng minh thư
        if (identify.length() > 13 || identify.length() < 9) {
            return false;
        } else {
            return identify.matches(IDENTIFY_PATTERN);
        }
    }

    public static boolean checkFullName(String fullName) { // check họ tên đầy đủ
        return fullName.matches(FULL_NAME_PATTERN); // nếu chuỗi đúng trả về true
    }


    public static boolean checkLength(String str) { // check độ dài
        if (str.length() > 50) {
            return true;
        }
        return false;
    }

    public static Date strToDate(String value, String format) throws Exception {
        sdf.applyPattern(format);
        return sdf.parse(value);
    }

    public static Date truncDate(DateTime dateTime) throws Exception {
        return new Date(dateTime.withTimeAtStartOfDay().getMillis());
    }

    public static String dateToString(Date value, String format) throws Exception {
        sdf.applyPattern(format);
        return sdf.format(value);
    }

    public static String strToStringDateVTE(String strDate, String formatCurrent, String formatNew) throws Exception {
        Date date = strToDate(strDate, formatCurrent);
        sdf.applyPattern(formatNew);
        return sdf.format(date);
    }

    public static boolean isBlank(String val) {
        return (val == null) || ("".equals(val.trim()));
    }

    public static String formatMethodName(String str) {
        String all = "";
        String index[] = str.split("_");
        if(index != null && index.length == 1){
            all = String.valueOf(index[0].charAt(0)).toUpperCase() + index[0].substring(1);
        } else {
            for (int i = 0; index != null && i < index.length; i++) {
                all += String.valueOf(index[i].charAt(0)).toUpperCase()
                        + index[i].substring(1).toLowerCase();
            }
        }
        return all;
    }

    public static String printArray(Object[] arrObjs) {
        String result = "";
        if(arrObjs != null && arrObjs.length > 0) {
            for (Object obj: arrObjs) {
                String value = "";

                if(String.valueOf(obj).length() > 1000) {
                    value = "...base64...";
                } else {
                    value = String.valueOf(obj);
                }
                result = result + ";" + value;
            }
        }
        result = result.replaceFirst(";", "");
        result = "[" + result + "]";
        return result;
    }

    /**
     * Lay random 1 chuoi String gom ca chu va so
     * @param length
     * @return
     */
    public static String randomString(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; ++idx) {
            buf[idx] = chars[random.nextInt(chars.length)];
        }
        return new String(buf);
    }

    /**
     * Lay random 1 chuoi String chi co so
     * @param length
     * @return
     */
    public static String randomDigit(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; ++idx) {
            buf[idx] = charsDigit[random.nextInt(charsDigit.length)];
        }
        return new String(buf);
    }

    /**
     * Ham thuc hien tang thoi gian len X gia tri
     * @param date
     * @param type
     * @param value
     * @return
     */
    public static Date addTime(Date date, int type, int value){
        if(value == 0){
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(type, value);
        return cal.getTime();
    }

    /**
     * Thuc hien convert object[] to bean theo AntColumn
     * @param objs
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertToBean(Object[] objs, T clazz){
        T bean = null;
        try {
            bean = (T) clazz.getClass().newInstance();
            Method[] methods = bean.getClass().getDeclaredMethods();
            if(methods != null && methods.length > 0) {
                for (Method med: methods) {
                    AntColumn ant = med.getAnnotation(AntColumn.class);
                    if(ant == null || med.getName().startsWith("get")){
                        continue;
                    }

                    if(med.getParameterCount() > 0) {
                        Type[] type = med.getGenericParameterTypes();
                        if(type[0].getTypeName().equals(Long.class.getCanonicalName())) {
                            Object value = objs[ant.index()];
                            med.invoke(bean, value == null ? null : Long.valueOf(String.valueOf(objs[ant.index()])));
                        } else if(type[0].getTypeName().equals(Integer.class.getCanonicalName())) {
                            Object value = objs[ant.index()];
                            med.invoke(bean, value == null ? null : Integer.valueOf(String.valueOf(objs[ant.index()])));
                        }  else if(type[0].getTypeName().equals(String.class.getCanonicalName())) {
                            Object value = objs[ant.index()];
                            med.invoke(bean, value == null ? null : String.valueOf(objs[ant.index()]));
                        } else {
                            Object value = objs[ant.index()];
                            med.invoke(bean, value == null ? null : objs[ant.index()]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }

        return (T) bean ;
    }

    public static String upper(String value){
        if (isBlank(value)){
            return "";
        } else {
            return value.toUpperCase();
        }
    }

    public static boolean isEmpty(List lst) {
        if (lst != null && !lst.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static String readInputStream(InputStream is){
        InputStreamReader rd = null;
        BufferedReader in = null;
        try {
            rd = new InputStreamReader(is, "UTF-8");
            in = new BufferedReader(rd);
            String line;
            String all = "";
            while ((line = in.readLine()) != null) {
                all += "\n" + line;
            }
            all = all.replaceFirst("\n", "");
            return all;
        } catch (Exception e) {
        } finally {
            if(in != null){
                try {
                    in.close();
                } catch (Exception e2) {
                }
            }
            if(rd != null){
                try {
                    rd.close();
                } catch (Exception e2) {
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (Exception e2) {
                }
            }
        }
        return null;
    }

    public static String unicode2ASII(String s) {
        String ret = "";
        try {
            if (s == null) {
                return s;
            }
            for (int i = 0; i < s.length(); i++) {
                int pos = uniChars.indexOf(s.charAt(i));
                if (pos >= 0) {
                    ret += noneChars.charAt(pos);
                } else {
                    ret += s.charAt(i);
                }
            }
        } catch (Exception ex) {
//            log.error("", ex);
        }
        return ret;
    }

//    /**
//     * Ham kiem tra xem tin nhan TB gui len co hop le ko
//     * @param lst
//     * @param message
//     * @return true: Hop le, false: Khong hop le
//     */
//    public static boolean validateSmsSyntax(List<CoreSmsSyntax> lst, String message) {
//        try {
//            if (lst != null && !lst.isEmpty()) {
//                long count = lst.stream().filter(item -> message.matches(item.getRegex())).count();
//                if (count > 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } catch (Exception ex) {
//            return false;
//        }
//        return false;
//    }
//
//    public static CorePackage getPackage(List<CorePackage> lst, String pkgCode) {
//        try {
//            if (lst != null && !lst.isEmpty()) {
//                Optional<CorePackage> itemFirst = lst.stream().filter(item -> pkgCode.equals(item.getCode())).findFirst();
//                if (itemFirst != null && itemFirst.isPresent()) {
//                    CorePackage bean = itemFirst.get();
//                    if (bean != null) {
//                        return bean;
//                    }
//                } else {
//                    return null;
//                }
//            }
//        } catch (Exception ex) {
//            return null;
//        }
//        return null;
//    }
//
//    /**
//     * Ham lay ra command theo cu phap tin nhan TB gui len
//     * @param lst
//     * @param message
//     * @return
//     */
//    public static String getSmsCommand(List<CoreSmsSyntax> lst, String message) {
//        String result = "";
//        try {
//            if (lst != null && !lst.isEmpty()) {
//                Optional<CoreSmsSyntax> itemFirst = lst.stream().filter(item -> message.matches(item.getRegex())).findFirst();
//                if (itemFirst != null && itemFirst.isPresent()) {
//                    CoreSmsSyntax bean = itemFirst.get();
//                    if (bean != null) {
//                        result = bean.getCommand();
//                    }
//                } else {
//                    result = Constants.NOT_FOUND;
//                }
//            }
//        } catch (Exception ex) {
//            log.error("", ex);
//            result = Constants.NOT_FOUND;
//        }
//        return result;
//    }

    public static void sleep(int time){
        try {
            if(time <= 0){
                return;
            }
            Thread.sleep(time);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void makeDir(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                FileUtils.forceMkdir(new File(path));
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    public static int getMonthsInRangeDate(Date dtFromDate, Date dtToDate){
        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTime(dtFromDate);

        Calendar calToDate = Calendar.getInstance();
        calToDate.setTime(dtToDate);

        int m1 = calFromDate.get(Calendar.YEAR) * 12 + calFromDate.get(Calendar.MONTH);
        int m2 = calToDate.get(Calendar.YEAR) * 12 + calToDate.get(Calendar.MONTH);

//        int yearsInBetween = calToDate.get(Calendar.YEAR) - calFromDate.get(Calendar.YEAR);
        int monthsDiff = m2 - m1;

        return monthsDiff;
    }

    public String getDayFromDate(Date dt){
        if (dt == null) {
            return "";
        } else {
            switch (dt.getDay()) {
                case 0: return  "SU";
                case 1: return  "MO";
                case 2: return  "TU";
                case 3: return  "WE";
                case 4: return  "TH";
                case 5: return  "FR";
                case 6: return  "SA";
                default: return "";
            }
        }
    }

    public String getProvinceXoSoFromDate(String regionCode, Date dt, String provinceName){
        String dayFromDate = getDayFromDate(dt);
        if (isBlank(dayFromDate)) return null;

        if ("MB".equalsIgnoreCase(regionCode)) {
            switch (dayFromDate) {
                case "SU": return "TBH";//Thai Binh
                case "MO": return "HNI";//Ha Noi
                case "TU": return "QNH";//Quang Ninh
                case "WE": return "BNH";//Bac Ninh
                case "TH": return "HNI";//Ha Noi
                case "FR": return "HPG";//Hai Phong
                case "SA": return "NDH";//Nam Dinh
                default: return "";
            }
        } else if ("MT".equalsIgnoreCase(regionCode)) {
            return "";
        } else if ("MN".equalsIgnoreCase(regionCode)) {
            return "";
        } else {
            return "";
        }
    }
}
