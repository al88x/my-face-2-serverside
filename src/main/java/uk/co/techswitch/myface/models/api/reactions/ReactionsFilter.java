package uk.co.techswitch.myface.models.api.reactions;

import org.springframework.web.util.UriBuilder;
import uk.co.techswitch.myface.models.api.Filter;

public class ReactionsFilter extends Filter {

    private Long postId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    @Override
    public void appendQueryParams(UriBuilder builder) {
        if(postId != null){
            builder.queryParam("postId", postId);
        }
    }
}
