package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.Serial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.AddressController;
import controller.UserController;
import entities.Address;
import entities.User;
import view.utils.BuilderLayout;
import view.utils.Message;

public class Profile extends JFrame {

    @Serial
    private static final long serialVersionUID = -3290552204306899863L;
    private final transient UserController userController;
    private final transient AddressController addressController;

    private JButton updateBtn;
    private JTextField userIdTxt;
    private JTextField usernameTxt;
    private JTextField emailTxt;
    private JTextField stateTxt;
    private JTextField cepTxt;
    private JTextField placeTxt;
    private JTextField numberTxt;

    public Profile() {
        super("PROFILE");
        addressController = new AddressController();
        userController = new UserController();
        Container container = getContentPane();
        setLayout(null);
        buildFrame(container);

        updateBtn.addActionListener(e -> {
            updateAddress();
            updateUser();
            updateTable();
            Message.showMessage("successfully updated");
        });
        populateTable();
    }

    private void updateTable() {
        populateTable();
    }

    private void updateAddress() {
        String cep = cepTxt.getText();
        String number = numberTxt.getText();
        Integer userId = Integer.parseInt(userIdTxt.getText());
        Address address = new Address(cep, number, userId);
        this.addressController.update(address);
    }

    private void updateUser() {
        String username = usernameTxt.getText();
        String email = emailTxt.getText();
        Integer userId = Integer.parseInt(userIdTxt.getText());
        User user = new User(username, email, userId);
        this.userController.update(user);
    }

    private void buildFrame(Container container) {
        final int CONTAINER_HORIZONTAL_SIZE = 320;
        final int CONTAINER_VERTICAL_SIZE = 510;
        buildButtons(container);
        buildInputs(container);
        setSize(CONTAINER_HORIZONTAL_SIZE, CONTAINER_VERTICAL_SIZE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void buildButtons(Container container) {
        updateBtn = new JButton("Update");
        int[] logoutBtnBounds = {40, 420, 80, 25};
        addButton(container, updateBtn, logoutBtnBounds, new Color(255, 197, 7), Color.WHITE);
    }

    private void addButton(Container container, JButton button, int[] bounds, Color background, Color foreground) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(background != null ? background : Color.WHITE);
        button.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(button);
    }

    private void buildInputs(Container container) {
        userIdTxt = new JTextField();
        // USERNAME
        JLabel usernameLabel = new JLabel("USERNAME");
        int[] usernameBounds = {40, 20, 220, 25};
        BuilderLayout.addLabel(container, usernameLabel, usernameBounds);

        usernameTxt = new JTextField();
        usernameTxt.setBounds(40, 45, 220, 25);
        container.add(usernameTxt);

        // EMAIL
        JLabel emailLabel = new JLabel("EMAIL");
        int[] priceBounds = {40, 80, 240, 25};
        BuilderLayout.addLabel(container, emailLabel, priceBounds);

        emailTxt = new JTextField();
        emailTxt.setBounds(40, 105, 220, 25);
        container.add(emailTxt);

        // ADDRESS INFO
        JLabel infoLabel = new JLabel("ADDRESS");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        int[] infoBounds = {40 + 70, 140, 100, 30};
        BuilderLayout.addLabel(container, infoLabel, infoBounds, null, new Color(179, 179, 179));

        // ADDRESS
        JLabel cepLabel = new JLabel("CEP");
        int[] cepBounds = {40, 170, 220, 25};
        BuilderLayout.addLabel(container, cepLabel, cepBounds);

        cepTxt = new JTextField();
        cepTxt.setBounds(40, 195, 220, 25);
        container.add(cepTxt);

        // NUMBER
        JLabel numberLabel = new JLabel("NUMBER");
        int[] numberBounds = {40, 230, 220, 25};
        BuilderLayout.addLabel(container, numberLabel, numberBounds);

        numberTxt = new JTextField();
        numberTxt.setBounds(40, 255, 220, 25);
        container.add(numberTxt);

        // STREET
        JLabel streetLabel = new JLabel("STREET");
        int[] streetBounds = {40, 290, 220, 25};
        BuilderLayout.addLabel(container, streetLabel, streetBounds);

        placeTxt = new JTextField();
        placeTxt.setEditable(false);
        placeTxt.setBounds(40, 315, 220, 25);
        container.add(placeTxt);

        // LOCAL STATE
        JLabel stateLabel = new JLabel("LOCAL");
        int[] stateBounds = {40, 350, 220, 25};
        BuilderLayout.addLabel(container, stateLabel, stateBounds);

        stateTxt = new JTextField();
        stateTxt.setEditable(false);
        stateTxt.setBounds(40, 375, 220, 25);
        container.add(stateTxt);
    }

    private void populateTable() {
        User user = userController.findById(1);
        Address address = addressController.findById(1);
        userIdTxt.setText(user.getId().toString());
        usernameTxt.setText(user.getUsername());
        emailTxt.setText(user.getEmail());
        cepTxt.setText(address.getCep());
        numberTxt.setText(address.getNumber());
        placeTxt.setText(address.getPlace());
        stateTxt.setText(address.getLocal());
    }

}
