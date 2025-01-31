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
import com.s2m.ss.api.pr.entities.SSEnt_Statistics;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.SSEnt_StcCompareCons;
import com.s2m.ss.api.pr.entities.SSEnt_StcLastMonthCons;
import com.s2m.ss.api.pr.entities.SSEnt_StcPropByChannel;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitCrdRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_StatisticsListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_StatisticsList implements SQLData {

	private final String REQUEST_TYPE 	= "STATISTICSLISTRQ";
	private final String RESPONSE_TYPE 	= "STATISTICSRS";
	
	private final String PACKAGE_SQL 	= SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL 	= "getStatistics";

	@Getter @Setter
	private SSEnt_HeadInitCrdRq request;
	@Getter @Setter
	private SSEnt_StatisticsListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_StatisticsList() {
		response = new SSEnt_StatisticsListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setStatistics(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_StatisticsList(SSEnt_HeadInitCrdRq request) {
		this();
		this.request = request;
	}
	
	@Override
	public String getSQLTypeName() throws SQLException {
		return REQUEST_TYPE;
	}

	@Override
	public void readSQL(SQLInput stream, String typeName) {
		
      try{
    	  logr.getLog().trace("start readSQL, SSRepo_StatisticsList");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	logr.getLog().trace("Authorized readSQL, SSRepo_StatisticsList");
        		SSEnt_Statistics statistics = (SSEnt_Statistics)stream.readObject();
        		response.setStatistics(statistics);
            }
            else
            {
            	logr.getLog().trace("Error readSQL, SSRepo_StatisticsList");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end readSQL, SSRepo_StatisticsList");
      }
      catch(NullPointerException e){
    	  	logr.getLog().trace("Pas de données disponible list des SSRepo_StatisticsList");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_StatisticsList",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_StatisticsList");
		stream.writeObject(request.getInitiator());
		logr.getLog().trace("end writeSQL, SSRepo_StatisticsList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_StatisticsListRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_StatisticsList");
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
		                new SqlReturnTypeData(SSRepo_StatisticsList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_StatisticsList)jdbcCall.executeObject(SSRepo_StatisticsList.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_StatisticsList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_StatisticsList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("STATISTICSTRUCT", SSEnt_Statistics.class);
	    myMap.put("LASTMONTHSTRUCT", SSEnt_StcLastMonthCons.class);
	    myMap.put("BYCOMPARESTRUCT", SSEnt_StcCompareCons.class);
	    myMap.put("PROPORTSTRUCT", SSEnt_StcPropByChannel.class);

	    return myMap;
	}
}
