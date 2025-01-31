package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

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
@ToString(callSuper=true)
public class SSEnt_Limit  implements SQLData{
	
	@Schema(description = "0:PAIEMENT, 1:RETRAIT")
	@NotNull(message="ENRL_TRXTYP_EMPTY")
	@Range(min=0,max=1,message="FILTER_NTRANS_VALUE")
	@JsonProperty("typetrx")
	private Integer typeTrx;
	@Schema(description = "0:quotidien, 1:Hebdomadaire, 2:Mensuel, 3:Annuel")
	@NotNull(message="ENRL_PERIOD_EMPTY")
	@Range(min=0,max=3,message="ENRL_PERIOD_VALUE")
	@JsonProperty("periodicity")
	private Integer periodicity;
	@Valid
	@JsonProperty("currency")
	private SSEnt_Currency currency;
	@NotNull(message="LIMIT_CUMUL_EMPTY")
    @DecimalMin(value = "0.0", inclusive = false,message="LIMIT_CUMUL_VALUE")
    @Digits(integer=12, fraction=2,message="LIMIT_CUMUL_LENGHT")
	@JsonProperty("currentlimit")
	private Double currentLimit;
	@Null(message="ENT_LIMIT_MAX")
	@JsonProperty("maxlimit")
	private Double maxLimit;

	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		return "LIMITSTRUCT";
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Limit");
		
		typeTrx = stream.readInt();
		periodicity = stream.readInt();
		currency = new SSEnt_Currency(stream.readInt(),stream.readString());
		currentLimit = stream.readDouble();
		maxLimit = stream.readDouble();
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Limit");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_Limit");
		
		stream.writeInt(typeTrx);
		stream.writeInt(periodicity);
		stream.writeInt((currency == null) ? -1 : currency.getCurrencyIsoNum());
		stream.writeString((currency == null) ? null : currency.getCurrencyIsoAlpha());
		stream.writeDouble(currentLimit);
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_Limit");
	}
}
