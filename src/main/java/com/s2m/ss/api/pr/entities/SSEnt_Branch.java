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
public class SSEnt_Branch  implements SQLData{

	@JsonProperty("branchiden")
	private String branchIden;
	@JsonProperty("branchcorpname")
	private String branchCorpName;
	@JsonProperty("branchphone")
	private String branchPhone;
	@JsonProperty("branchaddr")
	private String branchAddr;

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "BRANCHSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Branch");
		
		branchIden = stream.readString();
		branchCorpName = stream.readString();
		branchPhone = stream.readString();
		branchAddr = stream.readString();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Branch");
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {

	}
}
