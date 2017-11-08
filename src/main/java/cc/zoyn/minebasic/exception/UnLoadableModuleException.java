package cc.zoyn.minebasic.exception;

/**
 * when load a wrong module, we will throw this exception<br />
 * 当读取一个错误的模块时,我们将会抛出此异常
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
