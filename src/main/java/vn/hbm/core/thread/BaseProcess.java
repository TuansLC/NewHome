package vn.hbm.core.thread;

import lombok.extern.slf4j.Slf4j;
import vn.hbm.core.common.IGsonBase;

@Slf4j
public class BaseProcess extends ManageableThread implements IGsonBase {

    @Override
    protected boolean processSession() throws Exception {
//        log.info("BEGIN::------------------------------------------------------------");
        try {

        } catch (Exception ex) {
            log.error("", ex);
        }
//        log.info("END::------------------------------------------------------------");
        return true;
    }
}
