package com.thitiwas.acr122u.service;

import lombok.extern.slf4j.Slf4j;
import org.nfctools.mf.MfException;
import org.nfctools.mf.card.MfCard;
import org.nfctools.mf.classic.Key;
import org.nfctools.spi.acs.Acr122ReaderWriter;
import org.nfctools.spi.acs.AcsTerminal;
import org.nfctools.spi.acs.CardResolver;

import javax.smartcardio.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Slf4j
public class Read {

    public void readingProcessLightWeigh() throws IOException, CardException, AWTException {
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

        readingLightWeigh(terminal);




    }

    private void readingLightWeigh(CardTerminal terminal) throws CardException, AWTException {
        terminal.waitForCardPresent(0);

        Card card = terminal.connect("*");

        CardChannel channel = card.getBasicChannel();

        int key = 0xFF;
        int blockId = 0;
        int sectorId = 0;
        CommandAPDU loadKey = new CommandAPDU(255, 130, 0, 0, key);
        channel.transmit(loadKey);
        String keyUsed = "A";
        byte keyTypeToUse = (byte) (keyUsed.equals("A") ? 96 : 97);
        CommandAPDU auth = new CommandAPDU(255, 134, 0, blockId, new byte[]{1, 0, 0, keyTypeToUse, 0});
        channel.transmit(auth);
        CommandAPDU readBlock = new CommandAPDU(0xFF, 0xB0, 0x00, blockId, 0x10);
        ResponseAPDU transmit1 = channel.transmit(readBlock);


        String uid = convertByteArrayToHexString(transmit1.getData());
        log.debug("uid :{}" , uid);
        Utils.textPastTest(uid);
        terminal.waitForCardAbsent(0);
        readingLightWeigh(terminal);
    }

    public String convertByteArrayToHexString(byte[] b) {
        if (b != null) {
            StringBuilder s = new StringBuilder(2 * b.length);
            for (int i = 0; i < b.length; ++i) {
                final String t = Integer.toHexString(b[i]);
                final int l = t.length();
                if (l > 2) {
                    s.append(t.substring(l - 2));
                } else {
                    if (l == 1) {
                        s.append("0");
                    }
                    s.append(t);
                }
            }
            return s.toString().toUpperCase();
        } else {
            return "";
        }
    }


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
