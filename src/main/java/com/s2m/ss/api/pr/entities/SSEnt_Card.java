package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class SSEnt_Card implements SQLData{

	@JsonProperty("cardcode")
	protected Long cardCode;
	@JsonProperty("pcipan")
	protected String pciPan;
	@NotBlank(message = "CRD_NAME_EMPTY")
	@Size(min=1,max=25,message="CRD_NAME_LENGHT")
	@JsonProperty("nameprinted")
	protected String namePrinted;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "SECONDSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {

	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Card");
		stream.writeString(namePrinted);
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Card");
	}
}
