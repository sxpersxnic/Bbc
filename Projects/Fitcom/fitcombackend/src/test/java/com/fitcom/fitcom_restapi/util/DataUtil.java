package com.fitcom.fitcom_restapi.util;

import com.fitcom.fitcom_restapi.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.fitcom.fitcom_restapi.util.TestConstants.TEST_HEX_COLOR_RED;

public class DataUtil {
    public static User getTestUser(Integer index) {
        return getTestUsers().get(index);
    }

    public static List<User> getTestUsers() {
        List<User> userList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            User user = new User();
            user.setUuid(UUID.fromString(TestConstants.TESTUSER_UUID + i));
            user.setUsername("test_user" + i);
            user.setEmail("test-user" + i + "@localhost.com");
            user.setPassword("P@ssw0rd_" + i);
            user.setCreateTime(Instant.parse(TestConstants.TEST_CREATE_TIME));

            Garment garment = new Garment();
            garment.setUuid(UUID.fromString(TestConstants.TESTGARMENT_UUID + i));
            garment.setName("Garment" + i);
            garment.setOwner(user);
            user.setGarments(new HashSet<>());
            user.setRole(getTestRole(2));
            user.getGarments().add(garment);
            userList.add(user);
        }
        return userList;
    }

    public static Role getTestRole(Integer index) {
        return getTestRoles().get(index);
    }

    public static List<Role> getTestRoles() {
        List<Role> roleList = new ArrayList<>();
        int roleUuid = 1;

        for (ERole name : List.of(ERole.ROLE_ADMIN, ERole.ROLE_MODERATOR, ERole.ROLE_USER)) {
            Role role = new Role();
            role.setName(name);
            role.setUuid(UUID.fromString(TestConstants.TESTROLE_UUID + roleUuid++));
            roleList.add(role);
        }

        return roleList;
    }

    public static Garment getTestGarment(Integer index) {
        return getTestGarments().get(index);
    }
    public static List<Garment> getTestGarments() {
        List<Garment> garmentList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            garmentList.add(new Garment(UUID.fromString(TestConstants.TESTGARMENT_UUID + i), "TestGarment" + i, ECategory.NONE, TEST_HEX_COLOR_RED, getTestUser(0)));
        }
        return garmentList;
    }
}
