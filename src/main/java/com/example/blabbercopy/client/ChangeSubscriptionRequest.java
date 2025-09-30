package com.example.client;

import com.example.entity.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeSubscriptionRequest {
    private int followeeId;
    private int followerId;
    private SubscriptionType subscriptionType;
}
