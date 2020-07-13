package vn.hbm.core.common;

public interface MessageContants {
    interface Result {
        String MESSAGE_CODE = "messageResult";
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String EXCEPTION = "EXCEPTION";
    }

    interface Account {
        String ADDNEW_SUCCESS = "Thêm mới thành công";
        String ADDNEW_ERROR = "ERROR: Thêm mới thất bại";

        String DELETE_SUCCESS = "Xóa thành công";
        String DELETE_ERROR = "ERROR: Xóa không thành công";

        String UPDATE_SUCCESS = "Cập nhật thành công";
        String UPDATE_ERROR = "ERROR: Cập nhật không thành công";
    }
}
