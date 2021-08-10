package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.entity.User;

public interface UserRepostory extends JpaRepository<User, Integer> {

	User findByUserName(String username);
}
