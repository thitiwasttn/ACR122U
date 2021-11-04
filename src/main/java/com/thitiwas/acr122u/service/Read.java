package com.thitiwas.acr122u.service;

import lombok.extern.slf4j.Slf4j;
import org.nfctools.mf.MfException;
import org.nfctools.mf.card.MfCard;
import org.nfctools.spi.acs.Acr122ReaderWriter;
import org.nfctools.spi.acs.AcsTerminal;
import org.nfctools.spi.acs.CardResolver;

import javax.smartcardio.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Read {

    public void readingProcess() throws Exception {
        CardTerminal terminal = null;

        // show the list of available terminals
        TerminalFactory factory = TerminalFactory.getDefault();
        CardTerminals terminals1 = factory.terminals();

        List<CardTerminal> terminals = terminals1.list();
        log.debug("terminals :{}", terminals);

        if (terminals.isEmpty()) {
            throw new IOException("no device found");
        }

        for (CardTerminal cardTerminal : terminals) {
            if (cardTerminal.getName().contains("ACR122")) {
                terminal = cardTerminal;
                break;
            }
        }


        reading(terminal);
    }

    private void reading(CardTerminal terminal) throws CardException, MfException, AWTException {

        terminal.waitForCardPresent(0);
        Card card = terminal.connect("*");
        CardResolver cardResolver = new CardResolver();
        MfCard mfCard = cardResolver.resolvecard(card);

        AcsTerminal acsTerminal = new AcsTerminal();
        acsTerminal.setCardTerminal(terminal);
        Acr122ReaderWriter acr122ReaderWriter = new Acr122ReaderWriter(acsTerminal);


        String uid = readUid(acr122ReaderWriter, mfCard);
        log.debug("uid :{}", uid);
        Utils.textPastTest(uid);
        terminal.waitForCardAbsent(0);
        reading(terminal);
    }

    private String readUid(Acr122ReaderWriter acr122ReaderWriter, MfCard mfCard) throws CardException, AWTException {
       /* String uid = MifareUtils.dumpMifareClassic1KBlockV2(acr122ReaderWriter,
                mfCard,
                0,
                0,
                MifareUtils.COMMON_MIFARE_CLASSIC_1K_KEYS);*/

        String key = "FFFFFFFFFFFF";
        String uid = MifareUtils.readId(acr122ReaderWriter, mfCard, key);
        return uid;
    }
}