package com.notificationMicroservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationMicroservice.exception.FailedToSendEmailException;
import com.notificationMicroservice.mapper.NotificationMapper;
import com.notificationMicroservice.model.Notification;
import com.notificationMicroservice.repository.NotificationRepository;
import com.notificationMicroservice.service.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

import static com.notificationMicroservice.enumerators.NotificationStatus.*;
import static com.notificationMicroservice.enumerators.NotificationType.EMAIL;

import com.notificationMicroservice.dto.NotificationRequestDTO;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender emailSender;
    private final NotificationMapper notificationMapper;
    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.mail.username}")
    private String senderEmail;

    @KafkaListener(topics = "notification-topic", groupId = "notification_group")
    public void sendEmail(String message) throws FailedToSendEmailException {
        Notification notification = null;

        try {
            NotificationRequestDTO notificationRequest = objectMapper.readValue(message, NotificationRequestDTO.class);

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(notificationRequest.getRecipient());
            helper.setSubject(notificationRequest.getSubject());
            helper.setText(notificationRequest.getBody());

            byte[] attachmentBytes = Base64.getDecoder().decode(notificationRequest.getAttachment());
            helper.addAttachment("Movements_Report.xlsx", new ByteArrayResource(attachmentBytes));

            emailSender.send(mimeMessage);

            notification = notificationMapper.toEntity(notificationRequest);
            notification.setType(EMAIL);
            notification.setStatus(SENT);
            notification.setSender(senderEmail);

        } catch (MessagingException | JsonProcessingException e) {
            notification.setStatus(FAILED);
            throw new FailedToSendEmailException(notification.getRecipient());
        } finally {
            notificationRepository.save(notification);
        }
    }

    @Scheduled(cron = "0 0 0,12 * * ?")
    @Transactional
    @Override
    public void retryFailedEmails() throws JsonProcessingException, FailedToSendEmailException {
        List<Notification> failedNotifications = notificationRepository.findByStatus(FAILED);

        for (Notification notification : failedNotifications) {
            sendEmail(objectMapper.writeValueAsString(notification));
        }
    }
}