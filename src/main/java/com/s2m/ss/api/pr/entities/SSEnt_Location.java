package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

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
public class SSEnt_Location  implements SQLData{

	@Schema(description = "0:BRANCH, 1:ATM, 2:AGENT, 3:POS")
	@JsonProperty("locationtype")
	private Integer locationType;
	@JsonProperty("longitude")
	private String longitude;
	@JsonProperty("latitude")
	private String latitude;
	@JsonProperty("atm")
	private SSEnt_Atm atm;
	@JsonProperty("pos")
	private SSEnt_Pos pos;
	@JsonProperty("agent")
	private SSEnt_Merchant agent;
	@JsonProperty("branch")
	private SSEnt_Branch branch;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "LOCATIONSTRUCT";
	}
    
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Location");
		locationType = stream.readInt();
		longitude = stream.readString();
		latitude = stream.readString();
		switch (SSEnt_LocationType.values()[locationType]) {
		case BRANCH:
			branch = new SSEnt_Branch();
			stream.readInt();
			branch.setBranchIden(stream.readString());
			branch.setBranchCorpName(stream.readString());
			branch.setBranchPhone(stream.readString());
			branch.setBranchAddr(stream.readString());
			break;
		case ATM:
			atm = new SSEnt_Atm();
			atm.setIden(stream.readString());
			stream.readString();
			atm.setLabel(stream.readString());
			stream.readString();
			atm.setAddress(stream.readString());
			break;
		case AGENT:
			agent = new SSEnt_Merchant();
			stream.readInt();
			agent.setAgentIden(stream.readString());
			agent.setAgentName(stream.readString());
			agent.setAgentPhone(stream.readString());
			agent.setAgentAddr(stream.readString());
			break;
		case POS:
			pos = new SSEnt_Pos();
			pos.setIden(stream.readString());
			stream.readString();
			pos.setLabel(stream.readString());
			stream.readString();
			stream.readString();
			break;
		}
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Location");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
		
	}
}
