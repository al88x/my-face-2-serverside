package uk.co.techswitch.myface.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import uk.co.techswitch.myface.models.api.ResultsPage;
import uk.co.techswitch.myface.models.api.ResultsPageBuilder;
import uk.co.techswitch.myface.models.api.comments.CommentModel;
import uk.co.techswitch.myface.models.api.comments.CommentsFilter;
import uk.co.techswitch.myface.models.api.reactions.CreateReaction;
import uk.co.techswitch.myface.models.api.reactions.ReactionModel;
import uk.co.techswitch.myface.models.api.reactions.ReactionsFilter;
import uk.co.techswitch.myface.models.database.Reaction;
import uk.co.techswitch.myface.services.ReactionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/reactions")
public class ReactionsController {

    private final ReactionService reactionService;


    public ReactionsController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }


    @RequestMapping(value = "", method = GET)
    public ModelAndView searchReactions(ReactionsFilter filter){
        List<Reaction> reactions = reactionService.searchReactions(filter);
        int numberMatchingSearch = reactionService.countReactions(filter);

        ResultsPage results = new ResultsPageBuilder<ReactionModel, ReactionsFilter>()
                .withItems(reactions.stream().map(ReactionModel::new).collect(Collectors.toList()))
                .withFilter(filter)
                .withNumberMatchingSearch(numberMatchingSearch)
                .withBaseUrl("/reactions")
                .build();
        return new ModelAndView("/reactions/search", "results", results);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ModelAndView getReactionDetails(@PathVariable("id") long id){
        Reaction reaction = reactionService.getReactionById(id);
        ReactionModel model = new ReactionModel(reaction);

        return new ModelAndView("reactions/detail", "reactionModel", model);
    }

    @RequestMapping(value ="/create", method = POST)
    public RedirectView createReaction(@ModelAttribute @Valid CreateReaction reactionToBeCreated){
        Reaction reaction = reactionService.addReaction(reactionToBeCreated);

        return new RedirectView("/reactions/" + reaction.getId());
    }



}
