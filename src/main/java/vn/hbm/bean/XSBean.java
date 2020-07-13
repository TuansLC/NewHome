package vn.hbm.bean;

import java.util.Map;

public class XSBean implements Comparable<XSBean> {
    private String xsFunc;//Hinh thuc danh De, Lo, Xien, XienQuay...
    private String xsDataNum;//Day so danh theo hinh thuc
    private String xsFee;//Tien danh
    private Map<String, String> xsMapData;//Day so sau khi da format
    private String lineCode;//Dong du lieu
    private String xsType = "nghin";//NGHIN|DIEM
    private String xsLine;

    public String getXsFunc() {
        return xsFunc;
    }

    public void setXsFunc(String xsFunc) {
        this.xsFunc = xsFunc;
    }

    public String getXsDataNum() {
        return xsDataNum;
    }

    public void setXsDataNum(String xsDataNum) {
        this.xsDataNum = xsDataNum;
    }

    public String getXsFee() {
        return xsFee;
    }

    public void setXsFee(String xsFee) {
        this.xsFee = xsFee;
    }

    public Map<String, String> getXsMapData() {
        return xsMapData;
    }

    public void setXsMapData(Map<String, String> xsMapData) {
        this.xsMapData = xsMapData;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getXsType() {
        return xsType;
    }

    public void setXsType(String xsType) {
        this.xsType = xsType;
    }

    public String getXsLine() {
        return xsLine;
    }

    public void setXsLine(String xsLine) {
        this.xsLine = xsLine;
    }

    @Override
    public String toString() {
        return "line>>>" + xsLine + "\nline>>>" + lineCode + ";xsFunc==" + xsFunc + ";xsDataNum==" + xsDataNum + ";xsFee==" + xsFee + ";xsType==" +xsType;
    }

    @Override
    public int compareTo(XSBean o) {
        return this.getLineCode().compareTo(o.getLineCode());
    }
}
