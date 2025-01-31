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
import com.s2m.ss.api.pr.entities.SSEnt_Atm;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_Pos;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SelectTerminalListRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_TerminalsListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_TerminalsList implements SQLData {

	private final String REQUEST_TYPE 	= "TERMINALLISTRQ";
	private final String RESPONSE_TYPE 	= "TERMINALLISTRS";
	
	private final String PACKAGE_SQL 	= SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL 	= "getTerminalsList";

	@Getter @Setter
	private SSEnt_SelectTerminalListRq request;
	@Getter @Setter
	private SSEnt_TerminalsListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_TerminalsList() {
		response = new SSEnt_TerminalsListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setAtmTerminal(null);
		response.setPosTerminal(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_TerminalsList(SSEnt_SelectTerminalListRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_TerminalsList");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	boolean noDateFound=true;
            	logr.getLog().trace("Authorized readSQL, SSRepo_TerminalsList");
        		try {
        			Object[] objects = (Object[]) stream.readArray().getArray();
            		List<SSEnt_Pos> posList = Arrays.stream(objects).map(o -> (SSEnt_Pos)o).collect(Collectors.toList());
        			response.setPosTerminal(posList); 
        			noDateFound = false;
        		}
        		catch(Exception e) {
        			
        		}
        		try {

        			Object[] objects = (Object[]) stream.readArray().getArray();
            		List<SSEnt_Atm> atmList = Arrays.stream(objects).map(o -> (SSEnt_Atm)o).collect(Collectors.toList());
        			response.setAtmTerminal(atmList); 
        			noDateFound = false;
        		}
        		catch(Exception e) {
        			
        		}
        		
        		if(noDateFound) {
        	    	logr.getLog().trace("Pas de données disponible list des SSRepo_TerminalsList");
          			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
        		}
            }
            else
            {
            	logr.getLog().trace("Error readSQL, SSRepo_TerminalsList");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end readSQL, SSRepo_TerminalsList");
      }
      catch(NullPointerException e){
	    	logr.getLog().trace("Pas de données disponible list des SSRepo_TerminalsList");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_TerminalsList",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_TerminalsList");
		stream.writeObject(request.getInitiator());
		stream.writeObject(request.getFilter());
		logr.getLog().trace("end writeSQL, SSRepo_TerminalsList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_TerminalsListRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_TerminalsList");
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
		                new SqlReturnTypeData(SSRepo_TerminalsList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_TerminalsList)jdbcCall.executeObject(SSRepo_TerminalsList.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_TerminalsList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_TerminalsList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("POSSTRUCT", SSEnt_Pos.class);
	    myMap.put("ATMSTRUCT", SSEnt_Atm.class);
	    return myMap;
	}
}
