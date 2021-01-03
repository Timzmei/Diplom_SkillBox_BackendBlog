package main.model.repo;

import main.model.GlobalSettings;
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
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Long> {

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE global_settings s SET s.value = :value WHERE s.code = :code",
            nativeQuery = true)
    void insertSettings(@Param("code") String code, @Param("value") String value);

    @Query(value = "SELECT s.* FROM global_settings s WHERE s.code = :code", nativeQuery = true)
    GlobalSettings findAllGlobalSettings(@Param("code") String code);


}
