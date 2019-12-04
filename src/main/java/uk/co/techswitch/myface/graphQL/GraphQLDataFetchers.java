package uk.co.techswitch.myface.graphQL;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.techswitch.myface.models.api.comments.CommentsFilter;
import uk.co.techswitch.myface.models.api.posts.PostsFilter;
import uk.co.techswitch.myface.models.database.Comment;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.models.database.User;
import uk.co.techswitch.myface.services.CommentsService;
import uk.co.techswitch.myface.services.PostsService;
import uk.co.techswitch.myface.services.UsersService;

import java.util.List;

@Component
public class GraphQLDataFetchers {

    private final PostsService postsService;
    private final UsersService usersService;
    private final CommentsService commentsService;

    public GraphQLDataFetchers(PostsService postsService, UsersService usersService, CommentsService commentsService) {
        this.postsService = postsService;
        this.usersService = usersService;
        this.commentsService = commentsService;
    }

    public DataFetcher getPostByIdDataFetcher(){
        return environment -> {
            long postId = Long.valueOf(environment.getArgument("id"));
            Post post = postsService.getById(postId);

            return post;
        };
    }

    public DataFetcher getAllPostsDataFetcher(){
        return environment -> {
            long sender = Long.valueOf(environment.getArgument("sender"));
            PostsFilter filter = new PostsFilter();
            filter.setSender(sender);
            List<Post> posts = postsService.searchPosts(filter);

            return posts;
        };
    }


    public DataFetcher getUserFromPostDataFetcher() {
        return environment -> {
            Post post =(Post) environment.getSource();

            User user = usersService.getById(post.getSender());

            return user;
        };
    }

    public DataFetcher getCommentsByPostIdDataFetcher() {
        return environment -> {
            Post post = environment.getSource();
            CommentsFilter filter = new CommentsFilter();
            filter.setPost(post.getId());
            List<Comment> comments = commentsService.searchComments(filter);

            return comments;
        };
    }

    public DataFetcher getUserFromCommentDataFetcher() {
        return environment -> {
            Comment comment = (Comment)environment.getSource();
            User user = usersService.getById(comment.getSender());

            return user;
        };
    }
}
