package com.utility;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;

import com.employees.AddAccountRequest;
import com.employees.SearchAccountRequest;
import com.employees.SearchAccountResponse;

public class SolrUtility {

	public void insertDocument(String xml)
	{
		try {
		AddAccountRequest request=null;
		request=JMSUtility.convertXMLToObject(request, xml);
		//String url="http://c0018681.test.cloud.fedex.com:8983/solr/k8scollection/";
		String url="http://c0018681.test.cloud.fedex.com:8983/solr/helloworld/";
		HttpSolrClient solr=new HttpSolrClient.Builder(url).build();
		solr.setParser(new XMLResponseParser());
		
		SolrInputDocument document=new SolrInputDocument();
		
		if(request!=null)
		{
			
			document.addField("id", request.getObjId());
			document.addField("ExternalId", request.getExternalId() );
			document.addField("TypeCode", request.getTypeCode());
			document.addField("ExpirationTimeStamp", request.getExpirationTimestamp());
			document.addField("CustomerView", request.getCustomerView() );
			document.addField("CustomerVIewModel", request.getCustomerViewModelValue());
			document.addField("CompanyTypeCode", request.getCompanyTypeCode());
			document.addField("CompanyDescription", request.getCompanyDescription());
			document.addField("PhoneTypeCode", request.getPhoneTypeCode());
			document.addField("PhoneCountryCode", request.getPhcountryCode());
			document.addField("AreaCode", request.getAreaCode());
			document.addField("PhoneNumber", request.getPhoneNumber());
			document.addField("Extension", request.getExtension());
			document.addField("AddressTypeCode", request.getAddressTypeCode());
			document.addField("StreetLine1", request.getStreetLine1());
			document.addField("StreetLine2", request.getStreetLine2());
			document.addField("StreetLine3", request.getStreetLine3());
			document.addField("City", request.getCity());
			document.addField("State", request.getState());
			document.addField("PostalCode", request.getPostalCode());
			document.addField("AddCountryCode", request.getAddcountryCode());
		
		}
			solr.add(document);
			solr.commit();
		
		} catch (SolrServerException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
	} 
}
}
