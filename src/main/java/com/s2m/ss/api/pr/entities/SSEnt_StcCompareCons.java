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
public class SSEnt_StcCompareCons  implements SQLData{
	
	@JsonProperty("channel")
	private SSEnt_Channel channel;
	@JsonProperty("currentmonthsum")
	private Double sumAmountsCurrent;
	@JsonProperty("lastmonthsum")
	private Double sumAmountsLast;
	@JsonProperty("currency")
	private SSEnt_Currency currency;
	@JsonProperty("sumcmp")
	private Double sumAmounts;

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "BYCOMPARESTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_StcCompareCons");
		channel = new SSEnt_Channel(stream.readInt(),stream.readString(),null);
		sumAmountsCurrent = stream.readDouble();
		sumAmountsLast = stream.readDouble();
		currency = new SSEnt_Currency(stream.readInt(),stream.readString());
		sumAmounts = stream.readDouble();
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_StcCompareCons");
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
	}
}
