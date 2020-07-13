package vn.hbm.core.thread;

public class JvmGCProcess extends BaseProcess {

    @Override
    protected void loadParameters() throws Exception {
        super.loadParameters();
    }

    @Override
    protected boolean processSession() throws Exception {
//        log.info("RUN " + this.id);
        System.gc();
        safeSleep(1000L);
        return true;
    }
}
