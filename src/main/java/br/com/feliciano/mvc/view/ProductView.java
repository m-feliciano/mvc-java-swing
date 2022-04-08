package br.com.feliciano.mvc.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import br.com.feliciano.mvc.controller.ProductController;
import br.com.feliciano.mvc.domain.entities.Product;
import br.com.feliciano.mvc.view.utils.BuilderLayout;
import br.com.feliciano.mvc.view.utils.InputValidation;
import br.com.feliciano.mvc.view.utils.Message;

public class ProductView extends JFrame {

    @Serial
    private static final long serialVersionUID = -3290552204306899863L;
    private static final Font GLOBAL = new Font("SansSerif", Font.PLAIN, 14);
    private final transient ProductController productController;
    private JTextField nameTxt;
    private JTextField descriptionTxt;
    private JTextField priceTxt;
    private JButton saveBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton cleanBtn;
    private JTable table;
    private DefaultTableModel model;

    public ProductView() {
        super("PRODUCT");
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

        editBtn.addActionListener(e -> {
            update();
            updateTable();
        });

        deleteBtn.addActionListener(e -> {
            delete();
            cleanTable();
            updateTable();
        });

        cleanBtn.addActionListener(e -> cleanInputs());
        // price input validation
        priceTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                boolean valid = ((evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') || evt.getKeyChar() == '.' || evt.getKeyChar() == '\b');
                if (!valid) {
                    Message.showError("Please enter numeric value only");
                    priceTxt.setText("");
                }
            }
        });

        populateTable();
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 680;
        final int CONTAINER_VERTICAL_SIZE = 530;
        buildInputs(container);
        buildButtons(container);
        buildTable(container);
        setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildInputs(Container container) {
        // NAME
        JLabel nameLabel = new JLabel("PRODUCT NAME");
        int[] nameLabelBounds = {45, 10, 240, 20};
        BuilderLayout.addLabel(container, nameLabel, nameLabelBounds);

        nameTxt = new JTextField();
        nameTxt.setFont(GLOBAL);
        nameTxt.setBounds(45, 30, 280, 25);
        container.add(nameTxt);
        // DESCRIPTION
        JLabel descriptionLabel = new JLabel("PRODUCT DESCRIPTION");
        int[] descriptionBounds = {45, 60, 240, 20};
        BuilderLayout.addLabel(container, descriptionLabel, descriptionBounds);

        descriptionTxt = new JTextField();
        descriptionTxt.setBounds(45, 80, 280, 25);
        descriptionTxt.setFont(GLOBAL);
        container.add(descriptionTxt);
        // PRICE
        JLabel priceLabel = new JLabel("PRODUCT PRICE");
        int[] priceBounds = {45, 110, 240, 20};
        BuilderLayout.addLabel(container, priceLabel, priceBounds);

        priceTxt = new JTextField();
        priceTxt.setBounds(45, 135, 280, 25);
        priceTxt.setFont(GLOBAL);
        container.add(priceTxt);

    }

    private void buildButtons(Container container) {
        saveBtn = new JButton("Save");
        int[] savaBounds = {45, 190, 80, 25};
        BuilderLayout.addButton(container, saveBtn, savaBounds, new Color(40, 167, 69));

        cleanBtn = new JButton("Clean");
        int[] cleanBounds = {135, 190, 80, 25};
        BuilderLayout.addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125));

        deleteBtn = new JButton("Delete");
        int[] deleteBtnBounds = {45, 440, 80, 25};
        BuilderLayout.addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69));

        editBtn = new JButton("Update");
        int[] editBtnBounds = {140, 440, 80, 25};
        BuilderLayout.addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7));

    }

    private void buildTable(Container container) {
        model = new DefaultTableModel() {
            @Serial
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
        int[] columnsWidth = {30, 150, 220, 70, 100};
        // Configures table's column width.
        int i = 0;
        for (int width : columnsWidth) {
            TableColumn column = table.getColumnModel().getColumn(i++);
            column.setMinWidth(width);
            column.setMaxWidth(width);
            column.setPreferredWidth(width);
        }
        table.setRowHeight(18);
        DefaultTableCellRenderer cellRendererCenter = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRendererRight = new DefaultTableCellRenderer();
        cellRendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
        cellRendererCenter.setEnabled(false);
        cellRendererCenter.setFocusable(false);
        cellRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRendererCenter);
        table.getColumnModel().getColumn(3).setCellRenderer(cellRendererRight);
        table.getColumnModel().getColumn(4).setCellRenderer(cellRendererCenter);
        table.setBounds(45, 250, 570, 170);
        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        table.setFont(GLOBAL);
        container.add(table);
    }

    private void update() {
        Object obj = getInputObject();
        if (obj instanceof Integer id) {
            String name = (String) model.getValueAt(table.getSelectedRow(), 1);
            String description = (String) model.getValueAt(table.getSelectedRow(), 2);
            BigDecimal price = convertToPrice(String.valueOf(model.getValueAt(table.getSelectedRow(), 3)));
            Product prod = new Product(id, name, description, price);
            this.productController.update(prod);
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
            productController.delete(id);
            Message.showMessage("successfully deleted product id: " + id);
            model.removeRow(table.getSelectedRow());
        }
    }

    private boolean save() {
        if (!InputValidation.validate(nameTxt, descriptionTxt)) {
            Message.showError("must provider a name and description!");
            return false;
        }
        BigDecimal price = convertToPrice(priceTxt.getText());
        if (price == null || !(price.compareTo(BigDecimal.ZERO) > 0)) {
            Message.showError("price must be greater than R$1.00. format.: 10.00");
            return false;
        }

        Product product = new Product(nameTxt.getText(), descriptionTxt.getText(), price);
        this.productController.save(product);
        Message.showMessage("successfully saved");
        return true;
    }

    private BigDecimal convertToPrice(String str) {
        if (!InputValidation.validate(str)) {
            return null;
        }
        try {
            return new BigDecimal(str).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            Message.showError("Must provide a valid number");
            throw new IllegalArgumentException("Invalid price value");
        }
    }

    private void populateTable() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<br.com.feliciano.mvc.domain.entities.Product> products = productController.list();
        products.forEach(p -> model.addRow(new Object[]{p.getId(), p.getName(), p.getDescription(), p.getPrice(), sdf.format(p.getRegisterDate())}));
    }

    private void cleanInputs() {
        this.nameTxt.setText("");
        this.descriptionTxt.setText("");
        this.priceTxt.setText("");
    }

    private void updateTable() {
        cleanTable();
        populateTable();
    }

    private void cleanTable() {
        model.getDataVector().clear();
    }
}
