package main.model.repo;

import main.model.Tags2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tags2Post, Integer> {

    @Query(value = "SELECT t.* FROM tags2post t JOIN post p on p.id = t.post_id JOIN tag t2 on t2.id = t.tag_id WHERE t2.name = :name AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW()", nativeQuery = true)
    List<Tags2Post> getRecentTagsOnName(@Param("name") String name);

    @Query(value = "SELECT count(t.post_id) as count, t.* FROM tags2post t JOIN tag t2 on t2.id = t.tag_id GROUP BY t.tag_id ORDER BY count DESC", nativeQuery = true)
    List<Tags2Post> getRecentTags();
}
