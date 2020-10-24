package main.service;

import main.api.response.TagResponse;
import main.api.response.TagsResponse;
import main.model.Post;
import main.model.Tag;
import main.model.Tags2Post;
import main.model.repo.Tag2PostRepository;
import main.model.repo.TagRepository;
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

        List<Tags2Post> listTags = tag2PostRepository.getRecentTags();
        double normParam = (double) tag2PostRepository.getRecentTagsOnName(listTags.get(0).getTagId().getName()).size() / listTags.size();

        List<TagResponse> tagResponseList = new ArrayList<>();
//        int countPosts = new PostService().getCountPosts();

        for (Tags2Post t: listTags
             ) {
            TagResponse tagResponse = new TagResponse(t.getTagId().getName(), (double)(tag2PostRepository.getRecentTagsOnName(t.getTagId().getName()).size()) / listTags.size() / normParam);
//            tagResponse.setName(t.getId());
//            tagResponse.setWeight(tag2PostRepository.getRecentTagsOnName(t.getTagId().getName()).size() / listTags.size() / normParam);
            tagResponseList.add(tagResponse);
        }



        TagsResponse tagsResponse = new TagsResponse();
        tagsResponse.setTags(tagResponseList);



        return tagsResponse;
    }

}
