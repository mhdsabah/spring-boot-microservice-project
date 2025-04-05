package com.sabah.microservice.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.sabah.microservice.order_service.event.OrderPlacedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Got message from order-placed topic: {}", orderPlacedEvent);
        //send email to the customer
        String body = String.format("""
            Hi ,

            Your order with order number %s is now placed successfully.
            
            Best Regards
            Spring Shop
            """,
           // orderPlacedEvent.getFirstName().toString(),
           // orderPlacedEvent.getLastName().toString(),
           orderPlacedEvent.getOrderNumber());

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("microserviceshop@gmail.com");
            messageHelper.setTo("sabah.uni.acc@gmail.com");
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(body);
        };
        try {
            // System.out.println("\n\n\n\n" + orderPlacedEvent.getEmail() + "\n" + orderPlacedEvent.getOrderNumber()+"\n" + body+"\n\n\n\n");
            javaMailSender.send(messagePreparator);
            
            log.info("Order Notifcation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }


    }

}
