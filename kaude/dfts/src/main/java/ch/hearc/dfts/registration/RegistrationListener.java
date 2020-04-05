package ch.hearc.dfts.registration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import ch.hearc.dfts.models.User;
import ch.hearc.dfts.models.services.IUserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private IUserService service;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(user, token);

		String recipientAddress = user.getEmail();
		String subject = "Confirmation de l'adresse courriel";
		String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;

		String message = messages.getMessage("message.regSucc", null, event.getLocale());
		String messageEnd = messages.getMessage("message.regSuccEnd", null, event.getLocale());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("dontforgettosleep@comptarcilite.tk");
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl + "\r\n" + messageEnd);

		mailSender.send(email);
	}
}