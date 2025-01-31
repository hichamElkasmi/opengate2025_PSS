package com.s2m.ss.api.pr.dao;

import static com.s2m.ss.api.pr.entities.SSEnt_DF.getMsg;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.s2m.ss.api.pr.config.SS_LoggingImpl;
import com.s2m.ss.api.pr.entities.SSEnt_Country;
import com.s2m.ss.api.pr.entities.SSEnt_DF;
import com.s2m.ss.api.pr.entities.SSEnt_RestrictedCountries;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitCrdRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_RestCountryListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_RestCountriesList implements SQLData {

	private final String REQUEST_TYPE = "COUNTRYSLISTRQ";
	private final String RESPONSE_TYPE = "COUNTRYSLISTRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "GETCOUNTRYSLIST";

	@Getter @Setter
	private SSEnt_HeadInitCrdRq request;
	@Getter @Setter
	private SSEnt_RestCountryListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_RestCountriesList() {
		response = new SSEnt_RestCountryListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_RestCountriesList(SSEnt_HeadInitCrdRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_CountriesList");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	 logr.getLog().trace("Authorized readSQL, SSRepo_CountriesList");
            	 SSEnt_RestrictedCountries restCountries =  new SSEnt_RestrictedCountries();
            	 restCountries.setRestriction(stream.readInt());
            	 restCountries.setCountries(null);
            	 response.setRestListCountries(restCountries);
            	 if(restCountries.getRestriction()==1)
            	 {
	            	Object[] objects = (Object[]) stream.readArray().getArray();
	        		SSEnt_Country[] countries = Arrays.stream(objects).map(o -> (SSEnt_Country)o).toArray(SSEnt_Country[]::new);
	        		restCountries.setCountries(countries);
            	 }
            	 response.setRestListCountries(restCountries);
            }
            else
            {
            	 logr.getLog().trace("Error readSQL, SSRepo_CountriesList");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end processing, SSRepo_CountriesList");
      }
      catch(NullPointerException e){
    	  logr.getLog().trace("Pas de données disponible list des SSRepo_CountriesList");
  		//	response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_CountriesList",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }	
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		 logr.getLog().trace("start writeSQL, SSRepo_CountriesList");
		stream.writeObject(request.getInitiator());
		 logr.getLog().trace("end writeSQL, SSRepo_CountriesList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_RestCountryListRs processing(DataSource dataSource) {
		try {
			 logr.getLog().trace("start processing, SSRepo_CountriesList");
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
		                new SqlReturnTypeData(SSRepo_RestCountriesList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_RestCountriesList)jdbcCall.executeObject(SSRepo_RestCountriesList.class,in)).getResponse();
			 logr.getLog().trace("end processing, SSRepo_ChannelsList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_CountriesList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("COUNTRYSTRUCT", SSEnt_Country.class);
	    return myMap;
	}
}

