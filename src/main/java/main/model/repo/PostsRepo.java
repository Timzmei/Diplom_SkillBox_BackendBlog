package main.model.repo;

import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostsRepo extends PagingAndSortingRepository<Post, Integer> {
//    List<Post> findAllByTime(Date time, Pageable pageable);

//    List<Post> findPaginated(int pageNo, int pageSize);


}
