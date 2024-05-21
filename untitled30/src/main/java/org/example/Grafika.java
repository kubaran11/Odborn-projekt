package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grafika {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        JLabel nameLabel = new JLabel("jmeno:");
        JTextField nameField = new JTextField();

        JLabel chipDateLabel = new JLabel("Chip datum:");
        JTextField chipDateField = new JTextField();

        JLabel chipCodeLabel = new JLabel("Chip kod:");
        JTextField chipCodeField = new JTextField();

        JButton addButton = new JButton("dostat zaměstnance ");
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(chipDateLabel);
        panel.add(chipDateField);
        panel.add(chipCodeLabel);
        panel.add(chipCodeField);
        panel.add(new JLabel(""));
        panel.add(addButton);
        frame.add(panel);
        frame.setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String chipDate = chipDateField.getText();
                String chipCode = chipCodeField.getText();
                DBConnect.insertEmployeeWithChip(name, chipDate, chipCode);

                nameField.setText("");
                chipDateField.setText("");
                chipCodeField.setText("");

                JOptionPane.showMessageDialog(frame, "byl přdán zaměstnanec");
            }
        });
    }
}
