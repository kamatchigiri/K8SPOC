package com.fedex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedex.constants.AccountDao;
import com.fedex.model.EntityRepo;

@Service
public class AccountNumberService {

	/*
	 * @Autowired private EntityRepo entity;
	 */

	public synchronized boolean addAccount(AccountDao account) {
//		System.out.println("==========================> addAccount  "
//				+ account.getOBJ_ID());
		EntityRepo entity = new EntityRepo();

		if (entity.accountExists(account.getOBJ_ID())) {
			System.out.println("==========================> addAccount  if");
			return false;
		} else {
			System.out.println("==========================> addAccount  else");
			entity.addAccount(account);
			entity.addAddress(account);
			entity.addCompany(account);
			entity.addPhone(account);
			return true;
		}

	}

	public List<AccountDao> getAllDetails() {
		
		return null;
	}

}
