package com.fpt.g2.config;

import com.fpt.g2.constant.CommonConstant;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        HttpSession session = session();
        Long userId;
        if (session.getAttribute(CommonConstant.ID) == null) {
            userId = 9L;
        } else {
            userId = Long.valueOf(session.getAttribute(CommonConstant.ID).toString());
        }

        return Optional.of(userId);
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }
}