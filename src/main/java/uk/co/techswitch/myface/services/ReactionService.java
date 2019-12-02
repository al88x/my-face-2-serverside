package uk.co.techswitch.myface.services;

import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.reactions.CreateReaction;
import uk.co.techswitch.myface.models.api.reactions.ReactionsFilter;
import uk.co.techswitch.myface.models.database.Reaction;

import java.util.List;

@Service
public class ReactionService extends DatabaseService {

    public Reaction getReactionById(long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM reactions WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Reaction.class)
                        .one()
        );
    }

    public Reaction addReaction(CreateReaction reaction) {
        long id = jdbi.withHandle(handle -> {
                    handle.createUpdate("INSERT INTO reactions " +
                            "(post_id, user_id, reaction) " +
                            "VALUES " +
                            "(:postId, :userId, :reaction);")
                            .bind("postid", reaction.getPostId())
                            .bind("userId", reaction.getUserId())
                            .bind(":reaction", reaction.getReactionString())
                            .execute();

                    return handle.createQuery("SELECT LAST_INSERT_ID()")
                            .mapTo(Long.class)
                            .one();
                }
        );
        return getReactionById(id);
    }

    public void deleteReaction(long id) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM reactions WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }

    public List<Reaction> searchReactions(ReactionsFilter filter) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT * FROM reactions " +
                                "WHERE post_id = :postId " +
                                "LIMIT :limit " +
                                "OFFSET :offset;")
                        .bind("postId", filter.getPostId())
                        .bind("limit", filter.getPageSize())
                        .bind("offset", filter.getOffset())
                        .mapToBean(Reaction.class)
                        .list());
    }

    public int countReactions(ReactionsFilter filter) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM reactions " +
                        "WHERE post_id = :postId;")
                        .bind("postId", filter.getPostId())
                        .mapTo(Integer.class)
                        .one()
        );
    }
}
