package view;

import controller.CategoryController;
import entities.Category;
import view.utils.BuilderLayout;
import view.utils.Message;
import view.utils.Validation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.Serial;
import java.util.List;

public class CategoryFrame extends JFrame {

    @Serial
    private static final long serialVersionUID = -3290552204306899863L;
    private static final Font GLOBAL = new Font("SansSerif", Font.PLAIN, 14);
    private final transient CategoryController categoryController;
    private JTextField nameTxt;
    private JButton saveBtn;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton cleanBtn;
    private JTable table;
    private DefaultTableModel model;

    public CategoryFrame() {
        super("Category CRUD");
        categoryController = new CategoryController();
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
        populateTable();
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 480;
        final int CONTAINER_VERTICAL_SIZE = 330;
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
        JLabel nameLabel = new JLabel("CATEGORY NAME");
        int[] nameLabelBounds = {45, 10, 200, 20};
        BuilderLayout.addLabel(container, nameLabel, nameLabelBounds);

        nameTxt = new JTextField();
        nameTxt.setFont(GLOBAL);
        nameTxt.setBounds(45, 30, 280, 25);
        container.add(nameTxt);
    }

    private void buildButtons(Container container) {
        saveBtn = new JButton("Save");
        int[] savaBounds = {355, 30, 80, 25};
        BuilderLayout.addButton(container, saveBtn, savaBounds, new Color(40, 167, 69));

        cleanBtn = new JButton("Clean");
        int[] cleanBounds = {355, 60, 80, 25};
        BuilderLayout.addButton(container, cleanBtn, cleanBounds, new Color(108, 117, 125));

        deleteBtn = new JButton("Delete");
        int[] deleteBtnBounds = {355, 110, 80, 25};
        BuilderLayout.addButton(container, deleteBtn, deleteBtnBounds, new Color(220, 53, 69));

        editBtn = new JButton("Update");
        int[] editBtnBounds = {355, 140, 80, 25};
        BuilderLayout.addButton(container, editBtn, editBtnBounds, new Color(255, 197, 7));

    }

    private void buildTable(Container container) {
        model = new DefaultTableModel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        table = new JTable(model);
        model.addColumn("Category Id");
        model.addColumn("Category Name");
        // Defines table's column width.
        int[] columnsWidth = {30, 250};
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
        cellRendererCenter.setHorizontalAlignment(SwingConstants.CENTER);
        cellRendererCenter.setEnabled(false);
        cellRendererCenter.setFocusable(false);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRendererCenter);
        table.setBounds(45, 90, 280, 160);
        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        table.setFont(GLOBAL);
        container.add(table);
    }

    private void update() {
        Object obj = getInputObject();
        if (obj instanceof Integer id) {
            String name = (String) model.getValueAt(table.getSelectedRow(), 1);
            Category category = new Category(id, name);
            this.categoryController.update(category);
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
            categoryController.delete(id);
            Message.showMessage("successfully deleted category id: " + id);
            model.removeRow(table.getSelectedRow());
        }
    }

    private boolean save() {
        if (!Validation.validate(nameTxt)) {
            Message.showError("must provider a name!");
            return false;
        }
        Category category = new Category(nameTxt.getText());
        this.categoryController.save(category);
        Message.showMessage("successfully saved");
        return true;
    }

    private void populateTable() {
        List<Category> products = categoryController.list();
        products.forEach(p -> model.addRow(new Object[]{p.getId(), p.getName()}));
    }

    private void cleanInputs() {
        this.nameTxt.setText("");
    }

    private void updateTable() {
        cleanTable();
        populateTable();
    }

    private void cleanTable() {
        model.getDataVector().clear();
    }
}
