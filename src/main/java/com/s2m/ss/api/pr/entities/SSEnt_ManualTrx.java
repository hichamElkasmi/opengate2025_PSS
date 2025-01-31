package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
public class SSEnt_ManualTrx extends SSEnt_Request implements SQLData{


	@Schema(description = "0:débiteur, 1:créditeur")
	@NotNull(message="SNS_EMPTY")
	@Range(min=0,max=1,message="SNS_LENGHT")
	@JsonProperty("action")
	private Integer typeAction;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		
		return "MANUALSTRUCT";
	}
	
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
	}
	
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_ManualTrx");

		stream.writeDouble(amount);
		stream.writeString(label);
		stream.writeInt(typeAction);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_ManualTrx");
	}	
}
