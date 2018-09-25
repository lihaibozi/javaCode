package common.exception;

/**
 * 业务异常类
 * @author wujun
 */
public class BusinessException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String code;

	public BusinessException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
