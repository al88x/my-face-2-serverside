package uk.co.techswitch.myface.controllers.api;

import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.ResultsPage;
import uk.co.techswitch.myface.models.api.ResultsPageBuilder;
import uk.co.techswitch.myface.models.api.posts.CreatePost;
import uk.co.techswitch.myface.models.api.posts.PostModel;
import uk.co.techswitch.myface.models.api.posts.PostsFilter;
import uk.co.techswitch.myface.models.api.posts.UpdatePost;
import uk.co.techswitch.myface.models.database.Post;
import uk.co.techswitch.myface.services.PostsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/posts")
public class PostsApiController {

    private final PostsService postsService;

    public PostsApiController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping(value = "/{id}", method = GET)
    public PostModel getPostById(@PathVariable("id") long id){
        Post post = postsService.getById(id);

        return new PostModel(post);
    }

    @RequestMapping(value = "", method = GET)
    public ResultsPage getAllPosts(PostsFilter filter){
        List<Post> posts = postsService.searchPosts(filter);
        int numberMatchingSearch = postsService.countPosts(filter);

        ResultsPage results = new ResultsPageBuilder<PostModel, PostsFilter>()
                .withItems(posts.stream().map(PostModel::new).collect(Collectors.toList()))
                .withFilter(filter)
                .withNumberMatchingSearch(numberMatchingSearch)
                .withBaseUrl("/api/posts")
                .build();

        return results;
    }

    @RequestMapping(value = "/create", method = POST)
    public PostModel createApiPost(@ModelAttribute @Valid CreatePost createPost){
        Post post = postsService.createPost(createPost);

        return new PostModel(post);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public PostModel updateApiPost(@PathVariable("id") long id, @ModelAttribute @Valid UpdatePost updatePost){
        Post updatedPost = postsService.updatePost(id, updatePost);

        return new PostModel(updatedPost);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void deleteApiPost(@PathVariable("id") long id){
        postsService.deletePost(id);
    }


}
