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
public class SSEnt_Account implements SQLData{

	@NotBlank(message = "ACCOUNT_EMPTY") 
	@Size(min=1,max=24,message="ACCOUNT_LENGHT")
    @Pattern(regexp = "[0-9]+",message="ACCOUNT_VALUE")
	@JsonProperty("accountnbr")
	private String accountNbr;
	@JsonProperty("available")
	private Double available;
	@Schema(description = "0:débiteur, 1:créditeur")
	@JsonProperty("typeavailable")
	private Integer typeAvailable;
	@JsonProperty("currency")
	private SSEnt_Currency currency;
	@Schema(description = "1:débit, 2:prépayé, 3:épargne, 4:crédit")
	@JsonProperty("accounttype")
	private Integer accountType;
	@Schema(description = "0:inactive, 1:active")
	@JsonProperty("accountstatus")
	private Integer accountStatus;
	@Schema(description = "0:inactive, 1:active")
	@JsonProperty("accountcheck")
	private Integer accountCheck;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "ACCOUNTSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Account");
		accountNbr = stream.readString();
		available = stream.readDouble();
		typeAvailable = stream.readInt();
		currency = new SSEnt_Currency();
		currency.setCurrencyIsoNum(stream.readInt());
		currency.setCurrencyIsoAlpha(stream.readString());
		accountType = stream.readInt();
		accountStatus = stream.readInt();
		accountCheck = stream.readInt();
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Account");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
	}
}
