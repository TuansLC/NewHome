package vn.hbm.core.common;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtils {
    public static String leftPadding(String input, String pad, int len) {
        String result = "";
        if (input.length() == len) {
            result = input;
        } else if (input.length() > len) {
            result = input.substring(0, len);
        } else {
            for(result = input; result.length() < len; result = pad + result) {
            }

            if (result.length() > len) {
                result = result.substring(0, len);
            }
        }

        return result;
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat fmt = new SimpleDateFormat(pattern);
            return fmt.format(date);
        }
    }

    public static String nvl(Object input, String nullValue) {
        return input == null ? nullValue : input.toString();
    }

    public static String replaceAll(String source, String find, String replace) {
        if (find != null && find.length() != 0) {
            int offset = 0;
            int lastOffset = 0;

            StringBuilder result;
            for(result = new StringBuilder(); (offset = source.indexOf(find, offset)) >= 0; lastOffset = offset) {
                result.append(source.substring(lastOffset, offset));
                result.append(replace);
                offset += find.length();
            }

            result.append(source.substring(lastOffset, source.length()));
            return result.toString();
        } else {
            return source;
        }
    }

    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String convertString(String stringInput) {
        String convert = "ĂÂÀẰẦÁẮẤẢẲẨÃẴẪẠẶẬỄẼỂẺÉÊÈỀẾẸỆÔÒỒƠỜÓỐỚỎỔỞÕỖỠỌỘỢƯÚÙỨỪỦỬŨỮỤỰÌÍỈĨỊỲÝỶỸỴĐăâàằầáắấảẳẩãẵẫạặậễẽểẻéêèềếẹệôòồơờóốớỏổởõỗỡọộợưúùứừủửũữụựìíỉĩịỳýỷỹỵđ";
        String To = "AAAAAAAAAAAAAAAAAEEEEEEEEEEEOOOOOOOOOOOOOOOOOUUUUUUUUUUUIIIIIYYYYYDaaaaaaaaaaaaaaaaaeeeeeeeeeeeooooooooooooooooouuuuuuuuuuuiiiiiyyyyyd";

        for(int i = 0; i < To.length(); ++i) {
            stringInput = stringInput.replace(convert.charAt(i), To.charAt(i));
        }

        return stringInput;
    }
}
