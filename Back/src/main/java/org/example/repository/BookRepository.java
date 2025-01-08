package org.example.repository;

import org.example.model.Book;
import org.example.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 根据书号查询书籍
    List<Book> findByBookNumber(String bookNumber);

    // 根据供应商查询书籍
    List<Book> findBySuppliersContaining(Supplier supplier);


    // 根据库存存放位置查询书籍
    List<Book> findByStorageLocation(String storageLocation);
}
