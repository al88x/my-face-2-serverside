package uk.co.techswitch.myface.models.api.reactions;

import uk.co.techswitch.myface.models.database.Reaction;

public class ReactionModel {

    private Reaction reaction;

    public ReactionModel(Reaction reaction) {
        this.reaction = reaction;
    }

    public long getId(){
        return this.reaction.getId();
    }

    public long getPostId(){
        return this.reaction.getPostId();
    }

    public long getUserId(){
        return this.reaction.getUserId();
    }

    public ReactionType getReaction(){
        return this.reaction.getReaction();
    }

}
