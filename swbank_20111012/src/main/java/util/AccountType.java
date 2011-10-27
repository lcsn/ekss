package util;

import model.Account;
import model.CommonAccount;
import model.GiroAccount;
import model.SmartAccount;

public enum AccountType {
	SMARTACCOUNT(SmartAccount.class),
	GIROACCOUNT(CommonAccount.class),
	COMMONACCOUNT(GiroAccount.class);
	
	private Class<? extends Account> clazz;

	AccountType(Class<? extends Account> clazz) {
		this.clazz = clazz;
	}
	
	public Class<? extends Account> getClazz() {
		return clazz;
	}
	
}
