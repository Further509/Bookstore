package org.example.controller;

import org.example.model.Supplier;
import org.example.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // 获取所有供应商
    @GetMapping
    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    // 创建供应商
    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    // 更新供应商
    @PutMapping("/{id}")
    public Supplier updateSupplier(@PathVariable Long id, @RequestBody Supplier supplier) {
        return supplierService.updateSupplier(id, supplier);
    }

    // 删除供应商
    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }

    // 添加书目信息
    @PostMapping("/{id}/catalog")
    public Supplier addBookToCatalog(@PathVariable Long id, @RequestParam Long bookId) {
        return supplierService.addBookToCatalog(id, bookId);
    }

    // 删除书目信息
    @DeleteMapping("/{id}/catalog")
    public Supplier removeBookFromCatalog(@PathVariable Long id, @RequestParam Long bookId) {
        return supplierService.removeBookFromCatalog(id, bookId);
    }

    // 获取供应商供应的书籍ID列表
    @GetMapping("/{id}/books")
    public List<Long> getSupplierBookIds(@PathVariable Long id) {
        return supplierService.getSupplierBookIds(id);
    }
}
