package org.SimmonDobber;

import lombok.Getter;

public class RoleManagerMock {

    @Getter
    private static String currentRole = "student";

    public static void switchRoleToStudent() {
        currentRole = "student";
    }

    public static void switchRoleToAdmin() {
        currentRole = "admin";
    }

}
