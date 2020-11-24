package main.model.repo;

import main.api.response.TagResponseAnswerQuery;
import main.model.Tag;
import main.model.Tags2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag, Integer> {

    @Query(nativeQuery = true, value = "SELECT " +
            "t.name as name, COUNT(t.id) as count" +
            " FROM tag t JOIN tags2post tp ON tp.tag_id = t.id JOIN post p on p.id = tp.post_id WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW() GROUP BY tp.tag_id ORDER BY count DESC")
    List<TagResponseAnswerQuery> getRecentTags();

    @Query(nativeQuery = true, value = "SELECT t.name FROM tag t JOIN tags2post tp ON tp.tag_id = t.id WHERE tp.post_id = :post_id")
    List<String> getTagsByPost(@Param("post_id") int post_id);


}
