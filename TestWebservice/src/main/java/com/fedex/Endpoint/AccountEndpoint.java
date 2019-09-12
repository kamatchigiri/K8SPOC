package com.fedex.Endpoint;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Request;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fedex.constants.AccountDao;
import com.fedex.service.AccountNumberService;
//import com.fedex.serviceImpl.ServiceInterface;
import com.fedex.testservice.AccountDetails;
import com.fedex.testservice.AddAccountRequest;
import com.fedex.testservice.AddAccountResponse;
import com.fedex.testservice.GetAccountRequest;
import com.fedex.testservice.GetAccountResponse;
import com.fedex.testservice.ServiceStatus;
import com.fedex.utility.JMSUtility;
import com.fedex.utility.SolrUtility;

@Endpoint
public class AccountEndpoint {

	private static final String namespace_URI = "http://fedex.com/TestService";

	@Autowired
	private AccountNumberService accountService;
	
	private JMSPublisher jmsPublisher=new JMSPublisher();
	
	private SolrUtility solrUtility=new SolrUtility();
	
	
	public JMSPublisher getJmsPublisher() {
		return jmsPublisher;
	}

	public void setJmsPublisher(JMSPublisher jmsPublisher) {
		this.jmsPublisher = jmsPublisher;
	}

	private static final String xmlFilePath="X:\\KafkaProducer\\webservice.txt";

	@PayloadRoot(namespace = namespace_URI, localPart = "addAccountRequest")
	@ResponsePayload
	public AddAccountResponse addAccount(
			@RequestPayload AddAccountRequest request) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>AccountEndpoint");
		
		AddAccountResponse response = new AddAccountResponse();

		ServiceStatus serviceStatus = new ServiceStatus();
		// TODO: handle exception
		AccountDao account = new AccountDao();
		try {
			account.setEFF_TMSTP(request.getEffectiveTimestamp());
			account.setOBJ_ID(request.getObjId());
			account.setEXTERNAL_ID_NBR(request.getExternalId());
			account.setTYPE_CD(request.getTypeCode());
			account.setEXPR_TMSTP(request.getExpirationTimestamp());
			System.out.println("1=========>" + account.toString());

			// AccountDao additionalDetails = new AccountDao();
			// Company
			account.setCUST_VIEW_CD(request.getCustomerView());
			account.setCUST_MODEL_CD(request.getCustomerViewModelValue());
			account.setCMP_TYPE_CD(request.getCompanyTypeCode());
			account.setNAME_DESC(request.getCompanyDescription());
			System.out.println("2=========>" + account.toString());

			// Address
			account.setADD_TYPE_CD(request.getAddressTypeCode());
			account.setADDR_LINE_1_DESC(request.getStreetLine1());
			account.setADDR_LINE_2_DESC(request.getStreetLine2());
			account.setADDR_LINE_3_DESC(request.getStreetLine3());
			account.setCITY_NM(request.getCity());
			account.setSTATE_PROVINCE_NM(request.getState());
			account.setPSTL_CD(request.getPostalCode());
			account.setCNTRY_CD(request.getAddcountryCode());
			System.out.println("3==========>" + account.toString());

			// phone
			account.setPH_TYPE_CD(request.getPhoneTypeCode());
			account.setTEL_INTL_CD(request.getPhcountryCode());
			account.setTEL_AREA_CD(request.getAreaCode());
			account.setTEL_NBR(request.getPhoneNumber());
			account.setTEL_EXT_NBR(request.getExtension());
			System.out.println("4===========>" + account.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erorr======>" + e.getMessage());
		}
		System.out.println("AccountsDAO==========> " + account);

		boolean flag = accountService.addAccount(account);
		System.out.println("flag ====> " + flag);

		if (flag == false) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Content Already Available");
			response.setServiceStatus(serviceStatus);

		} else {

			AccountDetails accountdetail = new AccountDetails();

			BeanUtils.copyProperties(account, accountdetail);
			accountdetail.setObjId(request.getObjId());
			response.setAccountDetails(accountdetail);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Content Added Successfully");
			response.setServiceStatus(serviceStatus);

		}
		
		jmsPublisher.publishMessage(request);
		return response;

	}

	@PayloadRoot(namespace = namespace_URI, localPart = "getAccountRequest")
    @ResponsePayload
    public GetAccountResponse getAccountDetails(@RequestPayload GetAccountRequest request) {

			GetAccountResponse response = null;
			
			response=solrUtility.searchDocument(request);
           return response;
    }
}
