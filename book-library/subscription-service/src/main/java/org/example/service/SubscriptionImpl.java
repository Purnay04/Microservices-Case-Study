package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Subscription;
import org.example.repo.SubscriptionRepo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionImpl implements SubscriptionService{

    final SubscriptionRepo subscriptionRepo;
    public Optional<Subscription> doSubscription(Subscription subscriptionReq) {
        if(ObjectUtils.isEmpty(subscriptionReq))
            return Optional.of(null);
        subscriptionRepo.save(subscriptionReq);
        return Optional.of(subscriptionReq);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepo.findAll();
    }

    public Optional<List<Subscription>> getSubscription(String subscriber) {
        return subscriptionRepo.getSubscriptionBySubscriberName(subscriber);
    }
}
