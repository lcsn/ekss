package util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

import model.Account;
import controller.service.AccountService;

@FacesConverter(forClass = Account.class)
public class AccountConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value.isEmpty()) {
			return null;
		}
		
		Account account = null;
		try {
			InitialContext ic = new InitialContext();
			AccountService accountSerivce = (AccountService) ic.lookup("java:global/swbank_20111012/AccountService");
			account = accountSerivce.findAccountById(new Long(value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return account;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null) {
			return "";
		}
		return ((Account) value).getId().toString();
	}

}
