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
import com.s2m.ss.api.pr.entities.requests.SSEnt_MoneySendRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_AuthorisationRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_MoneySend implements SQLData {

	private final String REQUEST_TYPE = "MSENDRQ";
	private final String RESPONSE_TYPE = "MSENDRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "setMoneySend";

	
	@Getter @Setter
	private SSEnt_MoneySendRq request;
	@Getter @Setter
	private SSEnt_AuthorisationRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_MoneySend() {
		response = new SSEnt_AuthorisationRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setAuthorization(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}

	public SSRepo_MoneySend(SSEnt_MoneySendRq request) {
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
			logr.getLog().trace("start readSQL, SSRepo_MoneySend");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	logr.getLog().trace("Authorized readSQL, SSRepo_MoneySend");
        		response.setAuthorization((SSEnt_Authorization)stream.readObject());
            }
            else
            {
            	logr.getLog().trace("error readSQL, SSRepo_MoneySend");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
	      }
	      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_MoneySend",e);
	  		response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
	      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {		
		logr.getLog().trace("start writeSQL, SSRepo_MoneySend");
		stream.writeObject(request.getInitiator());
		stream.writeObject(request.getMoneySendTrx());
		logr.getLog().trace("end writeSQL, SSRepo_MoneySend");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_AuthorisationRs processing(DataSource dataSource) {
		try {	
			logr.getLog().trace("start processing, SSRepo_MoneySend");
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
		                new SqlReturnTypeData(SSRepo_MoneySend.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_MoneySend)jdbcCall.executeObject(SSRepo_MoneySend.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_MoneySend");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_MoneySend",e);
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
