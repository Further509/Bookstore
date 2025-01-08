package org.example.service;

import org.example.model.Book;
import org.example.model.OutOfStockRecord;
import org.example.model.PurchaseOrder;
import org.example.model.Supplier;
import org.example.repository.BookRepository;
import org.example.repository.OutOfStockRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.example.repository.SupplierRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private OutOfStockRecordRepository outOfStockRecordRepository;
    @Autowired
    private PurchaseOrderService purchaseOrderService;


    // 获取所有图书
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 添加新图书
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // 更新图书信息
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        book.setId(id);
        return bookRepository.save(book);
    }

    // 删除图书
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // 根据ID查询图书
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new RuntimeException("Book not found"));
    }

    // 根据书号查询书籍
    public List<Book> getBooksByBookNumber(String bookNumber) {
        return bookRepository.findByBookNumber(bookNumber);
    }

    // 根据供应商查询书籍
    public List<Book> getBooksBySupplier(Supplier supplier) {
        return bookRepository.findBySuppliersContaining(supplier);
    }

    // 更新库存数量（增加或减少）
    public Book updateStockQuantity(Long id, int quantity) {
        Book book = getBookById(id);
        // 确保库存量不为负
        if (book.getStockQuantity() + quantity < 0) {
            throw new RuntimeException("Insufficient stock quantity");
        }
        book.setStockQuantity(book.getStockQuantity() + quantity);
        return bookRepository.save(book);
    }

    // 新书入库（更新库存量）
    public Book addNewStock(Long id, int quantity) {
        Book book = getBookById(id);
        book.setStockQuantity(book.getStockQuantity() + quantity);
        return bookRepository.save(book);
    }

    // 更新书籍的库存位置
    public Book updateStorageLocation(Long id, String storageLocation) {
        Book book = getBookById(id);
        book.setStorageLocation(storageLocation);
        return bookRepository.save(book);
    }
    // 添加供应商到图书
    public Book addSupplierToBook(Long bookId, Long supplierId) {
        Book book = getBookById(bookId);
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        book.getSuppliers().add(supplier);
        supplier.getBooks().add(book);  // 双向关联
        bookRepository.save(book);
        return bookRepository.save(book);
    }

    // 移除供应商
    public Book removeSupplierFromBook(Long bookId, Long supplierId) {
        Book book = getBookById(bookId);
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        book.getSuppliers().remove(supplier);
        supplier.getBooks().remove(book);  // 双向关联
        bookRepository.save(book);
        return bookRepository.save(book);
    }
    // 检查库存并生成缺书记录

    public void checkStockAndGenerateOutOfStockRecords() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            System.out.println("Book: " + book.getTitle() + " Stock: " + book.getStockQuantity() + " MinStock: " + book.getMinStockQuantity());
            if (book.getStockQuantity() < book.getMinStockQuantity()) {
                int shortage = book.getMinStockQuantity() - book.getStockQuantity();
                OutOfStockRecord record = new OutOfStockRecord();
                record.setBook(book);
                record.setShortageQuantity(shortage);
                outOfStockRecordRepository.save(record);
                System.out.println("Saved OutOfStockRecord: " + record);
            }
        }
    }

    // 通过缺书记录生成采购单
    public PurchaseOrder generatePurchaseOrderFromOutOfStockRecords() {
        List<OutOfStockRecord> records = outOfStockRecordRepository.findAll();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        Double totalPrice = 0.0;

        for (OutOfStockRecord record : records) {
            Book book = record.getBook();
            totalPrice += book.getPrice() * record.getShortageQuantity();
            purchaseOrder.getBooks().add(book);
        }

        purchaseOrder.setTotalPrice(totalPrice);
        purchaseOrderService.createPurchaseOrder(purchaseOrder);

        // 删除缺书记录
        outOfStockRecordRepository.deleteAll(records);

        return purchaseOrder;
    }
}
