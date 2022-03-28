package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sb.append("ID= ");
		sb.append(id);
		sb.append(", NAME= ");
		sb.append(name);
		sb.append(", DESCRIPTION= ");
		sb.append(description);
		sb.append(", PRICE= ");
		sb.append(price);
		if (registerDate != null){
			sb.append(", REGISTER DATE= ");
			sb.append(sdf.format(registerDate));
		}
		return sb.toString();
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
