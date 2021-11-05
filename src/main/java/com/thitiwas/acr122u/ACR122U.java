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
            gui.setError();
        }
        log.debug("============= End Start =============");
    }
}
