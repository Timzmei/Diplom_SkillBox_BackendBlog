package main.service;

import main.api.request.CommentRequest;
import main.api.response.ApiCommentResponse;
import main.api.response.ApiCommentResponseError;
import main.model.User;
import main.model.repo.CommentRepository;
import main.model.repo.PostRepository;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity postComment(CommentRequest commentRequest, Principal principal) {

        if ((!(commentRequest.getParent_id() == null) && commentRepository.findCommentsById(commentRequest.getParent_id()) == null) || postRepository.findPostById(commentRequest.getPost_id()) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(commentRequest.getText().length() < 8) {
            ApiCommentResponseError apiCommentResponseError = new ApiCommentResponseError(false);
            return new ResponseEntity<>(apiCommentResponseError, HttpStatus.OK);
        }

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        commentRepository.addComment(commentRequest.getParent_id(), commentRequest.getPost_id(), commentRequest.getText(), new Date(), user.getId());

        int id = commentRepository.getLastId();
        ApiCommentResponse apiCommentResponse = new ApiCommentResponse(id);
        return new ResponseEntity<>(apiCommentResponse, HttpStatus.OK);
    }
}
