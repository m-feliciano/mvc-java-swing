package view;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class CategoryFrame extends JFrame {

    @Serial
    private static final long serialVersionUID = -3290552204306899863L;

    public CategoryFrame() {
        super("CATEGORY CRUD");
        Container container = getContentPane();
        setLayout(null);
        buildFrame(container);
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 320;
        final int CONTAINER_VERTICAL_SIZE = 350;
        setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
