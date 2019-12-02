package uk.co.techswitch.myface.services;

import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.users.CreateUser;
import uk.co.techswitch.myface.models.api.users.UpdateUser;
import uk.co.techswitch.myface.models.api.users.UsersFilter;
import uk.co.techswitch.myface.models.database.User;

import java.util.List;

@Service
public class UsersService extends DatabaseService {

    public List<User> searchUsers(UsersFilter filter) {
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

    public int countUsers(UsersFilter filter) {
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

    public User createUser(CreateUser user) {
        long id = jdbi.withHandle(handle -> {
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

        return getById(id);
    }

    public User updateUser(long id, UpdateUser user) {
        jdbi.withHandle(handle ->
                handle.createUpdate(
                        "UPDATE users SET " +
                                "username = :username, " +
                                "email = :email, " +
                                "first_name = :firstName, " +
                                "last_name = :lastName " +
                                "WHERE id = :id")
                        .bind("username", user.getUsername())
                        .bind("email", user.getEmail())
                        .bind("firstName", user.getFirstName())
                        .bind("lastName", user.getLastName())
                        .bind("id", id)
                        .execute()
        );
        return getById(id);
    }

    public void deleteUser(long id) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM users WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }
}
