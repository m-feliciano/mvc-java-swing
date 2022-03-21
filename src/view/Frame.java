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
	private JButton saveBtn, editBtn, cleanBtn, deleteBtn, pageBeforeBtn, pageNextBtn, categoryManagerBtn, logoutBtn, EditUserBtn;
	private JTable table;
	private DefaultTableModel model;
	private transient ProductController productController;
	private transient CategoryController categoryController;

	public Frame() {
		super("CRUD MVC");
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
//			Category category = (Category) categoryCombo.getSelectedItem();
//			product.setCategoryId(category.getId());
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
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(590, 10, 80, 20);
		logoutBtn.setBackground(new Color(248, 249, 250));
		logoutBtn.setForeground(Color.BLACK);
		container.add(logoutBtn);
		
		EditUserBtn = new JButton("Profile");
		EditUserBtn.setBounds(500, 10, 80, 20);
		EditUserBtn.setBackground(Color.BLUE);
		EditUserBtn.setForeground(Color.WHITE);
		container.add(EditUserBtn);
		
		JLabel nameLabel = new JLabel("PRODUCT NAME");
		nameLabel.setBounds(10, 10, 240, 15);
		nameLabel.setForeground(Color.BLACK);
		container.add(nameLabel);

		JLabel descriptionLabel = new JLabel("PRODUCT DESCRIPTION");
		descriptionLabel.setBounds(10, 50, 240, 15);
		descriptionLabel.setForeground(Color.BLACK);
		container.add(descriptionLabel);

		JLabel categoryLabel = new JLabel("CATEGORY");
		categoryLabel.setBounds(10, 90, 240, 15);
		categoryLabel.setForeground(Color.BLACK);
		container.add(categoryLabel);

		nameTxt = new JTextField();
		nameTxt.setBounds(10, 25, 280, 20);
		container.add(nameTxt);
		
		descriptionTxt = new JTextField();
		descriptionTxt.setBounds(10, 65, 280, 20);
		container.add(descriptionTxt);

		categoryCombo = new JComboBox<>();
//		categoryCombo.addItem(new Category(0, "Select"));
		categoryCombo.setBounds(10, 105, 170, 20);
		container.add(categoryCombo);

		List<Category> categories = categoryController.list();
		categories.forEach(c -> categoryCombo.addItem(c));
		
		categoryManagerBtn = new JButton("Manager");
		categoryManagerBtn.setBounds(190, 105, 100, 20);
		categoryManagerBtn.setBackground(Color.BLUE);
		categoryManagerBtn.setForeground(Color.WHITE);

		saveBtn = new JButton("Save");
		saveBtn.setBounds(10, 145, 80, 20);
		saveBtn.setBackground(new Color(40, 167, 69));
		saveBtn.setForeground(Color.WHITE);

		cleanBtn = new JButton("Clean");
		cleanBtn.setBounds(100, 145, 80, 20);
		cleanBtn.setBackground(new Color(108, 117, 125));
		cleanBtn.setForeground(Color.WHITE);

		container.add(categoryManagerBtn);
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

		Color pegaBtn = new Color(248, 249, 250);
		pageBeforeBtn = new JButton("<");
		pageBeforeBtn.setBounds(270, 420, 50, 20);
		pageBeforeBtn.setBackground(pegaBtn);

		pageNextBtn = new JButton(">");
		pageNextBtn.setBounds(360, 420, 50, 20);
		pageNextBtn.setBackground(pegaBtn);

		container.add(deleteBtn);
		container.add(editBtn);
		container.add(pageBeforeBtn);
		container.add(pageNextBtn);
		container.add(categoryManagerBtn);

		setSize(700, 500);
		setVisible(true);
		setResizable(false);
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
