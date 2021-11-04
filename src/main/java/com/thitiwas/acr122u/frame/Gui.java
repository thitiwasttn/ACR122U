package com.thitiwas.acr122u.frame;

import com.thitiwas.acr122u.ACR122U;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Slf4j
public class Gui extends JFrame {
    TrayIcon trayIcon;
    SystemTray tray;

    private final JButton resetButton;

    public JButton getResetButton() {
        return resetButton;
    }

    public Gui() {

        System.out.println("creating instance");
        try {
            System.out.println("setting look and feel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to set LookAndFeel");
        }

        if (SystemTray.isSupported()) {
            System.out.println("system tray supported");
            tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage("logo-icon-01.svg");
            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting....");
                    System.exit(0);
                }
            };
            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);
            defaultItem = new MenuItem("Open");
            defaultItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                    setExtendedState(JFrame.NORMAL);
                }
            });
            popup.add(defaultItem);
            trayIcon = new TrayIcon(image, "SystemTray Demo", popup);
            trayIcon.setImageAutoSize(true);
        } else {
            System.out.println("system tray not supported");
        }
        addWindowStateListener(e -> {
            if (e.getNewState() == ICONIFIED) {
                try {
                    tray.add(trayIcon);
                    setVisible(false);
                    System.out.println("added to SystemTray");
                } catch (AWTException ex) {
                    System.out.println("unable to add to tray");
                }
            }
            if (e.getNewState() == 7) {
                try {
                    tray.add(trayIcon);
                    setVisible(false);
                    System.out.println("added to SystemTray");
                } catch (AWTException ex) {
                    System.out.println("unable to add to system tray");
                }
            }
            if (e.getNewState() == MAXIMIZED_BOTH) {
                tray.remove(trayIcon);
                setVisible(true);
                System.out.println("Tray icon removed");
            }
            if (e.getNewState() == NORMAL) {
                tray.remove(trayIcon);
                setVisible(true);
                System.out.println("Tray icon removed");
            }
        });
        // setIconImage(Toolkit.getDefaultToolkit().getImage("Duke256.png"));

        setSize(700, 300);
        setTitle("\u0e42\u0e1b\u0e23\u0e41\u0e01\u0e23\u0e21\u0e2d\u0e48\u0e32\u0e19\u0e1a\u0e31\u0e15\u0e23");

        resetButton = new JButton("");
        resetButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
        resetButton.setText("\u0e2a\u0e16\u0e32\u0e19\u0e30 \u0e1b\u0e23\u0e01\u0e15\u0e34");
        resetButton.setForeground(Color.GREEN);
        resetButton.setEnabled(false);
        getContentPane().add(resetButton);


        resetButton.addActionListener(new AbstractAction() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                restartApplication();
            }
        });


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void restartApplication() throws IOException, URISyntaxException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(ACR122U.class.getProtectionDomain().getCodeSource().getLocation().toURI());


        /* is it a jar file? */
        if (!currentJar.getName().endsWith(".jar"))
            return;

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }
}
