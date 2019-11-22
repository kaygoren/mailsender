package yte.spring.mailsender;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import java.io.IOException;

@SpringBootApplication
public class MailsenderApplication {

	private static SimpleMailMessage simpleMailMessage = new SimpleMailMessage();


	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(MailsenderApplication.class, args);

		simpleMailMessage.setTo("kenanaygoren@gmail.com");
		simpleMailMessage.setText("mail sender example");
		simpleMailMessage.setSubject("spring tutorials");

		try {
//			ctx.getBean(MailUtil.class).sendEmail(simpleMailMessage);
			ctx.getBean(MailUtil.class).sendEmailWithAttachmnet(simpleMailMessage);
		}catch (MessagingException | WriterException | IOException e){
			e.printStackTrace();
		}
	}
}
