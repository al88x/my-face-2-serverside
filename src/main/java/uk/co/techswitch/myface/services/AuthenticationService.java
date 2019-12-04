package uk.co.techswitch.myface.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.passwords.CreateAuthentication;
import uk.co.techswitch.myface.models.database.Authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class AuthenticationService extends DatabaseService {

    public void createPassword(long userId, CreateAuthentication createAuthentication) {
        byte[] salt = getSalt();
        String hashedPassword = get_SHA_256_SecurePassword(createAuthentication.getPassword(), salt);

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

    public Authentication getHashedPassword(long userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT * FROM passwords " +
                                "WHERE " +
                                "user_id = :userId;")
                        .bind("userId", userId)
                        .mapToBean(Authentication.class)
                        .one());
    }




    private byte[] getSalt()
    {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String get_SHA_256_SecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
