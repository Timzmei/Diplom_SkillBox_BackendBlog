package main.service;

import main.api.response.TagsResponse;
import main.model.Post;
import main.model.Tag;
import main.model.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public TagsResponse getTags(String query) {
        TagsResponse tagsResponse = new TagsResponse();

        List<Tag> listTags = tagRepository.getRecentTags();



        return tagsResponse;
    }

}
