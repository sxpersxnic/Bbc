package com.fitcom.fitcom_restapi.security.utils;

public class SecurityConstants {
    // Controller
    public static final String AUTHCONTROLLER_ENDPOINT = "/api/v1/auth";
    public static final String SIGNUP_ENDPOINT = "/api/v1/auth/signup";
    public static final String SIGNIN_ENDPOINT = "/api/v1/auth/signin";
    public static final String SIGNOUT_ENDPOINT = "/api/v1/auth/signout";
    public static final String API_ENDPOINT = "/api/v1/**";

    // TestController
    public static final String AUTH_TEST_ENDPOINT = "/api/v1/test/**";
    public static final String ADMIN_CONTENT_ENDPOINT = "/api/v1/test/get/admin";
    public static final String MOD_CONTENT_ENDPOINT = "/api/v1/test/get/mod";
    public static final String USER_CONTENT_ENDPOINT = "/api/v1/test/get/user";
    public static final String PUBLIC_CONTENT_ENDPOINT = "/api/v1/test/get/public";

    // Jwt
    public static final String JWT_SECRET = "e4270e40561c313c2c5c79d891b9168238ec4261d20a7e0135cd2a38a4de1d23b9a5d6c7aecac6fa938470949aa6a275ea5366e253b5f8f37abff43cfe55671f7a60587a83ed790839fa53bff591e09c94259d063310d474b126de7d5fddf49cfb1b18cfd16b26103ce625dad141f468a861df1c806978806ed667cf3f4143e9755ccee2addf9c6a3e327dbc03f19e3e61189045695a076334fcfa0bb9d84bbc85e177d8c055cf4ce0bca8c6e030813bd4beb27982e86b639f0dd658ed3b744ab702409cc61b9416f00950d2467abc7b7e891f5db9396ca0d97fa485c41f6183e021205620d1ff3c7df216c276e2f10530caf7af1282d8ae795dd4ae9096c663f7ad95cc9115dfb6dbb05a9fdd8cd6b5da5e6de73c862e8e836fa343e34030a96b5e5f83243019cf94743221b18cb6800e1e117ef7e822aa8d0bf84cde640f972b470adc5579bc474a3cd76f23fdc3943b226b5ce639edc65eec703b85cf101454e786e60d9727decbd2917cad52a34a01cc263f9233ce8592a6a359eed7386f9dd9849018b36e79062b2cd08a16d00b6419b2a91c0c44939dc4a9590bdb3638cd29f521b2b660411c5e0393bf77b0f5a71a6152233c032d884473adcda2d85d4fea09f1624d7491c4646bf0bb209f1de9a25e5883bb93f3394239534ae59b8ec9d5e8372eba4cfc435dd8bcfbb403217605650ad442eac05742ebbb3dd259cd";
    public static final String ACCESS_TOKEN_NAME = "FitCom_API_AccessToken";
    public static final String ACCESS_TOKEN_EXPIRATION_TIME = "86400000";
}
