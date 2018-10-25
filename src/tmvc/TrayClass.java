package tmvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrayClass {

    static Image image = //new ImageIcon("C:\\Users\\Usuario\\eclipse-workspace\\Console\\icon").getImage();
            Toolkit.getDefaultToolkit().getImage("C:/Users/Usuario/eclipse-workspace/Console/icon/icons8-console-16.png");
    static TrayIcon trayIcon;
    public JFrame frame;
    public boolean finish = false;

    public TrayClass() {
        show();
    }

    public void getConsole(JFrame console){
        frame = console;
    }

    private void show() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        trayIcon = new TrayIcon(image, "Log Console");

        final PopupMenu popup = new PopupMenu();

        MenuItem aboutItem = new MenuItem("About");
        MenuItem exitItem = new MenuItem("Exit");
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Log Console\nAuthor by:\nTomás Amado, Carlos Di Mateo, María E. Martinez & Valeria Salas");
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finish=true;
                System.exit(0);
            }
        });

        trayIcon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setExtendedState(JFrame.NORMAL);
                frame.toFront();
                frame.setVisible(true);
                System.out.println("Click");
            }
        });

        trayIcon.setPopupMenu(popup);

        final SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}
