package uk.co.techswitch.myface.models.api.reactions;

public enum ReactionType {

    LIKE("like"),
    DISLIKE("dislike");

    private final String reactionString;

    ReactionType(String reactionString) {
        this.reactionString = reactionString;
    }
}
