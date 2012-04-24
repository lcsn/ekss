package util.validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="dateValidator")
public class DateValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
//		Date date = (Date) arg2;

//		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			sdf.setLenient(false);
//			Date tmp = sdf.parse(date.toString());
			Date tmp = (Date) arg2;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -18);
			Calendar _cal = Calendar.getInstance();
			_cal.setTime(tmp);
			if (_cal.after(cal)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Es tut uns leid, aber Sie sind zu jung."));
			}
			
//		} catch (ParseException e) {
//			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Eingabe ist kein gültiges Datum."));
//		}
	}

}
