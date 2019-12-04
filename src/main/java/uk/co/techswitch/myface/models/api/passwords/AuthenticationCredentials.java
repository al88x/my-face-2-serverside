package uk.co.techswitch.myface.models.api.passwords;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthenticationCredentials {

    @NotNull
    private String userName;
    @NotNull
    private String password;

    public AuthenticationCredentials(String credentialsEncodedBase64) {
        String base64Credentials = credentialsEncodedBase64.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentialsDecoded = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] values = credentialsDecoded.split(":", 2);
        this.userName = values[0];
        this.password = values[1];
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
