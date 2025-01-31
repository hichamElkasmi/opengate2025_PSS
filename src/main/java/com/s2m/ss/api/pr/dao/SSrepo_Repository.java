package com.s2m.ss.api.pr.dao;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;



import com.s2m.ss.api.pr.entities.requests.SSEnt_GetRepositoryRq;

import lombok.Getter;
import lombok.Setter;

public class SSrepo_Repository implements SQLData {

	    private final String SCHEMA_SQL = "OPENGATEV2";
	    private final String PACKAGE_SQL = "OPENGATE_REPOSITORY";
	    private final String PROCEDURE_SQL = "GET_REPOSITORY";
	 
	    @Getter @Setter
	    
	    private SSEnt_GetRepositoryRq request;
	 
	    @Getter @Setter
	    private String response;
	 
	    private SS_LoggingImpl logr;
	 
	    public SSrepo_Repository() {
	        logr = new SS_LoggingImpl();
	        logr.setClazz(getClass());
	    }
	 
	    public SSrepo_Repository(SSEnt_GetRepositoryRq request) {
	        this();
	        this.request = request;
	    }
	 
	    @Override
	    public void readSQL(SQLInput stream, String typeName) {
	        try {
	            logr.getLog().trace("Start readSQL, repository");
	            response = stream.readString(); // Read output parameter
	        } catch (NullPointerException e) {
	            logr.getLog().trace("No data available in repository: " + e.getMessage());
	        } catch (Exception e) {
	            logr.getLog().error("Error in readSQL, repository", e);
	        }
	    }
	 
	    @Override
	    public void writeSQL(SQLOutput stream) throws SQLException {
	        try {
	            logr.getLog().trace("Start writeSQL, repository");
	            stream.writeString(request != null ? request.toString() : null); // Map request to SQL structure
	            logr.getLog().trace("End writeSQL, repository");
	        } catch (Exception e) {
	            logr.getLog().error("Error in writeSQL, repository", e);
	        }
	    }
	 
	    @Override
	    public String getSQLTypeName() {
	        // Define the SQL type name
	        return "YOUR_TYPE_NAME"; // Replace with the actual type name used in Oracle DB
	    }
	 
	 
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	public String processing(DataSource dataSource) {
	    String response = null; // Initialize the response variable as String
	    try {
	        logr.getLog().trace("Start processing, repository");
	 
	        ObjectMapper mapper = new ObjectMapper();
	        String jsonRequest = mapper.writeValueAsString(request);
	        logr.getLog().trace("Serialized request: " + jsonRequest);
	 
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
	                .withSchemaName(SCHEMA_SQL)
	                .withCatalogName(PACKAGE_SQL)
	                .withProcedureName(PROCEDURE_SQL)
	                .declareParameters(
	                    new SqlParameter("ref_data_req", Types.VARCHAR), // Input parameter
	                    new SqlOutParameter("ref_data_res", Types.VARCHAR)  // Output parameter changed to VARCHAR
	                );
	 
	        // Pass parameters to procedure
	        Map<String, Object> inParams = Collections.singletonMap("ref_data_req", jsonRequest);
	        logr.getLog().trace("Input parameters: " + inParams);
	 
	        // Execute the procedure and retrieve the output parameter
	        Map<String, Object> outParams = jdbcCall.execute(inParams);
	        response = (String) outParams.get("ref_data_res");
	 
	        // Log the response
	        logr.getLog().trace("End processing, repository. Response: " + response);
	    } catch (Exception e) {
	        logr.getLog().error("Processing error", e);
	    }
	 
	    // Return the String response (instead of CLOB)
	    return response;
	}

	

}
