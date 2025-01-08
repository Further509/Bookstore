package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "out_of_stock_record")  // 显式指定表名
public class OutOfStockRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book; // 图书

    private Integer shortageQuantity; // 缺少的数量

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getShortageQuantity() {
        return shortageQuantity;
    }

    public void setShortageQuantity(Integer shortageQuantity) {
        this.shortageQuantity = shortageQuantity;
    }
}
