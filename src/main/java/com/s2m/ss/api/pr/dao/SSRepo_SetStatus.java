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
import com.s2m.ss.api.pr.entities.requests.SSEnt_OperationRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BaseRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_SetStatus implements SQLData {

	private final String REQUEST_TYPE = "SETSTATUSRQ";
	private final String RESPONSE_TYPE = "SETSTATUSRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "setStatus";
	
	@Getter @Setter
	private SSEnt_OperationRq request;
	@Getter @Setter
	private SSEnt_BaseRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_SetStatus() {
		response = new SSEnt_BaseRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}

	public SSRepo_SetStatus(SSEnt_OperationRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_SetStatus");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(!ent_Status.isAuthorized()){
            	logr.getLog().trace("Error readSQL, SSRepo_SetStatus");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end readSQL, SSRepo_SetStatus");
      }
      catch(NullPointerException e){
    	  logr.getLog().trace("Pas de données disponible list des SSRepo_SetStatus");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_SetStatus",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_SetStatus");
		stream.writeObject(request.getInitiator());
		stream.writeObject(request.getCardOperation());
		logr.getLog().trace("end writeSQL, SSRepo_SetStatus");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_BaseRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_SetStatus");
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
		                new SqlReturnTypeData(SSRepo_SetStatus.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_SetStatus)jdbcCall.executeObject(SSRepo_SetStatus.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_SetStatus");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_SetStatus",e);
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
