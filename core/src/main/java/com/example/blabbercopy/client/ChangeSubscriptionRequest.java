package com.example.blabbercopy.client;

import com.example.blabbercopy.entity.SubscriptionType;
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
