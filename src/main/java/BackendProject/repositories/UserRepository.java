package BackendProject.repositories;

import BackendProject.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user " +
            "where user_id = :userId and " +
            "(:searchText is null or concat(first_name, concat(' ' , last_name)) like :searchText)",
            countQuery = "select count(*) from users " +
                    "where user_id = :userId and " +
                    "(:searchText is null or concat(first_name, concat(' ' , last_name)) like :searchText)",
            nativeQuery = true)
    Slice<User> search(@Param("userId") Long userId, @Param("searchText") String searchText, Pageable pageable);

    Optional<User> findByEmail(String email);
}
