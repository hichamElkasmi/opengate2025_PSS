package com.s2m.ss.api.pr.dao;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.getMsg;

import java.sql.Array;
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
import com.s2m.ss.api.pr.entities.SSEnt_Channel;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ChannelChangeRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BaseRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.sql.OracleSQLOutput;

public class SSRepo_SetChannel implements SQLData {

	private final String REQUEST_TYPE = "SETCHANNELRQ";
	private final String RESPONSE_TYPE = "SETCHANNELRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "setChannel";
	
	@Getter @Setter
	private SSEnt_ChannelChangeRq request;
	@Getter @Setter
	private SSEnt_BaseRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_SetChannel() {
		response = new SSEnt_BaseRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_SetChannel(SSEnt_ChannelChangeRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_SetChannel");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(!ent_Status.isAuthorized()){
            	logr.getLog().trace("Error readSQL, SSRepo_SetChannel");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end readSQL, SSRepo_SetChannel");
      }
      catch(NullPointerException e){
	    	logr.getLog().trace("Pas de données disponible list des SSRepo_SetChannel");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_SetChannel",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_SetChannel");
		stream.writeObject(request.getInitiator());
		final OracleSQLOutput out = (OracleSQLOutput) stream;
	    OracleConnection conn = (OracleConnection) out.getSTRUCT().getJavaSqlConnection();
	    
	    final Array array = conn.createOracleArray("CHANNELSRESULT", request.getChannels());
		stream.writeArray(array);
		logr.getLog().trace("end writeSQL, SSRepo_SetChannel");
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_BaseRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_SetChannel");
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
		                new SqlReturnTypeData(SSRepo_SetChannel.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_SetChannel)jdbcCall.executeObject(SSRepo_SetChannel.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_SetChannel");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_SetChannel",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}
	
	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("CHANNELSTRUCT", SSEnt_Channel.class);
	    myMap.put("CHANNELSRESULT", SSEnt_Channel[].class);
	    return myMap;
	}
}
