package paint;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import net.miginfocom.swing.*;
import net.sevecek.util.*;
import sun.awt.geom.*;

public class HlavniOkno extends JFrame {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    JMenuBar menuBar1;
    JMenu menu1;
    JMenuItem menuOtevrit;
    JMenuItem menuUlozit;
    JMenuItem menuUlozitJako;
    JLabel labAktualniBarva;
    JLabel labBarva1;
    JLabel labBarva2;
    JLabel labBarva3;
    JLabel labBarva4;
    JLabel labBarva5;
    JLabel labObrazek;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    JPanel contentPane;
    MigLayout migLayoutManager;
    BufferedImage obrazek;
    File otevrenySoubor;
    Graphics2D stetec;
    int predchoziX = 0;
    int PredchoziY = 0;
    
    public HlavniOkno() {
        initComponents();

    }

    private void priKliknutiNaLabObrazek(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //System.out.println("["+x+","+y+"]");
        vyplnObrazek(obrazek,x,y,stetec.getColor());
        //stetec.drawLine(predchoziX,PredchoziY,x,y);
        labObrazek.repaint();
        //predchoziX = x;
        //PredchoziY = y;

    }
    private void priOtevreniOkna(WindowEvent e) {
        obrazek = new BufferedImage(1920, 1080, BufferedImage.TYPE_4BYTE_ABGR);
        //File soubor = new File("obrazek1.png");
        //File soubor = new File("C:\\Java-Training\\Projects\\Lekce11\\obrazek1.png");
        //nahrajObrazek(soubor);
        //labObrazek.setIcon(new ImageIcon(obrazek));
        stetec = (Graphics2D) obrazek.getGraphics();
        stetec.setColor(nahodnaBarva());
        stetec.setStroke(new BasicStroke(10));
        //stetec.drawLine(10,20,10,200);

    }
    private void priStiskuMenuOtevrit(ActionEvent e) {
        JFileChooser dialog;
        if (otevrenySoubor == null) {
            dialog = new JFileChooser(".");
        } else {
            dialog = new JFileChooser(otevrenySoubor.getParentFile());
        }
        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setMultiSelectionEnabled(false);
        dialog.addChoosableFileFilter(new FileNameExtensionFilter("Obrázky (*.png)", "png"));
        int vysledek = dialog.showOpenDialog(this);
        if (vysledek != JFileChooser.APPROVE_OPTION) {
            return;
        }

        otevrenySoubor = dialog.getSelectedFile();
        nahrajObrazek(otevrenySoubor);
    }
    //-------------------------------------------------------------------------

    /**
     * Vyplni <code>BufferedImage obrazek</code>
     * na pozicich <code>int x</code>, <code>int y</code>
     * barvou <code>Color barva</code>
     */
    public void vyplnObrazek(BufferedImage obrazek, int x, int y, Color barva) {
        if (barva == null) {
            barva = new Color(255, 255, 0);
        }

        // Zamez vyplnovani mimo rozsah
        if (x < 0 || x >= obrazek.getWidth() || y < 0 || y >= obrazek.getHeight()) {
            return;
        }

        WritableRaster pixely = obrazek.getRaster();
        int[] novyPixel = new int[] {barva.getRed(), barva.getGreen(), barva.getBlue(), barva.getAlpha()};
        int[] staryPixel = new int[] {255, 255, 255, 255};
        staryPixel = pixely.getPixel(x, y, staryPixel);

        // Pokud uz je pocatecni pixel obarven na cilovou barvu, nic nemen
        if (pixelyMajiStejnouBarvu(novyPixel, staryPixel)) {
            return;
        }

        // Zamez prebarveni cerne cary
        int[] cernyPixel = new int[] {0, 0, 0, staryPixel[3]};
        if (pixelyMajiStejnouBarvu(cernyPixel, staryPixel)) {
            return;
        }

        vyplnovaciSmycka(pixely, x, y, novyPixel, staryPixel);
    }

