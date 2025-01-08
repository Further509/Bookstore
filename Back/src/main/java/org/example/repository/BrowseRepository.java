package org.example.repository;

import org.example.model.Browse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BrowseRepository extends JpaRepository<Browse, Long> {

    // 查找特定用户的浏览记录
    List<Browse> findByUserId(Long userId);

    // 查找特定书籍的浏览记录
    List<Browse> findByBookId(Long bookId);

    // 查找用户特定时间段的浏览记录
    List<Browse> findByUserIdAndBrowseTimeBetween(Long userId, LocalDateTime startTime, LocalDateTime endTime);
}

