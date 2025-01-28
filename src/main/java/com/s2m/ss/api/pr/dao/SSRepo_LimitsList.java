package com.s2m.ss.api.pr.dao;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.getMsg;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.s2m.ss.api.pr.config.SS_LoggingImpl;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Limit;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitCrdRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_LimitsListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_LimitsList implements SQLData {

	private final String REQUEST_TYPE = "LIMITSLISTRQ";
	private final String RESPONSE_TYPE = "LIMITSLISTRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "GETLIMITSLIST";

	@Getter @Setter
	private SSEnt_HeadInitCrdRq request;
	@Getter @Setter
	private SSEnt_LimitsListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_LimitsList() {
		response = new SSEnt_LimitsListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_LimitsList(SSEnt_HeadInitCrdRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_LimitsList");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	 logr.getLog().trace("Authorized readSQL, SSRepo_LimitsList");
            	Object[] objects = (Object[]) stream.readArray().getArray();
        		List<SSEnt_Limit> limits = Arrays.stream(objects).map(o -> (SSEnt_Limit)o).collect(Collectors.toList());
        		response.setLimits(limits);
            }
            else
            {
            	 logr.getLog().trace("Error readSQL, SSRepo_LimitsList");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end processing, SSRepo_LimitsList");
      }
      catch(NullPointerException e){
    	  	logr.getLog().trace("Pas de données disponible list des SSRepo_LimitsList");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_LimitsList",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }	
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		 logr.getLog().trace("start writeSQL, SSRepo_LimitsList");
		stream.writeObject(request.getInitiator());
		 logr.getLog().trace("end writeSQL, SSRepo_LimitsList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_LimitsListRs processing(DataSource dataSource) {
		try {
			 logr.getLog().trace("start processing, SSRepo_LimitsList");
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
		                new SqlReturnTypeData(SSRepo_LimitsList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_LimitsList)jdbcCall.executeObject(SSRepo_LimitsList.class,in)).getResponse();
			 logr.getLog().trace("end processing, SSRepo_LimitsList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_LimitsList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	 //   myMap.put("LIMITBASESTRUCT", SSEnt_LimitBase.class);
	    myMap.put("LIMITSTRUCT", SSEnt_Limit.class);
	    return myMap;
	}
}

