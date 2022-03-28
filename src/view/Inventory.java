package view;

import controller.CategoryController;
import controller.ProductController;
import entities.Category;
import entities.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class Home extends JFrame {

    private static final long serialVersionUID = -3290552204306899863L;

    private JTextField nameTxt;
    private JTextField descriptionTxt;
    private JTextField priceTxt;
    private JComboBox<Category> categoryCombo;
    private JButton saveBtn;
    private JButton editBtn;
    private JButton cleanBtn;
    private JButton deleteBtn;
    private JButton pageBeforeBtn;
    private JButton pageNextBtn;
    private JButton categoryManagerBtn;
    private JButton editUserBtn;

    private JTable table;
    private JLabel priceTotalLabel;
    private DefaultTableModel model;
    private final transient ProductController productController;
    private final transient CategoryController categoryController;

    private static final String NUMBER_FORMAT_ERROR = "Number Format";
    private static final String UNSELECTED_ROW_ERROR = "Unselected row";
    private static final Color LIGHT_BUTTON = new Color(248, 249, 250);

    public Home() {
        super("CRUD MVC");
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
        categoryManagerBtn.addActionListener(e -> System.out.println("categoryManagerBtn"));

        editUserBtn.addActionListener(e -> {
            Profile login = new Profile();
            login.setAlwaysOnTop(true);
            login.show();
        });

        // price input validation
        priceTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent EVT) {
                boolean valid = ((EVT.getKeyChar() >= '0' && EVT.getKeyChar() <= '9') || EVT.getKeyChar() == '.' || EVT.getKeyChar() == '\b');
                if (!valid) {
                    showError("Please enter numeric value only", NUMBER_FORMAT_ERROR);
                    priceTxt.setText("");
                }
            }
        });

