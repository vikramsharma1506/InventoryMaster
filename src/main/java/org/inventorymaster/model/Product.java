package org.inventorymaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer stock;
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "--------------------" +
                "\nProduct Id   : " + id +
                "\nProduct Name : " + name +
                "\nPrice        : ₹" + price +
                "\nStock        : " + stock +
                "\nUnit         : " + unit +
                "\n--------------------";
    }
}
