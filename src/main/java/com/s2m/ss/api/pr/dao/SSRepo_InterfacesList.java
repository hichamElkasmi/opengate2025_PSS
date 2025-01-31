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
import com.s2m.ss.api.pr.entities.SSEnt_Interface;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SelectInterfaceListRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_InterfacesListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_InterfacesList implements SQLData {

	private final String REQUEST_TYPE 	= "INTERFACELISTRQ";
	private final String RESPONSE_TYPE 	= "INTERFACELISTRS";
	
	private final String PACKAGE_SQL 	= SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL 	= "getInterfaceList";

	@Getter @Setter
	private SSEnt_SelectInterfaceListRq request;
	@Getter @Setter
	private SSEnt_InterfacesListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_InterfacesList() {
		response = new SSEnt_InterfacesListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setInterfaces(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_InterfacesList(SSEnt_SelectInterfaceListRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_InterfacesList");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	logr.getLog().trace("Authorized readSQL, SSRepo_InterfacesList");
        			Object[] objects = (Object[]) stream.readArray().getArray();
            		List<SSEnt_Interface> interfaces = Arrays.stream(objects).map(o -> (SSEnt_Interface)o).collect(Collectors.toList());
        			response.setInterfaces(interfaces); 
            }
            else
            {
            	logr.getLog().trace("Error readSQL, SSRepo_InterfacesList");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end readSQL, SSRepo_InterfacesList");
      }
      catch(NullPointerException e){
	    	logr.getLog().trace("Pas de données disponible list des SSRepo_InterfacesList");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_InterfacesList",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_InterfacesList");
		stream.writeObject(request.getInitiator());
		stream.writeObject(request.getFilter());
		logr.getLog().trace("end writeSQL, SSRepo_InterfacesList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_InterfacesListRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_InterfacesList");
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
		                new SqlReturnTypeData(SSRepo_InterfacesList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_InterfacesList)jdbcCall.executeObject(SSRepo_InterfacesList.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_InterfacesList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_InterfacesList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("INTERFACESTRUCT", SSEnt_Interface.class);
	    return myMap;
	}
}
