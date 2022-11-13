package com.realworld.conduit.domain.repository;

import com.realworld.conduit.domain.object.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
  void save(User user);
  User findByEmail(String email);

  User findByUsername(String username);
}
