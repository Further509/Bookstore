package org.example.service;


import org.example.model.Browse;
import org.example.repository.BrowseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BrowseService {

    @Autowired
    private BrowseRepository browseRepository;

    // 用户浏览记录
    public Browse recordBrowse(Long userId, Long bookId) {
        Browse browse = new Browse();
        browse.setUserId(userId);
        browse.setBookId(bookId);
        browse.setBrowseTime(LocalDateTime.now());
        return browseRepository.save(browse);
    }

    // 获取某个用户的所有浏览记录
    public List<Browse> getBrowseHistory(Long userId) {
        return browseRepository.findByUserId(userId);
    }

    // 根据书籍ID获取浏览记录
    public List<Browse> getBrowseByBook(Long bookId) {
        return browseRepository.findByBookId(bookId);
    }

    // 根据时间段获取用户的浏览记录
    public List<Browse> getBrowseByUserAndTime(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return browseRepository.findByUserIdAndBrowseTimeBetween(userId, startTime, endTime);
    }
}

