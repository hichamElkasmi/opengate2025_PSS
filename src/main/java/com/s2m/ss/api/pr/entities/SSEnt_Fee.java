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
@ToString(callSuper=true)
public class SSEnt_Fee  implements SQLData{
	
	@Schema(description = "0:Frais demande carte, 1:Frais membre, 3:Frais personnalisation, 4:Frais renouvellement, 5:Frais remplacement, 6:Frais recalcul de pin, 7:Frais assurance, 8:Frais autre")
	@JsonProperty("feetype")
	private Integer feeType;
	@JsonProperty("feelabel")
	private String feeLabel;
	@JsonProperty("amount")
	private Double amount;
	@JsonProperty("currency")
	private SSEnt_Currency currency;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "FEESTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Fee");
		
		feeType = stream.readInt();
		feeLabel = stream.readString();
		amount = stream.readDouble();
		stream.readInt();
		stream.readString();
	//	currency = new SSEnt_Currency(stream.readInt(),stream.readString());
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Fee");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
}
