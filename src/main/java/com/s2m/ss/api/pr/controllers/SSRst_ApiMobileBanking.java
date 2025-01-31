package com.s2m.ss.api.pr.controllers;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.s2m.ss.api.pr.config.SS_Logging;
import com.s2m.ss.api.pr.entities.SSEnt_OperationType;
import com.s2m.ss.api.pr.entities.SSEnt_TransferType;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AccountListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AccountTransfertRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AnonymPrepaidCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AtmMonitoringRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AuthorizationListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_BaseRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_CardTransfertRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_CheckbookRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_CitiesListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_CreateCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_CustomerListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_DebitAppRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_DebitApplicationListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_DebitApplicationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_DebitApplicationValidationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_GetRepositoryRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitBnkRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitClsRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitCrdAccRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitCrdRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_InterTransfertRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_InternetMailOrderStatusRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_LimitChangeRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_LinkAccountCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ManageAddressRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ManualTransactionRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantAppliListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantAppliValidationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantApplicationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantApplicationUpdateRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantCommissionRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantTerminalTransactionsRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantTransactionsRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MerchantUpdateNoApplicationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_MoneySendRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_OperationActivationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_OperationEcomRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_OperationMotoRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_OperationOppositionRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_OperationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PosApplicationListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PosMonitoringRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PosTerminalApplicationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PosTerminalListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PrepaidAppliValidationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PrepaidApplicationListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_PrepaidApplicationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ProgramRiskRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_RedemptionRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ReloadRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_RenewRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ReplaceCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ReplacementRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ResetPinRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SelectInterfaceListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SelectTerminalListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SendPinByEmailRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SendPinBySmsRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SetCreditLimitRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SetCustomerInfoRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_SmsAlertRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_StatusMerchantRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_TerminalsByMerchantRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_TransactionsListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_TransfertRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_UpdateAccountRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_UpdateMobileRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ValidateCardPinRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_VirtualCardCreationRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_VoidCardlessRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_cardlistRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_changestatusRq;
import com.s2m.ss.api.pr.entities.responses.SSEnt_AccountsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_AuthorisationRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BalanceRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BanksListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_BaseRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_CardInfoRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_CardsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_ChecksListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_CitiesListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_CountriesListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_CustomerInfoRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_FeesListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_InterfacesListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_LimitsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_LocationsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_MiniStateListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_ProductsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_ReasonsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_TerminalsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_TransactionsListRs;
import com.s2m.ss.api.pr.service.SSSrv_ApiMobileBanking;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(info = @Info(title = "SS PARTNER API", description = "Select System PARTNER API S2M@2021", version = "v1.0", license = @License(name = "S2M@2021", url = "https://s2mworldwide.com")))
@RestController
@Validated
@Tag(name="API LIST")
public class SSRst_ApiMobileBanking {
	
	@Autowired
	SSSrv_ApiMobileBanking service;
	
	@Autowired
	SS_Logging log;   
	
	@PostConstruct
	public void initvals() {
		log.setClazz(getClass());
	}
	
