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
import com.s2m.ss.api.pr.entities.SSEnt_Fee;
import com.s2m.ss.api.pr.entities.SSEnt_Limit;
import com.s2m.ss.api.pr.entities.SSEnt_Product;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitBnkRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_ProductsListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_ProductsList implements SQLData {

	private final String REQUEST_TYPE 	= "PRODUCTSLISTRQ";
	private final String RESPONSE_TYPE 	= "PRODUCTSLISTRS";
	
	private final String PACKAGE_SQL 	= SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL 	= "getProductsList";

	@Getter @Setter
	private SSEnt_HeadInitBnkRq request;
	@Getter @Setter
	private SSEnt_ProductsListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_ProductsList() {
		response = new SSEnt_ProductsListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setProducts(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}
	
	public SSRepo_ProductsList(SSEnt_HeadInitBnkRq request) {
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
    	  logr.getLog().trace("start readSQL, SSRepo_ProductsList");
    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
            if(ent_Status.isAuthorized()){
            	logr.getLog().trace("Authorized readSQL, SSRepo_ProductsList");
        		Object[] objects = (Object[]) stream.readArray().getArray();
        		List<SSEnt_Product> products = Arrays.stream(objects).map(o -> (SSEnt_Product)o).collect(Collectors.toList());
        		response.setProducts(products);
            }
            else
            {
            	logr.getLog().trace("Error readSQL, SSRepo_ProductsList");
                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
            }
            logr.getLog().trace("end readSQL, SSRepo_ProductsList");
      }
      catch(NullPointerException e){
	    	logr.getLog().trace("Pas de données disponible list des SSRepo_ProductsList");
  			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
      }
      catch(Exception e){
	    	logr.getLog().error("readSQL, SSRepo_ProductsList",e);
  			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
      }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_ProductsList");
		stream.writeObject(request.getInitiator());
		logr.getLog().trace("end writeSQL, SSRepo_ProductsList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_ProductsListRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_ProductsList");
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
		                new SqlReturnTypeData(SSRepo_ProductsList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_ProductsList)jdbcCall.executeObject(SSRepo_ProductsList.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_ProductsList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_ProductsList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("PRODUCTSTRUCT", SSEnt_Product.class);
	    myMap.put("LIMITSTRUCT", SSEnt_Limit.class);
	    myMap.put("FEESTRUCT", SSEnt_Fee.class);
	    return myMap;
	}
}
