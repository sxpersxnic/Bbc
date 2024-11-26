package com.syncwave.backend.util;

import com.syncwave.backend.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static User getTestUser(int index) {
        return getTestUsers().get(index);
    }
    public static List<User> getTestUsers() {
        List<User> uList = new ArrayList<>();

        for (long i = 1L; i <= 4L; i++) {
            User u = new User();
            u.setId(i);
            u.setUsername("test_user" + i);
            u.setEmail("test_user" + i + "@localhost.com");
            u.setPassword("P@ssw0rd_" + i);
            uList.add(u);
        }
        return uList;
    }
}
