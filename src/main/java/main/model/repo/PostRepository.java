package main.model.repo;

import main.model.Post;
import main.model.PostComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT p.* FROM post p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW()", nativeQuery = true)
    List<Post> findAllPosts();

    @Query(value = "SELECT p.*, (SELECT count(*) FROM post_comments c WHERE c.post_id = p.id) as comments FROM post p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY comments DESC", nativeQuery = true)
    Page<Post> findAllOrderByCommentsDesc(Pageable pageable);

    @Query(value = "SELECT p.* FROM post p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findAllOrderByTimeDesc(Pageable pageable);

    @Query(value = "SELECT p.* FROM post p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post> findAllOrderByTime(Pageable pageable);

    @Query(value = "SELECT p.*, (SELECT sum(value) FROM post_votes c WHERE c.post_id = p.id) as votes FROM post p WHERE p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY votes DESC", nativeQuery = true)
    Page<Post> findAllOrderByVotesDesc(Pageable pageable);

    @Query(value = "SELECT p.* FROM post p WHERE p.text LIKE %:query% AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findAllOrderBySearch(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p WHERE p.time LIKE :date% AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post> findAllPostsByDate(@Param("date") String date, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p JOIN tags2post tp ON tp.post_id = p.id JOIN tag t ON t.id = tp.tag_id WHERE t.name = :tag AND p.is_active = 1 AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Page<Post> findAllPostsByTag(@Param("tag") String tag, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p WHERE p.id = :id AND p.moderation_status = 'ACCEPTED' AND p.`time` < NOW() ORDER BY p.time", nativeQuery = true)
    Post findPostAcceptedById(@Param("id") int id);

    @Query(value = "SELECT p.* FROM post p WHERE p.id = :id", nativeQuery = true)
    Post findPostById(@Param("id") int id);

    @Query(value = "SELECT p.* FROM post p JOIN user u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 1 AND p.moderation_status = :status AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findPostsModeration(@Param("status") String status, @Param("email") String email, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p JOIN user u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 0 AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findPostsMyInactive(Pageable pageable, @Param("email") String email);


    @Query(value = "SELECT p.* FROM post p JOIN user u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 1 AND p.moderation_status = :status AND p.`time` < NOW() ORDER BY p.time DESC", nativeQuery = true)
    Page<Post> findPostsMyIsactive(@Param("status") String status, @Param("email") String email, Pageable pageable);


    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO post (is_active, moderation_status, text, title, time, moderator_id, user_id, view_count)" +
                    " VALUES (:active, 'NEW', :text, :title, :date, :user_id, :user_id, 0)",
            nativeQuery = true)
    void insertPost(@Param("date") Date date, @Param("active") byte active, @Param("title") String title, @Param("text") String text, @Param("user_id") int user_id);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE post p SET p.is_active = :active, p.moderation_status = 'NEW', p.text = :text, p.title = :title, p.time = :date" +
                    " WHERE p.id = :id",
            nativeQuery = true)
    void updatePost(@Param("date") Date date, @Param("active") byte active, @Param("title") String title, @Param("text") String text, @Param("id") int id);


    @Modifying
    @Transactional
    @Query(
            value = "UPDATE post p SET p.view_count = :view_count WHERE p.id = :id ",
            nativeQuery = true)
    int updateViewPost(@Param("id") int id, @Param("view_count") int view_count);


    @Query(
            value = "SELECT LAST_INSERT_ID()",
            nativeQuery = true)
    int getLastId();

    @Query(value = "SELECT COUNT(*) FROM post p JOIN user u ON u.id = p.user_id WHERE u.email = :email AND p.is_active = 1 AND p.moderation_status = 'NEW'", nativeQuery = true)
    int findAllPostsIsModerate(@Param("email") String email);
}