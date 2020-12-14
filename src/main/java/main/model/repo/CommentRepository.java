package main.model.repo;

import main.model.Post;
import main.model.PostComments;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<PostComments, Integer>{



    @Query(value = "SELECT p.* FROM post_comments p WHERE p.post_id = :id", nativeQuery = true)
    List<PostComments> findComments(@Param("id") int id);

    @Query(value = "SELECT p.id FROM post_comments p WHERE id = :id", nativeQuery = true)
    Integer findCommentsById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO post_comments (parent_id, post_id, text, time, user_id)" +
                    " VALUES (:parent_id, :post_id, :text, :time, :user_id)",
            nativeQuery = true)
    void addComment(@Param("parent_id") Integer parent_id, @Param("post_id") int post_id, @Param("text") String text, @Param("time") Date time, @Param("user_id") int user_id);

    @Query(
            value = "SELECT LAST_INSERT_ID()",
            nativeQuery = true)
    int getLastId();



}