//        populateTable();
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 800;
        final int CONTAINER_VERTICAL_SIZE = 590;
        final int PADDING_LEFT = 45;

        buildComboCategory(container, PADDING_LEFT);
        buildInputs(container, CONTAINER_HORIZONTAL_SIZE, PADDING_LEFT);
        buildButtons(container, CONTAINER_HORIZONTAL_SIZE, PADDING_LEFT);
        buildTable(container, PADDING_LEFT);

        setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildComboCategory(Container container, int paddingLeft) {
        categoryCombo = new JComboBox<>();
        categoryCombo.setBounds(paddingLeft, 180, 170, 25);
        container.add(categoryCombo);

        List<Category> categories = categoryController.list();
        categories.forEach(c -> categoryCombo.addItem(c));
    }

    private void buildInputs(Container container, int eixoX, int paddingLeft) {
        // NAME
        JLabel nameLabel = new JLabel("PRODUCT NAME");
        int[] nameLabelBounds = {paddingLeft, 10, 240, 20};
        addLabel(container, nameLabel, nameLabelBounds, null, Color.BLACK);

        nameTxt = new JTextField();
        nameTxt.setBounds(paddingLeft, 30, 280, 25);
        container.add(nameTxt);

        // DESCRIPTION
        JLabel descriptionLabel = new JLabel("PRODUCT DESCRIPTION");
        int[] descriptionBounds = {paddingLeft, 60, 240, 20};
        addLabel(container, descriptionLabel, descriptionBounds, null, Color.BLACK);

        descriptionTxt = new JTextField();
        descriptionTxt.setBounds(paddingLeft, 80, 280, 25);
        container.add(descriptionTxt);

        // PRICE
        JLabel priceLabel = new JLabel("PRODUCT PRICE");
        int[] priceBounds = {paddingLeft, 110, 240, 20};
        addLabel(container, priceLabel, priceBounds, null, Color.BLACK);

        priceTxt = new JTextField();
        priceTxt.setHorizontalAlignment(SwingConstants.LEFT);
        priceTxt.setBounds(paddingLeft, 130, 280, 25);
        container.add(priceTxt);

        // CATEGORY
        JLabel categoryLabel = new JLabel("CATEGORY");
        int[] categoryBounds = {paddingLeft, 155, 240, 25};
        addLabel(container, categoryLabel, categoryBounds, null, Color.BLACK);

        // TOTAL DOC
        priceTotalLabel = new JLabel();
        priceTotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        int[] priceTotalBounds = {eixoX - 275, 500, 200, 25};
        addLabel(container, priceTotalLabel, priceTotalBounds, null, Color.BLACK);
    }

    private void buildButtons(Container container, int eixoX, int paddingLeft) {
        editUserBtn = new JButton("Profile");
        int[] editUserBtnBounds = {eixoX - 225, 10, 80, 25};
        addButton(container, editUserBtn, editUserBtnBounds, Color.BLUE, Color.WHITE);

        categoryManagerBtn = new JButton("Manager");
        int[] catManagerBounds = {paddingLeft + 180, 180, 100, 25};
        addButton(container, categoryManagerBtn, catManagerBounds, Color.BLUE, Color.WHITE);

        saveBtn = new JButton("Save");
        int[] savaBounds = {paddingLeft, 230, 80, 25};
        addButton(container, saveBtn, savaBounds, new Color(40, 167, 69), Color.WHITE);

        cleanBtn = new JButton("Clean");
        int[] cleanBounds = {paddingLeft + 90, 230, 80, 25};
        addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125), Color.WHITE);

        deleteBtn = new JButton("Delete");
        int[] deleteBtnBounds = {paddingLeft, 500, 80, 25};
        addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69), Color.WHITE);

        editBtn = new JButton("Update");
        int[] editBtnBounds = {paddingLeft + 90, 500, 80, 25};
        addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7), Color.WHITE);

        pageBeforeBtn = new JButton("<");
        int[] pageBeforeBtnBounds = {340, 500, 50, 25};
        addButton(container, pageBeforeBtn, pageBeforeBtnBounds, LIGHT_BUTTON, null);

        pageNextBtn = new JButton(">");
        int[] pageNextBtnBounds = {400, 500, 50, 25};
        addButton(container, pageNextBtn, pageNextBtnBounds, LIGHT_BUTTON, null);
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
        table.setBounds(paddingLeft, 280, 700, 200);
        container.add(table);
    }

    private void addButton(Container container, JButton button, int[] bounds, Color background, Color foreground) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(background != null ? background : Color.WHITE);
        button.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(button);
    }

    private void addLabel(Container container, JLabel label, int[] bounds, Color background, Color foreground) {
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setBackground(background != null ? background : Color.WHITE);
        label.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(label);
    }

    private void update() {
        Object obj = getInputObject();
        if (isInstanceOf(obj)) {
            Integer id = (Integer) obj;
            String name = (String) model.getValueAt(table.getSelectedRow(), 1);
            String description = (String) model.getValueAt(table.getSelectedRow(), 2);
            BigDecimal price = convertToPrice((String) model.getValueAt(table.getSelectedRow(), 3));
            Product prod = new Product(id, name, description, price);
            System.out.println(prod);
            this.productController.update(prod);
        }
    }

    private Object getInputObject() {
        try {
            return model.getValueAt(table.getSelectedRow(), 0);
        } catch (Exception e) {
            showError("Please select a row", UNSELECTED_ROW_ERROR);
        }
        return null;
    }

    private void delete() {
        Object obj = getInputObject();
        if (isInstanceOf(obj)) {
            Integer id = (Integer) obj;
            this.productController.delete(id);
            model.removeRow(table.getSelectedRow());
            JOptionPane.showMessageDialog(this, "successfully deleted", "ok", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean save() {
        boolean invalidTxt = nameTxt.getText().isEmpty() || descriptionTxt.getText().isEmpty();
        BigDecimal price = convertToPrice(priceTxt.getText());
        if (invalidTxt) {
            showError("ERROR: must provider a name and description!", "Invalid input");
            return false;
        }
        if (price == null || price.compareTo(BigDecimal.ONE) < 0) {
            showError("ERROR: price must be greater than R$1.00. format.: 10.00", NUMBER_FORMAT_ERROR);
            return false;
        }

        Product product = new Product(nameTxt.getText(), descriptionTxt.getText(), price);
//		Category category = (Category) categoryCombo.getSelectedItem();
//		product.setCategoryId(category.getId());
        this.productController.save(product);
        JOptionPane.showMessageDialog(this, "successfully saved", "ok", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private BigDecimal convertToPrice(String str) {
        boolean validString = str != null && !str.isBlank() && !str.isEmpty();
        if (validString) {
            try {
                return new BigDecimal(str).setScale(2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                showError("ERROR: Must provide a valid number", NUMBER_FORMAT_ERROR);
                throw new IllegalArgumentException("Invalid price value");
            } finally {
                this.priceTxt.setText("");
            }
        }
        return null;
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

    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private boolean isInstanceOf(Object obj) {
        return obj instanceof Integer;
    }
}
