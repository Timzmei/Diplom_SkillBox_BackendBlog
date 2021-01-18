package main.service;

import main.api.request.LikeRequest;
import main.api.response.ResultResponse;
import main.model.User;
import main.model.repo.PostVotesRepository;
import main.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class PostVotesService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostVotesRepository postVotesRepository;


    public ResponseEntity addLike(LikeRequest likeRequest, Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (postVotesRepository.findVotes(likeRequest.getPost_id(), user.getId()) == null || postVotesRepository.findVotes(likeRequest.getPost_id(), user.getId()).getValue() == 0) {

            postVotesRepository.updateVotes(likeRequest.getPost_id(), user.getId(), (byte) 1);
            return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);
        }
        else if (postVotesRepository.findVotes(likeRequest.getPost_id(), user.getId()).getValue() == 1){
                return new ResponseEntity(new ResultResponse(false), HttpStatus.OK);

        }

        postVotesRepository.insertVotes(new Date(), (byte) 1, likeRequest.getPost_id(), user.getId());

        return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);

    }

    public ResponseEntity addDislike(LikeRequest likeRequest, Principal principal) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (postVotesRepository.findVotes(likeRequest.getPost_id(), user.getId()).getValue() == 0){
            return new ResponseEntity(new ResultResponse(false), HttpStatus.OK);
        }

        else if (postVotesRepository.findVotes(likeRequest.getPost_id(), user.getId()).getValue() == 1) {

            postVotesRepository.updateVotes(likeRequest.getPost_id(), user.getId(), (byte) 0);
            return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);
        }
        postVotesRepository.insertVotes(new Date(), (byte) 0, likeRequest.getPost_id(), user.getId());

        return new ResponseEntity(new ResultResponse(true), HttpStatus.OK);

    }


}
