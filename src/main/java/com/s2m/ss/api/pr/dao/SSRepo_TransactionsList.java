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
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.SSEnt_Transaction;
import com.s2m.ss.api.pr.entities.requests.SSEnt_TransactionsListRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_TransactionsListRs;

import lombok.Getter;
import lombok.Setter;
import oracle.jdbc.OracleTypes;

public class SSRepo_TransactionsList implements SQLData {

	private final String REQUEST_TYPE = "TRANSACTIONSLISTRQ";
	private final String RESPONSE_TYPE = "TRANSACTIONSLISTRS";
	
	private final String PACKAGE_SQL = SSEnt_DF.PACKAGE_SQL;
	private final String PROCEDURE_SQL = "getTransactionsList";

	@Getter @Setter
	private SSEnt_TransactionsListRq request;
	@Getter @Setter
	private SSEnt_TransactionsListRs response;

	SS_LoggingImpl logr; 
	
	public SSRepo_TransactionsList() {
		response = new SSEnt_TransactionsListRs();
		response.setStatus(new SSEnt_Status(getMsg("ST_APPROVED"), getMsg("ER_CLEAN")));
		response.setTransactions(null);
		logr = new SS_LoggingImpl();
		logr.setClazz(getClass());
	}

	public SSRepo_TransactionsList(SSEnt_TransactionsListRq request) {
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
	    	  logr.getLog().trace("start readSQL, SSRepo_TransactionsList");
	    	    SSEnt_Status ent_Status = (SSEnt_Status)stream.readObject();
	            if(ent_Status.isAuthorized()){
	            	logr.getLog().trace("Authorized readSQL, SSRepo_TransactionsList");
	        		Object[] objects = (Object[]) stream.readArray().getArray();
	        		List<SSEnt_Transaction> transactions = Arrays.stream(objects).map(o -> (SSEnt_Transaction)o).collect(Collectors.toList());
	        		response.setTransactions(transactions);
	            }
	            else
	            {
	            	logr.getLog().trace("Error readSQL, SSRepo_TransactionsList");
	                response.setStatus(new SSEnt_Status(getMsg("ST_REJECTED"), 
	                		ent_Status.getErrorCode(), ent_Status.getErrorMsg()));
	            }
	            logr.getLog().trace("end readSQL, SSRepo_TransactionsList");
	        }
	        catch(NullPointerException e){
	        	logr.getLog().trace("Pas de données disponible list des transactions");
	    			response.setStatus(new SSEnt_Status(getMsg("ST_NODATA"), getMsg("ER_CLEAN")));
	        }
	        catch(Exception e){
		    	logr.getLog().error("readSQL, SSRepo_TransactionsList",e);
	    			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_STREAM"), e.getMessage()));
	        }
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		logr.getLog().trace("start writeSQL, SSRepo_TransactionsList");
		stream.writeObject(request.getInitiator());
		stream.writeObject(request.getFilter());
		logr.getLog().trace("end writeSQL, SSRepo_TransactionsList");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SSEnt_TransactionsListRs processing(DataSource dataSource) {
		try {
			logr.getLog().trace("start processing, SSRepo_TransactionsList");
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
		                new SqlReturnTypeData(SSRepo_TransactionsList.class, auxiliaryTypes)));
		    
			Map in = Collections.singletonMap("v_request", this);
			response = ((SSRepo_TransactionsList)jdbcCall.executeObject(SSRepo_TransactionsList.class,in)).getResponse();
			logr.getLog().trace("end processing, SSRepo_TransactionsList");
		}catch (Exception e) {  
			logr.getLog().error("processing, SSRepo_TransactionsList",e);
			response.setStatus(new SSEnt_Status(getMsg("ST_ERROR"), getMsg("ER_PROCESS"), e.getMessage()));
        }
		logr.logDebug("response: ",response);
		return response;
	}

	private Map<String, Class<?>> createMap() {
		Map<String,Class<?>> myMap = new HashMap<String,Class<?>>();
	    myMap.put("STATUSSTRUCT", SSEnt_Status.class);
	    myMap.put("TRANSACTIONSTRUCT", SSEnt_Transaction.class);
	    return myMap;
	}
}
