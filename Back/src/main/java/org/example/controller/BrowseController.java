package org.example.controller;

import org.example.model.Browse;
import org.example.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/browse")
public class BrowseController {

    @Autowired
    private BrowseService browseService;

    // 记录用户浏览书籍
    @PostMapping("/record")
    public Browse recordBrowse(@RequestParam Long userId, @RequestParam Long bookId) {
        return browseService.recordBrowse(userId, bookId);
    }

    // 获取用户所有的浏览记录
    @GetMapping("/history/{userId}")
    public List<Browse> getBrowseHistory(@PathVariable Long userId) {
        return browseService.getBrowseHistory(userId);
    }

    // 获取书籍的所有浏览记录
    @GetMapping("/history/book/{bookId}")
    public List<Browse> getBrowseByBook(@PathVariable Long bookId) {
        return browseService.getBrowseByBook(bookId);
    }

    // 获取特定时间段内用户的浏览记录
    @GetMapping("/history/time/{userId}")
    public List<Browse> getBrowseByUserAndTime(@PathVariable Long userId,
                                               @RequestParam LocalDateTime startTime,
                                               @RequestParam LocalDateTime endTime) {
        return browseService.getBrowseByUserAndTime(userId, startTime, endTime);
    }
}
