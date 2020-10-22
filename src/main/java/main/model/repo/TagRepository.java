package main.model.repo;

import main.model.Post;
import main.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

    @Query(value = "SELECT (SELECT count(*) FROM post p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW()) as count_posts, t2.name as tag_name, count(*) as count FROM tags2post t JOIN tag t2 on t2.id = t.tag_id GROUP BY t2.id ORDER BY count(*) DESC", nativeQuery = true)

    List<Tag> getRecentTags();
}
