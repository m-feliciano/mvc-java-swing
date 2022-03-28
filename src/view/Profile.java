package view;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private static final long serialVersionUID = -3290552204306899863L;

    JButton saveBtn;
    private JTextField userIdTxt;
    private JTextField usernameTxt;
    private JTextField emailTxt;
    private JTextField passwordTxt;
    private JTextField cepTxt;
    private JTextField streetTxt;
    private JTextField numberTxt;

    public Login() {
        super("CRUD LOGIN");
        Container container = getContentPane();
        setLayout(null);
        buildFrame(container);
        saveBtn.addActionListener(e -> {
        });
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 320;
        final int CONTAINER_VERTICAL_SIZE = 450;
        final int PADDING_LEFT = 40;
        buildButtons(container, PADDING_LEFT);
        buildInputs(container, PADDING_LEFT);
        setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildButtons(Container container, int PADDING_LEFT) {
        saveBtn = new JButton("Update");
        int[] logoutBtnBounds = {PADDING_LEFT, 360, 80, 25};
        addButton(container, saveBtn, logoutBtnBounds, new Color(255, 197, 7), Color.WHITE);
    }

    private void addButton(Container container, JButton button, int[] bounds, Color background, Color foreground) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(background != null ? background : Color.WHITE);
        button.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(button);
    }

    private void buildInputs(Container container, int PADDING_LEFT) {
        // USERNAME
        JLabel usernameLabel = new JLabel("USERNAME");
        int[] usernameBounds = {PADDING_LEFT, 20, 220, 25};
        addLabel(container, usernameLabel, usernameBounds, null, Color.BLACK);

        usernameTxt = new JTextField();
        usernameTxt.setBounds(PADDING_LEFT, 45, 220, 25);
        container.add(usernameTxt);

        // EMAIL
        JLabel emailLabel = new JLabel("EMAIL");
        int[] priceBounds = {PADDING_LEFT, 80, 240, 25};
        addLabel(container, emailLabel, priceBounds, null, Color.BLACK);

        emailTxt = new JTextField();
        emailTxt.setBounds(PADDING_LEFT, 105, 220, 25);
        container.add(emailTxt);

        // ADDRESS INFO
        JLabel infoLabel = new JLabel("ADDRESS");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        int[] infoBounds = {PADDING_LEFT + 70, 140, 100, 30};
        addLabel(container, infoLabel, infoBounds, null, new Color(179, 179,179));

        // ADDRESS
        JLabel cepLabel = new JLabel("CEP");
        int[] cepBounds = {PADDING_LEFT, 170, 220, 25};
        addLabel(container, cepLabel, cepBounds, null, Color.BLACK);

        cepTxt = new JTextField();
        cepTxt.setBounds(PADDING_LEFT, 195, 220, 25);
        container.add(cepTxt);

        // NUMBER
        JLabel numberLabel = new JLabel("NUMBER");
        int[] numberBounds = {PADDING_LEFT, 230, 220, 25};
        addLabel(container, numberLabel, numberBounds, null, Color.BLACK);

        numberTxt = new JTextField();
        numberTxt.setBounds(PADDING_LEFT, 255, 220, 25);
        container.add(numberTxt);

        // STREET
        JLabel streetLabel = new JLabel("STREET");
        int[] streetBounds = {PADDING_LEFT, 290, 220, 25};
        addLabel(container, streetLabel, streetBounds, null, Color.BLACK);

        streetTxt = new JTextField();
        streetTxt.setEditable(false);
        streetTxt.setBounds(PADDING_LEFT, 315, 220, 25);
        container.add(streetTxt);
    }

    private void addLabel(Container container, JLabel label, int[] bounds, Color background, Color foreground) {
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setBackground(background != null ? background : Color.WHITE);
        label.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(label);
    }

}
