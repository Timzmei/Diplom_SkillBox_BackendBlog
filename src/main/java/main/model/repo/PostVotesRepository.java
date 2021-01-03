package main.model.repo;

import main.model.GlobalSettings;
import main.model.Post;
import main.model.PostVotes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Long> {


    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO post_votes (time, value, post_id, user_id)" +
                    " VALUES (:time, :value, :post_id, :user_id)",
            nativeQuery = true)
    void insertVotes(@Param("time") Date time, @Param("value") byte value, @Param("post_id") int post_id, @Param("user_id") int user_id);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE post_votes p SET  p.value = :value WHERE p.post_id = :post_id AND p.user_id = :user_id",
            nativeQuery = true)
    void updateVotes(@Param("post_id") int post_id, @Param("user_id") int user_id, @Param("value") byte value);


    @Query(value = "SELECT p.* FROM post_votes p WHERE p.post_id = :post_id AND p.user_id = :user_id", nativeQuery = true)
    PostVotes findVotes(@Param("post_id") int post_id, @Param("user_id") int user_id);

}
