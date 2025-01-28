package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_ValidateCardPin   {

	
	@JsonProperty("encrypted_pin")
    private String encrypted_pin;
	
	@JsonProperty("institution")
    private String institution;
	
	@JsonProperty("track2")
    private String track2;
	
	
	
	/*
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_Address");
		
		stream.writeString(encrypted_pin);
		stream.writeString(institution);
		stream.writeString(track2);
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_Address");
	}




	@Override
	public String getSQLTypeName() throws SQLException {
	    SS_LoggingImpl log = new SS_LoggingImpl();
	    try {
	        log.getLog().trace("Entering getSQLTypeName");

	        String sqlTypeName = "EXPECTED_SQL_TYPE"; 

	        log.getLog().trace("Returning SQL Type Name: " + sqlTypeName);
	        return sqlTypeName;
	    } catch (Exception e) {
	        log.getLog().error("Error occurred while retrieving SQL type name", e);
	        throw new SQLException("Error retrieving SQL type name: " + e.getMessage(), e);
	    }
	}


	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		
	}*/
}
