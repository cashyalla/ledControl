package com.cashyalla.home.led.domain;

public class CommonResult {

	private boolean success;

	private String message = "요청하신 작업을 성공적으로 수행 하였습니다.";

	public CommonResult() {
	}

	public CommonResult(boolean success) {
		this.success = success;
	}

	public CommonResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public CommonResult(Throwable e) {
		this.success = false;
		this.message = e.getMessage();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
