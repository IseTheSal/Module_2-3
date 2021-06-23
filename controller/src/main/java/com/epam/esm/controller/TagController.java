package com.epam.esm.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tags/", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    /**
     * {@link Tag} service layer
     */
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Method used to find {@link Tag} by his id.
     *
     * @param id {@link Tag} <code>id</code>
     * @return ResponseEntity with {@link Tag}
     */
    @GetMapping(value = "/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Tag> findTagById(@PathVariable long id) {
        return new ResponseEntity<>(tagService.findById(id), HttpStatus.OK);
    }

    /**
     * Method used to find all {@link Tag}.
     *
     * @return ResponseEntity with <code>List</code> of {@link Tag}
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<Tag>> findAllTags() {
        return new ResponseEntity<>(tagService.findAll(), HttpStatus.OK);
    }

    /**
     * Method used to delete {@link Tag} by its id.
     *
     * @param id {@link Tag} <code>id</code>
     * @return ResponseEntity with {@link Tag} id
     */
    @DeleteMapping(value = "/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Long> deleteTagById(@PathVariable long id) {
        return new ResponseEntity<>(tagService.delete(id), HttpStatus.OK);
    }

    /**
     * Method used to create {@link Tag}.
     *
     * @param tag {@link Tag}
     * @return ResponseEntity with {@link Tag} object
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return new ResponseEntity<>(tagService.create(tag), HttpStatus.OK);
    }
}
