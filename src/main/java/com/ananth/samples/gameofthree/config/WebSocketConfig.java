package com.ananth.samples.gameofthree.config;

import com.ananth.samples.gameofthree.interceptor.UserPrincipalHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableSimpleBroker("/queue");
        config.setApplicationDestinationPrefixes("/api");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/game_of_three")
                .setHandshakeHandler(userPrincipalHandshakeInterceptor()).withSockJS();
    }

    @Bean
    public UserPrincipalHandshakeInterceptor userPrincipalHandshakeInterceptor() {
        return new UserPrincipalHandshakeInterceptor();
    }
}