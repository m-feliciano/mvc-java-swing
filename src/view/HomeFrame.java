package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controller.CategoryController;
import controller.ProductController;
import entities.Category;
import entities.Product;

public class HomeFrame extends JFrame {

	private static final long serialVersionUID = -3290552204306899863L;

	private JTextField nameTxt, descriptionTxt, priceTxt;
	private JComboBox<Category> categoryCombo;
	private JButton saveBtn, editBtn, cleanBtn, deleteBtn, pageBeforeBtn, pageNextBtn, categoryManagerBtn, logoutBtn,
			editUserBtn;
	private JTable table;
	private JLabel priceTotalLabel;
	private DefaultTableModel model;
	private transient ProductController productController;
	private transient CategoryController categoryController;

	private static final Color LIGHT_BUTTON = new Color(248, 249, 250);

	public HomeFrame() {
		super("CRUD MVC");
		Container container = getContentPane();
		setLayout(null);

		categoryController = new CategoryController();
		productController = new ProductController();

		buildFrame(container);

		saveBtn.addActionListener(e -> {
			if (save()) {
				cleanInputs();
				updateTable();
			}
		});

		deleteBtn.addActionListener(e -> {
			delete();
			updateTable();
		});

		editBtn.addActionListener(e -> {
			update();
			updateTable();
		});

		cleanBtn.addActionListener(e -> cleanInputs());

		pageBeforeBtn.addActionListener(e -> {
			System.out.println("pageBeforeBtn");
		});

		pageNextBtn.addActionListener(e -> {
			System.out.println("pageNextBtn");
		});

		categoryManagerBtn.addActionListener(e -> {
			System.out.println("categoryManagerBtn");
		});

		logoutBtn.addActionListener(e -> {
			System.out.println("logoutBtn");
		});

		editUserBtn.addActionListener(e -> {
			System.out.println("editUserBtn");
		});

		populateTable();
	}

