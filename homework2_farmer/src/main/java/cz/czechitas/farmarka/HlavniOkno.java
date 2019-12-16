package cz.czechitas.farmarka;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;

public class HlavniOkno extends JFrame {

    /**
     * per summer season
     */
    int numberOfYoungPerGoose = 15;
    /**
     * per summer season
     */
    int numberOfYoungPerRabbit = 40;
    /**
     * kilograms, per Rabbit
     */
    double ammountOfCarrotPerDay = 0.5;
    /**
     * kilograms, per Goose
     */
    double ammountOfGrainPerDay = 0.25;
    /**
     * days
     */
    double lengthOfWinter = 183.0;
    double kilogramsOfCarrotPerRow = 5.0;
    double kilogramsOfGrainPerRow = 2.0;

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    JLabel labGeese;
    JLabel labelRabbits;
    JLabel labGeeseMale;
    JTextField editGeeseMale;
    JLabel labRabbitsMale;
    JTextField editRabbitsMale;
    JLabel labGeeseFem;
    JTextField editGeeseFem;
    JLabel labRabbitsFem;
    JTextField editRabbitsFem;
    JButton btn1Calculate;
    JLabel labTotalNumberOfAnimals;
    JLabel labNumberOfGeese;
    JTextField editLNumberOfGeese;
    JLabel labNumberOfRabbits;
    JTextField editLNumberOfRabbits;
    JLabel labRequiredFood;
    JLabel labAmmountOfGrain;
    JTextField editAmmountOfGrain;
    JLabel labKg;
    JLabel labAmmountOfCarrot;
    JTextField editAmmountOfCarrot;
    JLabel labKg2;
    JLabel labNumberOfRowsGrain;
    JTextField editLNumberOfRowsGrain;
    JLabel labNumberOfRowsCarrot;
    JTextField editLNumberOfRowsCarrot;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    JPanel contentPane;
    MigLayout migLayoutManager;

    public void testInputIsInt(JTextField editField, JLabel labName) {

        try {
            int d = Integer.parseInt(editField.getText());
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Chybný údaj: " + labName.getText() + " " + editField.getText(), "Chyba", JOptionPane.ERROR_MESSAGE);
            throw e;
        }

    }

    public int calculateNumberOfAnimals(JTextField editMales, JTextField editFemales, int numberOfYoung) {
        /** Calculates total number of animals after one summer season, considers procreation.*/
        int males = Integer.parseInt(editMales.getText());
        int females = Integer.parseInt(editFemales.getText());
        int totalNumberOfAnimals;
        if (males >= 1) {
            totalNumberOfAnimals = females * numberOfYoung + females + males;
        } else {
            totalNumberOfAnimals = females;
        }
        return totalNumberOfAnimals;
    }

    public double calculateAmmountOfFood(int animals, double foodPerAnimal, double lengthOfSeason) {
        /** Calculates ammount of food needed for one season.*/
        double ammountOfFood = Math.round(animals * foodPerAnimal * lengthOfSeason * 10.0) / 10.0;
        return ammountOfFood;
    }

    public double calculateNumberOfRows(double ammountOfFood, double yieldPerRow) {
        double numberOfRows = Math.round(ammountOfFood / yieldPerRow * 10.0) / 10.0;
        return numberOfRows;
    }

