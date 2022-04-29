package br.com.feliciano.mvc.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import br.com.feliciano.mvc.controller.CategoryController;
import br.com.feliciano.mvc.controller.InventoryController;
import br.com.feliciano.mvc.controller.ProductController;
import br.com.feliciano.mvc.domain.entities.Category;
import br.com.feliciano.mvc.domain.entities.Inventory;
import br.com.feliciano.mvc.domain.entities.Product;
import br.com.feliciano.mvc.domain.entities.vo.InventoryVO;
import br.com.feliciano.mvc.view.utils.BuilderLayout;
import br.com.feliciano.mvc.view.utils.InputValidation;
import br.com.feliciano.mvc.view.utils.Message;

public class InventoryView extends JFrame {

	@Serial
	private static final long serialVersionUID = -3290552204306899863L;
	private static final Color LIGHT_BUTTON = new Color(248, 249, 250);
	private static final Font GLOBAL = new Font("SansSerif", Font.PLAIN, 14);
	private final transient ProductController productController;
	private final transient CategoryController categoryController;
	private final transient InventoryController inventoryController;
	private JTextField descriptionTxt;
	private JTextField quantityTxt;
	private JComboBox<Category> categoryCombo;
	private JComboBox<Product> productCombo;
	private JButton refreshComboCategoryBtn;
	private JButton refreshComboProductBtn;
	private JButton refreshTableBtn;
	private JButton saveBtn;
	private JButton editBtn;
	private JButton cleanBtn;
	private JButton deleteBtn;
	private JButton pageBeforeBtn;
	private JButton pageNextBtn;
	private JButton categoryManagerBtn;
	private JButton prodManagerBtn;
	private JButton editUserBtn;
	private JTable table;
	private JLabel priceTotalLabel;
	private DefaultTableModel model;