	private void buildFrame(Container container) {
		int eixoX = 800, eixoY = 590;
		int paddingLeft = 45;

		logoutBtn = new JButton("Logout");
		int[] logoutBtnBounds = { eixoX - 135, 10, 80, 25 };
		addButton(container, logoutBtn, logoutBtnBounds, new Color(248, 249, 250), Color.BLACK);

		editUserBtn = new JButton("Profile");
		int[] editUserBtnBounds = { eixoX - 225, 10, 80, 25 };
		addButton(container, editUserBtn, editUserBtnBounds, Color.BLUE, Color.WHITE);

		JLabel nameLabel = new JLabel("PRODUCT NAME");
		int[] nameLabelBounds = { paddingLeft, 10, 240, 20 };
		addLabel(container, nameLabel, nameLabelBounds, null, Color.BLACK);

		nameTxt = new JTextField();
		nameTxt.setBounds(paddingLeft, 30, 280, 25);
		container.add(nameTxt);

		JLabel descriptionLabel = new JLabel("PRODUCT DESCRIPTION");
		int[] descriptionBounds = { paddingLeft, 60, 240, 20 };
		addLabel(container, descriptionLabel, descriptionBounds, null, Color.BLACK);

		descriptionTxt = new JTextField();
		descriptionTxt.setBounds(paddingLeft, 80, 280, 25);
		container.add(descriptionTxt);

		// PRICE
		JLabel priceLabel = new JLabel("PRODUCT PRICE");
		int[] priceBounds = { paddingLeft, 110, 240, 20 };
		addLabel(container, priceLabel, priceBounds, null, Color.BLACK);

		priceTxt = new JTextField();
		priceTxt.setBounds(paddingLeft, 130, 280, 25);
		container.add(priceTxt);

		// CATEGORY
		JLabel categoryLabel = new JLabel("CATEGORY");
		int[] categoryBounds = { paddingLeft, 155, 240, 25 };
		addLabel(container, categoryLabel, categoryBounds, null, Color.BLACK);

		categoryCombo = new JComboBox<>();
		categoryCombo.setBounds(paddingLeft, 180, 170, 25);
		container.add(categoryCombo);

		List<Category> categories = categoryController.list();
		categories.forEach(c -> categoryCombo.addItem(c));

		categoryManagerBtn = new JButton("Manager");
		int[] catManagerBounds = { paddingLeft + 180, 180, 100, 25 };
		addButton(container, categoryManagerBtn, catManagerBounds, Color.BLUE, Color.WHITE);

		saveBtn = new JButton("Save");
		int[] savaBounds = { paddingLeft, 230, 80, 25 };
		addButton(container, saveBtn, savaBounds, new Color(40, 167, 69), Color.WHITE);

		cleanBtn = new JButton("Clean");
		int[] cleanBounds = { paddingLeft + 90, 230, 80, 25 };
		addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125), Color.WHITE);

		deleteBtn = new JButton("Delete");
		int[] deleteBtnBounds = { paddingLeft, 500, 80, 25 };
		addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69), Color.WHITE);

		editBtn = new JButton("Update");
		int[] editBtnBounds = { paddingLeft + 90, 500, 80, 25 };
		addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7), Color.WHITE);

		pageBeforeBtn = new JButton("<");
		int[] pageBeforeBtnBounds = { 340, 500, 50, 25 };
		addButton(container, pageBeforeBtn, pageBeforeBtnBounds, LIGHT_BUTTON, null);

		pageNextBtn = new JButton(">");
		int[] pageNextBtnBounds = { 400, 500, 50, 25 };
		addButton(container, pageNextBtn, pageNextBtnBounds, LIGHT_BUTTON, null);

		// TOTAL DOC
		priceTotalLabel = new JLabel();
		priceTotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		int[] priceTotalBounds = { eixoX - 275, 500, 200, 25 };
		addLabel(container, priceTotalLabel, priceTotalBounds, null, Color.BLACK);

		buildTable(container, paddingLeft);

		setSize(eixoX, eixoY);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void buildTable(Container container, int paddingLeft) {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return (column != 4 && column != 0);
			}
		};

		table = new JTable(model);
		model.addColumn("Product Id");
		model.addColumn("Product Name");
		model.addColumn("Product Description");
		model.addColumn("Product Price");
		model.addColumn("Created At");

		// Defines table's column width.
		int[] columnsWidth = { 30, 200, 260, 90, 120 };

		// Configures table's column width.
		int i = 0;
		for (int width : columnsWidth) {
			TableColumn column = table.getColumnModel().getColumn(i++);
			column.setMinWidth(width);
			column.setMaxWidth(width);
			column.setPreferredWidth(width);
		}

		DefaultTableCellRenderer cellRendererCenter = new DefaultTableCellRenderer();
		DefaultTableCellRenderer cellRendererRight = new DefaultTableCellRenderer();
		cellRendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
		cellRendererCenter.setEnabled(false);
		cellRendererCenter.setFocusable(false);
		cellRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(0).setCellRenderer(cellRendererCenter);
		table.getColumnModel().getColumn(3).setCellRenderer(cellRendererRight);
		table.getColumnModel().getColumn(4).setCellRenderer(cellRendererCenter);
		table.setBounds(paddingLeft, 280, 700, 200);
		container.add(table);
	}

	private void addButton(Container contanier, JButton button, int[] bounds, Color background, Color foreground) {
		button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		button.setBackground(background == null ? Color.WHITE : background);
		button.setForeground(foreground == null ? Color.BLACK : foreground);
		contanier.add(button);
	}

	private void addLabel(Container contanier, JLabel label, int[] bounds, Color background, Color foreground) {
		label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
		label.setBackground(background == null ? Color.WHITE : background);
		label.setForeground(foreground == null ? Color.BLACK : foreground);
		contanier.add(label);
	}

	private void update() {
		Object obj = model.getValueAt(table.getSelectedRow(), 0);
		if (!isInstanceOf(obj)) {
			showMessage();
		} else {
			Integer id = (Integer) obj;
			String name = (String) model.getValueAt(table.getSelectedRow(), 1);
			String description = (String) model.getValueAt(table.getSelectedRow(), 2);
			BigDecimal price = new BigDecimal((String) model.getValueAt(table.getSelectedRow(), 3));
			Product prod = new Product(id, name, description, price);
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

	private boolean save() {
		boolean validTxt = nameTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty();
		BigDecimal price = new BigDecimal(priceTxt.getText());
		int validPrice = price.compareTo(BigDecimal.ONE);
		System.out.println(price);
		if (validTxt) {
			JOptionPane.showMessageDialog(this, "must provider an name and description!");
			return false;
		} else if (validPrice <= 0) {
			JOptionPane.showMessageDialog(this, "must provider an valid price. ex.: 10.00");
			return false;
		} else {
			Product product = new Product(nameTxt.getText(), descriptionTxt.getText(), price);
//			Category category = (Category) categoryCombo.getSelectedItem();
//			product.setCategoryId(category.getId());
			this.productController.save(product);
			JOptionPane.showMessageDialog(this, "successfully saved");
			return true;
		}
	}

	private void populateTable() {
		List<Product> products = productController.list();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		products.forEach(p -> model.addRow(new Object[] { p.getId(), p.getName(), p.getDescription(),
				("R$ " + p.getPrice()), sdf.format(p.getRegisterDate()) }));
		Optional<BigDecimal> totalPrice = products.stream().map(Product::getPrice).reduce((t, u) -> u.add(t));
		if(totalPrice.isPresent() && totalPrice.get().compareTo(BigDecimal.ONE) > 0) {
			priceTotalLabel.setText("Document price: R$" + totalPrice.get());			
		}
	}

	private void cleanInputs() {
		this.nameTxt.setText("");
		this.descriptionTxt.setText("");
		this.priceTxt.setText("");
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
}
