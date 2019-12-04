package uk.co.techswitch.myface.models.api.passwords;

import uk.co.techswitch.myface.models.database.Authentication;

public class AuthenticationModel {

    private String message;

    public AuthenticationModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
