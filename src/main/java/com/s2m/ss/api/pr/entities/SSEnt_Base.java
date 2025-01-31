package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

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
public class SSEnt_Base implements SQLData{

	private Long code;
	private String name;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "PRODUCTSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Base");
		
		code = stream.readLong();
		name = stream.readString();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Base");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
}
