package yte.spring.mailsender;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
public class MailUtil {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailUtil(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(SimpleMailMessage simpleMailMessage){
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmailWithAttachmnet(SimpleMailMessage simpleMailMessage) throws MessagingException, IOException, WriterException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(Objects.requireNonNull(simpleMailMessage.getTo()));
        mimeMessageHelper.setSubject(Objects.requireNonNull(simpleMailMessage.getSubject()));
        mimeMessageHelper.setText(simpleMailMessage.getText());

        QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
//        qrCodeGenerator.generateQRCodeImage(simpleMailMessage.getSubject(), "C:\\Users\\kenan.aygoren\\Desktop\\kaygoren\\qrcode.png");
//        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\kenan.aygoren\\Desktop\\kaygoren\\qrcode.png"));
//        mimeMessageHelper.addAttachment("qrcode.png",file);
        InputStreamSource inputStreamSource = new ByteArrayResource(qrCodeGenerator.getQRCodeImage("5398333332"));


        mimeMessageHelper.addAttachment("qrcode.png", inputStreamSource);
        javaMailSender.send(mimeMessage);
    }
}
