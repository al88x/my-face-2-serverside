package uk.co.techswitch.myface.services;

import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.CreateUser;
import uk.co.techswitch.myface.models.api.UserFilter;
import uk.co.techswitch.myface.models.database.User;

import java.util.List;

@Service
public class UsersService extends DatabaseService {

    public List<User> searchUsers(UserFilter filter) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT * FROM users " +
                                "WHERE (:username IS NULL OR username LIKE :username) " +
                                "AND (:email IS NULL OR email LIKE :email) " +
                                "AND (:firstName IS NULL OR first_name LIKE :firstName) " +
                                "AND (:lastName IS NULL OR last_name LIKE :lastName) " +
                                "LIMIT :limit OFFSET :offset"
                )
                        .bind("username", filter.getUsername())
                        .bind("email", filter.getEmail())
                        .bind("firstName", filter.getFirstName())
                        .bind("lastName", filter.getLastName())
                        .bind("limit", filter.getPageSize())
                        .bind("offset", filter.getOffset())
                        .mapToBean(User.class)
                        .list()
        );
    }

    public int countUsers(UserFilter filter) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT COUNT(*) FROM users " +
                                "WHERE (:username IS NULL OR username LIKE :username) " +
                                "AND (:email IS NULL OR email LIKE :email) " +
                                "AND (:firstName IS NULL OR first_name LIKE :firstName) " +
                                "AND (:lastName IS NULL OR last_name LIKE :lastName) "
                )
                        .bind("username", filter.getUsername())
                        .bind("email", filter.getEmail())
                        .bind("firstName", filter.getFirstName())
                        .bind("lastName", filter.getLastName())
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public User getById(long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(User.class)
                        .one()
        );
    }

    public long createUser(CreateUser user) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate(
                    "INSERT INTO users " +
                            "(username, email, first_name, last_name) " +
                            "VALUES " +
                            "(:username, :email, :firstName, :lastName)")
                    .bind("username", user.getUsername())
                    .bind("email", user.getEmail())
                    .bind("firstName", user.getFirstName())
                    .bind("lastName", user.getLastName())
                    .execute();

            return handle.createQuery("SELECT LAST_INSERT_ID()")
                    .mapTo(Long.class)
                    .one();
        });
    }
}
