package com.dnd.eight.Domain.Notice;

import com.dnd.eight.Domain.Login.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByUserid(Long userid);
}
