package util.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

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
		String dateString = (String) arg2;

//		StringTokenizer tokenizer = new StringTokenizer(dateString, ".");
//		int dd = Integer.valueOf(tokenizer.nextToken());
//		int mm = Integer.valueOf(tokenizer.nextToken());
//		int yyyy = Integer.valueOf(tokenizer.nextToken());

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			sdf.setLenient(false);
			Date date = sdf.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -18);
			Calendar _cal = Calendar.getInstance();
			_cal.setTime(date);
			if (_cal.after(cal)) {
				throw new ValidatorException(new FacesMessage("Es tut uns leid, aber Sie sind zu jung."));
			}
			
		} catch (ParseException e) {
			throw new ValidatorException(new FacesMessage("Eingabe ist kein gültiges Datum."));
		}
//		finally {
//		}
		
//		if(dd < 1 || dd > 31) {
//			throw new ValidatorException(new FacesMessage("Tag muss zwischen 1 und 31 liegen."));
//		}else if(mm < 1 || mm > 12) {
//			throw new ValidatorException(new FacesMessage("Monat muss zwischen 1 und 12 liegen."));
//		}else if(yyyy < 1900 || yyyy > 2200) {
//			throw new ValidatorException(new FacesMessage("Jahr muss zwischen 1900 und 2200 liegen."));
//		}
	}

}
