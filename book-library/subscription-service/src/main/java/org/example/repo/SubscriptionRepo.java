package org.example.repo;

import org.example.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {
    @Query("select u from Subscription u where "
            + "u.subscriberName = :subscriber_name"
    )
    Optional<List<Subscription>> getSubscriptionBySubscriberName(String subscriber_name);
}
