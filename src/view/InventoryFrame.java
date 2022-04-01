package view;

import controller.CategoryController;
import controller.InventoryController;
import controller.ProductController;
import entities.Category;
import entities.Inventory;
import entities.Product;
import view.utils.BuilderLayout;
import view.utils.Message;
import view.utils.InputValidation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class InventoryFrame extends JFrame {

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

    public InventoryFrame() {
        super("INVENTORY CRUD");
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
            CategoryFrame categoryFrame = new CategoryFrame();
            categoryFrame.setVisible(true);
        });

        prodManagerBtn.addActionListener(e -> {
            ProductFrame productFrame = new ProductFrame();
            productFrame.setVisible(true);
        });

        editUserBtn.addActionListener(e -> {
            Profile login = new Profile();
            login.setVisible(true);
        });
        quantityTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent EVT) {
                boolean valid = ((EVT.getKeyChar() >= '0' && EVT.getKeyChar() <= '9') || EVT.getKeyChar() == '.' || EVT.getKeyChar() == '\b');
                if (!valid) {
                    Message.showError("Please enter numeric value only");
                    quantityTxt.setText("");
                }
            }
        });

        refreshComboCategoryBtn.addActionListener(e -> updateComboCategory());
        refreshComboProductBtn.addActionListener(e -> updateComboProduct());
        populateTable();
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 650;
        final int CONTAINER_VERTICAL_SIZE = 590;

        buildProductCategory(container);
        buildComboCategory(container);
        buildInputs(container);
        buildButtons(container);
        buildTable(container);

        setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildProductCategory(Container container) {
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
        int[] descriptionBounds = {45, 160, 240, 20};
        BuilderLayout.addLabel(container, descriptionLabel, descriptionBounds, null, Color.BLACK);
        descriptionTxt = new JTextField();
        descriptionTxt.setBounds(45, 185, 320, 25);
        container.add(descriptionTxt);

        // QUANTITY
        JLabel quantityLabel = new JLabel("QUANTITY");
        int[] quantityBounds = {45, 215, 240, 20};
        BuilderLayout.addLabel(container, quantityLabel, quantityBounds, null, Color.BLACK);

        quantityTxt = new JTextField();
        quantityTxt.setBounds(45, 240, 210, 25);
        container.add(quantityTxt);

        // PRODUCT
        JLabel productLabel = new JLabel("PRODUCT");
        int[] productBounds = {45, 35, 240, 25};
        BuilderLayout.addLabel(container, productLabel, productBounds, null, Color.BLACK);

        // CATEGORY
        JLabel categoryLabel = new JLabel("CATEGORY");
        int[] categoryBounds = {45, 95, 240, 25};
        BuilderLayout.addLabel(container, categoryLabel, categoryBounds, null, Color.BLACK);

        // TOTAL DOC
        priceTotalLabel = new JLabel();
        priceTotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        int[] priceTotalBounds = {375, 500, 200, 25};
        BuilderLayout.addLabel(container, priceTotalLabel, priceTotalBounds, null, Color.BLACK);
    }

    private void buildButtons(Container container) {
        editUserBtn = new JButton("Profile");
        int[] editUserBtnBounds = {510, 30, 80, 25};
        BuilderLayout.addButton(container, editUserBtn, editUserBtnBounds, Color.BLUE, Color.WHITE);

        prodManagerBtn = new JButton("Edit");
        int[] prodManagerBounds = {265, 60, 100, 25};
        BuilderLayout.addButton(container, prodManagerBtn, prodManagerBounds);

        refreshComboProductBtn = new JButton("Refresh");
        int[] refreshProdManagerBounds = {375, 60, 100, 25};
        BuilderLayout.addButton(container, refreshComboProductBtn, refreshProdManagerBounds, new Color(108, 117, 125), Color.WHITE);

        categoryManagerBtn = new JButton("Edit");
        int[] catManagerBounds = {45 + 220, 120, 100, 25};
        BuilderLayout.addButton(container, categoryManagerBtn, catManagerBounds);

        refreshComboCategoryBtn = new JButton("Refresh");
        int[] refreshCatManagerBounds = {155 + 220, 120, 100, 25};
        BuilderLayout.addButton(container, refreshComboCategoryBtn, refreshCatManagerBounds, new Color(108, 117, 125), Color.WHITE);

        saveBtn = new JButton("Save");
        int[] savaBounds = {45, 280, 80, 25};
        BuilderLayout.addButton(container, saveBtn, savaBounds, new Color(40, 167, 69), Color.WHITE);

        cleanBtn = new JButton("Clean");
        int[] cleanBounds = {45 + 90, 280, 80, 25};
        BuilderLayout.addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125), Color.WHITE);

        deleteBtn = new JButton("Delete");
        int[] deleteBtnBounds = {45, 500, 80, 25};
        BuilderLayout.addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69), Color.WHITE);

        editBtn = new JButton("Update");
        int[] editBtnBounds = {45 + 90, 500, 80, 25};
        BuilderLayout.addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7), Color.WHITE);

        pageBeforeBtn = new JButton("<");
        int[] pageBeforeBtnBounds = {240, 500, 50, 25};
        BuilderLayout.addButton(container, pageBeforeBtn, pageBeforeBtnBounds, LIGHT_BUTTON, null);

        pageNextBtn = new JButton(">");
        int[] pageNextBtnBounds = {300, 500, 50, 25};
        BuilderLayout.addButton(container, pageNextBtn, pageNextBtnBounds, LIGHT_BUTTON, null);
    }

    private void buildTable(Container container) {

        this.model = new DefaultTableModel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return (column != 0 && column != 2 && column != 5);
            }
        };

        this.model.addColumn("Id");
        this.model.addColumn("ProductId");
        this.model.addColumn("Product name");
        this.model.addColumn("CategoryId");
        this.model.addColumn("Quantity");
        this.model.addColumn("Inventory price");
        this.model.addColumn("Description");
        this.table = new JTable(model);
        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.table.setFont(GLOBAL);

        // Defines table's column width.
        int[] columnsWidth = {50, 50, 135, 50, 50, 90, 125};
        // Configures table's column width.
        int i = 0;
        for (int width : columnsWidth) {
            TableColumn column = table.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
        DefaultTableCellRenderer cellRendererCenter = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRendererNotEnabled = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRendererEnabled = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRendererRight = new DefaultTableCellRenderer();
        cellRendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
        cellRendererEnabled.setHorizontalAlignment(SwingConstants.CENTER);
        cellRendererCenter.setFocusable(false);
        cellRendererCenter.setEnabled(false);
        cellRendererNotEnabled.setEnabled(false);
        cellRendererNotEnabled.setFocusable(false);
        cellRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRendererCenter);
        table.getColumnModel().getColumn(1).setCellRenderer(cellRendererEnabled);
        table.getColumnModel().getColumn(2).setCellRenderer(cellRendererNotEnabled);
        table.getColumnModel().getColumn(3).setCellRenderer(cellRendererEnabled);
        table.getColumnModel().getColumn(4).setCellRenderer(cellRendererEnabled);
        table.getColumnModel().getColumn(5).setCellRenderer(cellRendererCenter);
        table.setBounds(45, 330, 550, 150);
        container.add(table);
    }

    private void update() {
        Object obj = getInputObject();
        if (obj instanceof Integer id) {
            int prodId = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 1).toString());
            int catId = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 3).toString());
            int quantity = Integer.parseInt(model.getValueAt(table.getSelectedRow(), 4).toString());
            String description = (String) model.getValueAt(table.getSelectedRow(), 6);
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
            Message.showError("ERROR: must provider a description!");
            return false;
        }
        if (!InputValidation.validate(quantityTxt)) {
            Message.showError("ERROR: must provider a quantity!");
            return false;
        }
        Category category = (Category) categoryCombo.getSelectedItem();
        Product product = (Product) productCombo.getSelectedItem();
        assert product != null;
        assert category != null;
        Inventory inventory = new Inventory(product.getId(), category.getId(), Integer.parseInt(quantityTxt.getText()), descriptionTxt.getText());
        this.inventoryController.save(inventory);
        Message.showMessage("successfully saved");
        return true;
    }

    private void populateTable() {
        List<Inventory> inventories = inventoryController.list();
        inventories.forEach(e -> model.addRow(new Object[]{e.getId(), e.getProductId(), loadProductBtId(e.getProductId()).getName(), e.getCategoryId(), e.getQuantity(), ("R$" + loadTotalPrice(e)), e.getDescription()}));
        Optional<BigDecimal> totalPrice = inventories.stream()
                .map(this::loadTotalPrice)
                .reduce((t, u) -> u.add(t));
        if (totalPrice.isPresent() && totalPrice.get().compareTo(BigDecimal.ONE) > 0) {
            priceTotalLabel.setText("Document price: R$" + totalPrice.get());
        }
    }

    private BigDecimal loadTotalPrice(Inventory e) {
        return new BigDecimal(String.valueOf(loadProductBtId(e.getProductId()).getPrice())).multiply(new BigDecimal(e.getQuantity()));
    }

    private Product loadProductBtId(int id) {
        return productController.findById(id);
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
