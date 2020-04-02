package ch.hearc.dfts.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ch.hearc.dfts.DTO.UserDto;

/**
 * Source : baeldung
 * @author julien.chappuis1
 *
 */

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UserDto user = (UserDto) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}
}