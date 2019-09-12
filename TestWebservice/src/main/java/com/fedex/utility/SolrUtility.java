package com.fedex.utility;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;






import com.fedex.testservice.AccountDetails;
import com.fedex.testservice.AddAccountRequest;
import com.fedex.testservice.GetAccountRequest;
import com.fedex.testservice.GetAccountResponse;

public class SolrUtility {

	public GetAccountResponse searchDocument(GetAccountRequest request)
	{
		GetAccountResponse getAccountResponse=new GetAccountResponse();
		
		try {
			String url="http://c0018681.test.cloud.fedex.com:8983/solr/helloworld/";
		HttpSolrClient solr=new HttpSolrClient(url);
		solr.setParser(new XMLResponseParser());
			SolrQuery query=new SolrQuery();
			query.set("q", "id:"+request.getObjId());
			
			
			QueryResponse response= solr.query(query);
			SolrDocumentList docList= response.getResults();
			for (SolrDocument solrDocument : docList) {
				AccountDetails accountDetails=new AccountDetails();
				String id=(String)solrDocument.getFieldValue("id");
				accountDetails.setObjId(Integer.valueOf(id));
				accountDetails.setExternalId((String)solrDocument.getFieldValue("ExternalId"));
				accountDetails.setTypeCode((String)solrDocument.getFieldValue("TypeCode"));
				accountDetails.setExpirationTimestamp((String)solrDocument.getFieldValue("ExpirationTimeStamp"));
				accountDetails.setCustomerView((String)solrDocument.getFieldValue("CustomerView"));
				accountDetails.setCustomerViewModelValue((String)solrDocument.getFieldValue("CustomerVIewModel"));
				accountDetails.setCompanyTypeCode((String)solrDocument.getFieldValue("CompanyTypeCode"));
				accountDetails.setCompanyDescription((String)solrDocument.getFieldValue("CompanyDescription"));
				accountDetails.setPhoneTypeCode((String)solrDocument.getFieldValue("PhoneTypeCode"));
				accountDetails.setPhcountryCode((String)solrDocument.getFieldValue("PhoneCountryCode"));
				accountDetails.setAreaCode((String)solrDocument.getFieldValue("AreaCode"));
				accountDetails.setPhoneNumber((String)solrDocument.getFieldValue("PhoneNumber"));
				accountDetails.setExtension((String)solrDocument.getFieldValue("Extension"));
				accountDetails.setAddressTypeCode((String)solrDocument.getFieldValue("AddressTypeCode"));
				accountDetails.setStreetLine1((String)solrDocument.getFieldValue("StreetLine1"));
				accountDetails.setStreetLine2((String)solrDocument.getFieldValue("StreetLine2"));
				accountDetails.setStreetLine3((String)solrDocument.getFieldValue("StreetLine3"));
				accountDetails.setCity((String)solrDocument.getFieldValue("City"));
				accountDetails.setState((String)solrDocument.getFieldValue("State"));
				accountDetails.setPostalCode((String)solrDocument.getFieldValue("PostalCode"));
				accountDetails.setAddcountryCode((String)solrDocument.getFieldValue("AddCountryCode"));
				
				getAccountResponse.setAccountDetails(accountDetails);
			System.out.println(solrDocument.toString());	
			}
		} catch (SolrServerException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
	} 
		
		return getAccountResponse;
}
}
