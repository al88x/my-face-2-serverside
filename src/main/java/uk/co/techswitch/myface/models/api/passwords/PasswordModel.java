package uk.co.techswitch.myface.models.api.passwords;

import uk.co.techswitch.myface.models.database.Password;

public class PasswordModel {

    private final Password password;

    public PasswordModel(Password password) {
        this.password = password;
    }

    public long getId(){
       return password.getId();
    }

    public long getUserId(){
        return password.getUserId();
    }

    public String getHashedPassword(){
        return password.getHashedPassword();
    }

    public String getSalt(){
        return password.getSalt();
    }
}