    /**
     * Provede skutecne vyplneni pomoci zasobniku
     */
    private void vyplnovaciSmycka(WritableRaster raster, int x, int y, int[] novaBarva, int[] nahrazovanaBarva) {
        Rectangle rozmery = raster.getBounds();
        int[] aktualniBarva = new int[] {255, 255, 255, 255};

        Deque<Point> zasobnik = new ArrayDeque<>(rozmery.width * rozmery.height);
        zasobnik.push(new Point(x, y));
        while (zasobnik.size() > 0) {
            Point point = zasobnik.pop();
            x = point.x;
            y = point.y;
            if (!pixelyMajiStejnouBarvu(raster.getPixel(x, y, aktualniBarva), nahrazovanaBarva)) {
                continue;
            }

            // Najdi levou zed, po ceste vyplnuj
            int levaZed = x;
            do {
                raster.setPixel(levaZed, y, novaBarva);
                levaZed--;
            }
            while (levaZed >= 0 && pixelyMajiStejnouBarvu(raster.getPixel(levaZed, y, aktualniBarva), nahrazovanaBarva));
            levaZed++;

            // Najdi pravou zed, po ceste vyplnuj
            int pravaZed = x;
            do {
                raster.setPixel(pravaZed, y, novaBarva);
                pravaZed++;
            }
            while (pravaZed < rozmery.width && pixelyMajiStejnouBarvu(raster.getPixel(pravaZed, y, aktualniBarva), nahrazovanaBarva));
            pravaZed--;

            // Pridej na zasobnik body nahore a dole
            for (int i = levaZed; i <= pravaZed; i++) {
                if (y > 0 && pixelyMajiStejnouBarvu(raster.getPixel(i, y - 1, aktualniBarva), nahrazovanaBarva)) {
                    if (!(i > levaZed && i < pravaZed
                            && pixelyMajiStejnouBarvu(raster.getPixel(i - 1, y - 1, aktualniBarva), nahrazovanaBarva)
                            && pixelyMajiStejnouBarvu(raster.getPixel(i + 1, y - 1, aktualniBarva), nahrazovanaBarva))) {
                        zasobnik.add(new Point(i, y - 1));
                    }
                }
                if (y < rozmery.height - 1 && pixelyMajiStejnouBarvu(raster.getPixel(i, y + 1, aktualniBarva), nahrazovanaBarva)) {
                    if (!(i > levaZed && i < pravaZed
                            && pixelyMajiStejnouBarvu(raster.getPixel(i - 1, y + 1, aktualniBarva), nahrazovanaBarva)
                            && pixelyMajiStejnouBarvu(raster.getPixel(i + 1, y + 1, aktualniBarva), nahrazovanaBarva))) {
                        zasobnik.add(new Point(i, y + 1));
                    }
                }
            }
        }
    }

    /**
     * Vrati true pokud RGB hodnoty v polich jsou stejne. Pokud jsou ruzne, vraci false
     */
    private boolean pixelyMajiStejnouBarvu(int[] barva1, int[] barva2) {
        return barva1[0] == barva2[0] && barva1[1] == barva2[1] && barva1[2] == barva2[2];
    }

    //-------------------------------------------------------------------------
    private void ulozObrazek(File soubor) {
        try {
            ImageIO.write(obrazek, "png", soubor);
        } catch (IOException ex) {
            throw new ApplicationPublicException(ex, "Nepodařilo se uložit obrázek mandaly do souboru " + soubor.getAbsolutePath());
        }


    }


    private void nahrajObrazek(File soubor) {
        try {
            // Bud jde vytvorit prazdny obrazek
            // obrazek = new BufferedImage(1920, 1080, BufferedImage.TYPE_4BYTE_ABGR);

            // Nebo nahrat existujici
            obrazek = ImageIO.read(soubor);
            labObrazek.setIcon(new ImageIcon(obrazek));
        } catch (IOException e1) {
            throw new ApplicationPublicException(e1, "Nepodařilo se nahrát obrázek mandaly ze souboru " + soubor.getAbsolutePath());
        }
    }

