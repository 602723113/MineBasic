package cc.zoyn.minebasic.exception;

/**
 * when module.yml wrong, we will throw this exception
 *
 * @author Zoyn
 * @since 2017-11-05
 */
public class UnLoadableModuleException extends Exception {

    public UnLoadableModuleException() {
    }

    public UnLoadableModuleException(String msg) {
        super(msg);
    }

    public UnLoadableModuleException(Throwable cause) {
        super(cause);
    }

    public UnLoadableModuleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
