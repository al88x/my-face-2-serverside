package uk.co.techswitch.myface.models.api.users;

import org.springframework.web.util.UriBuilder;
import uk.co.techswitch.myface.models.api.Filter;

public class UsersFilter extends Filter {
    private String username;
    private String email;
    private String firstName;
    private String lastName;


    public UsersFilter(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void appendQueryParams(UriBuilder builder) {
        if (username != null) {
            builder.queryParam("username", username);
        }

        if (email != null) {
            builder.queryParam("email", email);
        }

        if (firstName != null) {
            builder.queryParam("firstName", firstName);
        }

        if (lastName != null) {
            builder.queryParam("lastName", lastName);
        }
    }
}
