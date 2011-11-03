package util.converter;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import util.AccountType;

@FacesConverter(value="accountTypeConverter")
public class AccountTypeConverter extends EnumConverter {

	public AccountTypeConverter() {
		super(AccountType.class);
	}

}
