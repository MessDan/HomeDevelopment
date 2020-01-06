package nfcsecuretransmission_server;

import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.*;
import javax.swing.JOptionPane;

public class NFCSecureTransmission_Server implements ActionListener,Runnable {
    public DB_connection dbconn;
    static Frame frame;
    SendToClient sendclient;
    public NFCSecureTransmission_Server() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException{
        frame=new Frame();
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException {
        NFCSecureTransmission_Server server=new NFCSecureTransmission_Server();
        frame.startstop.addActionListener(server);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread Server=new Thread(this);
        if(e.getActionCommand().equals("Avvia connessione")){
            try {
                dbconn=new DB_connection();
                new Grp_Manager(dbconn.nfc_stm);
                Server.start();
                frame.startstop.setText("Ferma connessione");
            } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException ex) {
                Logger.getLogger(NFCSecureTransmission_Server.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Impossibile stabilire una connessione");
                frame.startstop.setText("Avvia connessione");
            }
        }else if(e.getActionCommand().equals("Ferma connessione"))
        {
            try {
                dbconn.loc_conn.close();
                dbconn.nfc_conn.close();
                //sendclient.server.close();
                Server.interrupt();
                frame.startstop.setText("Avvia connessione");
            } catch (SQLException ex) {
                Logger.getLogger(NFCSecureTransmission_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        try {
            sendclient=new SendToClient(dbconn.loc_stm,dbconn.nfc_stm);
            sendclient.server.setReuseAddress(true);
        } catch (IOException ex) {
            Logger.getLogger(NFCSecureTransmission_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
