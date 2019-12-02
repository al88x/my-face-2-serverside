package uk.co.techswitch.myface.models.api.reactions;

import javax.validation.constraints.NotNull;

public class CreateReaction {

    @NotNull
    private long userId;
    @NotNull
    private long postId;
    @NotNull
    private ReactionType reactionString;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public ReactionType getReactionString() {
        return reactionString;
    }

    public void setReactionString(ReactionType reactionString) {
        this.reactionString = reactionString;
    }
}
