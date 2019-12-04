package uk.co.techswitch.myface.services;

import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.passwords.CreatePassword;
import uk.co.techswitch.myface.models.database.Password;

@Service
public class PasswordService extends DatabaseService {

    public void createPassword(CreatePassword createPassword) {
        jdbi.withHandle(handle ->
                handle.createUpdate(
                        "INSERT INTO passwords " +
                                "(user_id, hashed_password, salt) " +
                                "VALUES " +
                                "(:userId, :hashedPassword, :salt);")
                        .bind("userId", createPassword.getUserId())
                        .bind("hashedPassword", createPassword.getHashedPassword())
                        .bind("salt", createPassword.getSalt())
                        .execute());
    }

    public Password getHashedPassword(long userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT * FROM passwords " +
                                "WHERE " +
                                "user_id = :userId;")
                        .bind("userId", userId)
                        .mapToBean(Password.class)
                        .one());
    }
}
