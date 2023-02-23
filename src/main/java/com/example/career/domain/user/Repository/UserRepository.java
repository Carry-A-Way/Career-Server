package com.example.career.domain.user.Repository;
import com.example.career.domain.user.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsernameAndPassword(String username, String password);
    public User findByUsername(String username);
    public User findByNickname(String nickname);
    public User findByTelephone(String telephone);

}
