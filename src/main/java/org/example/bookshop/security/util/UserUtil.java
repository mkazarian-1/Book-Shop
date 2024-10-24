package org.example.bookshop.security.util;

import org.example.bookshop.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static User getCurrenSesstionUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
