package vn.hbm.core.common;

import vn.hbm.bean.XSBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TEST {
    public static void main(String[] args) {
        String data= "Xq.83.52.18.57x300n.434.22.20x200n.xq.050.01.70x100n.454.19.86x100n.xq.19.20.48.22x200n.09.19.22.20.x 300n.xq.55.17.69.90x100n. 96.71.55.09x100n.\n" +
                "Xq.69.22.252.x 200nxq.69.22.20.57x200n .Lo.20.69x500₫.151 x 50₫ De.474.272.828.33.88.737x500k.171.575x1tr.de dau dit 7 x 2500n.\n" +
                "de. 080.181.282.383.484.474.797x200n.686x50n\n" +
                "Xq: 575 181x100 383 252x100.";

        data = "\n" +
                "Xien 44 88=500k\n" +
                "77 88=500k\n" +
                "090=500k\n" +
                "494=500k\n" +
                "De 77x800n. \n" +
                "\n" +
                "Xien 22 88=500k\n" +
                "44 77=500k\n" +
                "De kép x30k. \n" +
                "Xq 2: 131 69 15 x50k. \n" +
                "27 46 52 86 x50k.\n" +
                "Lô  61 30x200 010 x100\n" +
                "Lô 575x100n \n" +
                "Lô 61 30x200n 16 03 35 x100 010 x100 tin5\n" +
                "De 22x500. 77 33 88x1500k; \n" +
                "De kép x 50n. 33 00 77x 100n. \n" +
                "Đề 787 x 100 22.77x400\n" +
                "\n" +
                "Đề: 08 01 99 51 88 79 63 68 292 x 50, 83 97 x 120.\n" +
                "Đề 121 x 100, 989 x 5\n" +
                "Đề kép x1000k. Bộ 61 x1000k,11 66 181 x1000k. 363 686 131 x500k 585 x1000k 050 x500k 00 55 x1500k 33 88 x1000k 22 77 x1500k 101 161 030 x500k\n" +
                "De 11 66 22 77x 1500";
//        data = "xien quay 92 05 22 68x 200k \n" +
//                "lo 505 92x50điểm \n" +
//                "đề 898 797 292 x200k\n" +
//                "Lô 686 x200\n" +
//                "Lô 696x100";
        data = "Xq2: \n" +
                "88,33,838x500k.\n" +
                "727,77,22x500k.\n" +
                "22,77,33,88x200k.\n" +
                "272,838x200k.\n" +
                "Đề 33 44 77 88 x1 triệu.\n" +
                "\n" +
                "Xien 11 22=500k\n" +
                "11 00=500k\n" +
                "11 33=500k\n" +
                "11 44=500k\n" +
                "11.77=500k\n" +
                "11 88=500k\n" +
                "22 44=500k\n" +
                "22 77=500k\n" +
                "\n" +
                "\n" +
                "Xien 2 .11 44\n" +
                "           44 81\n" +
                "          11 81\n" +
                "          44 86\n" +
                "         11 86\n" +
                "         81 86  mỗi cặp x 1 triệu\n" +
                "Xien 2 .11 94 \n" +
                "           11 49\n" +
                "           11 52 \n" +
                "           81 94\n" +
                "          81 49\n" +
                "         81 44\n" +
                "        81 86\n" +
                "       81 88 moi cap x500n. \n" +
                "Xq2:\n" +
                "161 010 x200k\n" +
                "030 676 x200k\n" +
                "141 393 x200k\n" +
                "35 23 22 77 x200k.\n" +
                "\n" +
                "xien 13/31 x800n\n" +
                "Xq2:  (22 42 686)x80n\n" +
                "De 686x80n, 22 42x40n\n" +
                "de bo 29x40n\n" +
                "Xquay 2: (00 686)x40n,  (22 292)x40n\n" +
                "De 68 x 2700k \n" +
                "Xquay 2 030 06 54x200k\n" +
                "Xq2 59 17 78 79 x50k\n" +
                "Xq2 121 484 x50k. T6\n" +
                "\n" +
                "XQuay 2: 474-76-77 x 50\n" +
                "Đề bộ 55 x 100k\n" +
                "Đề 11.55x 100k\n" +
                "xien quay 2 -92 05 22 68x 200k \n" +
                " \n" +
                "xq2: 22 60 85 x 200, 10 53 73 x 200,\n" +
                "Lô 686 x100\n" +
                "Đề bộ 02 x50k 11 66 44 33 88 161 18 x50k.";

//        data = "Lô 474-76-77 x 50k\n" +
//                "Xiên4: 474-76-77 x 200k\n" +
//                "\n" +
//                "Đề bộ 55 x 100k\n" +
//                "\n" +
//                "Đề bộ 80 x 100k\n" +
//                "\n" +
//                "Đề cham 1.5 x 100k\n" +
//                "\n" +
//                "\n" +
//                "Đề 14-15-30-31-60-61-62-75-76-77-02-03-21-22-42-43-94-95 x 400k";

        String dataPlain = data;
        String[] arrDataPlain = dataPlain.split("\\n");
        data = Common.unicode2ASII(data).toLowerCase();
//        System.out.println(Common.unicode2ASII(data));
        //Tach moi dong thanh 1 line theo \n
        String[] arrData = data.split("\\n");
        List<XSBean> lstHasMoney = new ArrayList<>();
        List<XSBean> lstNoMoney = new ArrayList<>();
        int lineCount = 0;
//        int startData = 0;
        int startXsCmd = 0;
        String funcOld = "";
        int index = 0;
        for (String item: arrData) {
//            String[] split = item.split("x\\d+n");
//            System.out.println(split.length);
//            System.out.println(item);
            item = item.replaceAll("x\\s+", "x");
            item = item.replaceAll("\\s+x", "x");
//            item = item.replaceAll("\\s+[n|k|N|K|đ|₫|d]", "K");
            item = item.replaceAll("[n|k|N|K|đ|₫]\\.\\s+", "n.");
            item = item.replaceAll("[n|k|N|K|đ|₫]\\s+\\.", "n.");
//            System.out.println(item);
            Pattern pattern = Pattern.compile("[X|x|-|=]\\d+[(diem)|( trieu)|(trieu)|(tr)|n|k|N|K|đ|₫|d|!\\w]");
            Matcher matcher = pattern.matcher(item);
            int count = 0;
            startXsCmd = 0;
            String func = getFunc(item);
            if (!func.equals("")) {
                funcOld = func;
            }

            boolean isMatch = false;
            while(matcher.find()) {
                isMatch = true;
                count++;
//                System.out.println("found: " + count + " : " + matcher.start() + " - " + matcher.end());

                XSBean bean = new XSBean();
                bean.setXsLine(arrDataPlain[index]);
                bean.setLineCode((lineCount < 10) ? "0" + lineCount : String.valueOf(lineCount));
//                bean.setXsFee(item.substring(matcher.start(), matcher.end()).replaceAll("[x|-|=]", "").replaceAll("\\.", ""));
                String xsFee = item.substring(matcher.start(), matcher.end());
                if(xsFee.contains("t") && item.substring(matcher.start(), matcher.end() + 1).contains("tr")) {
                    xsFee = xsFee.replace("t", "000");
                } else if (item.length() > matcher.end() + 3 && (item.substring(matcher.start(), matcher.end() + 1).replaceFirst("\\s", "").contains("tr")
                        || item.substring(matcher.start(), matcher.end() + 3).replaceFirst("\\s", "").contains("tr"))) {
                    xsFee = xsFee.trim().concat("000");
                } else if(xsFee.contains("d") && item.substring(matcher.start(), matcher.end() + 3).contains("diem")) {
                    bean.setXsType("diem");
                }

                bean.setXsFee(xsFee.replaceAll("\\D", ""));
//                bean.setXsDataNum(item.replace(item.substring(matcher.start(), matcher.end()), ""));
//                System.out.println(item);
                String xsDataNum = item.substring(startXsCmd, matcher.start());
//                System.out.println(xsDataNum);
                if (xsDataNum != null && !"".equals(xsDataNum)) {
                    if(xsDataNum.trim().startsWith("xien quay")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace("xien quay", ""));
                    } else if(xsDataNum.trim().startsWith("xienquay")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace("xienquay", ""));
                    } else if(xsDataNum.trim().startsWith("xquay 2")) {
                        funcOld = "xien_quay2";
                        bean.setXsFunc("xien_quay2");
                        bean.setXsDataNum(xsDataNum.replace("xquay 2", ""));
                    } else if(xsDataNum.trim().startsWith("xquay2")) {
                        funcOld = "xien_quay2";
                        bean.setXsFunc("xien_quay2");
                        bean.setXsDataNum(xsDataNum.replace("xquay2", ""));
                    } else if(xsDataNum.trim().startsWith("xquay")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace("xquay", ""));
                    } else if(xsDataNum.trim().startsWith(".xquay")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace(".xquay", ""));
                    } else if(xsDataNum.trim().startsWith("xien4")) {
                        funcOld = "xien4";
                        bean.setXsFunc("xien4");
                        bean.setXsDataNum(xsDataNum.replace("xien4", ""));
                    } else if(xsDataNum.trim().startsWith("xien3")) {
                        funcOld = "xien3";
                        bean.setXsFunc("xien3");
                        bean.setXsDataNum(xsDataNum.replace("xien3", ""));
                    } else if(xsDataNum.trim().startsWith("xien 2")) {
                        funcOld = "xien2";
                        bean.setXsFunc("xien2");
                        bean.setXsDataNum(xsDataNum.replace("xien 2", ""));
                    } else if(xsDataNum.trim().startsWith("xien2")) {
                        funcOld = "xien2";
                        bean.setXsFunc("xien2");
                        bean.setXsDataNum(xsDataNum.replace("xien2", ""));
                    } else if(xsDataNum.trim().startsWith("xien")) {
                        funcOld = "xien";
                        bean.setXsFunc("xien");
                        bean.setXsDataNum(xsDataNum.replace("xien", ""));
                    } else if(xsDataNum.trim().startsWith(".xien")) {
                        funcOld = "xien";
                        bean.setXsFunc("xien");
                        bean.setXsDataNum(xsDataNum.replace(".xien", ""));
                    } if(xsDataNum.trim().startsWith("xq2")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace("xq2", ""));
                    } else if(xsDataNum.trim().startsWith(".xq2")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace(".xq2", ""));
                    } if(xsDataNum.trim().startsWith("xq")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace("xq", ""));
                    } else if(xsDataNum.trim().startsWith(".xq")) {
                        funcOld = "xien_quay";
                        bean.setXsFunc("xien_quay");
                        bean.setXsDataNum(xsDataNum.replace(".xq", ""));
                    } else if(xsDataNum.trim().startsWith("lo")) {
                        funcOld = "lo";
                        bean.setXsFunc("lo");
                        bean.setXsDataNum(xsDataNum.replace("lo", ""));
                    } else if(xsDataNum.trim().startsWith(".lo")) {
                        funcOld = "lo";
                        bean.setXsFunc("lo");
                        bean.setXsDataNum(xsDataNum.replace(".lo", ""));
                    } else if(xsDataNum.trim().startsWith("de")) {
                        funcOld = "de";
                        bean.setXsFunc("de");
                        bean.setXsDataNum(xsDataNum.replace("de", ""));
                    } else if(xsDataNum.trim().startsWith(".de")) {
                        funcOld = "de";
                        bean.setXsFunc("de");
                        bean.setXsDataNum(xsDataNum.replace(".de", ""));
                    } else if(xsDataNum.contains("moi cap") || xsDataNum.contains("moicap")) {
                        bean.setXsFunc(funcOld);
                        bean.setXsDataNum(xsDataNum.replace("moi cap", "").replace("moicap", ""));
                        //Set gia cho list no money va add vao list has money sau do clear di
                        if (!lstNoMoney.isEmpty()) {
                            for (XSBean r: lstNoMoney) {
                                r.setXsFee(xsFee.replaceAll("\\D", ""));
                            }
                            lstHasMoney.addAll(lstNoMoney);
                            lstNoMoney.clear();
                            System.out.println("CLEAR");
                        }
                    } else {
                        bean.setXsFunc(funcOld);
                        bean.setXsDataNum(xsDataNum.replace(funcOld, ""));
                    }
                }

