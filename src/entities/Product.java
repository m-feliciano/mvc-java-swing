package entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Product {

	private Integer id;
	private String name;
	private String description;
	private Date registerDate;

	public Product() {
		super();
	}

	public Product(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Product(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
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
		sb.append(", REGISTER DATE= ");
		sb.append(sdf.format(registerDate));
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
