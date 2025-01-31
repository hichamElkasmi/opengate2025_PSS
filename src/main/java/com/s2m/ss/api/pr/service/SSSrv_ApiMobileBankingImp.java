package com.s2m.ss.api.pr.service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.s2m.ss.api.pr.config.SS_Logging;
import com.s2m.ss.api.pr.entities.SSEnt_CardEventType;
import com.s2m.ss.api.pr.entities.SSEnt_Chk;
import com.s2m.ss.api.pr.entities.SSEnt_Header;
import com.s2m.ss.api.pr.entities.SSEnt_Initiator;
import com.s2m.ss.api.pr.entities.SSEnt_OperationType;
import com.s2m.ss.api.pr.entities.SSEnt_Status;
import com.s2m.ss.api.pr.entities.SSEnt_TransferType;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AccountListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AddSecCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AnonymPrepaidCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AtmMonitoringRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_AuthorizationListRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_BaseRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ChannelChangeRq;
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
import com.s2m.ss.api.pr.entities.requests.SSEnt_HeadInitRq;
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
import com.s2m.ss.api.pr.entities.requests.SSEnt_RenewRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ReplaceCardRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ReplacementRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_ResetPinRq;
import com.s2m.ss.api.pr.entities.requests.SSEnt_RestCountryChangeRq;
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
import com.s2m.ss.api.pr.entities.responses.SSEnt_ChannelsListRs;
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
import com.s2m.ss.api.pr.entities.responses.SSEnt_RestCountryListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_StatisticsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_TerminalsListRs;
import com.s2m.ss.api.pr.entities.responses.SSEnt_TransactionsListRs;
import  com.s2m.ss.api.pr.dao.*;

@Service
public class SSSrv_ApiMobileBankingImp implements SSSrv_ApiMobileBanking {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	SS_Logging logs; 
	
	@PostConstruct
	public void initvals() {
		logs.setClazz(getClass());
	}
	
	@Override
	public SSEnt_CardInfoRs getCardInfo(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getCardInfo");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_CardInfoRs ent_CardInfoRs = new SSEnt_CardInfoRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_CardInfo cardInfo = new SSRepo_CardInfo(headInitReq);
		ent_CardInfoRs = cardInfo.processing(dataSource);
		ent_CardInfoRs.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getCardInfo");
		logs.logDebug("ent_CardInfoRs: ",ent_CardInfoRs);
		
		return ent_CardInfoRs;
	}
	

	@Override
	public SSEnt_CustomerInfoRs getCustomerInfo(SSEnt_HeadInitClsRq headInitReq) {
		logs.getLog().trace("start service getCustomerInfo");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_CustomerInfoRs ent_CustomerInfoRs = new SSEnt_CustomerInfoRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_CustomerInfo customerInfo = new SSRepo_CustomerInfo(headInitReq);
		ent_CustomerInfoRs = customerInfo.processing(dataSource);
		ent_CustomerInfoRs.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getCustomerInfo");
		logs.logDebug("ent_CustomerInfoRs: ",ent_CustomerInfoRs);
		
		return ent_CustomerInfoRs;
	}
	
	@Override
	public SSEnt_CardsListRs getCardsList(SSEnt_HeadInitClsRq headInitReq) {
		logs.getLog().trace("start service getCardsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_CardsListRs cardListRes = new SSEnt_CardsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_CardsList cardsListRepo = new SSRepo_CardsList(headInitReq);
		cardListRes = cardsListRepo.processing(dataSource);
		cardListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getCardsList");
		logs.logDebug("cardListRes: ",cardListRes);
		
		return cardListRes;
	}

	@Override
	public SSEnt_MiniStateListRs getMiniState(SSEnt_HeadInitCrdAccRq headInitReq) {
		logs.getLog().trace("start service getMiniState");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_MiniStateListRs ministateListRes = new SSEnt_MiniStateListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_MiniStatement ministateListRepo = new SSRepo_MiniStatement(headInitReq);
		ministateListRes = ministateListRepo.processing(dataSource);
		ministateListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getMiniState");
		logs.logDebug("ministateListRes: ",ministateListRes);
		
		return ministateListRes;
	}
	
	@Override
	public SSEnt_TransactionsListRs getTransactionsList(SSEnt_TransactionsListRq transactionsListReq) {
		logs.getLog().trace("start service getTransactionsList");
		logs.logDebug("transactionsListReq: ",transactionsListReq);
		SSEnt_Chk chk = new SSEnt_Chk();
		SSEnt_TransactionsListRs transactionsListRes = new SSEnt_TransactionsListRs();
		SSEnt_Status status = chk.checkTransactionsList(transactionsListReq);

		if(status.isApproved()) {
			logs.getLog().trace("Approved service getTransactionsList");
			transactionsListReq.getInitiator().setRequestId(transactionsListReq.getHeader().getIdMsg());
			SSRepo_TransactionsList transactionsListRepo = new SSRepo_TransactionsList(transactionsListReq);
			transactionsListRes = transactionsListRepo.processing(dataSource);
		}else {
			logs.getLog().trace("Approved service getTransactionsList");
			transactionsListRes.setStatus(status);
			transactionsListRes.setTransactions(null);
		}
		transactionsListRes.setHeader(transactionsListReq.getHeader());
		
		logs.getLog().trace("end service getTransactionsList");
		logs.logDebug("transactionsListRes: ",transactionsListRes);
		
		return transactionsListRes;
	}

	@Override
	public SSEnt_RestCountryListRs getRestrictedCountry(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getRestrictedCountry");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_RestCountryListRs RestCountryListRes = new SSEnt_RestCountryListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_RestCountriesList countriesListRepo = new SSRepo_RestCountriesList(headInitReq);
		RestCountryListRes = countriesListRepo.processing(dataSource);
		RestCountryListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getRestrictedCountry");
		logs.logDebug("headInitReq: ",headInitReq);
		
		return RestCountryListRes;
	}
	
	@Override
	public SSEnt_StatisticsListRs getStatisticsList(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getStatisticsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_StatisticsListRs statisticsListRes = new SSEnt_StatisticsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_StatisticsList statisticsListRepo = new SSRepo_StatisticsList(headInitReq);
		statisticsListRes = statisticsListRepo.processing(dataSource);
		statisticsListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getStatisticsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		return statisticsListRes;
	}
	
	@Override
	public SSEnt_LimitsListRs getLimitsList(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getLimitsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_LimitsListRs limitsListRes = new SSEnt_LimitsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_LimitsList limitsListRepo = new SSRepo_LimitsList(headInitReq);
		limitsListRes = limitsListRepo.processing(dataSource);
		limitsListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getLimitsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		return limitsListRes;
	}
	
	@Override
	public SSEnt_AccountsListRs getAccountslist(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getAccountslist");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_AccountsListRs accountsListRes = new SSEnt_AccountsListRs();
		accountsListRes.setCheckbooks(null);
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_AccountsList accountsListRepo = new SSRepo_AccountsList(headInitReq);
		accountsListRes = accountsListRepo.processing(dataSource);
		
		accountsListRes.setHeader(headInitReq.getHeader());
		logs.getLog().trace("end service getAccountslist");
		logs.logDebug("headInitReq: ",headInitReq);
		
		return accountsListRes;
	}
	
	@Override
	public SSEnt_AccountsListRs getCheckAccountslist(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getAccountslist");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_AccountsListRs accountsListRes = new SSEnt_AccountsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_CheckAccountsList accountsListRepo = new SSRepo_CheckAccountsList(headInitReq);
		accountsListRes = accountsListRepo.processing(dataSource);
		accountsListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getAccountslist");
		logs.logDebug("headInitReq: ",headInitReq);
		
		return accountsListRes;
	}
	
	@Override
	public SSEnt_BaseRs setRequestPin(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service setRequestPin");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_ReqPINCard setRequestPinRepo = new SSRepo_ReqPINCard(headInitReq);
		baseRes = setRequestPinRepo.processing(dataSource);
		baseRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service setRequestPin");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setRenewCard(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service setRenewCard");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_RenewCard setRenewCardRepo = new SSRepo_RenewCard(headInitReq);
		baseRes = setRenewCardRepo.processing(dataSource);
		baseRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service setRenewCard");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_ChannelsListRs getChannelsList(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getChannelsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_ChannelsListRs channelListRes = new SSEnt_ChannelsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_ChannelsList channelsListRepo = new SSRepo_ChannelsList(headInitReq);
		channelListRes = channelsListRepo.processing(dataSource);
		channelListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getChannelsList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		return channelListRes;
	}

