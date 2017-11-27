package com.service;

import org.springframework.stereotype.Service;

import com.model.AccountInfo;
@Service
public class AccountService {

	private AccountInfo accountInfo;

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}

	@Override
	public String toString() {
		return "AccountService [accountInfo=" + accountInfo + "]";
	}
	

}
