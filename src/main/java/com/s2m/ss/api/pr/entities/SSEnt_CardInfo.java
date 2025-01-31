package com.s2m.ss.api.pr.entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.time.Instant;

import javax.validation.Valid;
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
public class SSEnt_CardInfo extends SSEnt_Card implements SQLData{

	@Valid
	@NotNull(message="ENT_PRODUCT_NULL")
	@JsonProperty("product")
	private SSEnt_Product product;
	@JsonProperty("expirydate")
	private Long expiryDate;
	@JsonProperty("loyaltybalance")
	private Long loyaltyBalance;
	@JsonProperty("cardstatus")
	private SSEnt_CardStatus cardStatus;
	@Valid
	@NotNull(message="ENRL_ADR_COUNT")
	@JsonProperty("caraddr")
	private SSEnt_Address carAddress;
	
	@JsonIgnore
	private SSEnt_CardEventType eventType;
	
	@JsonIgnore
	@Override
	public String getSQLTypeName() throws SQLException {
		String rType = "CARDSTRUCT";
		if(eventType == SSEnt_CardEventType.CREATE_CARD)
			rType = "CREATESTRUCT";

		return rType;
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_CardInfo");
		
		cardCode = (long) stream.readDouble();
		pciPan = stream.readString();
		namePrinted = stream.readString();
		product = new SSEnt_Product();
		product.setProductCode(stream.readInt());
		product.setProductDesc(stream.readString());
		
		try{
            Instant instant = (stream.readTimestamp()).toInstant() ;
            expiryDate =instant.getEpochSecond();
        }catch (Exception ex) {
        	expiryDate = null;
        }
		
		loyaltyBalance = (long) stream.readDouble();
		cardStatus = new SSEnt_CardStatus();
		cardStatus.setActivation((short)stream.readInt());
		cardStatus.setOpposition((short)stream.readInt());
		cardStatus.setPerso((short)stream.readInt());
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_CardInfo");
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		SS_LoggingImpl log = new SS_LoggingImpl();
		log.setClazz(getClass());
		log.getLog().trace("start readSQL SSEnt_CardInfo");
		
		stream.writeString(namePrinted);
		stream.writeInt(Integer.valueOf(product.getProductCode()));
		stream.writeString(carAddress.getAddr1());
		stream.writeString(carAddress.getAddr2());
		stream.writeInt((carAddress.getCountry() == null) ? -1 : carAddress.getCountry().getCountryCode());
		stream.writeString(carAddress.getPostalCode());
		stream.writeString(carAddress.getPhoneNum());
		
		log.getLog().debug(this.toString());
		log.getLog().trace("end readSQL SSEnt_CardInfo");
	}
}
