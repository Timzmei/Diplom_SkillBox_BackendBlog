package main.model.repo;

import main.api.response.TagResponseAnswerQuery;
import main.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    @Query(nativeQuery = true, value = "SELECT " +
            "t.name as name, COUNT(t.id) as count" +
            " FROM tag t JOIN tags2post tp ON tp.tag_id = t.id JOIN post p on p.id = tp.post_id WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.time < NOW() GROUP BY tp.tag_id ORDER BY count DESC")
    List<TagResponseAnswerQuery> getRecentTags();

    @Query(nativeQuery = true, value = "SELECT t.name FROM tag t JOIN tags2post tp ON tp.tag_id = t.id WHERE tp.post_id = :post_id")
    List<String> getTagsByPost(@Param("post_id") int post_id);

    @Query(nativeQuery = true, value = "SELECT t.id FROM tag t WHERE t.name = :name")
    Integer getByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "INSERT IGNORE INTO (name) VALUES (:name)")
    void insertTag(@Param("name") String name);
}
