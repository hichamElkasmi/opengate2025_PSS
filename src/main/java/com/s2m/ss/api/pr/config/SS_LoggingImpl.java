package com.s2m.ss.api.pr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class SS_LoggingImpl implements SS_Logging {
	
	private Logger logger;
	
	@Override
	public void setClazz(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}
	
	@Override
	public void logDebug(String str, Object object) {
		if(logger.isDebugEnabled() || logger.isTraceEnabled())
			logger.debug(str+toJsonString(object));
	}

	@Override
	public Logger getLog() {
		return logger;
	}
	
	private String toJsonString(Object object) {
		String jsonString = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			
		}
	
		return jsonString;
	}
}
