package ch.hearc.dfts.validators;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ch.hearc.dfts.dto.UserDto;
import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.services.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		UserDto user = (UserDto) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
		if (user.getName().length() < 6 || user.getName().length() > 32) {
			errors.rejectValue("name", "Le nom d'utilisateur doit être comprise entre 6 et 32 caractères");
		}

		if (userService.findByName(user.getName()) != null) {
			errors.rejectValue("name", "Le nom d'utilisateur déjà utilisé");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "L'adresse courriel déja utilisée");
		}
		
		if(!this.isValidEmail(user.getEmail())) {
			errors.rejectValue("email", "L'adresse courriel malformée");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "La longueur du mot de passe doit être comprise entre 8 et 32 caractères");
		}

		if (!user.getMatchingPassword().equals(user.getPassword())) {
			errors.rejectValue("password", "Les mots de passe ne sont pas identique");
		}
	}

	// source : https://www.geeksforgeeks.org/check-email-address-valid-not-java/
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
}
