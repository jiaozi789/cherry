package com.lm.cherry.server;

import java.io.IOException;

import com.lm.cherry.protocal.http.Http11Protocal;

public interface Response {
	public void processResponse(Http11Protocal httpProtocal)throws Exception;
}
