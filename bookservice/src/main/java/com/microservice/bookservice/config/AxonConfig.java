package com.microservice.bookservice.config;

import org.springframework.context.annotation.Bean;

import com.thoughtworks.xstream.XStream;

public class AxonConfig {
	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();
//		xStream.addPermission(AnyTypePermission.ANY);
		xStream.allowTypesByWildcard(new String[] {"com.microservice.**"});
		return xStream;
	}
}
