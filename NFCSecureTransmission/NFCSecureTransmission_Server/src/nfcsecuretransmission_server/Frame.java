package nfcsecuretransmission_server;

import java.awt.*;
import javax.swing.*;

public class Frame {
    JFrame fmr=new JFrame("NFC Secure Transmission - Server");
    JPanel panel=new JPanel();
    JButton startstop=new JButton("Avvia connessione");
    public Frame(){
        fmr.setBounds(50, 50, 400, 200);
        panel.setLayout(new GridBagLayout());
        panel.add(startstop, new GridBagConstraints());
        fmr.add(BorderLayout.CENTER,panel);
        fmr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fmr.setResizable(false);
        fmr.setVisible(true);
        
    }
}