    private void ulozitJako() {
        JFileChooser dialog;
        dialog = new JFileChooser(".");

        int vysledek = dialog.showSaveDialog(this);
        if (vysledek != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File soubor = dialog.getSelectedFile();
        if (!soubor.getName().contains(".") && !soubor.exists()) {
            soubor = new File(soubor.getParentFile(), soubor.getName() + ".png");
        }
        if (soubor.exists()) {
            int potvrzeni = JOptionPane.showConfirmDialog(this, "Soubor " + soubor.getName() + " už existuje.\nChcete jej přepsat?", "Přepsat soubor?", JOptionPane.YES_NO_OPTION);
            if (potvrzeni == JOptionPane.NO_OPTION) {
                return;
            }
        }
        ulozObrazek(soubor);
    }

    public  Color nahodnaBarva() {
        int cervenaSlozka;
        int zelenaSlozka;
        int modraSlozka;
        Random generatorNahodnychCisel = new Random();
        int novaCervena = generatorNahodnychCisel.nextInt(256);
        int novaZelena = generatorNahodnychCisel.nextInt(256);
        int novaModra = generatorNahodnychCisel.nextInt(256);
        Color vyslednaBarva = new Color(novaCervena, novaZelena, novaModra);
        return vyslednaBarva;
    }



    private void labBarvaXMouseClicked(MouseEvent e) {
        JLabel vybranyLabel = (JLabel) e.getSource();
        Color vybranaBarva = vybranyLabel.getBackground();
        stetec.setColor(vybranaBarva);
        labBarva1.setText("");
        labBarva2.setText("");
        labBarva3.setText("");
        labBarva4.setText("");
        labBarva5.setText("");
        vybranyLabel.setText("X");
    }

    private void priStiskuMenuUlozit(ActionEvent e) {
        if (otevrenySoubor == null){
            ulozitJako();
        }
        else {
            ulozObrazek(otevrenySoubor);
        }
    }

    private void priStiskuMenuUlozitJako(ActionEvent e) {
        ulozitJako();
    }





    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuOtevrit = new JMenuItem();
        menuUlozit = new JMenuItem();
        menuUlozitJako = new JMenuItem();
        labAktualniBarva = new JLabel();
        labBarva1 = new JLabel();
        labBarva2 = new JLabel();
        labBarva3 = new JLabel();
        labBarva4 = new JLabel();
        labBarva5 = new JLabel();
        labObrazek = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Paint");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                priOtevreniOkna(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "insets rel,hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[grow,fill]",
            // rows
            "[]" +
            "[grow,fill]"));
        this.contentPane = (JPanel) this.getContentPane();
        this.contentPane.setBackground(this.getBackground());
        LayoutManager layout = this.contentPane.getLayout();
        if (layout instanceof MigLayout) {
            this.migLayoutManager = (MigLayout) layout;
        }

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Soubor");

                //---- menuOtevrit ----
                menuOtevrit.setText("Otev\u0159\u00edt");
                menuOtevrit.addActionListener(e -> priStiskuMenuOtevrit(e));
                menu1.add(menuOtevrit);

                //---- menuUlozit ----
                menuUlozit.setText("Ulo\u017eit");
                menuUlozit.addActionListener(e -> priStiskuMenuUlozit(e));
                menu1.add(menuUlozit);

                //---- menuUlozitJako ----
                menuUlozitJako.setText("Ulo\u017eit Jako");
                menuUlozitJako.addActionListener(e -> priStiskuMenuUlozitJako(e));
                menu1.add(menuUlozitJako);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //---- labAktualniBarva ----
        labAktualniBarva.setText("Aktu\u00e1ln\u00ed barva:");
        contentPane.add(labAktualniBarva, "cell 0 0");

        //---- labBarva1 ----
        labBarva1.setBackground(Color.red);
        labBarva1.setOpaque(true);
        labBarva1.setFont(labBarva1.getFont().deriveFont(labBarva1.getFont().getStyle() | Font.BOLD, labBarva1.getFont().getSize() + 5f));
        labBarva1.setHorizontalAlignment(SwingConstants.CENTER);
        labBarva1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labBarvaXMouseClicked(e);
            }
        });
        contentPane.add(labBarva1, "cell 1 0,wmin 30,hmin 30");

        //---- labBarva2 ----
        labBarva2.setBackground(Color.yellow);
        labBarva2.setOpaque(true);
        labBarva2.setFont(labBarva2.getFont().deriveFont(labBarva2.getFont().getStyle() | Font.BOLD, labBarva2.getFont().getSize() + 5f));
        labBarva2.setHorizontalAlignment(SwingConstants.CENTER);
        labBarva2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labBarvaXMouseClicked(e);
            }
        });
        contentPane.add(labBarva2, "cell 2 0,wmin 30,hmin 30");

        //---- labBarva3 ----
        labBarva3.setOpaque(true);
        labBarva3.setBackground(Color.blue);
        labBarva3.setFont(labBarva3.getFont().deriveFont(labBarva3.getFont().getStyle() | Font.BOLD, labBarva3.getFont().getSize() + 5f));
        labBarva3.setHorizontalAlignment(SwingConstants.CENTER);
        labBarva3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labBarvaXMouseClicked(e);
            }
        });
        contentPane.add(labBarva3, "cell 3 0,wmin 30,hmin 30");

        //---- labBarva4 ----
        labBarva4.setBackground(new Color(0, 204, 0));
        labBarva4.setFont(labBarva4.getFont().deriveFont(labBarva4.getFont().getStyle() | Font.BOLD, labBarva4.getFont().getSize() + 5f));
        labBarva4.setOpaque(true);
        labBarva4.setHorizontalAlignment(SwingConstants.CENTER);
        labBarva4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labBarvaXMouseClicked(e);
            }
        });
        contentPane.add(labBarva4, "cell 4 0,wmin 30,hmin 30");

        //---- labBarva5 ----
        labBarva5.setBackground(new Color(255, 102, 102));
        labBarva5.setFont(labBarva5.getFont().deriveFont(labBarva5.getFont().getStyle() | Font.BOLD, labBarva5.getFont().getSize() + 5f));
        labBarva5.setHorizontalAlignment(SwingConstants.CENTER);
        labBarva5.setOpaque(true);
        labBarva5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                labBarvaXMouseClicked(e);
            }
        });
        contentPane.add(labBarva5, "cell 5 0,wmin 30,hmin 30");

        //---- labObrazek ----
        labObrazek.setBackground(new Color(153, 153, 255));
        labObrazek.setOpaque(true);
        labObrazek.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                priKliknutiNaLabObrazek(e);
            }
        });
        contentPane.add(labObrazek, "cell 0 1 7 1");
        setSize(575, 475);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
