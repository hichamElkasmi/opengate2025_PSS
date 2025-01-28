package com.s2m.ss.api.pr.entities;


import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class SSEnt_Initiator implements SQLData {
	
	@JsonIgnore
	protected UUID requestId;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {

		return "INITIATORRQ";
		
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {	
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_Initiator");

		stream.writeString(String.valueOf(requestId));
			
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_Initiator");
	}
}
