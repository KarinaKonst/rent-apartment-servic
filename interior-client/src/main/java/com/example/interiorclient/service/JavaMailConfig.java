package com.example.interiorclient.service;

import com.example.interiorclient.entity.SmtpEntity;
import com.example.interiorclient.repository.SmtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class JavaMailConfig {
@Autowired
private SmtpRepository smtpRepository;
    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        SmtpEntity smtpEntity=smtpRepository.findById(1L).get();
        javaMailSender.setHost(smtpEntity.getHost());
        javaMailSender.setPort(smtpEntity.getPort());
        javaMailSender.setUsername(smtpEntity.getUserName());
        javaMailSender.setPassword(smtpEntity.getPassword());

        Properties properties =javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", "smtps");
        properties.setProperty("mail.debug", "true");

        return javaMailSender;
    }
}
