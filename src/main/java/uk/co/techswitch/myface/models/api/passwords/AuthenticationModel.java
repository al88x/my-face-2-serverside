package uk.co.techswitch.myface.models.api.passwords;

import uk.co.techswitch.myface.models.database.Authentication;

public class AuthenticationModel {

    private final Authentication authentication;

    public AuthenticationModel(Authentication authentication) {
        this.authentication = authentication;
    }

    public long getId(){
       return authentication.getId();
    }

    public long getUserId(){
        return authentication.getUserId();
    }

    public String getHashedPassword(){
        return authentication.getHashedPassword();
    }

    public String getSalt(){
        return authentication.getSalt();
    }
}
