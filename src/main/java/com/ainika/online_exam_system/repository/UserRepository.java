package com.ainika.online_exam_system.repository;

import com.ainika.online_exam_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
