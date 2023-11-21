package com.microservice.borrowingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

@Configuration
public class AxonConfig {
	@Bean
	public XStream xStream() {
		XStream xStream = new XStream();
		xStream.addPermission(AnyTypePermission.ANY);
//		xStream.allowTypesByWildcard(new String[] {"com.microservice"});
		return xStream;
	}
}