	public InventoryView() {
		super("INVENTORY MVC");
		categoryController = new CategoryController();
		productController = new ProductController();
		inventoryController = new InventoryController();
		Container container = getContentPane();
		setLayout(null);
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
		pageBeforeBtn.addActionListener(e -> System.out.println("pageBeforeBtn"));
		pageNextBtn.addActionListener(e -> System.out.println("pageNextBtn"));
		categoryManagerBtn.addActionListener(e -> {
			CategoryView categoryView = new CategoryView();
			categoryView.setVisible(true);
		});

		prodManagerBtn.addActionListener(e -> {
			ProductView productView = new ProductView();
			productView.setVisible(true);
		});

		editUserBtn.addActionListener(e -> {
			ProfileView login = new ProfileView();
			login.setVisible(true);
		});
		quantityTxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent EVT) {
				boolean valid = ((EVT.getKeyChar() >= '0' && EVT.getKeyChar() <= '9') || EVT.getKeyChar() == '.'
						|| EVT.getKeyChar() == '\b');
				if (!valid) {
					Message.showError("Please enter numeric value only");
					quantityTxt.setText("");
				}
			}
		});

		refreshComboCategoryBtn.addActionListener(e -> updateComboCategory());
		refreshComboProductBtn.addActionListener(e -> updateComboProduct());
		refreshTableBtn.addActionListener(e -> updateTable());
		populateTable();
	}

	private void buildFrame(Container container) {
		final int CONTAINER_HORIZONTAL_SIZE = 800;
		final int CONTAINER_VERTICAL_SIZE = 630;

		buildInventory(container);
		buildComboCategory(container);
		buildInputs(container);
		buildButtons(container);
		buildTable(container);

		setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void buildInventory(Container container) {
		productCombo = new JComboBox<>();
		productCombo.setBounds(45, 60, 210, 25);
		container.add(productCombo);
		updateComboProduct();
	}

	private void buildComboCategory(Container container) {
		categoryCombo = new JComboBox<>();
		categoryCombo.setBounds(45, 120, 210, 25);
		container.add(categoryCombo);
		updateComboCategory();
	}

	private void updateComboCategory() {
		categoryCombo.removeAllItems();
		List<Category> categories = categoryController.list();
		categories.forEach(c -> categoryCombo.addItem(c));
	}

	private void updateComboProduct() {
		productCombo.removeAllItems();
		List<Product> products = productController.list();
		products.forEach(c -> productCombo.addItem(c));
	}

	private void buildInputs(Container container) {
		// DESCRIPTION
		JLabel descriptionLabel = new JLabel("DESCRIPTION");
		int[] descriptionBounds = { 45, 160, 240, 20 };
		BuilderLayout.addLabel(container, descriptionLabel, descriptionBounds, null, Color.BLACK);
		descriptionTxt = new JTextField();
		descriptionTxt.setBounds(45, 185, 320, 25);
		container.add(descriptionTxt);

		// QUANTITY
		JLabel quantityLabel = new JLabel("QUANTITY");
		int[] quantityBounds = { 45, 215, 240, 20 };
		BuilderLayout.addLabel(container, quantityLabel, quantityBounds, null, Color.BLACK);

		quantityTxt = new JTextField();
		quantityTxt.setBounds(45, 240, 210, 25);
		container.add(quantityTxt);

		// PRODUCT
		JLabel productLabel = new JLabel("PRODUCT");
		int[] productBounds = { 45, 35, 240, 25 };
		BuilderLayout.addLabel(container, productLabel, productBounds, null, Color.BLACK);

		// CATEGORY
		JLabel categoryLabel = new JLabel("CATEGORY");
		int[] categoryBounds = { 45, 95, 240, 25 };
		BuilderLayout.addLabel(container, categoryLabel, categoryBounds, null, Color.BLACK);

		// TOTAL DOC
		priceTotalLabel = new JLabel();
		priceTotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		int[] priceTotalBounds = { 500, 540, 200, 25 };
		BuilderLayout.addLabel(container, priceTotalLabel, priceTotalBounds, null, Color.BLACK);
	}

	private void buildButtons(Container container) {
		editUserBtn = new JButton("Profile");
		int[] editUserBtnBounds = { 600, 30, 80, 25 };
		BuilderLayout.addButton(container, editUserBtn, editUserBtnBounds, Color.BLUE, Color.WHITE);

		prodManagerBtn = new JButton("Products");
		int[] prodManagerBounds = { 265, 60, 100, 25 };
		BuilderLayout.addButton(container, prodManagerBtn, prodManagerBounds, Color.DARK_GRAY, Color.WHITE);

		refreshComboProductBtn = new JButton("Refresh");
		int[] refreshProdManagerBounds = { 375, 60, 80, 25 };
		BuilderLayout.addButton(container, refreshComboProductBtn, refreshProdManagerBounds);

		categoryManagerBtn = new JButton("Categories");
		int[] catManagerBounds = { 45 + 220, 120, 100, 25 };
		BuilderLayout.addButton(container, categoryManagerBtn, catManagerBounds, Color.DARK_GRAY, Color.WHITE);

		refreshComboCategoryBtn = new JButton("Refresh");
		int[] refreshCatManagerBounds = { 155 + 220, 120, 80, 25 };
		BuilderLayout.addButton(container, refreshComboCategoryBtn, refreshCatManagerBounds);

		saveBtn = new JButton("Save");
		int[] savaBounds = { 45, 280, 80, 25 };
		BuilderLayout.addButton(container, saveBtn, savaBounds, new Color(40, 167, 69), Color.WHITE);

		cleanBtn = new JButton("Clean");
		int[] cleanBounds = { 135, 280, 80, 25 };
		BuilderLayout.addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125), Color.WHITE);

		deleteBtn = new JButton("Delete");
		int[] deleteBtnBounds = { 45, 540, 80, 25 };
		BuilderLayout.addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69), Color.WHITE);

		editBtn = new JButton("Update");
		int[] editBtnBounds = { 45 + 90, 540, 80, 25 };
		BuilderLayout.addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7), Color.WHITE);

		refreshTableBtn = new JButton("Refresh");
		int[] refreshTableBounds = { 225, 540, 80, 25 };
		BuilderLayout.addButton(container, refreshTableBtn, refreshTableBounds);

		pageBeforeBtn = new JButton("<");
		int[] pageBeforeBtnBounds = { 340, 540, 50, 25 };
		BuilderLayout.addButton(container, pageBeforeBtn, pageBeforeBtnBounds, LIGHT_BUTTON, null);

		pageNextBtn = new JButton(">");
		int[] pageNextBtnBounds = { 400, 540, 50, 25 };
		BuilderLayout.addButton(container, pageNextBtn, pageNextBtnBounds, LIGHT_BUTTON, null);
	}

	private void buildTable(Container container) {
		
		buildTableTitle(container);

		this.model = new DefaultTableModel() {
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				List<Integer> columns = Arrays.asList(0, 2, 3, 5, 7);
				return !columns.contains(column);
			}
		};

		this.model.addColumn("Id");
		this.model.addColumn("ProductId");
		this.model.addColumn("Product name");
		this.model.addColumn("Product price");
		this.model.addColumn("CategoryId");
		this.model.addColumn("Category name");
		this.model.addColumn("Quantity");
		this.model.addColumn("Inventory price");
		this.model.addColumn("Description");

		this.table = new JTable(model);
		table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.table.setFont(GLOBAL);

		// Defines table's column width.
		int[] columnsWidth = { 40, 40, 135, 90, 40, 100, 40, 90, 125 };
		// Configures table's column width.
		int i = 0;
		for (int width : columnsWidth) {
			TableColumn column = table.getColumnModel().getColumn(i++);
			column.setMinWidth(width);
			column.setMaxWidth(width);
		}

		DefaultTableCellRenderer cellRightNotEnabled = new DefaultTableCellRenderer();
		cellRightNotEnabled.setHorizontalAlignment(SwingConstants.RIGHT);
		cellRightNotEnabled.setEnabled(false);
		table.getColumnModel().getColumn(3).setCellRenderer(cellRightNotEnabled);
		table.getColumnModel().getColumn(7).setCellRenderer(cellRightNotEnabled);

		DefaultTableCellRenderer cellCenter = new DefaultTableCellRenderer();
		cellCenter.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(4).setCellRenderer(cellCenter);
		table.getColumnModel().getColumn(6).setCellRenderer(cellCenter);

		DefaultTableCellRenderer cellLeftNotEnabled = new DefaultTableCellRenderer();
		cellLeftNotEnabled.setHorizontalAlignment(SwingConstants.LEFT);
		cellLeftNotEnabled.setEnabled(false);
		table.getColumnModel().getColumn(2).setCellRenderer(cellLeftNotEnabled);
		table.getColumnModel().getColumn(5).setCellRenderer(cellLeftNotEnabled);

		DefaultTableCellRenderer cellCenterNotEnabled = new DefaultTableCellRenderer();
		cellCenterNotEnabled.setHorizontalAlignment(SwingConstants.CENTER);
		cellCenterNotEnabled.setEnabled(false);
		table.getColumnModel().getColumn(0).setCellRenderer(cellCenterNotEnabled);

		table.setBounds(45, 352, 700, 150);
		container.add(table);
	}

	private void buildTableTitle(Container container) {
		final JLabel inventoryIdLabel = new JLabel("ID");
		int[] inventoryIdBounds = { 50, 330, 40, 20 };
		BuilderLayout.addLabel(container, inventoryIdLabel, inventoryIdBounds, Color.LIGHT_GRAY, Color.BLACK);

		final JLabel productIdLabel = new JLabel("ProdID");
		int[] productIdBounds = { 85, 330, 40, 20 };
		BuilderLayout.addLabel(container, productIdLabel, productIdBounds, Color.LIGHT_GRAY, Color.BLACK);

		final JLabel productNameLabel = new JLabel("ProdName");
		int[] productNameBounds = { 150, 330, 80, 20 };
		BuilderLayout.addLabel(container, productNameLabel, productNameBounds, Color.LIGHT_GRAY, Color.BLACK);

		final JLabel productPriceNameLabel = new JLabel("ProdPrice");
		int[] productPriceNameBounds = { 275, 330, 80, 20 };
		BuilderLayout.addLabel(container, productPriceNameLabel, productPriceNameBounds, Color.LIGHT_GRAY, Color.BLACK);

		final JLabel catIdLabel = new JLabel("CatID");
		int[] catIdBounds = { 355, 330, 40, 20 };
		BuilderLayout.addLabel(container, catIdLabel, catIdBounds, Color.LIGHT_GRAY, Color.BLACK);

		final JLabel catNameLabel = new JLabel("CatName");
		int[] catNameBounds = { 410, 330, 80, 20 };
		BuilderLayout.addLabel(container, catNameLabel, catNameBounds, Color.LIGHT_GRAY, Color.BLACK);

		final JLabel quantityLabel = new JLabel("Qty");
		int[] quantityBounds = { 500, 330, 40, 20 };
		BuilderLayout.addLabel(container, quantityLabel, quantityBounds, Color.LIGHT_GRAY, Color.BLACK);
		
		final JLabel priceLabel = new JLabel("Price in Stock");
		int[] priceBounds = { 535, 330, 80, 20 };
		BuilderLayout.addLabel(container, priceLabel, priceBounds, Color.LIGHT_GRAY, Color.BLACK);
		
		final JLabel descriptionLabel = new JLabel("Description");
		int[] descriptionBounds = { 635, 330, 80, 20 };
		BuilderLayout.addLabel(container, descriptionLabel, descriptionBounds, Color.LIGHT_GRAY, Color.BLACK);
		
	}

	private void update() {
		Object obj = getInputObject();
		if (obj instanceof Integer id) {
			int prodId = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 1).toString());
			int catId = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 4).toString());
			int quantity = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 6).toString());
			String description = (String) model.getValueAt(table.getSelectedRow(), 8);
			int inventoryId = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 0).toString());
			Inventory inventory = new Inventory(prodId, catId, quantity, description);
			inventory.setId(inventoryId);
			this.inventoryController.update(inventory);
			Message.showMessage("successfully updated item id: " + id);
		}
	}

	private Object getInputObject() {
		try {
			return model.getValueAt(table.getSelectedRow(), 0);
		} catch (Exception e) {
			Message.showError("Please select a row");
		}
		return null;
	}

	private void delete() {
		Object obj = getInputObject();
		if (obj instanceof Integer id) {
			inventoryController.delete(id);
			model.removeRow(table.getSelectedRow());
			Message.showMessage("successfully deleted item id: " + id);
		}
	}

	private boolean save() {
		if (!InputValidation.validate(descriptionTxt)) {
			Message.showError("must provider a description!");
			return false;
		}
		if (!InputValidation.validate(quantityTxt)) {
			Message.showError("must provider a quantity!");
			return false;
		}
		Category category = (Category) categoryCombo.getSelectedItem();
		Product product = (Product) productCombo.getSelectedItem();
		assert product != null;
		assert category != null;
		Inventory inventory = new Inventory(product.getId(), category.getId(), Integer.parseInt(quantityTxt.getText()),
				descriptionTxt.getText());
		this.inventoryController.save(inventory);
		Message.showMessage("successfully saved");
		return true;
	}

	private void populateTable() {
		List<InventoryVO> inventoriesVo = inventoryController.list();
		inventoriesVo.forEach(e -> model.addRow(new Object[] { e.getId(), e.getProductId(), e.getProductName(),
				"R$" + e.getProductPrice(), e.getCategoryId(), e.getCategoryName(), e.getQuantity(),
				"R$" + getTotalPrice(e), e.getDescription() }));

		Optional<BigDecimal> totalPrice = inventoriesVo.stream().map(this::getTotalPrice).reduce(BigDecimal::add);
		if (totalPrice.isPresent() && totalPrice.get().compareTo(BigDecimal.ONE) > 0) {
			priceTotalLabel.setText("Document price: R$" + totalPrice.get());
		}
	}

	private BigDecimal getTotalPrice(InventoryVO e) {
		return e.getProductPrice().multiply(new BigDecimal(e.getQuantity()));
	}

	private void cleanInputs() {
		this.descriptionTxt.setText("");
		this.quantityTxt.setText("");
		this.categoryCombo.setSelectedIndex(0);
		this.productCombo.setSelectedIndex(0);
	}

	private void updateTable() {
		cleanTable();
		populateTable();
	}

	private void cleanTable() {
		model.getDataVector().removeAllElements();
	}
}
