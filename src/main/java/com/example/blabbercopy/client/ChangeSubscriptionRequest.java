package com.example.blabbercopy.client;

import com.example.blabbercopy.entity.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeSubscriptionRequest {

    private Long followeeId;

    private Long followerId;

    private SubscriptionType subscriptionType;

}
