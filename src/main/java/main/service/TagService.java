package main.service;

import main.api.response.TagResponse;
import main.api.response.TagsResponse;
import main.model.Post;
import main.model.Tag;
import main.model.repo.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public TagsResponse getTags(String query) {

        List<Tag> listTags = tagRepository.getRecentTags();

        List<TagResponse> tagResponseList = new ArrayList<>();

        for (Tag t: listTags
             ) {
            TagResponse tagResponse = new TagResponse(t);
//            tagResponse.setName(t);
//            tagResponse.setWeight(t);
            tagResponseList.add(tagResponse);


        }



        TagsResponse tagsResponse = new TagsResponse();
        tagsResponse.setTags(tagResponseList);



        return tagsResponse;
    }

}
