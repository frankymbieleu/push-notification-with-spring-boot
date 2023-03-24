package com.frankymbieleu.push_notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidFcmOptions;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public String sendNotification(Note note) throws FirebaseMessagingException {
        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();
        AndroidNotification androidNotification = AndroidNotification.builder().setClickAction("FLUTTER_NOTIFICATION_CLICK")
                .setPriority(AndroidNotification.Priority.MAX)
                .setBody(note.getContent())
                .setTitle(note.getSubject())
                .build();

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(androidNotification)
                .setPriority(AndroidConfig.Priority.HIGH)
                .putAllData(note.getData()).build();

        Message message = Message
                .builder()
                .setToken(note.getToken())
                .setNotification(notification)
                .putAllData(note.getData())
                .setAndroidConfig(androidConfig)
                .build();
        return firebaseMessaging.send(message);
    }


    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            firebaseMessaging.subscribeToTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscribe to topic fail", e);
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            firebaseMessaging.unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase unsubscribe from topic fail", e);
        }
    }

    public String sendPnsToTopic(Note notificationRequestDto) {
        Notification notification = Notification
                .builder()
                .setTitle(notificationRequestDto.getSubject())
                .setBody(notificationRequestDto.getContent())
                .build();
        Message message = Message.builder()
                .setTopic(notificationRequestDto.getTarget())
                .setNotification(notification)
                .putAllData(notificationRequestDto.getData())
                .build();

        String response = null;
        try {
            response = firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }
}