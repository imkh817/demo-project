package com.example.demo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationEvents {

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {

        log.info("event: {}", event);
      log.info("로그인 성공");
    }

    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event){
      log.info("로그인 실패");
    }
}
