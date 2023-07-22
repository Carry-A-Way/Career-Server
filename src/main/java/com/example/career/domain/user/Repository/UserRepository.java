package com.example.career.domain.user.Repository;
import com.example.career.domain.user.Entity.User;

import lombok.Builder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
    public Optional<User> findByUsername(String username);
    public User findByNickname(String nickname);
    public User findByTelephone(String telephone);

    public boolean existsByUsername(String username);



}
