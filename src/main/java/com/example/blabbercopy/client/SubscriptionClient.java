package com.example.blabbercopy.client;

import com.example.blabbercopy.entity.SubscriptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class SubscriptionClient {
    @Value("${app.client.subscription-service.change-subscription}")
    private String changeSubscriptionUrl;
    @Value("${app.client.subscription-service.delete-subscription-by-id}")
    private String deleteSubscriptionByIdUrl;
    @Value("${app.client.subscription-service.username}")
    private String username;
    @Value("${app.client.subscription-service.password}")
    private String password;

    private final RestClient restClient;

    public void subscribe(int id, int subscriberId) {
        sendChangeSubscriptionRequest(id, subscriberId, SubscriptionType.SUBSCRIBE);
    }

    public void unsubscribe(int id, int subscriberId) {
        sendChangeSubscriptionRequest(id, subscriberId, SubscriptionType.UNSUBSCRIBE);
    }

    public void deleteSubscription(int subscriptionId) {
        log.info("send delete subscription by id {}", subscriptionId);
        var response = restClient.delete().uri(deleteSubscriptionByIdUrl, subscriptionId).headers(httpHeaders ->
                httpHeaders.setBasicAuth(username, password)).retrieve().toEntity(Void.class);
        log.info("delete subscription response status {}", response.getStatusCode());
    }

    private void sendChangeSubscriptionRequest(int id, int subscriberId, SubscriptionType subscriptionType) {
        log.info("Send change subscription request with id {} with subscriber id {} and type {}", id, subscriberId, subscriptionType);
        var response = restClient.post().uri(changeSubscriptionUrl).headers(httpHeaders -> httpHeaders.setBasicAuth(username, password))
                .body(new ChangeSubscriptionRequest(id, subscriberId, SubscriptionType.SUBSCRIBE)).retrieve().toEntity(ChangeSubscriptionResponse.class);
        log.info("Change subscription response status code {} ", response.getStatusCode());
        log.info("Change subscription response body {}", response.getBody());
    }

}
