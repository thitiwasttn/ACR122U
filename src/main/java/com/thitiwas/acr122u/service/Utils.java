package com.thitiwas.acr122u.service;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Utils {


    public static void typeString(String string) {
        /*String test = "kgoerkgporkgoperkgpokgoer";
        StringSelection selection = new StringSelection(test);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);*/

        if (string == null) {
            return;
        }

        try {
            Robot robot = new Robot();

            for (int i = 0; i < string.length(); i++) {
                //Getting current char
                char c = string.charAt(i);

                //Pressing shift if it's uppercase
                if (Character.isUpperCase(c)) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }

                //Actually pressing the key
                robot.keyPress(Character.toUpperCase(c));
                robot.keyRelease(Character.toUpperCase(c));

                //Releasing shift if it's uppercase
                if (Character.isUpperCase(c)) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }

                //Optional delay to make it look like it's a human typing
                // Thread.sleep(20 + new Random().nextInt(150));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void textPastTest(String text) throws AWTException {
        // String text = "Hello World";
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }


}