//                if(xsFee.contains("t") && item.substring(matcher.start(), matcher.end() + 1).contains("tr")) {
//                    xsFee = xsFee.replace("t", "000");
//                } else if (item.length() > matcher.end() + 3 && (item.substring(matcher.start(), matcher.end() + 1).replaceFirst("\\s", "").contains("tr")
//                        || item.substring(matcher.start(), matcher.end() + 3).replaceFirst("\\s", "").contains("tr"))) {
//                    xsFee = xsFee.trim().replace("t", "000");
//                } else if(xsFee.contains("d") && item.substring(matcher.start(), matcher.end() + 3).contains("diem")) {
//                    bean.setXsType("DIEM");
//                }
                if(item.substring(matcher.start(), matcher.end()).contains("t") && item.substring(matcher.start(), matcher.end() + 1).contains("tr")) {
                    startXsCmd = matcher.end() + 1;
                } else if (item.length() > matcher.end() + 3 && item.substring(matcher.start(), matcher.end() + 2).replaceFirst("\\s", "").contains("tr")) {
                    startXsCmd = matcher.end() + 3;
                } else if (item.length() > matcher.end() + 3 && item.substring(matcher.start(), matcher.end() + 3).replaceFirst("\\s", "").contains("tr")) {
                    startXsCmd = matcher.end() + 4;
                } else if(xsFee.contains("d") && item.substring(matcher.start(), matcher.end() + 3).contains("diem")) {
                    startXsCmd = matcher.end() + 3;
                } else {
                    startXsCmd = matcher.end();
                }
                lstHasMoney.add(bean);
