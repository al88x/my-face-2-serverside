package uk.co.techswitch.myface.models.database;

import uk.co.techswitch.myface.models.api.reactions.ReactionType;

public class Reaction {

    private long id;
    private long postId;
    private long userId;
    private ReactionType reaction;


    public ReactionType getReaction() {
        return reaction;
    }

    public void setReaction(ReactionType reaction) {
        this.reaction = reaction;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
