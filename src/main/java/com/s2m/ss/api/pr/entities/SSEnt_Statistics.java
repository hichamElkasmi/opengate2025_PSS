package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
public class SSEnt_Statistics  implements SQLData{

	@JsonProperty("lastmonthcons")
	private List<SSEnt_StcLastMonthCons> lastMonths;
	@JsonProperty("comparecons")
	private List<SSEnt_StcCompareCons> byCompares;
	@JsonProperty("proportionchannels")
	private List<SSEnt_StcPropByChannel> byChannels;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "STATISTICSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Statistics");
		
		try {
			Object[] objects = (Object[]) stream.readArray().getArray();
			lastMonths = Arrays.stream(objects).map(o -> (SSEnt_StcLastMonthCons)o).collect(Collectors.toList());
		}
		catch(Exception e) {
			
		}
		
		try {
			Object[] objects = (Object[]) stream.readArray().getArray();
			byCompares = Arrays.stream(objects).map(o -> (SSEnt_StcCompareCons)o).collect(Collectors.toList());
		}
		catch(Exception e) {
			
		}
		
		try {
			Object[] objects = (Object[]) stream.readArray().getArray();
			byChannels = Arrays.stream(objects).map(o -> (SSEnt_StcPropByChannel)o).collect(Collectors.toList());
		}
		catch(Exception e) {
			
		}
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Statistics");
		
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
		
	}
}
