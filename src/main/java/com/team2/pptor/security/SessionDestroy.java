package com.team2.pptor.security;

package com.team2.pptor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Component
public class SessionDestroy {

    private static final Logger LOG
            = Logger.getLogger(String.valueOf(SessionDestroy.class));

    @Autowired
    private SessionRegistry sessionRegistry;

    @PostConstruct
    public void init() {
        List<SessionInformation> list = sessionRegistry.getAllSessions( dupLoginUserName, false );

        for ( int n = 0; n < list.size(); n++ ) {

            SessionInformation info = list.get( n );

            info.expireNow();

        }
    }
}