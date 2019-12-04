package uk.co.techswitch.myface.controllers.api;

import org.springframework.web.bind.annotation.*;
import uk.co.techswitch.myface.models.api.ResultsPage;
import uk.co.techswitch.myface.models.api.ResultsPageBuilder;
import uk.co.techswitch.myface.models.api.comments.CommentModel;
import uk.co.techswitch.myface.models.api.comments.CommentsFilter;
import uk.co.techswitch.myface.models.api.comments.CreateComment;
import uk.co.techswitch.myface.models.api.comments.UpdateComment;
import uk.co.techswitch.myface.models.database.Comment;
import uk.co.techswitch.myface.services.CommentsService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentsService commentsService;

    public CommentApiController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping(value = "/{id}")
    public CommentModel getCommentById(@PathVariable("id") long id){
        Comment comment = commentsService.getById(id);

        return new CommentModel(comment);
    }

    @GetMapping
    public ResultsPage searchComments(@ModelAttribute CommentsFilter filter){
        List<Comment> comments = commentsService.searchComments(filter);
        int numberOfCommentsFound = commentsService.countComments(filter);

        ResultsPage results = new ResultsPageBuilder<CommentModel, CommentsFilter>()
                .withItems(comments.stream().map(CommentModel::new).collect(Collectors.toList()))
                .withFilter(filter)
                .withNumberMatchingSearch(numberOfCommentsFound)
                .withBaseUrl("/api/comments")
                .build();

        return results;
    }

    @PostMapping(value = "/create")
    public CommentModel createComment(@ModelAttribute @Valid CreateComment createComment){
        Comment comment = commentsService.createComment(createComment);

        return new CommentModel(comment);
    }

    @PostMapping(value = "/{id}")
    public CommentModel updateComment(@PathVariable("id") long id ,@ModelAttribute @Valid UpdateComment updateComment){
        Comment comment = commentsService.updateComment(id, updateComment);

        return new CommentModel(comment);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteComment(@PathVariable("id") long id){
        commentsService.deleteComment(id);
    }
}
