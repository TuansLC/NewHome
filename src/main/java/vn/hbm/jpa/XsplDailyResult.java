package vn.hbm.jpa;

import vn.hbm.core.annotation.AntColumn;
import vn.hbm.core.annotation.AntTable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@AntTable(name = "xspl_daily_result", key = "id")
public class XsplDailyResult implements Serializable {
    private Long id;
    private String regionCode;
    private String proviceCode;
    private String resultCode;
    private String resultValue;
    private String resultLevel;
    private Timestamp createdTime;
    private Integer status;
    private Timestamp modifyTime;
    private Date resultDate;

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public Long getId() {
        return id;
    }

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public void setId(Long id) {
        this.id = id;
    }

    @AntColumn(name = "region_code", index = 1)
    public String getRegionCode() {
        return regionCode;
    }

    @AntColumn(name = "region_code", index = 1)
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @AntColumn(name = "province_code", index = 2)
    public String getProviceCode() {
        return proviceCode;
    }

    @AntColumn(name = "province_code", index = 2)
    public void setProviceCode(String proviceCode) {
        this.proviceCode = proviceCode;
    }

    @AntColumn(name = "result_code", index = 3)
    public String getResultCode() {
        return resultCode;
    }

    @AntColumn(name = "result_code", index = 3)
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @AntColumn(name = "result_value", index = 4)
    public String getResultValue() {
        return resultValue;
    }

    @AntColumn(name = "result_value", index = 4)
    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    @AntColumn(name = "result_level", index = 5)
    public String getResultLevel() {
        return resultLevel;
    }

    @AntColumn(name = "result_level", index = 5)
    public void setResultLevel(String resultLevel) {
        this.resultLevel = resultLevel;
    }

    @AntColumn(name = "created_time", index = 6)
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    @AntColumn(name = "created_time", index = 6)
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @AntColumn(name = "status", index = 7)
    public Integer getStatus() {
        return status;
    }

    @AntColumn(name = "status", index = 7)
    public void setStatus(Integer status) {
        this.status = status;
    }

    @AntColumn(name = "modify_time", index = 8)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    @AntColumn(name = "modify_time", index = 8)
    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @AntColumn(name = "result_date", index = 9)
    public Date getResultDate() {
        return resultDate;
    }

    @AntColumn(name = "result_date", index = 9)
    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }
}
