package com.embel.asset.exception;

import java.io.IOException;

public class ExceptionPayloadResponse extends IOException{
	
	public String devIMEI;

	public ExceptionPayloadResponse() {
		super();
	}

	public ExceptionPayloadResponse(String devIMEI) {
		super("Device IMEI Number is not Register ---> "+devIMEI);
		
	}

}
