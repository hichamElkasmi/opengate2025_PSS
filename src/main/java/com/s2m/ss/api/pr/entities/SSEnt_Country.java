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
@ToString(callSuper=true)
public class SSEnt_Country  implements SQLData{
	
	@Schema(description = "identifiant unique du pays sur le CMS (cou_code)")
	@NotNull(message = "CONT_CODE_EMPTY")
	@Max(value = 9999999999L, message = "CONT_CODE_LENGHT")
	@JsonProperty("countrycode")
	private Integer countryCode;
	@JsonProperty("countryiden")
	private String countryIden;
	@JsonProperty("countryname")
	private String countryName;
	@Schema(description = "Code de pays en ISO  ISO ALPHA")
	@JsonProperty("countryiso")
	private String countryIso;
	

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "COUNTRYSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Country");
		
		countryCode = stream.readInt();
		countryIden = stream.readString();
		countryName = stream.readString();
		countryIso = stream.readString();

		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Country");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Country");
		stream.writeInt(countryCode);
		stream.writeString(null);
		stream.writeString(null);
		stream.writeString(null);
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Country");
	}
}
