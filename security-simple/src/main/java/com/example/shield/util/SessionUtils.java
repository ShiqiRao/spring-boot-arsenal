package com.example.shield.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionUtils {

    private final SessionRegistry sessionRegistry;

    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof SecurityProperties.User) {
                UserDetails userDetails = (UserDetails) principal;
                //遍历sessionRegistry中的principal 找到对应用户的session
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        //让session立刻失效
                        information.expireNow();
                    }
                }
            }
        }
    }
}