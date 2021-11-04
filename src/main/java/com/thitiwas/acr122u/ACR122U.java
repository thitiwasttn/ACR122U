package com.thitiwas.acr122u;

import com.thitiwas.acr122u.frame.Gui;
import com.thitiwas.acr122u.service.Read;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class ACR122U {
    public static void main(String[] args) {
        log.debug("============= System Start =============");
        Gui gui = new Gui();
        Read read = new Read();
        try {
            read.readingProcessLightWeigh();
        } catch (Exception e) {
            e.printStackTrace();
            gui.getResetButton().setText("\u0e40\u0e01\u0e34\u0e14\u0e02\u0e49\u0e2d\u0e1c\u0e34\u0e14\u0e1e\u0e25\u0e32\u0e14 \u0e01\u0e14\u0e17\u0e35\u0e48\u0e19\u0e35\u0e48\u0e40\u0e1e\u0e37\u0e48\u0e2d\u0e41\u0e01\u0e49\u0e44\u0e02");
            gui.getResetButton().setForeground(Color.RED);
            gui.getResetButton().setEnabled(true);
        }
        log.debug("============= End Start =============");
    }
}
