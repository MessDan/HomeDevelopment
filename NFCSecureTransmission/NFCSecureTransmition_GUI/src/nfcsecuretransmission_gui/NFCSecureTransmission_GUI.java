package nfcsecuretransmission_gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

public class NFCSecureTransmission_GUI implements ActionListener{
        static Frame fmr;
        SendToServer sendserver;
        int panel;
        static int control=0;
        public NFCSecureTransmission_GUI()throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException {
            this.fmr=new Frame();
            try{
                this.sendserver=new SendToServer();
            }catch (Exception e){
            }
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, IOException {
        NFCSecureTransmission_GUI gui=new NFCSecureTransmission_GUI();
        try{
            gui.updateCBX();
            gui.fmr.add_usr.addActionListener(gui);
            gui.fmr.manual_id.addActionListener(gui);
            gui.fmr.add_auth.addActionListener(gui);
            gui.fmr.add_loc.addActionListener(gui);
            gui.fmr.add_acs.addActionListener(gui);
            gui.fmr.add_group.addActionListener(gui);
            gui.fmr.add_user.addActionListener(gui);
            gui.fmr.rem_user.addActionListener(gui);
            gui.fmr.add_acs_user.addActionListener(gui);
            gui.fmr.rem_acs_user.addActionListener(gui);
            gui.fmr.cancel.addActionListener(gui);
            gui.fmr.confirm.addActionListener(gui);
            gui.fmr.usr_list.addActionListener(gui);
            gui.fmr.confirm_add.addActionListener(gui);
        }catch(Exception e){
            JOptionPane.showMessageDialog(fmr,"Impossibile contattare il Server, contattare un Amministratore");
        }
    }
    
    public void updateCBX(){
        fmr.fillCBX(fmr.loc_ct_cbx,sendserver.city);
        fmr.fillCBX(fmr.grp_admin_cbx,sendserver.admin);
        fmr.fillCBX(fmr.grp_user_cbx,sendserver.user);
        fmr.fillCBX(fmr.auth_nm_cbx,sendserver.auth);
        fmr.fillCBX(fmr.loc_nm_cbx,sendserver.location);
        fmr.fillCBX(fmr.acs_list_cbx,sendserver.access);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String acc_comm=e.getActionCommand();
        switch(acc_comm)
        {
            case "Aggiungi utenti": if(fmr.auth_nm_cbx.getItemCount()!=0){
                                        fmr.frame.remove(fmr.main_pnl);
                                        fmr.createUserPnl(fmr.c);
                                        fmr.frame.add(BorderLayout.CENTER,fmr.usr_pnl);
                                        panel=1;
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Non sono presenti livelli di autorizzazione, impossibile continuare");
                                    }
                                    break;
            
            case "Aggiungi autorizzazioni": fmr.frame.remove(fmr.main_pnl);
                                            fmr.createAuthPnl(fmr.c);
                                            fmr.frame.add(BorderLayout.CENTER,fmr.auth_pnl);
                                            panel=2;
                                            break;
            case "Aggiungi location": fmr.frame.remove(fmr.main_pnl);
                                      fmr.createLocPnl(fmr.c);
                                      fmr.frame.add(BorderLayout.CENTER,fmr.loc_pnl);
                                      panel=3;
                                      break;
            case "Aggiungi accesso": if(fmr.auth_nm_cbx.getItemCount()!=0){
                                        if(fmr.loc_nm_cbx.getItemCount()!=0){
                                            fmr.frame.remove(fmr.loc_pnl);
                                            fmr.createAccPnl(fmr.c);
                                            fmr.frame.add(BorderLayout.CENTER,fmr.acs_pnl);
                                            panel=4;
                                        }else{
                                            JOptionPane.showMessageDialog(fmr,"Non sono presenti possibi luoghi su cui creare punti di accesso");
                                        }
                                     }else{
                                        JOptionPane.showMessageDialog(fmr, "Non sono presenti livelli di autorizzazione, impossibile continuare");
                                     }
                                     break;
            case "Aggiungi gruppo": if(fmr.grp_admin_cbx.getItemCount()!=0){
                                        if(fmr.loc_nm_cbx.getItemCount()!=0){
                                            fmr.frame.remove(fmr.main_pnl);
                                            fmr.createGroupPnl(fmr.c);
                                            fmr.frame.add(BorderLayout.CENTER,fmr.grp_pnl);
                                            panel=5;
                                        }else{
                                            JOptionPane.showMessageDialog(fmr, "Non sono presenti possibili luoghi, impossibile continuare");
                                            panel=0;
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(fmr, "Non sono presenti possibili amministratori, impossibile continuare");
                                        panel=0;
                                    }
                                    break;
            
            case "Annulla": fmr.frame.remove(fmr.usr_pnl);
                            fmr.frame.remove(fmr.auth_pnl);
                            fmr.frame.remove(fmr.loc_pnl);
                            fmr.frame.remove(fmr.acs_pnl);
                            fmr.frame.remove(fmr.grp_pnl);
                            fmr.frame.remove(fmr.end);
                            fmr.frame.add(BorderLayout.CENTER,fmr.main_pnl);
                            break;
            case "Conferma": Vector vec=sendserver.ControlPanel(panel);
                             if(vec!=null){
                                if(sendserver.SendTo(sendserver.socket, vec)){
                                    fmr.frame.remove(fmr.usr_pnl);
                                    fmr.frame.remove(fmr.auth_pnl);
                                    fmr.frame.remove(fmr.loc_pnl);
                                    fmr.frame.remove(fmr.acs_pnl);
                                    fmr.frame.remove(fmr.grp_pnl);
                                    fmr.frame.remove(fmr.end);
                                    fmr.frame.add(BorderLayout.CENTER,fmr.main_pnl);
                                    Vector response=sendserver.ReceiveFrom(sendserver.socket);
                                    if((Boolean)response.get(0)==true){
                                        switch((int)vec.get(0)){
                                            case 1: fmr.fillCBX(fmr.grp_admin_cbx, sendserver.ReceiveFrom(sendserver.socket));
                                                    fmr.fillCBX(fmr.grp_user_cbx,sendserver.ReceiveFrom(sendserver.socket));
                                                    break;
                                            case 2: fmr.fillCBX(fmr.auth_nm_cbx, sendserver.ReceiveFrom(sendserver.socket));
                                                    break;
                                            case 3: fmr.fillCBX(fmr.loc_nm_cbx, sendserver.ReceiveFrom(sendserver.socket));
                                                    break;
                                            case 4: fmr.fillCBX(fmr.acs_list_cbx, sendserver.ReceiveFrom(sendserver.socket)); 
                                                    break;
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Il server ha riscontrato un errore, i dati inseriti potrebbero non essere corretti");
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"Errore durante l'Output");
                                } 
                             }else if(vec==null)
                                JOptionPane.showMessageDialog(null,"Errore nella raccolta dati");
                             break;
            case "- >": if(e.getSource()==fmr.add_user){
                            try{
                                fmr.avoid_user_lm.addElement(fmr.sel_user_lm.get(fmr.sel_user_ls.getSelectedIndex()));
                                fmr.sel_user_lm.removeElementAt(fmr.sel_user_ls.getSelectedIndex());
                            }catch(Exception ex){}
                        }else{
                            try{
                                fmr.avoid_access_lm.addElement(fmr.sel_access_lm.get(fmr.sel_access_ls.getSelectedIndex()));
                                fmr.sel_access_lm.removeElementAt(fmr.sel_access_ls.getSelectedIndex());
                            }catch(Exception ex){}
                        }
                        break;
            case "< -": if(e.getSource()==fmr.rem_user){
                            try{
                                fmr.sel_user_lm.addElement(fmr.avoid_user_lm.get(fmr.avoid_user_ls.getSelectedIndex()));
                                fmr.avoid_user_lm.removeElementAt(fmr.avoid_user_ls.getSelectedIndex());
                            }catch(Exception ex){}
                        }else{
                            try{
                                fmr.sel_access_lm.addElement(fmr.avoid_access_lm.get(fmr.avoid_access_ls.getSelectedIndex()));
                                fmr.avoid_access_lm.removeElementAt(fmr.avoid_access_ls.getSelectedIndex());
                            }catch(Exception ex){}
                        }
                        break;
            case "Aggiungi utenti e accessi": if(fmr.grp_user_cbx.getItemCount()!=0){
                                        if(fmr.acs_list_cbx.getItemCount()!=0){
                                            fmr.createScrollPnl();
                                            fmr.add_user_fmr.add(BorderLayout.CENTER,fmr.user_grp_pnl);
                                            fmr.add_user_fmr.add(BorderLayout.PAGE_END,fmr.confirm_add);
                                            fmr.add_user_fmr.setBounds(fmr.getX()+700, fmr.getY()+100, 600, 500);
                                            fmr.add_user_fmr.setResizable(false);
                                            fmr.add_user_fmr.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                                            fmr.add_user_fmr.setVisible(true); 
                                        }else{
                                            JOptionPane.showMessageDialog(null, "Non sono presenti accessi da inserire nel gruppo");
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Non sono presenti utenti da inserire nel gruppo");
                                    }
                                    break;
            case "ID Manuale":  fmr.id_txfd.setText(JOptionPane.showInputDialog("Inserisci qui il Codice:"));
                                break;
            case "Conferma Inserimento":  fmr.avoid_user.clear();
                                          fmr.avoid_access.clear();
                                          if(!fmr.avoid_user_lm.isEmpty()){
                                            for(int i=0;i<fmr.avoid_user_lm.getSize();i++)
                                                fmr.avoid_user.add(fmr.avoid_user_lm.get(i));
                                            if(!fmr.avoid_access_lm.isEmpty()){
                                                for(int i=0;i<fmr.avoid_access_lm.getSize();i++)
                                                    fmr.avoid_access.add(fmr.avoid_access_lm.get(i));
                                                fmr.add_user_fmr.setVisible(false);
                                            }else{
                                                JOptionPane.showMessageDialog(null,"Nessun accesso selezionato");
                                            }
                                          }else{
                                              JOptionPane.showMessageDialog(null,"Nessun utente selezionato");
                                          }
                                          break;
        }
        if(!acc_comm.equals("Annulla")&&!acc_comm.equals("Conferma")&&panel!=0){
            fmr.frame.add(BorderLayout.PAGE_END,fmr.end);}
        fmr.frame.validate();
        fmr.frame.repaint();
    }
}
