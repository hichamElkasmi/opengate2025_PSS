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

import io.swagger.v3.oas.annotations.media.Schema;
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
public class SSEnt_InitiatorClient extends SSEnt_Initiator implements SQLData {
	
	@Schema(description = "Correspond à la valeur de l’id client sur SS (cus_iden), la liste de clients est partagée entre SS-HOST")
	@NotBlank(message = "INIT_IDENT_EMPTY")
	@Size(min=1,max=20,message="INIT_IDENT_LENGHT")
	@JsonProperty("clientid")
	private String clientId;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {

		return "INITIATOR1RQ";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {	
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_InitiatorClient");

		stream.writeString(String.valueOf(requestId));
		stream.writeString(clientId);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_InitiatorClient");
	}
}
