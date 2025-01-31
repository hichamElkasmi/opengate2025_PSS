package com.s2m.ss.api.pr.entities;


import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SSEnt_InitiatorBank extends SSEnt_Initiator implements SQLData {


	@NotBlank(message = "INIT_BANK_EMPTY")
	@Size(min=1,max=10,message="INIT_BANK_LENGHT")
	@JsonProperty("bankcode")
	private String bankCode;	
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "INITIATOR4RQ";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {	
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_InitiatorBank");

		stream.writeString(String.valueOf(requestId));
		stream.writeString(bankCode);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_InitiatorBank");
	}
}
