package org.example.controller;

import org.example.model.PurchaseOrder;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/out-of-stock")
public class OutOfStockController {

    @Autowired
    private BookService bookService;

    // 检查库存并生成缺书记录
    @PostMapping("/check")
    public void checkStock() {
        bookService.checkStockAndGenerateOutOfStockRecords();
    }

    // 根据缺书记录生成采购单
    @PostMapping("/generate-purchase-order")
    public PurchaseOrder generatePurchaseOrder() {
        return bookService.generatePurchaseOrderFromOutOfStockRecords();
    }
}