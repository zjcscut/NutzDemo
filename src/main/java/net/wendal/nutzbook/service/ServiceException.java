package net.wendal.nutzbook.service;


/**
 * Service 层异常类
 * @author Shach
 * @email shacaihong@vip.qq.com
 *
 */
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1271236975865688491L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause ) {
        super( message, cause );
    }

    public ServiceException(String message ) {
        super( message );
    }

    public ServiceException(Throwable cause ) {
        super( cause );
    }

}
