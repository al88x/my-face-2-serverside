package uk.co.techswitch.myface.services;

import org.springframework.stereotype.Service;
import uk.co.techswitch.myface.models.api.posts.CreatePost;
import uk.co.techswitch.myface.models.api.posts.PostsFilter;
import uk.co.techswitch.myface.models.api.posts.UpdatePost;
import uk.co.techswitch.myface.models.database.Post;

import java.util.Date;
import java.util.List;

@Service
public class PostsService extends DatabaseService {

    public List<Post> searchPosts(PostsFilter filter) {
        return jdbi.withHandle(handle ->
                handle
                        .createQuery(
                                "SELECT * " +
                                        "FROM posts " +
                                        "WHERE (:sender is NULL OR sender = :sender) " +
                                        "AND (:before is NULL OR timestamp < :before) " +
                                        "AND (:after is NULL OR timestamp > :after) " +
                                        "ORDER BY timestamp DESC " +
                                        "LIMIT :limit " +
                                        "OFFSET :offset")
                        .bind("sender", filter.getSender())
                        .bind("before", filter.getBefore())
                        .bind("after", filter.getAfter())
                        .bind("limit", filter.getPageSize())
                        .bind("offset", filter.getOffset())
                        .mapToBean(Post.class)
                        .list()
        );
    }

    public int countPosts(PostsFilter filter) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                        "SELECT COUNT(*) FROM posts " +
                                "WHERE (:sender is NULL OR sender = :sender) " +
                                "AND (:before is NULL OR timestamp < :before) " +
                                "AND (:after is NULL OR timestamp > :after)")
                        .bind("sender", filter.getSender())
                        .bind("before", filter.getBefore())
                        .bind("after", filter.getAfter())
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public Post getById(long id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM posts WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Post.class)
                        .one()
        );
    }

    public Post createPost(CreatePost post) {
        long id = jdbi.withHandle(handle -> {
                    handle.createUpdate(
                            "INSERT INTO posts " +
                                    "(sender, message, image_url, timestamp) " +
                                    "VALUES " +
                                    "(:sender, :message, :imageUrl, :timestamp)")
                            .bind("sender", post.getSender())
                            .bind("message", post.getMessage())
                            .bind("imageUrl", post.getImageUrl())
                            .bind("timestamp", new Date())
                            .execute();

                    return handle.createQuery("SELECT LAST_INSERT_ID()")
                            .mapTo(Long.class)
                            .one();
                }
        );

        return getById(id);
    }

    public Post updatePost(Long id, UpdatePost updatePost) {
        jdbi.withHandle(handle ->
                handle.createUpdate(
                        "UPDATE posts SET " +
                                "sender = :senderId, " +
                                "message = :message, " +
                                "image_url = :imageUrl, " +
                                "timestamp = :timestamp " +
                                "WHERE id = :id")
                        .bind("id", id)
                        .bind("senderId", updatePost.getSender())
                        .bind("message", updatePost.getMessage())
                        .bind("imageUrl", updatePost.getImageUrl())
                        .bind("timestamp", updatePost.getTimestamp())
                        .execute()
        );
        return getById(id);
    }

    public void deletePost(long id) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM posts WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
    }
}
