package org.example.service;

import org.example.model.Book;
import org.example.model.PurchaseOrder;
import org.example.repository.BookRepository;
import org.example.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private BookRepository bookRepository;
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder purchaseOrder) {
        if (!purchaseOrderRepository.existsById(id)) {
            throw new RuntimeException("Purchase Order not found");
        }
        purchaseOrder.setId(id);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    // 更新采购单到货时增加库存
    public void markPurchaseOrderAsReceived(Long orderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));

        for (Book book : purchaseOrder.getBooks()) {
            book.setStockQuantity(book.getMinStockQuantity());  // 根据实际数量增加
            bookRepository.save(book);
        }

        purchaseOrderRepository.delete(purchaseOrder);  // 处理完成后删除采购单
    }
}

