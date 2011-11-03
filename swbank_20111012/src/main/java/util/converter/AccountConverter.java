package util.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Account;
import controller.service.AccountService;

@FacesConverter(forClass = Account.class, value = "accountConverter")
public class AccountConverter implements Converter {

	@EJB
	private AccountService accountService;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value.isEmpty()) {
			return null;
		}
		Account account = null;
		try {
			account = accountService.findAccountById(new Long(value));
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
