package ch.bbcag.ticketshop.security;


public final class SignInResponseDTO {

    private final String accessToken;
    private Integer userId;

    public SignInResponseDTO(String accessToken, Integer userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getUserId() {
        return userId;
    }

}
