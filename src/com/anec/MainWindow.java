package com.anec;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    private final static String VERSION = "0.1.2";

    private final JTextField textFieldValue = new JTextField();
    private final JTextField textFieldResult = new JTextField();

    private final JRadioButton radioButtonKilowattToMetricHorsepower = new JRadioButton("Киловатты в лошадиные силы");
    private final JRadioButton radioButtonMetricHorsepowerToKilowatt = new JRadioButton("Лошадиные силы в киловатты");

    private final JButton buttonConvert = new JButton("Перевести");
    private final JButton buttonAbout = new JButton("О программе");

    public MainWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("PowerConverter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(490, 150);
        setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2,5, 5));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        textFieldValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonConvert.doClick();
                }
            }
        });
        container.add(textFieldValue);

        textFieldResult.setEditable(false);
        container.add(textFieldResult);

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonKilowattToMetricHorsepower);
        group.add(radioButtonMetricHorsepowerToKilowatt);

        radioButtonKilowattToMetricHorsepower.setSelected(true);
        container.add(radioButtonKilowattToMetricHorsepower);
        container.add(radioButtonMetricHorsepowerToKilowatt);

        buttonConvert.addActionListener(e -> {
            String value = textFieldValue.getText().contains(",") ?
                    textFieldValue.getText().replace(',', '.').trim() :
                    textFieldValue.getText().trim();

            double result;

            try {
                result = radioButtonKilowattToMetricHorsepower.isSelected() ?
                        ConvertPower.kilowattsToMetricHorsepower(Double.parseDouble(value)) :
                        ConvertPower.metricHorsepowerToKilowatts(Double.parseDouble(value));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Нужно вводить число",
                        this.getTitle(), JOptionPane.ERROR_MESSAGE);
                return;
            }

            String stringResult = textFieldValue.getText().contains(".") ? String.valueOf(result) :
                    String.valueOf(result).replace('.', ',');

            textFieldResult.setText(stringResult.endsWith(",0") || stringResult.endsWith(".0") ?
                    stringResult.replace((stringResult.contains(".") ? ".0" : ",0"), "") :
                    stringResult);
        });
        container.add(buttonConvert);

        buttonAbout.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Эта программа предназначена для конвертации\n" +
                        "                                  мощности\n\n" +
                        "                                Версия: " + VERSION + "\n\n" +
                        "                              Created by Anec",
                "О программе", JOptionPane.PLAIN_MESSAGE));
        container.add(buttonAbout);

        for (Component c : this.getComponents()) {
            SwingUtilities.updateComponentTreeUI(c);
        }
    }
}
