package view.utils;

import javax.swing.*;
import java.awt.*;

public class BuilderLayout {

    public static void addLabel(Container container, JLabel label, int[] bounds) {
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        container.add(label);
    }

    public static void addLabel(Container container, JLabel label, int[] bounds, Color background, Color foreground) {
        label.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        label.setBackground(background != null ? background : Color.WHITE);
        label.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(label);
    }

    public static void addButton(Container container, JButton button, int[] bounds, Color foreground) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(foreground != null ? foreground : Color.WHITE);
        button.setForeground(Color.WHITE);
        container.add(button);
    }

    public static void addButton(Container container, JButton button, int[] bounds) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        container.add(button);
    }
    public static void addButton(Container container, JButton button, int[] bounds, Color background, Color foreground) {
        button.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        button.setBackground(background != null ? background : Color.WHITE);
        button.setForeground(foreground != null ? foreground : Color.BLACK);
        container.add(button);
    }
}
