package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
public class SSEnt_Transfer extends SSEnt_Request implements SQLData {

	@JsonIgnore
	private SSEnt_TransferType reqType;
	@NotBlank(message = "TOENTITY_EMPTY") 
	@Size(min=1,max=24,message="TOENTITY_LENGHT")
    @Pattern(regexp = "[0-9]+",message="TOENTITY_VALUE")
	@JsonProperty("toentity")
	private String toEntity;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		
		return "TRANSFERSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
	
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_Transfer");
		
		stream.writeInt(reqType.ordinal());
		stream.writeDouble(amount);
		stream.writeString(label);
		stream.writeString(toEntity);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_Transfer");
	}	
}
