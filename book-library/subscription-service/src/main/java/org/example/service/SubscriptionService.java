package org.example.service;

import org.example.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    Optional<Subscription> doSubscription(Subscription subscriptionReq);

    List<Subscription> getAllSubscriptions();

    Optional<List<Subscription>> getSubscription(String subscriber);
}
