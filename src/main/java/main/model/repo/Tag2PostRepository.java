package main.model.repo;

import main.model.Tags2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tags2Post, Integer> {

    @Query(nativeQuery = true, value = "INSERT IGNORE INTO (post_id, tag_id) VALUES (:post_id, :tag_id)")
    void insertTag2Post(@Param("post_id") int post_id, @Param("tag_id") int tag_id);
}
