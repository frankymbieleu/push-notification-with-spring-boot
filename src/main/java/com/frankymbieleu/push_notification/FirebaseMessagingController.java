package com.frankymbieleu.push_notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class FirebaseMessagingController {

    private FirebaseMessagingService firebaseService;

    public FirebaseMessagingController(FirebaseMessagingService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping(value = "/send")
    @ResponseBody
    public String sendNotification(@RequestBody Note note) throws FirebaseMessagingException {
        return firebaseService.sendNotification(note);
    }

    @PostMapping(value = "/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        firebaseService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping(value = "/unsubscribe")
    public void unsubscribeFromTopic(@RequestBody  SubscriptionRequestDto subscriptionRequestDto) {
        firebaseService.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping(value = "/topic")
    public String sendPnsToTopic(@RequestBody Note notificationRequestDto) {
        return firebaseService.sendPnsToTopic(notificationRequestDto);
    }
}