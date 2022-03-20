package view;

import java.awt.Color;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CategoryController;
import controller.ProductController;
import entities.Category;
import entities.Product;

public class Frame extends JFrame {

	private static final long serialVersionUID = -3290552204306899863L;

	private JTextField nameTxt, descriptionTxt;
	private JComboBox<Category> categoryCombo;
	private JButton saveBtn, editBtn, cleanBtn, deleteBtn;
	private JTable table;
	private DefaultTableModel model;
	private transient ProductController productController;
	private transient CategoryController categoryController;

	public Frame() {
		super("Produtos");
		Container container = getContentPane();
		setLayout(null);

		this.categoryController = new CategoryController();
		this.productController = new ProductController();

		buildFrame(container);

		saveBtn.addActionListener(e -> {
			save();
			updateTable();
			cleanInputs();
		});
		cleanBtn.addActionListener(e -> cleanInputs());
		deleteBtn.addActionListener(e -> {
			delete();
			updateTable();
		});
		editBtn.addActionListener(e -> {
			update();
			updateTable();
		});
	}

	private void update() {
		Object obj = model.getValueAt(table.getSelectedRow(), 0);
		if (!isInstanceOf(obj)) {
			showMessage();
		} else {
			Integer id = (Integer) obj;
			String name = (String) model.getValueAt(table.getSelectedRow(), 1);
			String description = (String) model.getValueAt(table.getSelectedRow(), 2);
			Product prod = new Product(id, name, description);
			this.productController.update(prod);

		}
	}

	private void delete() {
		Object obj = model.getValueAt(table.getSelectedRow(), 0);
		if (!isInstanceOf(obj)) {
			showMessage();
		} else {
			Integer id = (Integer) obj;
			this.productController.delete(id);
			model.removeRow(table.getSelectedRow());
			JOptionPane.showMessageDialog(this, "successfully deleted");
		}
	}

	private void showMessage() {
		JOptionPane.showMessageDialog(this, "Please, select an row");
	}

	private void save() {
		boolean validInput = nameTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty();
		if (validInput) {
			JOptionPane.showMessageDialog(this, "must provider an name and description!");
		} else {
			Product product = new Product(nameTxt.getText(), descriptionTxt.getText());
			Category category = (Category) categoryCombo.getSelectedItem();
			product.setCategoryId(category.getId());
			this.productController.save(product);
			JOptionPane.showMessageDialog(this, "successfully saved");
		}
	}

	private void populateTable() {
		List<Product> products = productController.list();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		products.forEach(p -> model
				.addRow(new Object[] { p.getId(), p.getName(), p.getDescription(), sdf.format(p.getRegisterDate()) }));
	}

	private void cleanInputs() {
		this.nameTxt.setText("");
		this.descriptionTxt.setText("");
		this.categoryCombo.setSelectedIndex(0);
	}

	private void updateTable() {
		cleanTable();
		populateTable();
	}

	private void cleanTable() {
		model.getDataVector().clear();
	}

	private boolean isInstanceOf(Object obj) {
		return obj instanceof Integer;
	}

	private void buildFrame(Container container) {
		JLabel nameLabel = new JLabel("Product name");
		nameLabel.setBounds(10, 10, 240, 15);
		nameLabel.setForeground(Color.BLACK);

		JLabel descriptionLabel = new JLabel("Product description");
		descriptionLabel.setBounds(10, 50, 240, 15);
		descriptionLabel.setForeground(Color.BLACK);

		JLabel categoryLabel = new JLabel("Product category");
		categoryLabel.setBounds(10, 90, 240, 15);
		categoryLabel.setForeground(Color.BLACK);

		container.add(nameLabel);
		container.add(descriptionLabel);
		container.add(categoryLabel);

		nameTxt = new JTextField();
		nameTxt.setBounds(10, 25, 265, 20);
		descriptionTxt = new JTextField();
		descriptionTxt.setBounds(10, 65, 265, 20);

		categoryCombo = new JComboBox<>();
		categoryCombo.addItem(new Category(0, "Select"));
		categoryCombo.setBounds(10, 105, 170, 20);

		List<Category> categories = categoryController.list();

		categories.forEach(c -> categoryCombo.addItem(c));

		container.add(nameTxt);
		container.add(descriptionTxt);
		container.add(categoryCombo);

		saveBtn = new JButton("Save");
		saveBtn.setBounds(10, 145, 80, 20);
		saveBtn.setBackground(new Color(40, 167, 69));
		saveBtn.setForeground(Color.WHITE);

		cleanBtn = new JButton("Clean");
		cleanBtn.setBounds(100, 145, 80, 20);
		cleanBtn.setBackground(new Color(108, 117, 125));
		cleanBtn.setForeground(Color.WHITE);

		container.add(saveBtn);
		container.add(cleanBtn);

		buildModel(container);
		table.setBounds(10, 185, 660, 200);

		deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(10, 420, 80, 20);
		deleteBtn.setBackground(new Color(220, 53, 69));
		deleteBtn.setForeground(Color.WHITE);

		editBtn = new JButton("Update");
		editBtn.setBounds(100, 420, 80, 20);
		editBtn.setBackground(new Color(255, 197, 7));

		container.add(deleteBtn);
		container.add(editBtn);

		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void buildModel(Container container) {
		model = new DefaultTableModel();
		model.addColumn("Product Id");
		model.addColumn("Product Name");
		model.addColumn("Product Description");
		model.addColumn("Created At");

		table = new JTable(model);
		populateTable();
		container.add(table);
	}
}
