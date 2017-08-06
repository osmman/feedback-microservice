package com.github.osmman.feedback;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Api("resource description")
@RequestMapping("/feedback")
public class FeedbackController {

    public final static Long DEFAULT_SKIP = 0L;
    public final static Predicate<Feedback> DEFAULT_FILTER = feedback -> true;

    @Autowired
    private FeedbackRepository repository;

    @GetMapping
    @ResponseBody
    public List<Feedback> find(@RequestParam("name") Optional<String> name, @RequestParam("offset") Optional<Long> offset, @RequestParam("limit") Optional<Long> limit) {
        Stream<Feedback> stream = name
                .map(repository::findByName)
                .orElse(repository.findAll())
                .skip(offset.orElse(DEFAULT_SKIP));
        stream = limit.map(stream::limit).orElse(stream);
        return stream.collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Feedback findOne(@PathVariable(value = "id") Long id) {
        return repository.findOne(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Feedback create(@Valid @RequestBody Feedback feedback) {
        Feedback updatedFeedback = feedback.toBuilder()
                .id(null)
                .createdAt(new Date())
                .build();
        return repository.save(updatedFeedback);
    }
}
