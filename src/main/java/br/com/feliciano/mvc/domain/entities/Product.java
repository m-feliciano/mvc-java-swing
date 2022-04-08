package br.com.feliciano.mvc.domain.entities;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    private String name;
    private String description;
    private Date registerDate;
    private BigDecimal price;

    public Product(String name, String description, BigDecimal price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(Integer id, String name, String description, BigDecimal price) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new StringBuilder()
                .append(id)
                .append(" - ")
                .append("name: ")
                .append(name)
                .append(", description: ")
                .append(description)
                .append(", price: R$")
                .append(price)
                .append(", Created_at: ")
                .append(sdf.format(registerDate))
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Product other = (Product) obj;
        return Objects.equals(id, other.id);
    }

}
