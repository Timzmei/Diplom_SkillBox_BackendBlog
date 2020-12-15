package main.service;

import main.api.request.ModerateRequest;
import main.api.response.ResultResponse;
import main.model.User;
import main.model.repo.PostRepository;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ModerateService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity postModerate(ModerateRequest moderateRequest, Principal principal) {


        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (user.getIsModerator() == 0){
            return new ResponseEntity<>(new ResultResponse(false), HttpStatus.OK);
        }

        postRepository.updateModeratePost(moderateRequest.getDecision(), moderateRequest.getPost_id(), user.getId());

        if (postRepository.findPostById(moderateRequest.getPost_id()).getModerationStatus().equals("NEW")){
            return new ResponseEntity<>(new ResultResponse(false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResultResponse(true), HttpStatus.OK);
    }
}
