package ch.hearc.dfts.models.services;

import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.VerificationToken;

public interface IUserService {
	void save(User user);

	User findByName(String name);

	User findByEmail(String email);

	//User registerNewUserAccount(User accountDto) throws Exception;

	User getUser(String verificationToken);

	void saveRegisteredUser(User user);

	void createVerificationToken(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);
}