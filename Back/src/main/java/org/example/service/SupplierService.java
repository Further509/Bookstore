package org.example.service;

import org.example.model.Book;
import org.example.model.Supplier;
import org.example.repository.BookRepository;
import org.example.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplier) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        supplier.setId(id);
        return supplierRepository.save(supplier);
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

    // 添加书目信息
    public Supplier addBookToCatalog(Long supplierId, Long bookId) {
        Supplier supplier = getSupplierById(supplierId);
        Book book = getBookById(bookId);
        supplier.getBooks().add(book);
        book.getSuppliers().add(supplier);
        supplierRepository.save(supplier);
        bookRepository.save(book);
        return supplier;
    }

    // 删除书目信息
    public Supplier removeBookFromCatalog(Long supplierId, Long bookId) {
        Supplier supplier = getSupplierById(supplierId);
        Book book = getBookById(bookId);
        supplier.getBooks().remove(book);
        book.getSuppliers().remove(supplier);
        supplierRepository.save(supplier);
        bookRepository.save(book);
        return supplier;
    }

    // 获取供应商供应的书籍ID列表
    public List<Long> getSupplierBookIds(Long supplierId) {
        Supplier supplier = getSupplierById(supplierId);
        return supplier.getBooks().stream().map(Book::getId).toList();
    }

    // 获取单个供应商
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    // 获取单本书籍
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
