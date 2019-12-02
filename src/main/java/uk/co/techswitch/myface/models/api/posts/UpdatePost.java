package uk.co.techswitch.myface.models.api.posts;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UpdatePost {
    @NotNull
    private Long sender;
    @NotBlank
    private String message;
    private String imageUrl;
    private Date timestamp;

    public Long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @DateTimeFormat(pattern = PostModel.DATE_PATTERN)
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
