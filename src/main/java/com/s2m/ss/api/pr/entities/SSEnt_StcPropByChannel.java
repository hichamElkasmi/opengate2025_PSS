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
@ToString(callSuper=true)
public class SSEnt_StcPropByChannel  implements SQLData{
	
	@JsonProperty("channel")
	private SSEnt_Channel channel;
	@JsonProperty("percent")
	private Integer percent;
	@JsonProperty("totalamount")
	private Double sumAmounts;
	@JsonProperty("currency")
	private SSEnt_Currency currency;

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "PROPORTSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_StcPropByChannel");
		
		channel = new SSEnt_Channel(stream.readInt(),stream.readString(),null);
		percent = stream.readInt();
		sumAmounts = stream.readDouble();
		currency = new SSEnt_Currency(stream.readInt(),stream.readString());
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_StcPropByChannel");
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
	}
}
