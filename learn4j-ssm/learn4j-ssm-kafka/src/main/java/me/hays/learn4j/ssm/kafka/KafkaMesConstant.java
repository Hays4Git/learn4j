package me.hays.learn4j.ssm.kafka;

public enum KafkaMesConstant {
	SUCCESS("00000", "成功"),
	SEND_ERROR("30001", "发送消息超时"),
	NO_RESULT("30002", "未查询到返回结果"),
	NO_OFFSET("30003", "未查到返回数据的offset")
	;
	private String code;
	private String message;

    private KafkaMesConstant(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