	@Operation(summary = "allows to get from CMS the cards list of a customer")
	//@RolesAllowed({"GETCARDSLIST", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcardslist")
	public SSEnt_CardsListRs getCardsList(@RequestBody  @Valid SSEnt_HeadInitClsRq headInitReq) {
		log.getLog().trace("start getCardsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_CardsListRs ent_CardsListRs = service.getCardsList(headInitReq);
		log.logDebug("ent_CardsListRs: ",ent_CardsListRs);
		log.getLog().trace("end getCardsList");
		return ent_CardsListRs;
	}
	
	@Operation(summary = "allows to get from CMS the card info")
	@RolesAllowed({"GETCARDINFO", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcardinfo")
	public SSEnt_CardInfoRs getCardInfo(@RequestBody @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getCardInfo");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_CardInfoRs ent_CardInfoRs = service.getCardInfo(headInitReq);
		log.logDebug("ent_CardInfoRs: ",ent_CardInfoRs);
		log.getLog().trace("end getCardInfo");
		return ent_CardInfoRs;
	}
	
	@Operation(summary = "allows to get from CMS the customer info")
	@RolesAllowed({"GETCUSTOMERINFO", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcustomerinfo")
	public SSEnt_CustomerInfoRs getCustomerInfo(@RequestBody  @Valid SSEnt_HeadInitClsRq headInitReq) {
		log.getLog().trace("start getCustomerInfo");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_CustomerInfoRs ent_CustomerInfoRs = service.getCustomerInfo(headInitReq);
		log.logDebug("ent_CustomerInfoRs: ",ent_CustomerInfoRs);
		log.getLog().trace("end getCustomerInfo");
		return ent_CustomerInfoRs;
	}
	
	@Operation(summary = "allows to get from CMS the card transactions list")
	@RolesAllowed({"GETTRANSACTIONSLIST", "ALL_API_PARTNER"})
	@PostMapping(path="/api/gettransactionslist")
	public SSEnt_TransactionsListRs getTransactionsList(@RequestBody  @Valid SSEnt_TransactionsListRq transactionsListReq) {
		log.getLog().trace("start getTransactionsList");
		log.logDebug("transactionsListReq: ",transactionsListReq);
		SSEnt_TransactionsListRs ent_TransactionsListRs = service.getTransactionsList(transactionsListReq);
		log.logDebug("ent_TransactionsListRs: ",ent_TransactionsListRs);
		log.getLog().trace("end getTransactionsList");
		return ent_TransactionsListRs;
	}
	
	@Operation(summary = "allows to get the mini statement(with balance) for a card from the CMS (through the host need to check availability of the service)")
	@RolesAllowed({"GETMINISTATE", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getministate")
	public SSEnt_MiniStateListRs getMiniState(@RequestBody  @Valid SSEnt_HeadInitCrdAccRq headInitReq) {
		log.getLog().trace("start getMiniState");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_MiniStateListRs ent_MiniStateListRs = service.getMiniState(headInitReq);
		log.logDebug("ent_MiniStateListRs: ",ent_MiniStateListRs);
		log.getLog().trace("end getMiniState");
		return ent_MiniStateListRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to generate a new PIN")
	@RolesAllowed({"REQUESTPIN", "ALL_API_PARTNER"})
	@PostMapping(path="/api/requestpin")
	public SSEnt_BaseRs setRequestPin(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start setRequestPin");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_BaseRs ent_BaseRs = service.setRequestPin(headInitReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setRequestPin");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to renew his card")
	@RolesAllowed({"SETRENEWCARD", "ALL_API_PARTNER"})
	@PostMapping(path="/api/setrenewcard")
	public SSEnt_BaseRs setRenewCard(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start setRenewCard");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_BaseRs ent_BaseRs = service.setRenewCard(headInitReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setRenewCard");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to get the accounts list for card from the CMS")
	@RolesAllowed({"GETACCOUNTSLIST", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getaccountslist")
	public SSEnt_AccountsListRs getAccountslist(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getAccountslist");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_AccountsListRs ent_AccountsListRs = service.getAccountslist(headInitReq);
		log.logDebug("ent_AccountsListRs: ",ent_AccountsListRs);
		log.getLog().trace("end getAccountslist");
		return ent_AccountsListRs;
	}
	
	@Operation(summary = "allows to get the check book accounts list (with checkbook sizes) for card from the CMS")
	@RolesAllowed({"GETCHECKACCOUNTSLIST", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcheckaccountslist")
	public SSEnt_AccountsListRs getCheckAccountslist(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getAccountslist");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_AccountsListRs ent_AccountsListRs = service.getCheckAccountslist(headInitReq);
		log.logDebug("ent_AccountsListRs: ",ent_AccountsListRs);
		log.getLog().trace("end getAccountslist");
		return ent_AccountsListRs;
	}
	/*
	@Operation(summary = "allows to get from CMS the card transactions statistics")
	@RolesAllowed({"GETCARDTRXSTATISTICS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcardtrxstatistics")
	public SSEnt_StatisticsListRs getStatisticsList(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getStatisticsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_StatisticsListRs ent_StatisticsListRs = service.getStatisticsList(headInitReq);
		log.logDebug("ent_StatisticsListRs: ",ent_StatisticsListRs);
		log.getLog().trace("end getStatisticsList");
		return ent_StatisticsListRs;
	}
	*/
	@Operation(summary = "allows to get from CMS the card limits for payments and withdrawals in every periodicity(Daily, Weekly,Monthly)")
	@RolesAllowed({"GETCARDLIMITS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcardlimits")
	public SSEnt_LimitsListRs getLimitsList(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getLimitsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_LimitsListRs ent_LimitsListRs = service.getLimitsList(headInitReq);
		log.logDebug("ent_LimitsListRs: ",ent_LimitsListRs);
		log.getLog().trace("end getLimitsList");
		return ent_LimitsListRs;
	}
	/*
	@Operation(summary = "allows to retrieve the country list for card restrictions from the CMS")
	@RolesAllowed({"GETRESTRICTEDCOUNTRY", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getrestrictedcountry")
	public SSEnt_RestCountryListRs getRestrictedCountry(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getRestrictedCountry");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_RestCountryListRs ent_RestCountryListRs = service.getRestrictedCountry(headInitReq);
		log.logDebug("ent_RestCountryListRs: ",ent_RestCountryListRs);
		log.getLog().trace("end getRestrictedCountry");
		return ent_RestCountryListRs;
	}
	
	@Operation(summary = "allows to get from CMS the available channels for the card and the status of each channel")
	@RolesAllowed({"GETCARDCHANNELS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcardchannels")
	public SSEnt_ChannelsListRs getChannelsList(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getChannelsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_ChannelsListRs ent_ChannelsListRs = service.getChannelsList(headInitReq);
		log.logDebug("ent_ChannelsListRs: ",ent_ChannelsListRs);
		log.getLog().trace("end getChannelsList");
		return ent_ChannelsListRs;
	}*/
	
	@Operation(summary = "allows to activate / deactivate a card in CMS")
	@RolesAllowed({"UPDATECARDACTIVATION", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updatecardactivation")
	public SSEnt_BaseRs setActivation(@RequestBody  @Valid SSEnt_OperationActivationRq activationRq) {
		log.getLog().trace("start setActivation");
		log.logDebug("activationRq: ",activationRq);
		SSEnt_OperationRq operationRq = new SSEnt_OperationRq(activationRq.getActivation(), activationRq.getInitiator(),
				activationRq.getHeader());
		SSEnt_BaseRs ent_BaseRs = service.setStatus(operationRq, SSEnt_OperationType.ACTIVATION);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setActivation");
		return ent_BaseRs;
	}
/*
	@Operation(summary = "allows to send to CMS the customer request for a secondary")
	@RolesAllowed({"ADDSECONDARYCARD", "ALL_API_PARTNER"})
	@PostMapping(path="/api/addsecondarycard")
	public SSEnt_BaseRs setAddSecCard(@RequestBody  @Valid SSEnt_AddSecCardRq addSecCardReq) {
		log.getLog().trace("start setAddSecCard");
		log.logDebug("addSecCardReq: ",addSecCardReq);
		SSEnt_BaseRs ent_BaseRs = service.setAddSecCard(addSecCardReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setAddSecCard");
		return ent_BaseRs;
	}
	*/
	@Operation(summary = "allows to send to CMS the customer request to replace his card")
	@RolesAllowed({"REPLACECARD", "ALL_API_PARTNER"})
	@PostMapping(path="/api/replacecard")
	public SSEnt_BaseRs setReplaceCard(@RequestBody  @Valid SSEnt_ReplaceCardRq replaceCardReq) {
		log.getLog().trace("start setReplaceCard");
		log.logDebug("replaceCardReq: ",replaceCardReq);
		SSEnt_BaseRs ent_BaseRs = service.setReplaceCard(replaceCardReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setReplaceCard");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to get a new CheckBook (through the host need to check availability of the service)")
	@RolesAllowed({"REQUESTCHECKBOOK", "ALL_API_PARTNER"})
	@PostMapping(path="/api/requestcheckbook")
	public SSEnt_BaseRs setcheckbook(@RequestBody  @Valid SSEnt_CheckbookRq checkBookReq) {
		log.getLog().trace("start setReplaceCard");
		log.logDebug("checkBookReq: ",checkBookReq);
		SSEnt_BaseRs ent_BaseRs = service.setCheckbook(checkBookReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setReplaceCard");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to add / remove a card from the opposition list in CMS")
	@RolesAllowed({"UPDATECARDOPPOSITION", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updatecardopposition")
	public SSEnt_BaseRs setOpposition(@RequestBody  @Valid SSEnt_OperationOppositionRq oppositionRq) {
		log.getLog().trace("start setOpposition");
		log.logDebug("oppositionRq: ",oppositionRq);
		
		SSEnt_OperationRq operationRq = new SSEnt_OperationRq(oppositionRq.getOpposition(), oppositionRq.getInitiator(),
				oppositionRq.getHeader());
		SSEnt_BaseRs ent_BaseRs = service.setStatus(operationRq, SSEnt_OperationType.OPPOSITION);
		
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setOpposition");
		
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to activate / deactivate moto service for a card in CMS")
	@RolesAllowed({"UPDATEMOTOSTATUS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updatemotostatus")
	public SSEnt_BaseRs updateMotoStatus(@RequestBody  @Valid SSEnt_OperationMotoRq motoRq) {
		log.getLog().trace("start updateMotoStatus");
		log.logDebug("motoRq: ",motoRq);
		
		SSEnt_OperationRq operationRq = new SSEnt_OperationRq(motoRq.getMoto(), motoRq.getInitiator(),
				motoRq.getHeader());
		SSEnt_BaseRs ent_BaseRs = service.setStatus(operationRq, SSEnt_OperationType.MOTO);
		
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end updateMotoStatus");
		
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to activate / deactivate ecom service for a card in CMS")
	@RolesAllowed({"UPDATECARDECOM", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updatecardecom")
	public SSEnt_BaseRs setPayment(@RequestBody  @Valid SSEnt_OperationEcomRq ecomRq) {
		log.getLog().trace("start setPayment");
		log.logDebug("ecomRq: ",ecomRq);
		
		SSEnt_OperationRq operationRq = new SSEnt_OperationRq(ecomRq.getEcom(), ecomRq.getInitiator(),
				ecomRq.getHeader());
		
		SSEnt_BaseRs ent_BaseRs = service.setStatus(operationRq, SSEnt_OperationType.PAYEMENT);
		
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setPayment");
		
		return ent_BaseRs;
		
	}
	
	@Operation(summary = "allows to update in CMS the card limits for payments and withdrawals in each periodicity(Daily, Weekly,Monthly)")
	@RolesAllowed({"UPDATECARDLIMITS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updatecardlimits")
	public SSEnt_BaseRs setLimits(@RequestBody  @Valid SSEnt_LimitChangeRq limitChangeReq) {
		log.getLog().trace("start setLimits");
		log.logDebug("limitChangeReq: ",limitChangeReq);
		SSEnt_BaseRs ent_BaseRs = service.setLimits(limitChangeReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setLimits");
		return ent_BaseRs;
	}
/*
	@Operation(summary = "allows to activate/deactivate the country restriction in the CMS")
	@RolesAllowed({"UPDATERESTRICTEDCOUNTRY", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updaterestrictedcountry")
	public SSEnt_BaseRs setRestrictedcountry(@RequestBody   @Valid SSEnt_RestCountryChangeRq restCountryChangeReq) {
		log.getLog().trace("start setRestrictedcountry");
		log.logDebug("restCountryChangeReq: ",restCountryChangeReq);
		SSEnt_BaseRs ent_BaseRs = service.setRestrictedcountry(restCountryChangeReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setRestrictedcountry");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to update in the CMS channels status for the card")
	@RolesAllowed({"UPDATECARDCHANNELS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/updatecardchannels")
	public SSEnt_BaseRs setChannels(@RequestBody  @Valid SSEnt_ChannelChangeRq channelChangeReq) {
		log.getLog().trace("start setChannels");
		log.logDebug("channelChangeReq: ",channelChangeReq);
		SSEnt_BaseRs ent_BaseRs = service.setChannels(channelChangeReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setChannels");
		return ent_BaseRs;
	}*/
	
	@Operation(summary = "allows to get the balance for a card(choose account) from the CMS (through the host need to check availability of the service)")
	@RolesAllowed({"GETBALANCE", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getbalance")
	public SSEnt_BalanceRs getBalance(@RequestBody  @Valid SSEnt_HeadInitCrdAccRq headInitReq) {
		log.getLog().trace("start getBalance");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_BalanceRs ent_BalanceRs = service.getBalance(headInitReq);
		log.logDebug("ent_BalanceRs: ",ent_BalanceRs);
		log.getLog().trace("end getBalance");
		return ent_BalanceRs;
	}

	@Operation(summary = "allows to send to CMS the customer request to create a new card")
	@RolesAllowed({"CREATENEWCARD", "ALL_API_PARTNER"})
	@PostMapping(path="/api/createnewcard")
	public SSEnt_BaseRs setCreateCard(@RequestBody  @Valid SSEnt_CreateCardRq createCardReq) {
		log.getLog().trace("start setCreateCard");
		log.logDebug("createCardReq: ",createCardReq);
		SSEnt_BaseRs ent_BaseRs = service.setCreateCard(createCardReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setCreateCard");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to create a debit app")
	@RolesAllowed({"REQUESTNEWCARD", "ALL_API_PARTNER"})
	@PostMapping(path="/api/requestnewcard")
	public SSEnt_BaseRs setReqNewCard(@RequestBody  @Valid SSEnt_DebitAppRq debitAppRq) {
		log.getLog().trace("start setReqNewCard");
		log.logDebug("debitAppRq: ",debitAppRq);
		SSEnt_BaseRs ent_BaseRs = service.setReqNewCard(debitAppRq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setReqNewCard");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to get from CMS the list of products")
	@RolesAllowed({"GETCMSPRODUCTS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmsproducts")
	public SSEnt_ProductsListRs getProductsList(@RequestBody  @Valid SSEnt_HeadInitBnkRq headInitReq) {
		log.getLog().trace("start getProductsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_ProductsListRs productsListRs = service.getProductsList(headInitReq);
		log.logDebug("productsListRs: ",productsListRs);
		log.getLog().trace("end getProductsList");
		return productsListRs;
	}

	@Operation(summary = "allows to get from CMS the list of reasons")
	@RolesAllowed({"GETCMSOPERATIONREASONS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmsoperationreasons")
	public SSEnt_ReasonsListRs getReasonsList(@RequestBody  @Valid SSEnt_HeadInitBnkRq headInitReq) {
		log.getLog().trace("start getReasonsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_ReasonsListRs reasonsListRs = service.getReasonsList(headInitReq);
		log.logDebug("reasonsListRs: ",reasonsListRs);
		log.getLog().trace("end getReasonsList");
		return reasonsListRs;
	}

	@Operation(summary = "allows to get from CMS the list of check book sizes")
	@RolesAllowed({"GETCMSCHECKSIZES", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmschecksizes")
	public SSEnt_ChecksListRs getChecksList(@RequestBody  @Valid SSEnt_HeadInitBnkRq headInitReq) {
		log.getLog().trace("start getChecksList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_ChecksListRs checksListRs = service.getChecksList(headInitReq);
		log.logDebug("checksListRs: ",checksListRs);
		log.getLog().trace("end getChecksList");
		return checksListRs;
	}

	@Operation(summary = "allows to get from CMS the list of countries")
	@RolesAllowed({"GETCMSCOUNTRIES", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmscountries")
	public SSEnt_CountriesListRs getCountriesList(@RequestBody  @Valid SSEnt_HeadInitBnkRq headInitReq) {
		log.getLog().trace("start getCountriesList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_CountriesListRs countriesListRs = service.getCountriesList(headInitReq);
		log.logDebug("countriesListRs: ",countriesListRs);
		log.getLog().trace("end getCountriesList");
		return countriesListRs;
	}
	
	@Operation(summary = "allows to get from CMS the list of cities for a country")
	@RolesAllowed({"GETCMSCITIES", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmscities")
	public SSEnt_CitiesListRs getCmsCities(@RequestBody  @Valid SSEnt_CitiesListRq ent_CitiesListRq) {
		log.getLog().trace("start getCmsCities");
		log.logDebug("ent_CitiesListRq: ",ent_CitiesListRq);
		SSEnt_CitiesListRs citiesListRs = service.getCitiesList(ent_CitiesListRq);
		log.logDebug("citiesListRs: ",citiesListRs);
		log.getLog().trace("end getCmsCities");
		return citiesListRs;
	}
	
	@Operation(summary = "allows to get from CMS the cards list of a customer")
	@RolesAllowed({"PINGAPI", "ALL_API_PARTNER"})
	@PostMapping(path="/api/pingapi")
	public SSEnt_BaseRs pingApi(@RequestBody  @Valid SSEnt_BaseRq baseReq) {
		log.getLog().trace("start pingApi");
		log.logDebug("baseReq: ",baseReq);
		SSEnt_BaseRs ent_BaseRs = service.pingApi(baseReq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end pingApi");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to get branch, ATM and POS terminal location from CMS")
	@RolesAllowed({"GETCMSLOCATION", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmslocation")
	public SSEnt_LocationsListRs getLocationsList(@RequestBody  @Valid SSEnt_HeadInitBnkRq headInitReq) {
		log.getLog().trace("start getLocationsList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_LocationsListRs locationsListRs = service.getLocationsList(headInitReq);
		log.logDebug("locationsListRs: ",locationsListRs);
		log.getLog().trace("end getLocationsList");
		return locationsListRs;
	}
	
	@Operation(summary = "allows to get from CMS the list of banks")
	@RolesAllowed({"GETCMSBANKS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmsbanks")
	public SSEnt_BanksListRs getBanksList(@RequestBody  @Valid SSEnt_BaseRq baseRq) {
		log.getLog().trace("start getBanksList");
		log.logDebug("baseRq: ",baseRq);
		SSEnt_BanksListRs banksListRs = service.getBanksList(baseRq);
		log.logDebug("banksListRs: ",banksListRs);
		log.getLog().trace("end getBanksList");
		return banksListRs;
	}
	
	@Operation(summary = "allows to tranfer money from card/account to an account (same cardholder)")
	@RolesAllowed({"INTERTRANSFER", "ALL_API_PARTNER"})
	@PostMapping(path="/api/intertransfer")
	public SSEnt_AuthorisationRs interTransfer(@RequestBody  @Valid SSEnt_InterTransfertRq interTransfertRq) {
		log.getLog().trace("start interTransfer");
		log.logDebug("interTransfertRq: ",interTransfertRq);
		SSEnt_TransfertRq transfertRq = new SSEnt_TransfertRq(interTransfertRq.getHeader(), interTransfertRq.getInitiator(), interTransfertRq.getTransferTrx());
		SSEnt_AuthorisationRs ent_TransfertRs = service.reqTransfert(transfertRq, SSEnt_TransferType.INTERTRANSFER);
		log.logDebug("ent_TransfertRs: ",ent_TransfertRs);
		log.getLog().trace("end interTransfer");
		return ent_TransfertRs;
	}
	
	@Operation(summary = "allows to reload a prepaid card from an account/card")
	@RolesAllowed({"RECHARGEPRP", "ALL_API_PARTNER"})
	@PostMapping(path="/api/rechargeprp")
	public SSEnt_AuthorisationRs rechargePrp(@RequestBody  @Valid SSEnt_ReloadRq reloadRq) {
		log.getLog().trace("start rechargePrp");
		log.logDebug("reloadRq: ",reloadRq);
		SSEnt_TransfertRq transfertRq = new SSEnt_TransfertRq(reloadRq.getHeader(), reloadRq.getInitiator(), reloadRq.getTransferTrx());
		SSEnt_AuthorisationRs ent_TransfertRs = service.reqTransfert(transfertRq, SSEnt_TransferType.RELOADPRP);
		log.logDebug("ent_TransfertRs: ",ent_TransfertRs);
		log.getLog().trace("end rechargePrp");
		return ent_TransfertRs;
	}
	
	@Operation(summary = "allows to send money to a beneficiary")
	@RolesAllowed({"MONEYSEND", "ALL_API_PARTNER"})
	@PostMapping(path="/api/moneysend")
	public SSEnt_AuthorisationRs moneySend(@RequestBody  @Valid SSEnt_MoneySendRq moneySendRq) {
		log.getLog().trace("start moneySend");
		log.logDebug("moneySendRq: ",moneySendRq);
		SSEnt_AuthorisationRs ent_AuthorisationRs = service.reqMoneySend(moneySendRq);
		log.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		log.getLog().trace("end moneySend");
		return ent_AuthorisationRs;
	}

	@Operation(summary = "allows to update the customer info")
	@RolesAllowed({"SETCUSTOMERINFO", "ALL_API_PARTNER"})
	@PostMapping(path="/api/setcustomerinfo")
	public SSEnt_BaseRs setCustomerInfo(@RequestBody  @Valid SSEnt_SetCustomerInfoRq customerInfoRq) {
		log.getLog().trace("start setCustomerInfo");
		log.logDebug("enrollmentReq: ",customerInfoRq);
		SSEnt_BaseRs ent_BaseRs = service.setCustomerInfo(customerInfoRq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end setCustomerInfo");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to get from CMS the Terminals list selection")
	@RolesAllowed({"GETTERMINALLIST", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmsterminals")
	public SSEnt_TerminalsListRs getCmsTerminals(@RequestBody  @Valid SSEnt_SelectTerminalListRq selectTerminalListRq) {
		log.getLog().trace("start getCmsTerminals");
		log.logDebug("selectTerminalListRq: ",selectTerminalListRq);
		SSEnt_TerminalsListRs terminalsListRs = service.getTerminalList(selectTerminalListRq);
		log.logDebug("terminalsListRs: ",terminalsListRs);
		log.getLog().trace("end getCmsTerminals");
		return terminalsListRs;
	}

	@Operation(summary = "allows to get from CMS the interfaces list selection")
	@RolesAllowed({"GETCMSINTERFACES", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getcmsinterfaces")
	public SSEnt_InterfacesListRs getCmsInterfaces(@RequestBody  @Valid SSEnt_SelectInterfaceListRq selectListRq) {
		log.getLog().trace("start getCmsInterfaces");
		log.logDebug("selectListRq: ",selectListRq);
		SSEnt_InterfacesListRs interfacesListRs = service.getInterfaceList(selectListRq);
		log.logDebug("interfacesListRs: ",interfacesListRs);
		log.getLog().trace("end getCmsInterfaces");
		return interfacesListRs;
	}

	@Operation(summary = "allows to get from CMS the card fees list")
	@RolesAllowed({"GETFEESLIST", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getfeeslist")
	public SSEnt_FeesListRs getFeesList(@RequestBody  @Valid SSEnt_HeadInitCrdRq headInitReq) {
		log.getLog().trace("start getFeesList");
		log.logDebug("headInitReq: ",headInitReq);
		SSEnt_FeesListRs feesListRs = service.getFeesList(headInitReq);
		log.logDebug("feesListRs: ",feesListRs);
		log.getLog().trace("end getFeesList");
		return feesListRs;
	}

	
	@Operation(summary = "allows to transfer money from card/account to debit card")
	@RolesAllowed({"CARDTRANSFER", "ALL_API_PARTNER"})
	@PostMapping(path="/api/cardtransfer")
	public SSEnt_AuthorisationRs cardTransfer(@RequestBody  @Valid SSEnt_CardTransfertRq cardTransfertRq) {
		log.getLog().trace("start cardtransfer");
		log.logDebug("cardTransfertRq: ",cardTransfertRq);
		SSEnt_TransfertRq transfertRq = new SSEnt_TransfertRq(cardTransfertRq.getHeader(), cardTransfertRq.getInitiator(), cardTransfertRq.getTransferTrx());
		SSEnt_AuthorisationRs ent_TransfertRs = service.reqTransfert(transfertRq, SSEnt_TransferType.CARDRANSFER);
		log.logDebug("ent_TransfertRs: ",ent_TransfertRs);
		log.getLog().trace("end cardtransfer");
		return ent_TransfertRs;
	}
	
	
	@Operation(summary = "allows to transfer money from card/account to debit account")
	@RolesAllowed({"ACCOUNTTRANSFER", "ALL_API_PARTNER"})
	@PostMapping(path="/api/accounttransfer")
	public SSEnt_AuthorisationRs accountTransfer(@RequestBody  @Valid SSEnt_AccountTransfertRq accountTransfertRq) {
		log.getLog().trace("start accounttransfer");
		log.logDebug("accountTransfertRq: ",accountTransfertRq);
		SSEnt_TransfertRq transfertRq = new SSEnt_TransfertRq(accountTransfertRq.getHeader(), accountTransfertRq.getInitiator(), accountTransfertRq.getTransferTrx());
		SSEnt_AuthorisationRs ent_TransfertRs = service.reqTransfert(transfertRq, SSEnt_TransferType.ACCOUNTTRANSFER);
		log.logDebug("ent_TransfertRs: ",ent_TransfertRs);
		log.getLog().trace("end accounttransfer");
		return ent_TransfertRs;
	}
	
	
	@Operation(summary = "allows to send to CMS the customer request to link the card with an account")
	@RolesAllowed({"LINKACCOUNTTOCARD", "ALL_API_PARTNER"})
	@PostMapping(path="/api/linkaccounttocard")
	public SSEnt_BaseRs linkAccountToCard(@RequestBody  @Valid SSEnt_LinkAccountCardRq linkAccountCardRq) {
		log.getLog().trace("start linkAccountToCard");
		log.logDebug("linkAccountCardRq: ",linkAccountCardRq);
		SSEnt_BaseRs ent_BaseRs = service.linkAccountToCard(linkAccountCardRq);
		log.logDebug("ent_BaseRs: ",ent_BaseRs);
		log.getLog().trace("end linkAccountToCard");
		return ent_BaseRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to process a manual transaction")
	@RolesAllowed({"MANUALTRANSACTION", "ALL_API_PARTNER"})
	@PostMapping(path="/api/manualtransaction")
	public SSEnt_AuthorisationRs manualtransaction(@RequestBody  @Valid SSEnt_ManualTransactionRq manualTransactionRq) {
		log.getLog().trace("start manualtransaction");
		log.logDebug("manualTransactionRq: ",manualTransactionRq);
		SSEnt_AuthorisationRs ent_AuthorisationRs = service.reqManualTrx(manualTransactionRq);
		log.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		log.getLog().trace("end manualtransaction");
		return ent_AuthorisationRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to change the credit limit of a credit card")
	@RolesAllowed({"SETCREDITLIMIT", "ALL_API_PARTNER"})
	@PostMapping(path="/api/setcreditlimit")
	public SSEnt_AuthorisationRs setCreditLimit(@RequestBody  @Valid SSEnt_SetCreditLimitRq creditLimitRq) {
		log.getLog().trace("start setCreditLimit");
		log.logDebug("creditLimitRq: ",creditLimitRq);
		SSEnt_AuthorisationRs ent_AuthorisationRs = service.setCreditLimit(creditLimitRq);
		log.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		log.getLog().trace("end setCreditLimit");
		return ent_AuthorisationRs;
	}
	
	@Operation(summary = "allows to send to CMS the customer request to process a void cardless transaction")
	@RolesAllowed({"VOIDCARDLESS", "ALL_API_PARTNER"})
	@PostMapping(path="/api/voidcardless")
	public SSEnt_BaseRs voidCardless(@RequestBody  @Valid SSEnt_VoidCardlessRq voidCardlessRq) {
		log.getLog().trace("start voidCardless");
		log.logDebug("voidCardlessRq: ",voidCardlessRq);
		SSEnt_BaseRs baseRs = service.reqVoidCardless(voidCardlessRq);
		log.logDebug("baseRs: ",baseRs);
		log.getLog().trace("end voidCardless");
		return baseRs;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the repository details.")
	//@RolesAllowed({"GETREPOSITORY", "ALL_API_PARTNER"})
	@PostMapping(path="/api/getrepository")
	public String GetRepository(@RequestBody  @Valid SSEnt_GetRepositoryRq getrepositoty) {
		log.getLog().trace("start GetRepository");
		log.logDebug("GetRepository: ",getrepositoty);
		String repo = service.getRepository(getrepositoty);
		log.logDebug("GetRepository: ",repo);
		log.getLog().trace("end GetRepository");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to change the status of a specified entity")
	//@RolesAllowed({"changestatut", "ALL_API_PARTNER"})
	@PostMapping(path="/api/changestatut")
	public String GetChangeStt(@RequestBody   SSEnt_changestatusRq  changestatut) {
		log.getLog().trace("start changestatut");
		log.logDebug("changestatut: ",changestatut);
		String repo = service.changestatus(changestatut);
		log.logDebug("changestatut: ",repo);
		log.getLog().trace("end changestatut");
		return repo;
	}
	
	@Operation(summary = " Allows sending a request to the CMS to validate the card PIN entered by the customer")
	//@RolesAllowed({"validateCardPin", "ALL_API_PARTNER"})
	@PostMapping(path="/api/validateCardPin")
	public String ValidateCardPin(@RequestBody   SSEnt_ValidateCardPinRq  validateCardPin) {
		log.getLog().trace("start validateCardPin");
		log.logDebug("validateCardPin: ",validateCardPin);
		String repo = service.validateCardPin(validateCardPin);
		log.logDebug("validateCardPin: ",repo);
		log.getLog().trace("end validateCardPin");
		return repo;
	}

	@Operation(summary = "Allows sending a request to the CMS to submit a prepaid card application ")
	//@RolesAllowed({"prepaidApplication", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PrepaidApplication")
	public String PrepaidApplication(@RequestBody   SSEnt_PrepaidApplicationRq  prepaidApplication) {
		log.getLog().trace("start prepaidApplication");
		log.logDebug("prepaidApplication: ",prepaidApplication);
		String repo = service.PrepaidApplication(prepaidApplication);
		log.logDebug("PrepaidApplication: ",repo);
		log.getLog().trace("end PrepaidApplication");
		return repo;
	}
	@Operation(summary = "Allows sending a request to the CMS to validate a prepaid card application")
	//@RolesAllowed({"PrepaidApplicationValidation", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PrepaidApplicationValidation")
	public String PrepaidApplicationValidation(@RequestBody   SSEnt_PrepaidAppliValidationRq  prepaidApplicationValidation) {
		log.getLog().trace("start prepaidApplicationValidation");
		log.logDebug("prepaidApplicationValidation: ",prepaidApplicationValidation);
		String repo = service.PrepaidApplicationValidation(prepaidApplicationValidation);
		log.logDebug("prepaidApplicationValidation: ",repo);
		log.getLog().trace("end prepaidApplicationValidation");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of prepaid card applications")
	//@RolesAllowed({"prepaidapplicationlist", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PrepaidApplicationList")
	public String PrepaidApplicationList(@RequestBody   SSEnt_PrepaidApplicationListRq  prepaidApplicationlist) {
		log.getLog().trace("start prepaidApplicationlist");
		log.logDebug("prepaidApplicationValidation: ",prepaidApplicationlist);
		String repo = service.PrepaidApplicationList(prepaidApplicationlist);
		log.logDebug("prepaidApplicationlist: ",repo);
		log.getLog().trace("end prepaidapplicationlist");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to process a redemption transaction for a custome ")
	@RolesAllowed({"Redemption", "ALL_API_PARTNER"})
	@PostMapping(path="/api/Redemption")
	public String Redemption(@RequestBody   SSEnt_RedemptionRq  RedemptionRq) {
		log.getLog().trace("start RedemptionRq");
		log.logDebug("RedemptionRq: ",RedemptionRq);
		String repo = service.Redemption(RedemptionRq);
		log.logDebug("RedemptionRq: ",repo);
		log.getLog().trace("end RedemptionRq");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending a request to the CMS to submit a new merchant application or retrieve details of an existing one")
	//@RolesAllowed({"MerchantApplication", "ALL_API_PARTNER"})
	@PostMapping(path="/api/MerchantApplication")
	public String MerchantApplication(@RequestBody  @Valid SSEnt_MerchantApplicationRq  MerchantAppliRq) {
		log.getLog().trace("start MerchantAppliRq");
		log.logDebug("MerchantApplication: ",MerchantAppliRq);
		String repo = service.MerchantApp(MerchantAppliRq);
		log.logDebug("MerchantApplication: ",repo);
		log.getLog().trace("end MerchantApplication");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to perform validation of a merchant application")
	//@RolesAllowed({"MerchantApplicationVali", "ALL_API_PARTNER"})
	@PostMapping(path="/api/MerchantApplicationValidation")
	public String MerchantApplicationVali(@RequestBody  @Valid SSEnt_MerchantAppliValidationRq  MerchantApplivaliRq) {
		log.getLog().trace("start MerchantApplivaliRq");
		log.logDebug("MerchantApplicationValidation: ",MerchantApplivaliRq);
		String repo = service.MerchantAppVali(MerchantApplivaliRq);
		log.logDebug("MerchantApplicationValidation: ",repo);
		log.getLog().trace("end MerchantApplicationValidation");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to update the details of an existing merchant application")
	//@RolesAllowed({"MerchantApplicationUpdate", "ALL_API_PARTNER"})
	@PostMapping(path="/api/MerchantApplicationUpdate")
	public String MerchantApplicationUpdate(@RequestBody  @Valid SSEnt_MerchantApplicationUpdateRq  MerchantAppliUpdateRq) {
		log.getLog().trace("start MerchantApplicationUpdate");
		log.logDebug("MerchantApplicationUpdate: ",MerchantAppliUpdateRq);
		String repo = service.MerchantAppUpdate(MerchantAppliUpdateRq);
		log.logDebug("MerchantApplicationUpdate: ",repo);
		log.getLog().trace("end MerchantApplicationUpdate");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to update a merchant's details without submitting a new application.")
	//@RolesAllowed({"MerchantUpdateNoApplication", "ALL_API_PARTNER"})
	@PostMapping(path="/api/MerchantUpdateNoApplication")
	public String MerchantUpdNoApp(@RequestBody  @Valid SSEnt_MerchantUpdateNoApplicationRq  MerchantUpdNoApp) {
		log.getLog().trace("start MerchantUpdateNoApplication");
		log.logDebug("MerchantUpdateNoApplication: ",MerchantUpdNoApp);
		String repo = service.MerchantUpdateNoApplication(MerchantUpdNoApp);
		log.logDebug("MerchantUpdateNoApplication: ",repo);
		log.getLog().trace("end MerchantUpdateNoApplication");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of merchant applications")
	//@RolesAllowed({"merchantApplicationlist", "ALL_API_PARTNER"})
	@PostMapping(path="/api/merchantApplicationlist")
	public String merchantApplicationlist(@RequestBody  @Valid SSEnt_MerchantAppliListRq  merchantAppList) {
		log.getLog().trace("start merchantApplicationlist");
		log.logDebug("merchantApplicationlist: ",merchantAppList);
		String repo = service.MerchantAppliList(merchantAppList);
		log.logDebug("merchantApplicationlist: ",repo);
		log.getLog().trace("end merchantApplicationlist");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of POS terminals associated with a merchant")
	//@RolesAllowed({"PosTerminalList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PosTerminalList")
	public String posterminallist(@RequestBody  @Valid SSEnt_PosTerminalListRq  postterminallist) {
		log.getLog().trace("start PosTerminalList");
		log.logDebug("merchantApplicationlist: ",postterminallist);
		String repo = service.PosTerminalList(postterminallist);
		log.logDebug("PosTerminalList: ",repo);
		log.getLog().trace("end PosTerminalList");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to process a POS terminal application for a merchant.")
	//@RolesAllowed({"PosTerminalApplication", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PosTerminalApplication")
	public String PosTerminalApplication(@RequestBody  @Valid SSEnt_PosTerminalApplicationRq  posterminalApp) {
		log.getLog().trace("start PosTerminalApplication");
		log.logDebug("PosTerminalApplication: ",posterminalApp);
		String repo = service.PosTerminalapplication(posterminalApp);
		log.logDebug("PosTerminalApplication: ",repo);
		log.getLog().trace("end PosTerminalApplication");
		return repo;
	}
	@Operation(summary = "Allows sending a request to the CMS to monitor the status and performance of POS terminals")
	//@RolesAllowed({"PosMonitoring", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PosMonitoring")
	public String PosMonitoring(@RequestBody  @Valid SSEnt_PosMonitoringRq  PosMonitoringRq) {
		log.getLog().trace("start PosMonitoring");
		log.logDebug("PosMonitoring: ",PosMonitoringRq);
		String repo = service.PosMonitoring(PosMonitoringRq);
		log.logDebug("PosMonitoring: ",repo);
		log.getLog().trace("end PosMonitoring");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of authorizations for transactions")
	//@RolesAllowed({"AuthorizationList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/AuthorizationList")
	public String AuthorizationList(@RequestBody  @Valid SSEnt_AuthorizationListRq  authlistRq) {
		log.getLog().trace("start AuthorizationList");
		log.logDebug("AuthorizationList: ",authlistRq);
		String repo = service.AuthorizationList(authlistRq);
		log.logDebug("AuthorizationList: ",repo);
		log.getLog().trace("end AuthorizationList");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of authorizations for transactions")
	//@RolesAllowed({"AuthorizationInquiryList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/AuthorizationInquiryList")
	public String AuthorizationInquiryList(@RequestBody  @Valid SSEnt_AuthorizationListRq  authlistRq) {
		log.getLog().trace("start AuthorizationInquiryList");
		log.logDebug("AuthorizationInquiryList: ",authlistRq);
		String repo = service.AuthorizationList(authlistRq);
		log.logDebug("AuthorizationInquiryList: ",repo);
		log.getLog().trace("end AuthorizationInquiryList");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of POS applications")
	//@RolesAllowed({"PosApplicationList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/PosApplicationList")
	public String PosApplicationList(@RequestBody  @Valid SSEnt_PosApplicationListRq  posapplist) {
		log.getLog().trace("start PosApplicationList");
		log.logDebug("PosApplicationList: ",posapplist);
		String repo = service.PosApplicationList(posapplist);
		log.logDebug("PosApplicationList: ",repo);
		log.getLog().trace("end PosApplicationList");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to update the customer's mobile number")
	//@RolesAllowed({"updatemobile", "ALL_API_PARTNER"})
	@PostMapping(path="/api/UpdateMobile")
	public String PosApplicationList(@RequestBody  @Valid SSEnt_UpdateMobileRq  updatemob) {
		log.getLog().trace("start UpdateMobile");
		log.logDebug("UpdateMobile: ",updatemob);
		String repo = service.UpdateMobile(updatemob);
		log.logDebug("UpdateMobile: ",repo);
		log.getLog().trace("end UpdateMobile");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to update the details of a customer account.")
	//@RolesAllowed({"UpdateAccount", "ALL_API_PARTNER"})
	@PostMapping(path="/api/UpdateAccount")
	public String UpdateAccount(@RequestBody  @Valid SSEnt_UpdateAccountRq  updateacc) {
		log.getLog().trace("start UpdateAccount");
		log.logDebug("UpdateAccount: ",updateacc);
		String repo = service.UpdateAccount(updateacc);
		log.logDebug("UpdateAccount: ",repo);
		log.getLog().trace("end UpdateAccount");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to update the status of a merchant.")
	//@RolesAllowed({"updatemerchant", "ALL_API_PARTNER"})
	@PostMapping(path="/api/UpdateMerchantStatus")
	public String UpdateMerchant(@RequestBody  @Valid SSEnt_StatusMerchantRq  updatemerch) {
		log.getLog().trace("start UpdateMerchant");
		log.logDebug("UpdateMerchant: ",updatemerch);
		String repo = service.UpdateMerchantStatus(updatemerch);
		log.logDebug("UpdateMerchant: ",repo);
		log.getLog().trace("end Update Merchant");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to process an internet or mail order transaction")
	//@RolesAllowed({"InternetMailOrder", "ALL_API_PARTNER"})
	@PostMapping(path="/api/InternetMailOrder")
	public String InternetMailOrder(@RequestBody  @Valid SSEnt_InternetMailOrderStatusRq  internetmailorder) {
		log.getLog().trace("start InternetMailOrder");
		log.logDebug("InternetMailOrder: ",internetmailorder);
		String repo = service.InternetMailOrderStatus(internetmailorder);
		log.logDebug("InternetMailOrder: ",repo);
		log.getLog().trace("end InternetMailOrder");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to create an anonymous prepaid card for the customer")
	//@RolesAllowed({"AnonymPrepaidCard", "ALL_API_PARTNER"})
	@PostMapping(path="/api/AnonymPrepaidCard")
	public String AnonymPrepaidCard(@RequestBody  @Valid SSEnt_AnonymPrepaidCardRq  anonymprepaid) {
		log.getLog().trace("start AnonymPrepaidCard");
		log.logDebug("AnonymPrepaidCard: ",anonymprepaid);
		String repo = service.AnonymPrepaidCard(anonymprepaid);
		log.logDebug("AnonymPrepaidCard: ",repo);
		log.getLog().trace("end AnonymPrepaidCard");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve or update the commission details for a specific merchant")
	//@RolesAllowed({"MerchantCommission", "ALL_API_PARTNER"})
	@PostMapping(path="/api/MerchantCommission")
	public String AnonymPrepaidCard(@RequestBody  @Valid SSEnt_MerchantCommissionRq  merchancommission) {
		log.getLog().trace("start MerchantCommission");
		log.logDebug("MerchantCommission: ",merchancommission);
		String repo = service.MerchantCommission(merchancommission);
		log.logDebug("MerchantCommission: ",repo);
		log.getLog().trace("end MerchantCommission");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to manage the customer's address details")
	//@RolesAllowed({"ManageAddress", "ALL_API_PARTNER"})
	@PostMapping(path="/api/ManageAddress")
	public String ManageAddress(@RequestBody  @Valid SSEnt_ManageAddressRq  manageaddress) {
		log.getLog().trace("start ManageAddress");
		log.logDebug("ManageAddress: ",manageaddress);
		String repo = service.ManageAddress(manageaddress);
		log.logDebug("ManageAddress: ",repo);
		log.getLog().trace("end ManageAddress");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of terminals associated with a specific merchant.")
	//@RolesAllowed({"TerminalsByMerchant", "ALL_API_PARTNER"})
	@PostMapping(path="/api/TerminalsByMerchant")
	public String TerminalsByMerchant(@RequestBody  @Valid SSEnt_TerminalsByMerchantRq  terminalbymerchant) {
		log.getLog().trace("start TerminalsByMerchant");
		log.logDebug("TerminalsByMerchant: ",terminalbymerchant);
		String repo = service.TerminalsByMerchant(terminalbymerchant);
		log.logDebug("TerminalsByMerchant: ",repo);
		log.getLog().trace("end TerminalsByMerchant");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of transactions for a specific merchant")
	//@RolesAllowed({"GetMerchantTransactions", "ALL_API_PARTNER"})
	@PostMapping(path="/api/GetMerchantTransactions")
	public String GetMerchantTransactions(@RequestBody  @Valid SSEnt_MerchantTransactionsRq  merchanttransac) {
		log.getLog().trace("start GetMerchantTransactions");
		log.logDebug("GetMerchantTransactions: ",merchanttransac);
		String repo = service.GetMerchantTransactions(merchanttransac);
		log.logDebug("GetMerchantTransactions: ",repo);
		log.getLog().trace("end GetMerchantTransactions");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending a request to the CMS to retrieve the list of transactions for a specific merchant terminal.")
	//@RolesAllowed({"MerchantTerminalTransactions", "ALL_API_PARTNER"})
	@PostMapping(path="/api/MerchantTerminalTransactions")
	public String MerchantTerminalTransactions(@RequestBody  @Valid SSEnt_MerchantTerminalTransactionsRq  merchantterminaltransac) {
		log.getLog().trace("start MerchantTerminalTransactions");
		log.logDebug("MerchantTerminalTransactions: ",merchantterminaltransac);
		String repo = service.MerchantTerminalTransactions(merchantterminaltransac);
		log.logDebug("MerchantTerminalTransactions: ",repo);
		log.getLog().trace("end MerchantTerminalTransactions");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to send a PIN code via SMS to the customer.")
	//@RolesAllowed({"SendPinBySms", "ALL_API_PARTNER"})
	@PostMapping(path="/api/SendPinBySms")
	public String SendPinBySms(@RequestBody  @Valid SSEnt_SendPinBySmsRq  sendpinsms) {
		log.getLog().trace("start SendPinBySms");
		log.logDebug("SendPinBySms: ",sendpinsms);
		String repo = service.SendPinBySms(sendpinsms);
		log.logDebug("SendPinBySms: ",repo);
		log.getLog().trace("end SendPinBySms");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to assess the risk associated with a specific program")
	//@RolesAllowed({"ProgramRisk", "ALL_API_PARTNER"})
	@PostMapping(path="/api/ProgramRisk")
	public String ProgramRisk(@RequestBody  @Valid SSEnt_ProgramRiskRq  programrisk) {
		log.getLog().trace("start ProgramRisk");
		log.logDebug("ProgramRisk: ",programrisk);
		String repo = service.ProgramRisk(programrisk);
		log.logDebug("ProgramRisk: ",repo);
		log.getLog().trace("end ProgramRisk");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending a request to the CMS to trigger an SMS alert to the customer")
	//@RolesAllowed({"SmsAlert", "ALL_API_PARTNER"})
	@PostMapping(path="/api/SmsAlert")
	public String SmsAlert(@RequestBody  @Valid SSEnt_SmsAlertRq  smsalert) {
		log.getLog().trace("start SmsAlert");
		log.logDebug("SmsAlert: ",smsalert);
		String repo = service.SmsAlert(smsalert);
		log.logDebug("SmsAlert: ",repo);
		log.getLog().trace("end SmsAlert");
		return repo;
	}
	
	@Operation(summary = "Allows sending a request to the CMS to send a PIN code via email to the customer")
	//@RolesAllowed({"SendPinByEmail", "ALL_API_PARTNER"})
	@PostMapping(path="/api/SendPinByEmail")
	public String SendPinByEmail(@RequestBody  @Valid SSEnt_SendPinByEmailRq  emailpin) {
		log.getLog().trace("start SendPinByEmail");
		log.logDebug("SendPinByEmail: ",emailpin);
		String repo = service.SendPinByEmail(emailpin);
		log.logDebug("SendPinByEmail: ",repo);
		log.getLog().trace("end SendPinByEmail");
		return repo;
	}

	
	@Operation(summary = "Allows sending a request to the CMS to monitor the status and performance of ATMs")
	//@RolesAllowed({"AtmMonitoring", "ALL_API_PARTNER"})
	@PostMapping(path="/api/AtmMonitoring")
	public String AtmMonitoring(@RequestBody  @Valid SSEnt_AtmMonitoringRq  atmmonitoring) {
		log.getLog().trace("start AtmMonitoring");
		log.logDebug("AtmMonitoring: ",atmmonitoring);
		String repo = service.AtmMonitoring(atmmonitoring);
		log.logDebug("AtmMonitoring: ",repo);
		log.getLog().trace("end AtmMonitoring");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending a request to the CMS to create a virtual card for the customer")
	//@RolesAllowed({"VirtualCardCreation", "ALL_API_PARTNER"})
	@PostMapping(path="/api/VirtualCardCreation")
	public String VirtualCardCreation(@RequestBody  @Valid SSEnt_VirtualCardCreationRq  virtualcard) {
		log.getLog().trace("start VirtualCardCreation");
		log.logDebug("VirtualCardCreation: ",virtualcard);
		String repo = service.VirtualCardCreation(virtualcard);
		log.logDebug("VirtualCardCreation: ",repo);
		log.getLog().trace("end VirtualCardCreation");
		return repo;
	}
	
	@Operation(summary = "Allows to send to CMS the customer request to create a debit application")
	//@RolesAllowed({"DebitApplication", "ALL_API_PARTNER"})
	@PostMapping(path="/api/DebitApplication")
	public String DebitApplication(@RequestBody  @Valid SSEnt_DebitApplicationRq  debitappl) {
		log.getLog().trace("start DebitApplication");
		log.logDebug("DebitApplication: ",debitappl);
		String repo = service.DebitApplication(debitappl);
		log.logDebug("DebitApplication: ",repo);
		log.getLog().trace("end DebitApplication");
		return repo;
	}

	
	
	@Operation(summary = "Allows to send to CMS the customer request to create a debit application validation")
	//@RolesAllowed({"DebitApplicationValidation", "ALL_API_PARTNER"})
	@PostMapping(path="/api/DebitApplicationValidation")
	public String DebitApplication(@RequestBody  @Valid SSEnt_DebitApplicationValidationRq  debitapplvali) {
		log.getLog().trace("start DebitApplicationValidation");
		log.logDebug("DebitApplicationValidation: ",debitapplvali);
		String repo = service.DebitApplicationValidation(debitapplvali);
		log.logDebug("DebitApplicationValidation: ",repo);
		log.getLog().trace("end DebitApplicationValidation");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending request to the CMS to display the Debit Application List")
	//@RolesAllowed({"DebitApplicationList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/DebitApplicationList")
	public String DebitApplicationList(@RequestBody  @Valid SSEnt_DebitApplicationListRq  debitappllist) {
		log.getLog().trace("start DebitApplicationList");
		log.logDebug("DebitApplicationList: ",debitappllist);
		String repo = service.DebitApplicationList(debitappllist);
		log.logDebug("DebitApplicationList: ",repo);
		log.getLog().trace("end DebitApplicationList");
		return repo;
	}
	
	
	@Operation(summary = "Allows sending request to the CMS to reset the PIN")
	//@RolesAllowed({"ResetPin", "ALL_API_PARTNER"})
	@PostMapping(path="/api/ResetPin")
	public String ResetPin(@RequestBody  @Valid SSEnt_ResetPinRq  resetpin) {
		log.getLog().trace("start ResetPin");
		log.logDebug("ResetPin: ",resetpin);
		String repo = service.ResetPin(resetpin);
		log.logDebug("ResetPin: ",repo);
		log.getLog().trace("end ResetPin");
		return repo;
	}
	
	@Operation(summary = "Allows sending the customer's request to the CMS to generate a new PAN")
	//@RolesAllowed({"Replacement", "ALL_API_PARTNER"})
	@PostMapping(path="/api/Replacement")
	public String Replacement(@RequestBody  @Valid SSEnt_ReplacementRq  replacement) {
		log.getLog().trace("start Replacement");
		log.logDebug("Replacement: ",replacement);
		String repo = service.Replacement(replacement);
		log.logDebug("Replacement: ",repo);
		log.getLog().trace("end Replacement");
		return repo;
	}
	
	
	@Operation(summary = "Enables sending a request to the CMS to Renew")
	//@RolesAllowed({"Renew", "ALL_API_PARTNER"})
	@PostMapping(path="/api/Renew")
	public String Renew(@RequestBody  @Valid SSEnt_RenewRq  renew) {
		log.getLog().trace("start Renew");
		log.logDebug("Renew: ",renew);
		String repo = service.Renew(renew);
		log.logDebug("Renew: ",repo);
		log.getLog().trace("end Renew");
		return repo;
	}
	
	
	@Operation(summary = "Enables sending a request to the CMS to display the CardList")
	//@RolesAllowed({"CardList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/CardList")
	public String CardList(@RequestBody  @Valid SSEnt_cardlistRq  cardlist) {
		log.getLog().trace("start CardList");
		log.logDebug("CardList: ",cardlist);
		String repo = service.CardList(cardlist);
		log.logDebug("CardList: ",repo);
		log.getLog().trace("end CardList");
		return repo;
	}
	@Operation(summary = "Enables sending a request to the CMS to display the AccountList")
	//@RolesAllowed({"AccountList", "AccountList"})
	@PostMapping(path="/api/AccountList")
	public String AccountList(@RequestBody  @Valid SSEnt_AccountListRq  accountlist) {
		log.getLog().trace("start AccountList");
		log.logDebug("AccountList: ",accountlist);
		String repo = service.AccountList(accountlist);
		log.logDebug("AccountList: ",repo);
		log.getLog().trace("end AccountList");
		return repo;
	}
	
	@Operation(summary = "Enables sending a request to the CMS to display the list of customers")
	//@RolesAllowed({"CustomerList", "ALL_API_PARTNER"})
	@PostMapping(path="/api/CustomerList")
	public String AccountList(@RequestBody  @Valid SSEnt_CustomerListRq  customerList) {
		log.getLog().trace("start CustomerList");
		log.logDebug("CustomerList: ",customerList);
		String repo = service.CustomerList(customerList);
		log.logDebug("CustomerList: ",repo);
		log.getLog().trace("end CustomerList");
		return repo;
	}
	
		
}
