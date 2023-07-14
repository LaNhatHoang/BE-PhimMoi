package Server.reponse;

import lombok.Builder;

@Builder
public class AuthReponse {
    private boolean status;
    private String message;
    private String accessToken;
}
