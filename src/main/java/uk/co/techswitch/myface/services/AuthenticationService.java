package uk.co.techswitch.myface.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.passwords.AuthenticationCredentials;
import uk.co.techswitch.myface.models.api.passwords.AuthenticationModel;
import uk.co.techswitch.myface.models.api.users.UsersFilter;
import uk.co.techswitch.myface.models.database.Authentication;
import uk.co.techswitch.myface.models.database.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class AuthenticationService extends DatabaseService {

    private UsersService userService;

    @Autowired
    public AuthenticationService(UsersService userService) {
        this.userService = userService;
    }

    public void createPassword(long userId, AuthenticationCredentials authenticationCredentials) {
        String salt = getSalt();
        String hashedPassword = get_SHA_256_SecurePassword(authenticationCredentials.getPassword(), salt);

        jdbi.withHandle(handle ->
                handle.createUpdate(
                        "INSERT INTO passwords " +
                                "(user_id, hashed_password, salt) " +
                                "VALUES " +
                                "(:userId, :hashedPassword, :salt);")
                        .bind("userId", userId)
                        .bind("hashedPassword", hashedPassword)
                        .bind("salt", salt)
                        .execute());
    }


    private String getSalt() {
        return RandomStringUtils.randomAlphabetic(6);
    }

    private String get_SHA_256_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            passwordToHash += salt;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public ResponseEntity checkPassword(AuthenticationCredentials credentials) {

        List<User> users = userService.searchUsers(new UsersFilter(credentials.getUserName()));
        long userId = users.get(0).getId();
        Authentication authenticationCredentialsFromDatabase = jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT * FROM passwords " +
                                "WHERE " +
                                "user_id = :userId;")
                        .bind("userId", userId)
                        .mapToBean(Authentication.class)
                        .one());

        String hashedPassword = get_SHA_256_SecurePassword(credentials.getPassword(), authenticationCredentialsFromDatabase.getSalt());
        if(hashedPassword.equals(authenticationCredentialsFromDatabase.getHashedPassword())){
            return new ResponseEntity(new AuthenticationModel("Sign in successfully"), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Unauthorized",HttpStatus.UNAUTHORIZED);
    }
}
