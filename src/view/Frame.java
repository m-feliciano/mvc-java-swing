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

	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JLabel categoryLabel;
	private JTextField nameTxt;
	private JTextField descriptionTxt;
	private JComboBox<Category> categoryCombo;
	private JButton saveBtn;
	private JButton editBtn;
	private JButton cleanBtn;
	private JButton deleteBtn;
	private JTable jTable;
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

		saveBtn.addActionListener(e -> save());
		cleanBtn.addActionListener(e -> cleanInputs());
		deleteBtn.addActionListener(e -> delete());
		editBtn.addActionListener(e -> update());
	}

	private void update() {
		Object obj = model.getValueAt(jTable.getSelectedRow(), jTable.getSelectedColumn());
		if (!isInstanceOf(obj)) {
			JOptionPane.showMessageDialog(this, "Please, select an ID");
		} else {
			Integer id = (Integer) obj;
			String name = (String) model.getValueAt(jTable.getSelectedRow(), 1);
			String description = (String) model.getValueAt(jTable.getSelectedRow(), 2);
			Product prod = new Product(id, name, description);
			this.productController.update(prod);
			updateTable();
		}
	}

	private void delete() {
		Object obj = model.getValueAt(jTable.getSelectedRow(), jTable.getSelectedColumn());
		if (!isInstanceOf(obj)) {
			JOptionPane.showMessageDialog(this, "Please, select an ID");
		} else {
			Integer id = (Integer) obj;
			this.productController.delete(id);
			model.removeRow(jTable.getSelectedRow());
			JOptionPane.showMessageDialog(this, "successfully deleted");
			updateTable();
		}
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
			this.cleanInputs();
			updateTable();
		}
	}

	private void populateTable() {
		List<Product> products = listProduct();
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
		return obj instanceof Number;
	}

	private List<Category> listCategory() {
		return this.categoryController.list();
	}

	private List<Product> listProduct() {
		return this.productController.list();
	}

	private void buildFrame(Container container) {
		nameLabel = new JLabel("Product name");
		descriptionLabel = new JLabel("Product description");
		categoryLabel = new JLabel("Product category");

		container.add(nameLabel);
		container.add(descriptionLabel);
		container.add(categoryLabel);

		nameTxt = new JTextField();
		descriptionTxt = new JTextField();
		categoryCombo = new JComboBox<>();

		categoryCombo.addItem(new Category(0, "Select"));
		List<Category> categories = this.listCategory();

		categories.forEach(c -> categoryCombo.addItem(c));

		container.add(nameTxt);
		container.add(descriptionTxt);
		container.add(categoryCombo);

		saveBtn = new JButton("Save");
		cleanBtn = new JButton("Clean");

		container.add(saveBtn);
		container.add(cleanBtn);

		buildModel(container);

		deleteBtn = new JButton("Delete");
		editBtn = new JButton("Update");

		configLayout();

		container.add(deleteBtn);
		container.add(editBtn);

		setSize(700, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void buildModel(Container container) {
		// COLUMN HEADERS
		String[] columnHeaders = { "ID", "NAME", "DESCRIPTION", "CREATED DATE" };

		// CREATE TABLE
		jTable = new JTable();
		model = (DefaultTableModel) jTable.getModel();


		model.addColumn("Product Id");
		model.addColumn("Product Name");
		model.addColumn("Product Description");
		model.addColumn("Created At");

		ChangeName(jTable, 0, "Stu_name");
		ChangeName(jTable, 2, "Paper");

		populateTable();

		container.add(jTable);
	}

	public void ChangeName(JTable table, int col_index, String col_name) {
		table.getColumnModel().getColumn(col_index).setHeaderValue(col_name);
	}

	private void configLayout() {

		nameLabel.setBounds(10, 10, 240, 15);
		nameLabel.setForeground(Color.BLACK);

		descriptionLabel.setBounds(10, 50, 240, 15);
		descriptionLabel.setForeground(Color.BLACK);

		categoryLabel.setBounds(10, 90, 240, 15);
		categoryLabel.setForeground(Color.BLACK);

		nameTxt.setBounds(10, 25, 265, 20);
		descriptionTxt.setBounds(10, 65, 265, 20);

		categoryCombo.setBounds(10, 105, 170, 20);

		saveBtn.setBounds(10, 145, 80, 20);
		saveBtn.setBackground(new Color(40, 167, 69));
		saveBtn.setForeground(Color.WHITE);

		cleanBtn.setBounds(100, 145, 80, 20);
		cleanBtn.setBackground(new Color(108, 117, 125));
		cleanBtn.setForeground(Color.WHITE);

		jTable.setBounds(10, 185, 660, 200);

		deleteBtn.setBounds(10, 420, 80, 20);
		deleteBtn.setBackground(new Color(220, 53, 69));
		deleteBtn.setForeground(Color.WHITE);

		editBtn.setBounds(100, 420, 80, 20);
		editBtn.setBackground(new Color(255, 197, 7));
	}
}
