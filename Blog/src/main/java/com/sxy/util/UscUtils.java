package com.sxy.util;

import com.sxy.shiro.Account;
import org.apache.shiro.SecurityUtils;

public class UscUtils {

    public static Account account() {
        return (Account) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long userId() {
        return account().getId();
    }

    public static boolean checkId(Long id) {
        return userId().equals(id);
    }
}
