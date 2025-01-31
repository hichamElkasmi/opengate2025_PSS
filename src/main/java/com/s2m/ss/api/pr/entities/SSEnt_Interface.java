package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

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
public class SSEnt_Interface  implements SQLData{

	private String iden;
	private String label;
	@JsonProperty("status")
	private Integer intStatus;
	private String primaryIp;
	private Integer primaryPort;
	private String secondaryIp;
	private Integer secondaryPort;
	@JsonProperty("type")
	private String intType;

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "INTERFACESTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Interface");
		
		iden = stream.readString();
		label = stream.readString();
		intStatus = stream.readInt();
		primaryIp = stream.readString();
		primaryPort = stream.readInt();
		secondaryIp = stream.readString();
		secondaryPort = stream.readInt();		
		intType = stream.readString();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Interface");
		
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
		
	}

}
