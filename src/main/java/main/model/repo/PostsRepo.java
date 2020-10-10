package main.model.repo;

import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface PostsRepo extends PagingAndSortingRepository<Post, Integer> {
//    Page<Post> findAllByTime(Date time, Pageable pageable);

    Page<Post> findAll(Pageable pageable);


}
