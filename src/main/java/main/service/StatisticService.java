package main.service;

import main.api.response.PostResponse;
import main.api.response.StatisticResponse;
import main.model.Post;
import main.model.PostVotes;
import main.model.User;
import main.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Tag2PostRepository tag2PostRepository;

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @PreAuthorize("hasAuthority('user:write')")
    public StatisticResponse getMy(Principal principal) {

        StatisticResponse statisticResponse = new StatisticResponse();

        List<Post> postList = postRepository.findMyPosts("ACCEPTED", principal.getName());
        System.out.println(postList);

        if (postList.size() == 0){

            statisticResponse.setDislikesCount(0);
            statisticResponse.setFirstPublication(0);
            statisticResponse.setLikesCount(0);
            statisticResponse.setPostsCount(0);
            statisticResponse.setViewsCount(0);

            return statisticResponse;

        }

        int postsCount = postList.size();
        long likeCount = 0;
        long disLikeCount = 0;
        long viewsCount = 0;
        long firstPublication = postList.get(0).getTime().getTime();

        for (Post p:
             postList) {
          likeCount += getLikeCount(p);
          disLikeCount += getDislikeCount(p);
          viewsCount += p.getViewCount();
        }

        statisticResponse.setDislikesCount(disLikeCount);
        statisticResponse.setFirstPublication(firstPublication);
        statisticResponse.setLikesCount(likeCount);
        statisticResponse.setPostsCount(postsCount);
        statisticResponse.setViewsCount(viewsCount);

        return statisticResponse;
    }



    public ResponseEntity getAll(Principal principal) {


        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (user.getIsModerator() == 0 && globalSettingsRepository.findAllGlobalSettings("STATISTICS_IS_PUBLIC").getValue().equals("NO")){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        StatisticResponse statisticResponse = new StatisticResponse();

        List<Post> postList = postRepository.findAllPosts();

        int postsCount = postList.size();
        long likeCount = 0;
        long disLikeCount = 0;
        long viewsCount = 0;
        long firstPublication = postList.get(0).getTime().getTime();

        for (Post p:
                postList) {
            likeCount += getLikeCount(p);
            disLikeCount += getDislikeCount(p);
            viewsCount += p.getViewCount();
        }

        statisticResponse.setDislikesCount(disLikeCount);
        statisticResponse.setFirstPublication(firstPublication);
        statisticResponse.setLikesCount(likeCount);
        statisticResponse.setPostsCount(postsCount);
        statisticResponse.setViewsCount(viewsCount);

        return new ResponseEntity<>(statisticResponse, HttpStatus.OK);
    }



    public long getLikeCount(Post post) {
        long likeCount = 0;

        if(!(post.getLike() == null)) {
            LinkedList<PostVotes> like = new LinkedList<>(post.getLike());
            for (PostVotes l : like
            ) {
                if (l.getValue() == 1) {
                    likeCount++;
                }

            }
        }

        return likeCount;
    }

    public long getDislikeCount(Post post) {
        long dislikeCount = 0;

        if(!(post.getLike() == null)) {

            LinkedList<PostVotes> like = new LinkedList<>(post.getLike());
            for (PostVotes l : like
            ) {
                if (l.getValue() == 0) {
                    dislikeCount++;
                }

            }
        }
        return dislikeCount;
    }
}
