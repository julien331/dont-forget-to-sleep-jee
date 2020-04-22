package ch.hearc.dfts;

import java.util.Locale;
import java.util.Properties;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class BeanConfiguration {
	@Value("${spring.mail.host}")
	private String smtpHost;
	
	@Value("${spring.mail.port}")
	private int smtpPort;
	
	@Value("${spring.mail.username}")
	private String smtpUsername;
	
	@Value("${spring.mail.password}")
	private String smtpPassword;
	

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(smtpHost);
	    mailSender.setPort(smtpPort);
	
	    mailSender.setUsername(smtpUsername);
	    mailSender.setPassword(smtpPassword);
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	
    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }
}
