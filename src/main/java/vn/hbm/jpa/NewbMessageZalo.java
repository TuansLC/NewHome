package vn.hbm.jpa;

import vn.hbm.core.annotation.AntColumn;
import vn.hbm.core.annotation.AntTable;

import java.io.Serializable;

@AntTable(name = "newb_zalo_text", key = "id")
public class NewbMessageZalo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String message;
    private String phone;
    private int status;

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public int getId() {
        return id;
    }

    @AntColumn(name = "id", auto_increment = true, index = 0)
    public void setId(int id) {
        this.id = id;
    }

    @AntColumn(name = "message", index = 1)
    public String getMessage() {
        return message;
    }

    @AntColumn(name = "message", index = 1)
    public void setMessage(String message) {
        this.message = message;
    }

    @AntColumn(name = "phone", index = 2)
    public String getPhone() {
        return phone;
    }

    @AntColumn(name = "phone", index = 2)
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @AntColumn(name = "status", index = 3)
    public int getStatus() {
        return status;
    }

    @AntColumn(name = "status", index = 3)
    public void setStatus(int status) {
        this.status = status;
    }
}
