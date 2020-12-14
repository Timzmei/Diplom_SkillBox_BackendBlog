package main.service;

import main.api.request.CommentRequest;
import main.api.response.ApiCommentResponse;
import main.model.Post;
import main.model.PostComments;
import main.model.User;
import main.model.repo.CommentRepository;
import main.model.repo.PostRepository;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<ApiCommentResponse> postComment(CommentRequest commentRequest, Principal principal) {

        Integer parentCommentId;

        if (commentRequest.getParent_id() == null){
            parentCommentId = null;
        }
        else{
            parentCommentId = commentRepository.findCommentsById(commentRequest.getParent_id());
        }
        Integer postId = postRepository.findPostById(commentRequest.getPost_id()).getId();

        if (parentCommentId == null || postId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(commentRequest.getText().length() < 2) {
            ApiCommentResponse apiCommentResponse = new ApiCommentResponse();
            return new ResponseEntity<>(apiCommentResponse, HttpStatus.OK);
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        commentRepository.addComment(commentRequest.getParent_id(), commentRequest.getPost_id(), commentRequest.getText(), new Date(), user.getId());

        int id = commentRepository.getLastId();
        ApiCommentResponse apiCommentResponse = new ApiCommentResponse(id);
        return new ResponseEntity<>(apiCommentResponse, HttpStatus.OK);
    }
}
