package com.s2m.ss.api.pr.config;

import org.slf4j.Logger;

public interface SS_Logging {
	
	public void logDebug(String str, Object object);
	public Logger getLog();	
	public void setClazz(Class<?> clazz);
}
