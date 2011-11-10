package controller.service;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import util.AccountType;

public class AccountTypeService {

	@Named
	@Produces
	public AccountType[] getAccountTypes() {
		return AccountType.values();
	}

}
