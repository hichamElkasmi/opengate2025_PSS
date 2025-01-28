package com.s2m.ss.api.pr.dao;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.getMsg;

import java.sql.SQLData;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.s2m.ss.api.pr.config.SS_LoggingImpl;
import com.s2m.ss.api.pr.entities.SSEnt_Authorization;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ManualTransactionRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_AuthorisationRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_ManualTrx implements SQLData {

	private final String REQUEST_TYPE = "MANUALRQ";
	private final String RESPONSE_TYPE = "MANUALRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "setManualTrx";

	
	@Getter @Setter
	private SSEnt_ManualTransactionRq request;
	@Getter @Setter
	private SSEnt_AuthorisationRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_ManualTrx() {
		response = new SSEnt_AuthorisationRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setAuthorization(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}

	public SSRepo_ManualTrx(SSEnt_ManualTransactionRq request) {
		this();
		this.request = request;
	}
	
	@Override
	public String getSQLTypeName() throws SQLException {
		return REQUEST_TYPE;
	}

	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		try{
			logr.getLog().trace("start readSQL, SSRepo_ManualTrx");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	logr.getLog().trace("Authorized readSQL, SSRepo_ManualTrx");
        		response.setAuthorization((SSEnt_Authorization)stream.readObject());
            }
            else
            {
            	logr.getLog().trace("error readSQL, SSRepo_ManualTrx");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
	      }
	      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_ManualTrx",e);
	  		response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
	      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {		
		logr.getLog().trace("start writeSQL, SSRepo_ManualTrx");
		stream.writeObject(request.getInitiator());
		stream.writeObject(request.getManualTrx());
		logr.getLog().trace("end writeSQL, SSRepo_ManualTrx");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_AuthorisationRs processing(DataSource dataSource) {
		try {	
			logr.getLog().trace("start processing, SSRepo_ManualTrx");
			Map<String, Class<?>> auxiliaryTypes = createMap();
		    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
		        .withCatalogName(PACKAGE_SQL)
		        .withProcedureName(PROCEDURE_SQL)
	         .declareParameters(
	                 new SqlParameter("v_request", OracleTypes.STRUCT,
	                		 REQUEST_TYPE))
		        .declareParameters(
		            new SqlOutParameter(
		                "v_response",
		                OracleTypes.STRUCT,
		                RESPONSE_TYPE,
		                new SqlReturnTypeData(SSRepo_ManualTrx.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_ManualTrx)jdbcCall.executeObject(SSRepo_ManualTrx.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_ManualTrx");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_ManualTrx",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}
	
	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("AUTHORIZATIONSTRUCT", SSEnt_Authorization.class);
	    return myMap;
	}
}
