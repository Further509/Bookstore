package org.example.repository;

import org.example.model.OutOfStockRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutOfStockRecordRepository extends JpaRepository<OutOfStockRecord, Long> {
}