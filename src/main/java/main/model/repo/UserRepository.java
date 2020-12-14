package main.model.repo;

import main.model.Post;
import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.email FROM user u WHERE u.email = :email", nativeQuery = true)
    String findUserByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO user (email, name, reg_time, password, is_moderator) VALUES (:email, :name, :time, :password, 0)",
            nativeQuery = true)
    void insertUser(@Param("email") String email, @Param("name") String name, @Param("time") Date time, @Param("password") String password);

    Optional<User> findByEmail(String email);


    @Modifying
    @Transactional
    @Query(
            value = "UPDATE user SET code = :code WHERE email = :email",
            nativeQuery = true)
    void saveCode(@Param("email") String email, @Param("code") String code);
}