    public HlavniOkno() {
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        try {
            testInputIsInt(editGeeseMale, labGeeseMale);
            testInputIsInt(editGeeseFem, labGeeseFem);
            testInputIsInt(editRabbitsMale, labRabbitsMale);
            testInputIsInt(editRabbitsFem, labRabbitsFem);
        } catch (NumberFormatException | NullPointerException e1) {
            return;
        }

        int numberOfGeesePerSeason = calculateNumberOfAnimals(editGeeseMale, editGeeseFem, numberOfYoungPerGoose);
        editLNumberOfGeese.setText(Integer.toString(numberOfGeesePerSeason));
        int numberOfRabbitsPerSeason = calculateNumberOfAnimals(editRabbitsMale, editRabbitsFem, numberOfYoungPerRabbit);
        editLNumberOfRabbits.setText(Integer.toString(numberOfRabbitsPerSeason));

        double ammountOfCarrotPerWinter = calculateAmmountOfFood(numberOfRabbitsPerSeason, ammountOfCarrotPerDay, lengthOfWinter);
        editAmmountOfCarrot.setText(Double.toString(ammountOfCarrotPerWinter));

        double numberOfRowsCarrot = calculateNumberOfRows(ammountOfCarrotPerWinter, kilogramsOfCarrotPerRow);
        editLNumberOfRowsCarrot.setText(Double.toString(numberOfRowsCarrot));

        double ammountOfGrainPerWinter = calculateAmmountOfFood(numberOfGeesePerSeason, ammountOfGrainPerDay, lengthOfWinter);
        editAmmountOfGrain.setText(Double.toString(ammountOfGrainPerWinter));

        double numberOfRowsGrain = calculateNumberOfRows(ammountOfGrainPerWinter, kilogramsOfGrainPerRow);
        editLNumberOfRowsGrain.setText(Double.toString(numberOfRowsGrain));

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        labGeese = new JLabel();
        labelRabbits = new JLabel();
        labGeeseMale = new JLabel();
        editGeeseMale = new JTextField();
        labRabbitsMale = new JLabel();
        editRabbitsMale = new JTextField();
        labGeeseFem = new JLabel();
        editGeeseFem = new JTextField();
        labRabbitsFem = new JLabel();
        editRabbitsFem = new JTextField();
        btn1Calculate = new JButton();
        labTotalNumberOfAnimals = new JLabel();
        labNumberOfGeese = new JLabel();
        editLNumberOfGeese = new JTextField();
        labNumberOfRabbits = new JLabel();
        editLNumberOfRabbits = new JTextField();
        labRequiredFood = new JLabel();
        labAmmountOfGrain = new JLabel();
        editAmmountOfGrain = new JTextField();
        labKg = new JLabel();
        labAmmountOfCarrot = new JLabel();
        editAmmountOfCarrot = new JTextField();
        labKg2 = new JLabel();
        labNumberOfRowsGrain = new JLabel();
        editLNumberOfRowsGrain = new JTextField();
        labNumberOfRowsCarrot = new JLabel();
        editLNumberOfRowsCarrot = new JTextField();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Farm\u00e1\u0159ka");
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "insets rel,hidemode 3",
                // columns
                "[fill]" +
                        "[grow,fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[grow,fill]" +
                        "[fill]",
                // rows
                "[fill]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));
        this.contentPane = (JPanel) this.getContentPane();
        this.contentPane.setBackground(this.getBackground());
        LayoutManager layout = this.contentPane.getLayout();
        if (layout instanceof MigLayout) {
            this.migLayoutManager = (MigLayout) layout;
        }

        //---- labGeese ----
        labGeese.setText("Husy");
        labGeese.setHorizontalAlignment(SwingConstants.RIGHT);
        labGeese.setFont(new Font("Segoe UI", Font.BOLD, 17));
        contentPane.add(labGeese, "cell 0 0 2 1,alignx center,growx 0");

        //---- labelRabbits ----
        labelRabbits.setText("Kr\u00e1l\u00edci");
        labelRabbits.setFont(new Font("Segoe UI", Font.BOLD, 17));
        contentPane.add(labelRabbits, "cell 5 0 2 1,alignx center,growx 0");

        //---- labGeeseMale ----
        labGeeseMale.setText("Po\u010det samc\u016f:");
        labGeeseMale.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labGeeseMale, "cell 0 1");

        //---- editGeeseMale ----
        editGeeseMale.setColumns(10);
        contentPane.add(editGeeseMale, "cell 1 1");

