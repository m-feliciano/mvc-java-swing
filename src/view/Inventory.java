package view;

import controller.CategoryController;
import controller.ProductController;
import entities.Category;
import entities.Product;
import view.utils.BuilderLayout;
import view.utils.Message;
import view.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.Serial;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class Inventory extends JFrame {

    @Serial
    private static final long serialVersionUID = -3290552204306899863L;
    private static final Color LIGHT_BUTTON = new Color(248, 249, 250);
    private final transient ProductController productController;
    private final transient CategoryController categoryController;
    private JTextField descriptionTxt;
    private JComboBox<Category> categoryCombo;
    private JComboBox<Product> productCombo;
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

    public Inventory() {
        super("INVENTORY CRUD");
        categoryController = new CategoryController();
        productController = new ProductController();
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
            categoryFrame.setAlwaysOnTop(true);
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
//        populateTable();
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 800;
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

        List<Product> products = productController.list();
        products.forEach(c -> productCombo.addItem(c));
    }

    private void buildComboCategory(Container container) {
        categoryCombo = new JComboBox<>();
        categoryCombo.setBounds(45, 120, 210, 25);
        container.add(categoryCombo);

        List<Category> categories = categoryController.list();
        categories.forEach(c -> categoryCombo.addItem(c));
    }

    private void buildInputs(Container container) {
        // DESCRIPTION
        JLabel descriptionLabel = new JLabel("DESCRIPTION");
        int[] descriptionBounds = {45, 160, 240, 20};
        BuilderLayout.addLabel(container, descriptionLabel, descriptionBounds, null, Color.BLACK);

        descriptionTxt = new JTextField();
        descriptionTxt.setBounds(45, 185, 320, 25);
        container.add(descriptionTxt);

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
        int[] priceTotalBounds = {800 - 275, 500, 200, 25};
        BuilderLayout.addLabel(container, priceTotalLabel, priceTotalBounds, null, Color.BLACK);
    }

    private void buildButtons(Container container) {
        editUserBtn = new JButton("Profile");
        int[] editUserBtnBounds = {800 - 200, 30, 80, 25};
        BuilderLayout.addButton(container, editUserBtn, editUserBtnBounds, Color.BLUE, Color.WHITE);

        prodManagerBtn = new JButton("Add");
        int[] prodManagerBounds = {45 + 220, 60, 100, 25};
        BuilderLayout.addButton(container, prodManagerBtn, prodManagerBounds);

        categoryManagerBtn = new JButton("Add");
        int[] catManagerBounds = {45 + 220, 120, 100, 25};
        BuilderLayout.addButton(container, categoryManagerBtn, catManagerBounds);

        saveBtn = new JButton("Save");
        int[] savaBounds = {45, 230, 80, 25};
        BuilderLayout.addButton(container, saveBtn, savaBounds, new Color(40, 167, 69), Color.WHITE);

        cleanBtn = new JButton("Clean");
        int[] cleanBounds = {45 + 90, 230, 80, 25};
        BuilderLayout.addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125), Color.WHITE);

        deleteBtn = new JButton("Delete");
        int[] deleteBtnBounds = {45, 500, 80, 25};
        BuilderLayout.addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69), Color.WHITE);

        editBtn = new JButton("Update");
        int[] editBtnBounds = {45 + 90, 500, 80, 25};
        BuilderLayout.addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7), Color.WHITE);

        pageBeforeBtn = new JButton("<");
        int[] pageBeforeBtnBounds = {340, 500, 50, 25};
        BuilderLayout.addButton(container, pageBeforeBtn, pageBeforeBtnBounds, LIGHT_BUTTON, null);

        pageNextBtn = new JButton(">");
        int[] pageNextBtnBounds = {400, 500, 50, 25};
        BuilderLayout.addButton(container, pageNextBtn, pageNextBtnBounds, LIGHT_BUTTON, null);
    }

    private void buildTable(Container container) {
        this.model = new DefaultTableModel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return (column != 4 && column != 0);
            }
        };

        this.table = new JTable(model);
        this.model.addColumn("Product Id");
        this.model.addColumn("Product Name");
        this.model.addColumn("Product Description");
        this.model.addColumn("Product Price");
        this.model.addColumn("Created At");
        // Defines table's column width.
        int[] columnsWidth = {30, 200, 260, 90, 120};
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
        table.setBounds(45, 280, 700, 200);
        container.add(table);
    }

    private void update() {
        Object obj = getInputObject();
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
    }

    private boolean save() {
        if (Validation.validate(descriptionTxt)) {
            Message.showError("ERROR: must provider a name and description!");
            return false;
        }
//      Product product = new Product(nameTxt.getText(), descriptionTxt.getText(), price);
//		Category category = (Category) categoryCombo.getSelectedItem();
//		product.setCategoryId(category.getId());
//      this.productController.save(product);
        Message.showMessage("successfully saved");
        return true;
    }

    private void populateTable() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<Product> products = productController.list();
        products.forEach(p ->
                model.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getPrice(),
                        sdf.format(p.getRegisterDate())
                }));
        Optional<BigDecimal> totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce((t, u) -> u.add(t));
        if (totalPrice.isPresent() && totalPrice.get().compareTo(BigDecimal.ONE) > 0) {
            priceTotalLabel.setText("Document price: R$" + totalPrice.get());
        }
    }

    private void cleanInputs() {
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
}
