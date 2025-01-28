package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.s2m.ss.api.pr.config.SS_LoggingImpl;

public class SSEnt_InitiatorRepo extends SSEnt_Initiator implements SQLData  {

	@JsonProperty("data")
	@Size(min=3 , max=20 , message= "data length")
	@NotBlank(message = "data_empty")
	private String data;
	@NotBlank(message="institution_empty")
	@Size(min=2,max=4,message="institution length")
	@JsonProperty("institution")
	private String institution;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {

		return "INITIATOR2RQ";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {	
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_InitiatorCard");

		stream.writeString(String.valueOf(requestId));
		stream.writeString(data);
		stream.writeString(institution);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_InitiatorCard");
	}
}
