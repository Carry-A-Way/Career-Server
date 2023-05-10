package com.example.career.domain.user.Repository;
import com.example.career.domain.user.Entity.User;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // 유저 정보에서 권한정보를 같이 가져옴. EntityGraph는 lazy loading이 아닌 eager loading.
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
    public Optional<User> findByUsername(String username);
    public User findByNickname(String nickname);
    public User findByTelephone(String telephone);

    public boolean existsByUsername(String username);

}