//                System.out.println(bean.toString());
            }
            if (!isMatch) {
                if(item != null && !item.trim().equals("")) {
                    String[] arrLine = item.split("\\t");
                    for (String line : arrLine) {
//                    System.out.println("======" + line);
//                        if (line.trim().startsWith("xq")) {
//
//                        } else if (line.trim().startsWith(".xq")) {
//
//                        } else if (line.trim().startsWith("xien quay")) {
//
//                        } else if (line.trim().startsWith("xienquay")) {
//
//                        } else if (line.trim().startsWith("xien")) {
//
//                        } else if (line.trim().startsWith(".xien")) {
//
//                        } else if (line.trim().startsWith("lo")) {
//
//                        } else if (line.trim().startsWith(".lo")) {
//
//                        } else if (line.trim().startsWith("de")) {
//
//                        } else if (line.trim().startsWith(".de")) {
//
//                        } else if (line.contains("moi cap") || line.contains("moicap")) {
//
//                        } else {
                        if (line.trim().equals("xq2") || line.trim().equals("xq2:")){

                        } else {
                            XSBean bean = new XSBean();
                            bean.setLineCode((lineCount < 10) ? "0" + lineCount : String.valueOf(lineCount));
                            bean.setXsFunc(funcOld);
                            bean.setXsDataNum(line.trim());
                            bean.setXsLine(arrDataPlain[index]);
//                            System.out.println("ADD==" + bean.toString());
                            lstNoMoney.add(bean);
                        }
//                        }
                    }
                }
            }
            lineCount++;
            index++;
        }
        Collections.sort(lstHasMoney);
        String lineOld = "";
        LinkedHashMap<String, Object> linkedMap = new LinkedHashMap();
        for (XSBean r : lstHasMoney) {
//            System.out.println(r);
            if (!lineOld.equals(r.getXsLine())) {
                lineOld = r.getXsLine();
            }
            String temp = Common.unicode2ASII(lineOld).toLowerCase();
            if (!"".equals(getFunc(temp))) {
                linkedMap.put(lineOld, r);
            } else {
                linkedMap.put(r.getXsFunc().concat(":").concat(lineOld), r);
            }
        }
        System.out.println("FINISH");
    }

    private static String getFunc(String data){
        String funcOld = "";
        if(data.trim().startsWith("xien quay")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith("xienquay")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith("xquay 2")) {
            funcOld = "xien_quay2";
        } else if(data.trim().startsWith("xquay2")) {
            funcOld = "xien_quay2";
        } else if(data.trim().startsWith("xquay")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith(".xquay")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith("xq")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith(".xq")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith("xq2")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith(".xq2")) {
            funcOld = "xien_quay";
        } else if(data.trim().startsWith("xien4")) {
            funcOld = "xien4";
        } else if(data.trim().startsWith("xien3")) {
            funcOld = "xien3";
        } else if(data.trim().startsWith("xien2")) {
            funcOld = "xien2";
        } else if(data.trim().startsWith("xien 2")) {
            funcOld = "xien2";
        } else if(data.trim().startsWith("xien")) {
            funcOld = "xien";
        } else if(data.trim().startsWith(".xien")) {
            funcOld = "xien";
        } else if(data.trim().startsWith("lo")) {
            funcOld = "lo";
        } else if(data.trim().startsWith(".lo")) {
            funcOld = "lo";
        } else if(data.trim().startsWith("de")) {
            funcOld = "de";
        } else if(data.trim().startsWith(".de")) {
            funcOld = "de";
        }

        return funcOld;
    }
}
