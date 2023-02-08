package com.quinbay.scheduler.service;

import com.quinbay.scheduler.api.MailAPI;
import com.quinbay.scheduler.model.EmailDetails;
import com.quinbay.scheduler.model.Issues;
import com.quinbay.scheduler.model.ReturnData;
import com.quinbay.scheduler.model.Users;
import com.quinbay.scheduler.repository.IssueRepository;
import com.quinbay.scheduler.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class MailService implements MailAPI {


    UsersRepository usersRepository;

    IssueRepository issueRepository;

    @Autowired JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") String sender;

    public String sendMailWithAttachment(EmailDetails details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

    @Override
    public ReturnData displayByUserId(String userId) {
        ReturnData returnData = new ReturnData();
        try {
            if(usersRepository.findById(userId).get()!=null) {
                returnData.setStatus(true);
                returnData.setMessage("Data fetched successfully");
                Users user = usersRepository.findById(userId).get();
                user.setPassword(null);
                returnData.setUserData(user);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            returnData.setStatus(false);
            returnData.setMessage("User does not exist!");
            returnData.setUserData(null);
        }
        return returnData;
    }

    @Override
    public  List<Users> displayAllUsers() {
        try {
            return usersRepository.findAll();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    public Optional<Issues> displayByIssueId(String id) {
        Optional<Issues> opt = issueRepository.findByIssueId(id);
        if(opt.isPresent()) {
            return opt;
        }
        else
            return null;
    }

    public List<Issues> displayAllIssues() {
        return issueRepository.findAll();
    }
}
