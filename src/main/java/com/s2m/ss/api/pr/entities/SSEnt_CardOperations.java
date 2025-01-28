package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

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
public class SSEnt_CardOperations implements SQLData {

	@Schema(description = "0:disable, 1:enable")
	@NotNull(message = "CRD_OPER_STATUT")
	@Range(min= 0, max= 1,message="CRD_OPERATION")
	@JsonProperty("status")
	private Short operation;
	@Value("OPERATION FROM MOBILE")
	private String motif;
	
	@JsonIgnore
	private SSEnt_OperationType operationType;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "CARDSTATUSSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {

		
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start writeSQL SSEnt_Address");
		
		stream.writeInt(operation);
		stream.writeInt(operationType.ordinal());
		stream.writeString(motif);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end writeSQL SSEnt_Address");
	}
}
