package com.cherry.winter.yakuzi.Message;


import javax.ws.rs.WebApplicationException;

/**
 * 应用程序异常 <ul> 包括错误号, 错误消息 </ul>
 *
 * @author fangwei
 */
public class ApplicationException extends WebApplicationException {

  private int errorCode;

  /**
   * 初始化新的应用程序异常实例为内部服务器错误(500).
   */
  public ApplicationException() {
    super(ErrorMessages.INTERNAL_SERVER_ERROR.getMessage());
    errorCode = ErrorMessages.INTERNAL_SERVER_ERROR.getErrorCode();
  }

  /**
   * 初始化新的应用程序异常实例为指定的错误类型.
   */
  public ApplicationException(ErrorMessages errorMessages) {
    super(errorMessages.getMessage());
    errorCode = errorMessages.getErrorCode();
  }

  /**
   * 获取错误号
   */
  public int getErrorCode() {
    return errorCode;
  }
}
