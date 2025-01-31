package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

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
public class SSEnt_City  implements SQLData{
	
	@NotNull(message = "CITY_CODE_EMPTY")
	@Max(value = 9999999999L, message = "CITY_CODE_LENGHT")
	@JsonProperty("citycode")
	private Integer cityCode;
	@JsonProperty("cityiden")
	private String cityIden;
	@JsonProperty("cityname")
	private String cityName;
	@JsonProperty("cityiso")
	private String cityIso;
	

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "CITYSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_City");
		
		cityCode = stream.readInt();
		cityIden = stream.readString();
		cityName = stream.readString();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_City");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_City");
		stream.writeInt(cityCode);
		stream.writeString(null);
		stream.writeString(null);
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_City");
	}
}
