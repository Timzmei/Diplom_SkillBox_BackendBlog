package main.service;

import main.api.response.TagResponse;
import main.api.response.TagResponseAnswerQuery;
import main.api.response.TagsResponse;
import main.model.Tags2Post;
import main.model.repo.Tag2PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
//    private TagRepository tagRepository;
    private Tag2PostRepository tag2PostRepository;

    public TagsResponse getTags(String query) {

        List<TagResponseAnswerQuery> listTags = tag2PostRepository.getRecentTags();
        double normParam = (double) listTags.get(0).getCount()/listTags.size();

        List<TagResponse> tagResponseList = new ArrayList<>();

        for (TagResponseAnswerQuery t: listTags
             ) {
            TagResponse tagResponse = new TagResponse(t.getName(), ((double)t.getCount()/listTags.size() / normParam));

            tagResponseList.add(tagResponse);
        }



        TagsResponse tagsResponse = new TagsResponse();
        tagsResponse.setTags(tagResponseList);



        return tagsResponse;
    }

}
