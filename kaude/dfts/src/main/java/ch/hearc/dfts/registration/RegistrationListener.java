package ch.hearc.dfts.registration;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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

	public static String makeUrl(HttpServletRequest request){
		String url = request.getRequestURL().toString();
	    return url.replace("/signup","");
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(user, token);

		String host = RegistrationListener.makeUrl(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
		
		
		String recipientAddress = user.getEmail();
		String subject = "Confirmation de l'adresse courriel";
		String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
		
		String message = messages.getMessage("message.regSucc", null, event.getLocale());
		String messageEnd = messages.getMessage("message.regSuccEnd", null, event.getLocale());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("dontforgettosleep@comptarcilite.tk");
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + "\r\n" + host + confirmationUrl + "\r\n" + messageEnd);

		mailSender.send(email);
	}
}