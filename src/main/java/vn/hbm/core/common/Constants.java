package vn.hbm.core.common;

public interface Constants {
    String PREFIX_AUTH = "Basic:";
    String APP_KEY = "ChucMungVano2020";
    Integer TRANS_ID_LENGTH = 10;
    Integer G_STATUS_ACTIVE = 1;
    Integer G_STATUS_DEACTIVE = 0;
    Integer G_STATUS_BLOCK = 2;
    Integer G_STATUS_WAIT = 3;
    String G_RESULT_SUCESS = "1";
    String G_RESULT_ERROR = "0";

    public interface ManageableThreadState {
        public byte NORMAL = 0;
        public byte INFO = 1;
        public byte WARN = 2;
        public byte ERROR = 3;
        public byte IDLE = 4;
    }

    public interface HAMode {
        public String MASTER = "MASTER";
        public String BACKUP = "BACKUP";
    }
}