	@Override
	public SSEnt_BaseRs setAddSecCard(SSEnt_AddSecCardRq addSecCardReq) {
		logs.getLog().trace("start service setAddSecCard");
		logs.logDebug("addSecCardReq: ",addSecCardReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		addSecCardReq.getInitiator().setRequestId(addSecCardReq.getHeader().getIdMsg());
		SSRepo_AddSecCard addSecRepo = new SSRepo_AddSecCard(addSecCardReq);
		baseRes = addSecRepo.processing(dataSource);
		baseRes.setHeader(addSecCardReq.getHeader());
		
		logs.getLog().trace("end service setAddSecCard");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setCheckbook(SSEnt_CheckbookRq checkbookReq) {
		logs.getLog().trace("start service setCheckbook");
		logs.logDebug("checkbookReq: ",checkbookReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		checkbookReq.getInitiator().setRequestId(checkbookReq.getHeader().getIdMsg());
		SSRepo_ReqCheckBook reqCheckBookRepo = new SSRepo_ReqCheckBook(checkbookReq);
		baseRes = reqCheckBookRepo.processing(dataSource);
		baseRes.setHeader(checkbookReq.getHeader());
		
		logs.getLog().trace("end service setCheckbook");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setReplaceCard(SSEnt_ReplaceCardRq replaceCardReq) {
		logs.getLog().trace("start service setReplaceCard");
		logs.logDebug("replaceCardReq: ",replaceCardReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		replaceCardReq.getInitiator().setRequestId(replaceCardReq.getHeader().getIdMsg());
		SSRepo_ReplaceCard replaceCardRepo = new SSRepo_ReplaceCard(replaceCardReq);
		baseRes = replaceCardRepo.processing(dataSource);
		baseRes.setHeader(replaceCardReq.getHeader());
		
		logs.getLog().trace("end service setReplaceCard");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setStatus(SSEnt_OperationRq operationRq, SSEnt_OperationType operationType) {
		logs.getLog().trace("start service setStatus");
		logs.logDebug("operationRq: ",operationRq);
		logs.logDebug("operationType: ",operationType);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		operationRq.getInitiator().setRequestId(operationRq.getHeader().getIdMsg());
		operationRq.getCardOperation().setOperationType(operationType);
		SSRepo_SetStatus setStatusRepo = new SSRepo_SetStatus(operationRq);
		baseRes = setStatusRepo.processing(dataSource);
		baseRes.setHeader(operationRq.getHeader());
		
		logs.getLog().trace("end service setStatus");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setLimits(SSEnt_LimitChangeRq limitChangeReq) {
		logs.getLog().trace("start service setLimits");
		logs.logDebug("limitChangeReq: ",limitChangeReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		limitChangeReq.getInitiator().setRequestId(limitChangeReq.getHeader().getIdMsg());
		SSRepo_SetLimit setLimitRepo = new SSRepo_SetLimit(limitChangeReq);
		baseRes = setLimitRepo.processing(dataSource);
		baseRes.setHeader(limitChangeReq.getHeader());
		
		logs.getLog().trace("end service setLimits");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setRestrictedcountry(SSEnt_RestCountryChangeRq restCountryChangeReq){
		logs.getLog().trace("start service setRestrictedcountry");
		logs.logDebug("restCountryChangeReq: ",restCountryChangeReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		restCountryChangeReq.getInitiator().setRequestId(restCountryChangeReq.getHeader().getIdMsg());
		SSRepo_SetCountry setCountryRepo = new SSRepo_SetCountry(restCountryChangeReq);
		baseRes = setCountryRepo.processing(dataSource);
		baseRes.setHeader(restCountryChangeReq.getHeader());
		
		logs.getLog().trace("end service setRestrictedcountry");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_BaseRs setChannels(SSEnt_ChannelChangeRq channelChangeReq) {
		logs.getLog().trace("start service setChannels");
		logs.logDebug("limitChangeReq: ",channelChangeReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		channelChangeReq.getInitiator().setRequestId(channelChangeReq.getHeader().getIdMsg());
		SSRepo_SetChannel setChannelRepo = new SSRepo_SetChannel(channelChangeReq);
		baseRes = setChannelRepo.processing(dataSource);
		baseRes.setHeader(channelChangeReq.getHeader());
		
		logs.getLog().trace("end service setChannels");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}

	@Override
	public SSEnt_BalanceRs getBalance(SSEnt_HeadInitCrdAccRq headInitReq) {
		logs.getLog().trace("start service getBalance");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_BalanceRs balanceRes = new SSEnt_BalanceRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_Balance balanceRepo = new SSRepo_Balance(headInitReq);
		balanceRes = balanceRepo.processing(dataSource);
		balanceRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getBalance");
		logs.logDebug("balanceRes: ",balanceRes);
		
		return balanceRes;
	}

	@Override
	public SSEnt_BaseRs setCreateCard(SSEnt_CreateCardRq createCardReq) {
		logs.getLog().trace("start service setCreateCard");
		logs.logDebug("createCardReq: ",createCardReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		createCardReq.getInitiator().setRequestId(createCardReq.getHeader().getIdMsg());
		createCardReq.getCard().setEventType(SSEnt_CardEventType.CREATE_CARD);
		SSRepo_CreateCard creatCardRepo = new SSRepo_CreateCard(createCardReq);
		baseRes = creatCardRepo.processing(dataSource);
		baseRes.setHeader(createCardReq.getHeader());
		
		logs.getLog().trace("end service setCreateCard");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}

	@Override
	public SSEnt_BaseRs setReqNewCard(SSEnt_DebitAppRq debitAppRq) {
		logs.getLog().trace("start service setReqNewCard");
		logs.logDebug("debitAppRq: ",debitAppRq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		debitAppRq.getInitiator().setRequestId(debitAppRq.getHeader().getIdMsg());
		SSRepo_CreateDebit createDebit = new SSRepo_CreateDebit(debitAppRq);
		baseRes = createDebit.processing(dataSource);
		baseRes.setHeader(debitAppRq.getHeader());
		
		logs.getLog().trace("end service setReqNewCard");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}

	@Override
	public SSEnt_ReasonsListRs getReasonsList(SSEnt_HeadInitBnkRq headInitReq) {
		logs.getLog().trace("start service getReasonsList");
		logs.logDebug("baseReq: ",headInitReq);
		
		SSEnt_ReasonsListRs reasonsListRes = new SSEnt_ReasonsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_ReasonsList reasonsListRepo = new SSRepo_ReasonsList(headInitReq);
		reasonsListRes = reasonsListRepo.processing(dataSource);
		reasonsListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getReasonsList");
		logs.logDebug("reasonsListRes: ",reasonsListRes);
		
		return reasonsListRes;
	}
	
	@Override
	public SSEnt_ChecksListRs getChecksList(SSEnt_HeadInitBnkRq headInitReq) {
		logs.getLog().trace("start service getChecksList");
		logs.logDebug("baseReq: ",headInitReq);
		
		SSEnt_ChecksListRs checksListRes = new SSEnt_ChecksListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_CheckBookList checksListRepo = new SSRepo_CheckBookList(headInitReq);
		checksListRes = checksListRepo.processing(dataSource);
		checksListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getChecksList");
		logs.logDebug("checksListRes: ",checksListRes);
		
		return checksListRes;
	}

	@Override
	public SSEnt_LocationsListRs getLocationsList(SSEnt_HeadInitBnkRq headInitReq) {
		logs.getLog().trace("start service getLocationsList");
		logs.logDebug("baseReq: ",headInitReq);
		
		SSEnt_LocationsListRs locationsListRes = new SSEnt_LocationsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_LocationsList locationsListRepo = new SSRepo_LocationsList(headInitReq);
		locationsListRes = locationsListRepo.processing(dataSource);
		locationsListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getLocationsList");
		logs.logDebug("locationsListRes: ",locationsListRes);
		
		return locationsListRes;
	}

	@Override
	public SSEnt_CountriesListRs getCountriesList(SSEnt_HeadInitBnkRq headInitReq) {
		logs.getLog().trace("start service getCountriesList");
		logs.logDebug("baseReq: ",headInitReq);
		
		SSEnt_CountriesListRs countriesListRes = new SSEnt_CountriesListRs();
			headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
			SSRepo_CountriesList countriesListRepo = new SSRepo_CountriesList(headInitReq);
			countriesListRes = countriesListRepo.processing(dataSource);
		countriesListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getCountriesList");
		logs.logDebug("countriesListRes: ",countriesListRes);
		
		return countriesListRes;
	}

	@Override
	public SSEnt_CitiesListRs getCitiesList(@RequestBody  SSEnt_CitiesListRq ent_CitiesListRq) {
		logs.getLog().trace("start service getCitiesList");
		logs.logDebug("baseReq: ",ent_CitiesListRq);
		
		SSEnt_CitiesListRs citiesListRes = new SSEnt_CitiesListRs();
		ent_CitiesListRq.getInitiator().setRequestId(ent_CitiesListRq.getHeader().getIdMsg());
		SSRepo_CitiesList citiesListRepo = new SSRepo_CitiesList(ent_CitiesListRq);
		citiesListRes = citiesListRepo.processing(dataSource);
		citiesListRes.setHeader(ent_CitiesListRq.getHeader());
		
		logs.getLog().trace("end service getCitiesList");
		logs.logDebug("countriesListRes: ",citiesListRes);
		
		return citiesListRes;
	}
	
	@Override
	public SSEnt_BanksListRs getBanksList(SSEnt_BaseRq baseRq) {
		logs.getLog().trace("start service getBanksList");
		logs.logDebug("baseRq: ",baseRq);
		
		SSEnt_BanksListRs banksListRes = new SSEnt_BanksListRs();
		SSEnt_HeadInitRq headInitReq = new SSEnt_HeadInitRq();
		headInitReq.setHeader(baseRq.getHeader());
		headInitReq.setInitiator(new SSEnt_Initiator());
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_BanksList bankApiRepo = new SSRepo_BanksList(headInitReq);
		banksListRes = bankApiRepo.processing(dataSource);
		banksListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getBanksList");
		logs.logDebug("banksListRes: ",banksListRes);
		
		return banksListRes;
	}
	
	@Override
	public SSEnt_ProductsListRs getProductsList(SSEnt_HeadInitBnkRq headInitReq) {
		logs.getLog().trace("start service getProductsList");
		logs.logDebug("baseReq: ",headInitReq);
		
		SSEnt_ProductsListRs productsListRes = new SSEnt_ProductsListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_ProductsList productsListRepo = new SSRepo_ProductsList(headInitReq);
		productsListRes = productsListRepo.processing(dataSource);
		productsListRes.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getProductsList");
		logs.logDebug("productsListRes: ",productsListRes);
		
		return productsListRes;
	}

	@Override
	public SSEnt_BaseRs pingApi(SSEnt_BaseRq baseReq) {
		logs.getLog().trace("start service pingApi");
		logs.logDebug("baseReq: ",baseReq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		SSRepo_PingApi pingApiRepo = new SSRepo_PingApi(baseReq);
		baseRes = pingApiRepo.processing(dataSource);
		baseRes.setHeader(baseReq.getHeader());
		
		logs.getLog().trace("end service pingApi");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_AuthorisationRs reqTransfert(SSEnt_TransfertRq ent_TransfertRq, SSEnt_TransferType requestType) {
		logs.getLog().trace("start service reqTransfert");
		logs.logDebug("ent_TransfertRq: ",ent_TransfertRq);
		
		SSEnt_AuthorisationRs ent_AuthorisationRs = new SSEnt_AuthorisationRs();
		ent_TransfertRq.getInitiator().setRequestId(ent_TransfertRq.getHeader().getIdMsg());
		ent_TransfertRq.getTransferTrx().setReqType(requestType);
		SSRepo_Transfer repo_Transfert = new SSRepo_Transfer(ent_TransfertRq);
		ent_AuthorisationRs = repo_Transfert.processing(dataSource);
		ent_AuthorisationRs.setHeader(ent_TransfertRq.getHeader());
		
		logs.getLog().trace("end service reqTransfert");
		logs.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		
		return ent_AuthorisationRs;
	}
	
	@Override
	public SSEnt_AuthorisationRs reqMoneySend(SSEnt_MoneySendRq ent_MoneySendRq) {
		logs.getLog().trace("start service reqMoneySend");
		logs.logDebug("ent_MoneySendRq: ",ent_MoneySendRq);
		
		SSEnt_AuthorisationRs ent_AuthorisationRs = new SSEnt_AuthorisationRs();
		ent_MoneySendRq.getInitiator().setRequestId(ent_MoneySendRq.getHeader().getIdMsg());
		SSRepo_MoneySend repo_Transfert = new SSRepo_MoneySend(ent_MoneySendRq);
		ent_AuthorisationRs = repo_Transfert.processing(dataSource);
		ent_AuthorisationRs.setHeader(ent_MoneySendRq.getHeader());
		
		logs.getLog().trace("end service reqMoneySend");
		logs.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		
		return ent_AuthorisationRs;
	}
	
	@Override
	public SSEnt_BaseRs setCustomerInfo(SSEnt_SetCustomerInfoRq customerInfoRq) {
		logs.getLog().trace("start service setCustomerInfo");
		logs.logDebug("customerInfoRq: ",customerInfoRq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		customerInfoRq.getInitiator().setRequestId(customerInfoRq.getHeader().getIdMsg());
		SSRepo_SetCustomerInfo setCustomerInfoRepo = new SSRepo_SetCustomerInfo(customerInfoRq);
		baseRes = setCustomerInfoRepo.processing(dataSource);
		baseRes.setHeader(customerInfoRq.getHeader());
		
		logs.getLog().trace("end service setCustomerInfo");
		logs.logDebug("baseRes: ",baseRes);
		
		
		return baseRes;
		
	}

	@Override
	public SSEnt_TerminalsListRs getTerminalList(@Valid SSEnt_SelectTerminalListRq selectTerminalListRq) {
		logs.getLog().trace("start service getTerminalList");
		logs.logDebug("selectTerminalListRq: ",selectTerminalListRq);
		
		SSEnt_TerminalsListRs terminalsListRs = new SSEnt_TerminalsListRs();
		selectTerminalListRq.getInitiator().setRequestId(selectTerminalListRq.getHeader().getIdMsg());
		SSRepo_TerminalsList terminalsList = new SSRepo_TerminalsList(selectTerminalListRq);
		terminalsListRs = terminalsList.processing(dataSource);
		terminalsListRs.setHeader(selectTerminalListRq.getHeader());
		
		logs.getLog().trace("end service getTerminalList");
		logs.logDebug("terminalsListRs: ",terminalsListRs);
		
		return terminalsListRs;
	}

	@Override
	public SSEnt_InterfacesListRs getInterfaceList(@Valid SSEnt_SelectInterfaceListRq interfaceListRq) {
		logs.getLog().trace("start service getInterfaceList");
		logs.logDebug("interfaceListRq: ",interfaceListRq);
		
		SSEnt_InterfacesListRs interfacesListRs = new SSEnt_InterfacesListRs();
		interfaceListRq.getInitiator().setRequestId(interfaceListRq.getHeader().getIdMsg());
		SSRepo_InterfacesList interfacesList = new SSRepo_InterfacesList(interfaceListRq);
		interfacesListRs = interfacesList.processing(dataSource);
		interfacesListRs.setHeader(interfaceListRq.getHeader());
		
		logs.getLog().trace("end service getInterfaceList");
		logs.logDebug("interfacesListRs: ",interfacesListRs);
		
		return interfacesListRs;
	}

	@Override
	public SSEnt_FeesListRs getFeesList(SSEnt_HeadInitCrdRq headInitReq) {
		logs.getLog().trace("start service getInterfaceList");
		logs.logDebug("headInitReq: ",headInitReq);
		
		SSEnt_FeesListRs feesListRs = new SSEnt_FeesListRs();
		headInitReq.getInitiator().setRequestId(headInitReq.getHeader().getIdMsg());
		SSRepo_FeeList feeList = new SSRepo_FeeList(headInitReq);
		feesListRs = feeList.processing(dataSource);
		feesListRs.setHeader(headInitReq.getHeader());
		
		logs.getLog().trace("end service getInterfaceList");
		logs.logDebug("feesListRs: ",feesListRs);
		
		return feesListRs;
	}
	
	@Override
	public SSEnt_BaseRs linkAccountToCard(SSEnt_LinkAccountCardRq linkAccountCardRq) {
		logs.getLog().trace("start service linkAccountToCard");
		logs.logDebug("linkAccountCardRq: ",linkAccountCardRq);
		
		SSEnt_BaseRs baseRes = new SSEnt_BaseRs();
		linkAccountCardRq.getInitiator().setRequestId(linkAccountCardRq.getHeader().getIdMsg());
		SSRepo_LinkAccountCard  linkAccountCard= new SSRepo_LinkAccountCard(linkAccountCardRq);
		baseRes = linkAccountCard.processing(dataSource);
		baseRes.setHeader(linkAccountCardRq.getHeader());
		
		logs.getLog().trace("end service linkAccountToCard");
		logs.logDebug("baseRes: ",baseRes);
		
		return baseRes;
	}
	
	@Override
	public SSEnt_AuthorisationRs reqManualTrx(SSEnt_ManualTransactionRq manualTransactionRq) {
		logs.getLog().trace("start service reqManualTrx");
		logs.logDebug("manualTransactionRq: ",manualTransactionRq);
		
		SSEnt_AuthorisationRs ent_AuthorisationRs = new SSEnt_AuthorisationRs();
		manualTransactionRq.getInitiator().setRequestId(manualTransactionRq.getHeader().getIdMsg());
		SSRepo_ManualTrx repo_ManualTrx = new SSRepo_ManualTrx(manualTransactionRq);
		ent_AuthorisationRs = repo_ManualTrx.processing(dataSource);
		ent_AuthorisationRs.setHeader(manualTransactionRq.getHeader());
		
		logs.getLog().trace("end service reqManualTrx");
		logs.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		
		return ent_AuthorisationRs;
	}
	
	@Override
	public SSEnt_AuthorisationRs setCreditLimit(SSEnt_SetCreditLimitRq creditLimitRq) {
		logs.getLog().trace("start service setCreditLimit");
		logs.logDebug("creditLimitRq: ",creditLimitRq);
		
		SSEnt_AuthorisationRs ent_AuthorisationRs = new SSEnt_AuthorisationRs();
		creditLimitRq.getInitiator().setRequestId(creditLimitRq.getHeader().getIdMsg());
		SSRepo_SetCreditLimit creditLimit = new SSRepo_SetCreditLimit(creditLimitRq);
		ent_AuthorisationRs = creditLimit.processing(dataSource);
		ent_AuthorisationRs.setHeader(creditLimitRq.getHeader());
		
		logs.getLog().trace("end service setCreditLimit");
		logs.logDebug("ent_AuthorisationRs: ",ent_AuthorisationRs);
		
		return ent_AuthorisationRs;
	}
	
	@Override
	public SSEnt_BaseRs reqVoidCardless(SSEnt_VoidCardlessRq voidCardlessRq) {
		logs.getLog().trace("start service reqVoidCardless");
		logs.logDebug("voidCardlessRq: ",voidCardlessRq);
		
		SSEnt_BaseRs baseRs = new SSEnt_BaseRs();
		voidCardlessRq.getInitiator().setRequestId(voidCardlessRq.getHeader().getIdMsg());
		SSRepo_VoidCardless repo_VoidCardless = new SSRepo_VoidCardless(voidCardlessRq);
		baseRs = repo_VoidCardless.processing(dataSource);
		baseRs.setHeader(voidCardlessRq.getHeader());
		
		logs.getLog().trace("end service reqVoidCardless");
		logs.logDebug("baseRs: ",baseRs);
		
		return baseRs;
	}

	@Override
    public String getRepository(SSEnt_GetRepositoryRq repoRq) {
    String baseRs;
    SSEnt_Header header = repoRq.getHeader(); 
    SSrepo_Repository repo_Reposetory = new SSrepo_Repository(repoRq);
    baseRs = repo_Reposetory.processing(dataSource);
    String finalResponse;
    try {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseBody = mapper.readTree(baseRs);
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.set("header", mapper.valueToTree(header)); 
        responseJson.set("body", responseBody); 
        finalResponse = mapper.writeValueAsString(responseJson);
    } catch (Exception e) {
        logs.getLog().error("Error while formatting the response", e);
        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
    }
    return finalResponse;
}
	
	@Override
	public String changestatus(SSEnt_changestatusRq changesttsRq) {
	    String baseRs;
	    SSEnt_Header header = changesttsRq.getHeader(); 
	    SSrepo_Changestatus changeStatus = new SSrepo_Changestatus(changesttsRq);
	    baseRs = changeStatus.processing(dataSource);
	    String finalResponse;
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode responseBody = mapper.readTree(baseRs);
	        ObjectNode responseJson = mapper.createObjectNode();
	        responseJson.set("header", mapper.valueToTree(header)); 
	        responseJson.set("body", responseBody); 
	        finalResponse = mapper.writeValueAsString(responseJson);
	    } catch (Exception e) {
	        logs.getLog().error("Error while formatting the response", e);
	        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
	    }
	    return finalResponse;
	}
	@Override
	public String validateCardPin(SSEnt_ValidateCardPinRq validateCardPinRq) {
	    String baseRs;
	    SSEnt_Header header = validateCardPinRq.getHeader(); 
	    SSrepo_validateCardPin changeStatus = new SSrepo_validateCardPin(validateCardPinRq);
	    baseRs = changeStatus.processing(dataSource);
	    String finalResponse;
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode responseBody = mapper.readTree(baseRs);
	        ObjectNode responseJson = mapper.createObjectNode();
	        responseJson.set("header", mapper.valueToTree(header)); 
	        responseJson.set("body", responseBody); 
	        finalResponse = mapper.writeValueAsString(responseJson);
	    } catch (Exception e) {
	        logs.getLog().error("Error while formatting the response", e);
	        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
	    }
	    return finalResponse;
	}

	@Override
	public String PrepaidApplication(SSEnt_PrepaidApplicationRq prepaidApplicationRq) {
	    String baseRs;
	    SSEnt_Header header = prepaidApplicationRq.getHeader(); 
	    SSRepo_PrepaidApplication prepaidapp = new SSRepo_PrepaidApplication(prepaidApplicationRq);
	    baseRs = prepaidapp.processing(dataSource);
	    String finalResponse;
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode responseBody = mapper.readTree(baseRs);
	        ObjectNode responseJson = mapper.createObjectNode();
	        responseJson.set("header", mapper.valueToTree(header)); 
	        responseJson.set("body", responseBody); 
	        finalResponse = mapper.writeValueAsString(responseJson);
	    } catch (Exception e) {
	        logs.getLog().error("Error while formatting the response", e);
	        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
	    }
	    return finalResponse;
	}

	@Override
	public String PrepaidApplicationValidation(SSEnt_PrepaidAppliValidationRq prepaidApplicationvaliRq) {
	    String baseRs;
	    SSEnt_Header header = prepaidApplicationvaliRq.getHeader(); 
	    SSRepo_PrepaidApplicationValidation prepaidappVali = new SSRepo_PrepaidApplicationValidation(prepaidApplicationvaliRq);
	    baseRs = prepaidappVali.processing(dataSource);
	    String finalResponse;
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode responseBody = mapper.readTree(baseRs);
	        ObjectNode responseJson = mapper.createObjectNode();
	        responseJson.set("header", mapper.valueToTree(header)); 
	        responseJson.set("body", responseBody); 
	        finalResponse = mapper.writeValueAsString(responseJson);
	    } catch (Exception e) {
	        logs.getLog().error("Error while formatting the response", e);
	        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
	    }
	    return finalResponse;
	}	
	
	
	
	@Override
	public String PrepaidApplicationList(SSEnt_PrepaidApplicationListRq prepaidApplicationlistRq) {
	    String baseRs;
	    SSEnt_Header header = prepaidApplicationlistRq.getHeader(); 
	    SSRepo_PrepaidApplicationList prepaidapplist = new SSRepo_PrepaidApplicationList(prepaidApplicationlistRq);
	    baseRs = prepaidapplist.processing(dataSource);
	    String finalResponse;
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode responseBody = mapper.readTree(baseRs);
	        ObjectNode responseJson = mapper.createObjectNode();
	        responseJson.set("header", mapper.valueToTree(header)); 
	        responseJson.set("body", responseBody); 
	        finalResponse = mapper.writeValueAsString(responseJson);
	    } catch (Exception e) {
	        logs.getLog().error("Error while formatting the response", e);
	        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
	    }
	    return finalResponse;
	}	
   
	@Override
	public String Redemption(SSEnt_RedemptionRq RedemptionRq) {
	    String baseRs;
	    SSEnt_Header header = RedemptionRq.getHeader(); 
	    SSRepo_Redemption redemption = new SSRepo_Redemption(RedemptionRq);
	    baseRs = redemption.processing(dataSource);
	    String finalResponse;
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode responseBody = mapper.readTree(baseRs);
	        ObjectNode responseJson = mapper.createObjectNode();
	        responseJson.set("header", mapper.valueToTree(header)); 
	        responseJson.set("body", responseBody); 
	        finalResponse = mapper.writeValueAsString(responseJson);
	    } catch (Exception e) {
	        logs.getLog().error("Error while formatting the response", e);
	        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
	    }
	    return finalResponse;
	}	
	
	@Override
	public String PosMonitoring(SSEnt_PosMonitoringRq posMonitoringRq) {
		 String baseRs;
		    SSEnt_Header header = posMonitoringRq.getHeader(); 
		    SSRepo_PosMonitoring posmonitoring = new SSRepo_PosMonitoring(posMonitoringRq);
		    baseRs = posmonitoring.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
		
	}
	
	@Override
	public String MerchantApp(SSEnt_MerchantApplicationRq merchantappliRq) {
		 String baseRs;
		    SSEnt_Header header = merchantappliRq.getHeader(); 
		    SSRepo_MerchantApplication merchantapp = new SSRepo_MerchantApplication(merchantappliRq);
		    baseRs = merchantapp.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
		
	}
	
	
	@Override
	public String MerchantAppVali(SSEnt_MerchantAppliValidationRq merchantapplivaliRq) {
		 String baseRs;
		    SSEnt_Header header = merchantapplivaliRq.getHeader(); 
		    SSRepo_MerchantAppliValidation merchantappvali = new SSRepo_MerchantAppliValidation(merchantapplivaliRq);
		    baseRs = merchantappvali.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
		
	}
	
	@Override
	public String MerchantAppUpdate(SSEnt_MerchantApplicationUpdateRq merchantappliUpdate) {
		 String baseRs;
		    SSEnt_Header header = merchantappliUpdate.getHeader(); 
		    SSRepo_MerchantApplicationUpdate merchantappupdate = new SSRepo_MerchantApplicationUpdate(merchantappliUpdate);
		    baseRs = merchantappupdate.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String MerchantAppliList(SSEnt_MerchantAppliListRq merchantApplist) {
		 String baseRs;
		    SSEnt_Header header = merchantApplist.getHeader(); 
		    SSRepo_MerchantAppliList merchantapplist = new SSRepo_MerchantAppliList(merchantApplist);
		    baseRs = merchantapplist.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String PosTerminalList(SSEnt_PosTerminalListRq posterminallist) {
		 String baseRs;
		    SSEnt_Header header = posterminallist.getHeader(); 
		    SSRepo_PosTerminalList postermllist = new SSRepo_PosTerminalList(posterminallist);
		    baseRs = postermllist.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String MerchantUpdateNoApplication(SSEnt_MerchantUpdateNoApplicationRq merchantupdatenoApp) {
		 String baseRs;
		    SSEnt_Header header = merchantupdatenoApp.getHeader(); 
		    SSRepo_MerchantUpdateNoApplication merchantupNoApp = new SSRepo_MerchantUpdateNoApplication(merchantupdatenoApp);
		    baseRs = merchantupNoApp.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	@Override
	public String PosTerminalapplication(SSEnt_PosTerminalApplicationRq posterminalapp) {
		 String baseRs;
		    SSEnt_Header header = posterminalapp.getHeader(); 
		    SSRepo_PosTerminalApplication postermapp = new SSRepo_PosTerminalApplication(posterminalapp);
		    baseRs = postermapp.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String AuthorizationList(SSEnt_AuthorizationListRq authList) {
		 String baseRs;
		    SSEnt_Header header = authList.getHeader(); 
		    SSRepo_AuthorizationList autholist = new SSRepo_AuthorizationList(authList);
		    baseRs = autholist.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}

	@Override
	public String PosApplicationList(SSEnt_PosApplicationListRq posappList) {
		 String baseRs;
		    SSEnt_Header header = posappList.getHeader(); 
		    SSRepo_PosApplicationList posApplist = new SSRepo_PosApplicationList(posappList);
		    baseRs = posApplist.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String UpdateMobile(SSEnt_UpdateMobileRq updatemobile) {
		    String baseRs;
		    SSEnt_Header header = updatemobile.getHeader(); 
		    SSRepo_UpdateMobile updatemob = new SSRepo_UpdateMobile(updatemobile);
		    baseRs = updatemob.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String UpdateAccount(SSEnt_UpdateAccountRq updateaccount) {
		    String baseRs;
		    SSEnt_Header header = updateaccount.getHeader(); 
		    SSRepo_UpdateAccount updateacc = new SSRepo_UpdateAccount(updateaccount);
		    baseRs = updateacc.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String UpdateMerchantStatus(SSEnt_StatusMerchantRq statusmerchant) {
		    String baseRs;
		    SSEnt_Header header = statusmerchant.getHeader(); 
		    SSRepo_StatusMerchant updatemerch = new SSRepo_StatusMerchant(statusmerchant);
		    baseRs = updatemerch.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}

	@Override
	public String InternetMailOrderStatus(SSEnt_InternetMailOrderStatusRq internetmailorder) {
		    String baseRs;
		    SSEnt_Header header = internetmailorder.getHeader(); 
		    SSRepo_InternetMailOrderStatus internetmailord = new SSRepo_InternetMailOrderStatus(internetmailorder);
		    baseRs = internetmailord.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String AnonymPrepaidCard(SSEnt_AnonymPrepaidCardRq anonymprepaid) {
		    String baseRs;
		    SSEnt_Header header = anonymprepaid.getHeader(); 
		    SSRepo_AnonymPrepaidCard anonymprepa = new SSRepo_AnonymPrepaidCard(anonymprepaid);
		    baseRs = anonymprepa.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String MerchantCommission(SSEnt_MerchantCommissionRq merchantcommi) {
		    String baseRs;
		    SSEnt_Header header = merchantcommi.getHeader(); 
		    SSRepo_MerchantCommission merchantcom = new SSRepo_MerchantCommission(merchantcommi);
		    baseRs = merchantcom.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}

	
	@Override
	public String ManageAddress(SSEnt_ManageAddressRq manageadress) {
		    String baseRs;
		    SSEnt_Header header = manageadress.getHeader(); 
		    SSRepo_ManageAddress manageadr = new SSRepo_ManageAddress(manageadress);
		    baseRs = manageadr.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String TerminalsByMerchant(SSEnt_TerminalsByMerchantRq terminalsbymerchant) {
		    String baseRs;
		    SSEnt_Header header = terminalsbymerchant.getHeader(); 
		    SSRepo_TerminalsByMerchant terminalmerch = new SSRepo_TerminalsByMerchant(terminalsbymerchant);
		    baseRs = terminalmerch.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String GetMerchantTransactions(SSEnt_MerchantTransactionsRq merchantransac) {
		    String baseRs;
		    SSEnt_Header header = merchantransac.getHeader(); 
		    SSRepo_MerchantTransactions merchanttransac = new SSRepo_MerchantTransactions(merchantransac);
		    baseRs = merchanttransac.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String MerchantTerminalTransactions(SSEnt_MerchantTerminalTransactionsRq merchantterminaltransac) {
		    String baseRs;
		    SSEnt_Header header = merchantterminaltransac.getHeader(); 
		    SSRepo_MerchantTerminalTransactions merchanttransac = new SSRepo_MerchantTerminalTransactions(merchantterminaltransac);
		    baseRs = merchanttransac.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	
	@Override
	public String SendPinBySms(SSEnt_SendPinBySmsRq sendpinbysms) {
		    String baseRs;
		    SSEnt_Header header = sendpinbysms.getHeader(); 
		    SSRepo_SendPinBySms sendpinsms = new SSRepo_SendPinBySms(sendpinbysms);
		    baseRs = sendpinsms.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	
	@Override
	public String ProgramRisk(SSEnt_ProgramRiskRq programrisk) {
		    String baseRs;
		    SSEnt_Header header = programrisk.getHeader(); 
		    SSRepo_ProgramRisk progrisk = new SSRepo_ProgramRisk(programrisk);
		    baseRs = progrisk.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String SmsAlert(SSEnt_SmsAlertRq smsalert) {
		    String baseRs;
		    SSEnt_Header header = smsalert.getHeader(); 
		    SSRepo_SmsAlert smsAlert = new SSRepo_SmsAlert(smsalert);
		    baseRs = smsAlert.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String SendPinByEmail(SSEnt_SendPinByEmailRq pinemail) {
		    String baseRs;
		    SSEnt_Header header = pinemail.getHeader(); 
		    SSRepo_SendPinByEmail pinbyemail = new SSRepo_SendPinByEmail(pinemail);
		    baseRs = pinbyemail.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String AtmMonitoring(SSEnt_AtmMonitoringRq atmmonitoring) {
		    String baseRs;
		    SSEnt_Header header = atmmonitoring.getHeader(); 
		    SSRepo_AtmMonitoring atmMonitoring = new SSRepo_AtmMonitoring(atmmonitoring);
		    baseRs = atmMonitoring.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String VirtualCardCreation(SSEnt_VirtualCardCreationRq virtualcard) {
		    String baseRs;
		    SSEnt_Header header = virtualcard.getHeader(); 
		    SSRepo_VirtualCardCreation virtualCard = new SSRepo_VirtualCardCreation(virtualcard);
		    baseRs = virtualCard.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}

	
	@Override
	public String DebitApplication(SSEnt_DebitApplicationRq debitappli) {
		    String baseRs;
		    SSEnt_Header header = debitappli.getHeader(); 
		    SSRepo_DebitApplication debitapp = new SSRepo_DebitApplication(debitappli);
		    baseRs = debitapp.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String DebitApplicationValidation(SSEnt_DebitApplicationValidationRq debitapplivali) {
		    String baseRs;
		    SSEnt_Header header = debitapplivali.getHeader(); 
		    SSRepo_DebitApplicationValidation debitappvali = new SSRepo_DebitApplicationValidation(debitapplivali);
		    baseRs = debitappvali.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String DebitApplicationList(SSEnt_DebitApplicationListRq debitapplilist) {
		    String baseRs;
		    SSEnt_Header header = debitapplilist.getHeader(); 
		    SSRepo_DebitApplicationList debitappli = new SSRepo_DebitApplicationList(debitapplilist);
		    baseRs = debitappli.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String ResetPin(SSEnt_ResetPinRq resetpin) {
		    String baseRs;
		    SSEnt_Header header = resetpin.getHeader(); 
		    SSRepo_ResetPin respin = new SSRepo_ResetPin(resetpin);
		    baseRs = respin.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	@Override
	public String Replacement(SSEnt_ReplacementRq replacement) {
		    String baseRs;
		    SSEnt_Header header = replacement.getHeader(); 
		    SSRepo_Replacement replac = new SSRepo_Replacement(replacement);
		    baseRs = replac.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String Renew(SSEnt_RenewRq Renew) {
		    String baseRs;
		    SSEnt_Header header = Renew.getHeader(); 
		    SSRepo_Renew renew = new SSRepo_Renew(Renew);
		    baseRs = renew.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}

	@Override
	public String CardList(SSEnt_cardlistRq Cardlist) {
		    String baseRs;
		    SSEnt_Header header = Cardlist.getHeader(); 
		    SSRepo_cardlist cardlist = new SSRepo_cardlist(Cardlist);
		    baseRs = cardlist.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	@Override
	public String AccountList(SSEnt_AccountListRq accountlist) {
		    String baseRs;
		    SSEnt_Header header = accountlist.getHeader(); 
		    SSRepo_AccountList accountList = new SSRepo_AccountList(accountlist);
		    baseRs = accountList.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	
	
	
	@Override
	public String CustomerList(SSEnt_CustomerListRq customerlist) {
		    String baseRs;
		    SSEnt_Header header = customerlist.getHeader(); 
		    SSRepo_CustomerList customerList = new SSRepo_CustomerList(customerlist);
		    baseRs = customerList.processing(dataSource);
		    String finalResponse;
		    try {
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode responseBody = mapper.readTree(baseRs);
		        ObjectNode responseJson = mapper.createObjectNode();
		        responseJson.set("header", mapper.valueToTree(header)); 
		        responseJson.set("body", responseBody); 
		        finalResponse = mapper.writeValueAsString(responseJson);
		    } catch (Exception e) {
		        logs.getLog().error("Error while formatting the response", e);
		        finalResponse = "{\"status\":\"error\",\"message\":\"An error occurred while processing the response\"}";
		    }
		    return finalResponse;
	}
	}