        //---- labRabbitsMale ----
        labRabbitsMale.setText("Po\u010det samc\u016f:");
        labRabbitsMale.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labRabbitsMale, "cell 5 1");

        //---- editRabbitsMale ----
        editRabbitsMale.setColumns(10);
        contentPane.add(editRabbitsMale, "cell 6 1");

        //---- labGeeseFem ----
        labGeeseFem.setText("Po\u010det samic:");
        labGeeseFem.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labGeeseFem, "cell 0 2");

        //---- editGeeseFem ----
        editGeeseFem.setColumns(10);
        contentPane.add(editGeeseFem, "cell 1 2");

        //---- labRabbitsFem ----
        labRabbitsFem.setText("Po\u010det samic:");
        labRabbitsFem.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labRabbitsFem, "cell 5 2");

        //---- editRabbitsFem ----
        editRabbitsFem.setColumns(10);
        contentPane.add(editRabbitsFem, "cell 6 2");

        //---- btn1Calculate ----
        btn1Calculate.setText("Vypo\u010d\u00edtat");
        btn1Calculate.addActionListener(e -> button1ActionPerformed(e));
        contentPane.add(btn1Calculate, "cell 0 3 8 1,alignx center,growx 0");

        //---- labTotalNumberOfAnimals ----
        labTotalNumberOfAnimals.setText("Celkov\u00fd po\u010det po sezon\u011b p\u0159ed zimou");
        labTotalNumberOfAnimals.setHorizontalAlignment(SwingConstants.LEFT);
        labTotalNumberOfAnimals.setFont(new Font("Segoe UI", Font.BOLD, 17));
        contentPane.add(labTotalNumberOfAnimals, "cell 0 5 6 1");

        //---- labNumberOfGeese ----
        labNumberOfGeese.setText("Po\u010det hus:");
        labNumberOfGeese.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labNumberOfGeese, "cell 0 6");

        //---- editLNumberOfGeese ----
        editLNumberOfGeese.setEditable(false);
        editLNumberOfGeese.setColumns(10);
        contentPane.add(editLNumberOfGeese, "cell 1 6");

        //---- labNumberOfRabbits ----
        labNumberOfRabbits.setText("Po\u010det kr\u00e1l\u00edk\u016f:");
        labNumberOfRabbits.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labNumberOfRabbits, "cell 5 6");

        //---- editLNumberOfRabbits ----
        editLNumberOfRabbits.setColumns(10);
        editLNumberOfRabbits.setEditable(false);
        contentPane.add(editLNumberOfRabbits, "cell 6 6");

        //---- labRequiredFood ----
        labRequiredFood.setText("Pot\u0159ebn\u00e9 potraviny");
        labRequiredFood.setHorizontalAlignment(SwingConstants.LEFT);
        labRequiredFood.setFont(new Font("Segoe UI", Font.BOLD, 17));
        contentPane.add(labRequiredFood, "cell 0 8 2 1");

        //---- labAmmountOfGrain ----
        labAmmountOfGrain.setText("Hmotnost p\u0161enice:");
        labAmmountOfGrain.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labAmmountOfGrain, "cell 0 9");

        //---- editAmmountOfGrain ----
        editAmmountOfGrain.setColumns(10);
        editAmmountOfGrain.setEditable(false);
        contentPane.add(editAmmountOfGrain, "cell 1 9");

        //---- labKg ----
        labKg.setText("kg");
        contentPane.add(labKg, "cell 2 9 2 1");

        //---- labAmmountOfCarrot ----
        labAmmountOfCarrot.setText("Hmotnost mrkve:");
        labAmmountOfCarrot.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labAmmountOfCarrot, "cell 5 9");

        //---- editAmmountOfCarrot ----
        editAmmountOfCarrot.setColumns(10);
        editAmmountOfCarrot.setEditable(false);
        contentPane.add(editAmmountOfCarrot, "cell 6 9");

        //---- labKg2 ----
        labKg2.setText("kg");
        contentPane.add(labKg2, "cell 7 9");

        //---- labNumberOfRowsGrain ----
        labNumberOfRowsGrain.setText("Po\u010det \u0159\u00e1dk\u016f p\u0161enice:");
        labNumberOfRowsGrain.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labNumberOfRowsGrain, "cell 0 10");

        //---- editLNumberOfRowsGrain ----
        editLNumberOfRowsGrain.setColumns(10);
        editLNumberOfRowsGrain.setEditable(false);
        contentPane.add(editLNumberOfRowsGrain, "cell 1 10");

        //---- labNumberOfRowsCarrot ----
        labNumberOfRowsCarrot.setText("Po\u010det \u0159\u00e1dk\u016f mrkve:");
        labNumberOfRowsCarrot.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(labNumberOfRowsCarrot, "cell 5 10");

        //---- editLNumberOfRowsCarrot ----
        editLNumberOfRowsCarrot.setColumns(10);
        editLNumberOfRowsCarrot.setEditable(false);
        contentPane.add(editLNumberOfRowsCarrot, "cell 6 10");
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
