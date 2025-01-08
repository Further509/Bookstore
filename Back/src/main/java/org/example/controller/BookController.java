package org.example.controller;

import org.example.model.Book;
import org.example.model.Supplier;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 获取所有图书
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // 添加新图书
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    // 更新图书信息
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    // 删除图书
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // 查询图书
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // 根据书号查询书籍
    @GetMapping("/bookNumber/{bookNumber}")
    public ResponseEntity<List<Book>> getBooksByBookNumber(@PathVariable String bookNumber) {
        return ResponseEntity.ok(bookService.getBooksByBookNumber(bookNumber));
    }

    // 根据供应商查询书籍
    @GetMapping("/supplier/{supplier}")
    public ResponseEntity<List<Book>> getBooksBySupplier(@PathVariable Supplier supplier) {
        return ResponseEntity.ok(bookService.getBooksBySupplier(supplier));
    }

    // 新书入库
    @PostMapping("/{id}/stock")
    public ResponseEntity<Book> addNewStock(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(bookService.addNewStock(id, quantity));
    }

    // 更新库存数量
    @PutMapping("/{id}/stock")
    public ResponseEntity<Book> updateStockQuantity(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(bookService.updateStockQuantity(id, quantity));
    }
    // 添加供应商到图书
    @PostMapping("/{id}/supplier/{supplierId}")
    public ResponseEntity<Book> addSupplierToBook(@PathVariable Long id, @PathVariable Long supplierId) {
        return ResponseEntity.ok(bookService.addSupplierToBook(id, supplierId));
    }

    // 移除供应商
    @DeleteMapping("/{id}/supplier/{supplierId}")
    public ResponseEntity<Book> removeSupplierFromBook(@PathVariable Long id, @PathVariable Long supplierId) {
        return ResponseEntity.ok(bookService.removeSupplierFromBook(id, supplierId));
    }
}
