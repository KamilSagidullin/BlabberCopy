package com.example.blabbercopy.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeSubscriptionResponse {

    private Long id;

    private Set<Long> subscriberIds = new HashSet<>();

}