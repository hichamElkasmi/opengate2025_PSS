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
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_VoidCardlessRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BaseRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_VoidCardless implements SQLData {

	private final String REQUEST_TYPE = "VOIDCLRQ";
	private final String RESPONSE_TYPE = "VOIDCLRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "setVoidCardless";

	
	@Getter @Setter
	private SSEnt_VoidCardlessRq request;
	@Getter @Setter
	private SSEnt_BaseRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_VoidCardless() {
		response = new SSEnt_BaseRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}

	public SSRepo_VoidCardless(SSEnt_VoidCardlessRq request) {
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
	    	  logr.getLog().trace("start readSQL, SSRepo_VoidCardless");
	    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
	            if(!ent_Status.isAuthorized()){
	            	logr.getLog().trace("Error readSQL, SSRepo_VoidCardless");
	                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
	                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
	            }
	            logr.getLog().trace("end readSQL, SSRepo_VoidCardless");
	      }
	      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_VoidCardless",e);
	  		response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
	      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {		
		logr.getLog().trace("start writeSQL, SSRepo_VoidCardless");
		stream.writeObject(request.getInitiator());
		stream.writeString(request.getVoidCardlessTrx().getRrnbr());
		logr.getLog().trace("end writeSQL, SSRepo_VoidCardless");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_BaseRs processing(DataSource dataSource) {
		try {	
			logr.getLog().trace("start processing, SSRepo_VoidCardless");
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
		                new SqlReturnTypeData(SSRepo_VoidCardless.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_VoidCardless)jdbcCall.executeObject(SSRepo_VoidCardless.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_VoidCardless");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_VoidCardless",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}
	
	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    return myMap;
	}
}
