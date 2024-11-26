package com.fitcom.fitcom_restapi.util;

public class TestConstants {
    /** UUIDv4 must follow this structure:
     * * * xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx * * *
     * - x: any hexadeciaml digit (0-9, a-f) * *
     * - y: 8,9,a,b * * * * * * * * * * *
     * - needs to have a length of 36 characters
     * - the test constants have only a length of 35 characters, since the 36th characters will be added in each method
     * */

    /**
     * UUIDv4 must follow this structure:
     * {@code xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx}
     * where:
     * - {@code x} represents any hexadecimal digit (0-9, a-f)
     * - {@code y} represents one of the following characters: 8, 9, a, or b
     *
     * Notes:
     * - The UUID must have a length of 36 characters.
     * - The test constants have only a length of 35 characters, since the 36th character will be added in each method.
     */


    public static final String TESTUSER_UUID =      "00000000-0000-4000-8000-00000000000";
    public static final String TESTROLE_UUID =      "00000000-0000-4000-9000-00000000000";
    public static final String TESTGARMENT_UUID =   "00000000-0000-4000-a000-00000000000";
    public static final String TEST_HEX_COLOR_RED = "#ff0000";
    public static final String TEST_HEX_COLOR_BLUE = "#0000ff";
    public static final String TEST_HEX_COLOR_GREEN = "#00ff00";
    public static final String TEST_CREATE_TIME = "2024-05-15T15:14:41.748925500Z";
    public static final String USERCONTROLLERTEST_URL = "http://localhost:8080/api/v1/users";
    public static final String ROLECONTROLLERTEST_URL = "http://localhost:8080/api/v1/roles";
    public static final String GARMENTCONTROLLERTEST_URL = "http://localhost:8080/api/v1/garments";
    public static final String AUTHCONTROLLERTEST_URL = "http://localhost:8080/api/v1/auth";
    public static final String DEFAULT_ROLE = "USER";
}
