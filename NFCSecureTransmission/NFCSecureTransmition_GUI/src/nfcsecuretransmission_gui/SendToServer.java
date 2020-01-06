package nfcsecuretransmission_gui;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class SendToServer{
    Socket socket;
    Vector city=null,admin=null,user=null,location=null,auth=null,access=null,temp;
    public SendToServer() throws IOException{
        InetAddress ia=InetAddress.getLocalHost();
        socket=new Socket(ia.getHostName(),7777);
        if(socket.isConnected())
            updateVector();
    }
    
    public void updateVector(){
        while(city==null||admin==null||user==null||location==null||auth==null||access==null){
            temp=ReceiveFrom(socket);
            if(temp.get(0).equals("city"))
                city=temp;
            else if(temp.get(0).equals("admin"))
                admin=temp;
            else if(temp.get(0).equals("user"))
                user=temp;
            else if(temp.get(0).equals("authorization"))
                auth=temp;
            else if(temp.get(0).equals("location"))
                location=temp;
            else if(temp.get(0).equals("access"))
                access=temp;
            }
    }
    
    public Vector ControlPanel(int p){
        Vector vec=new Vector();
        switch(p)
        {
            case 1: vec.add(p);
                    if(!NFCSecureTransmission_GUI.fmr.name_txfd.getText().equals("")){
                        NFCSecureTransmission_GUI.fmr.name_txfd.setBackground(Color.WHITE);
                        vec.add(NFCSecureTransmission_GUI.fmr.name_txfd.getText());
                        if(!NFCSecureTransmission_GUI.fmr.surname_txfd.getText().equals("")){
                            NFCSecureTransmission_GUI.fmr.surname_txfd.setBackground(Color.WHITE);
                            vec.add(NFCSecureTransmission_GUI.fmr.surname_txfd.getText());
                            if(!NFCSecureTransmission_GUI.fmr.age_txfd.getText().equals("")){
                                if(NFCSecureTransmission_GUI.fmr.age_txfd.getText().length()==10){
                                    NFCSecureTransmission_GUI.fmr.age_txfd.setBackground(Color.WHITE);
                                    vec.add(NFCSecureTransmission_GUI.fmr.age_txfd.getText());
                                    if(!NFCSecureTransmission_GUI.fmr.email_txfd.getText().equals("")){
                                        String email=NFCSecureTransmission_GUI.fmr.email_txfd.getText();
                                        if(email.contains("@")&&email.contains(".it")||email.contains(".com")){
                                            NFCSecureTransmission_GUI.fmr.email_txfd.setBackground(Color.WHITE);
                                            vec.add(NFCSecureTransmission_GUI.fmr.email_txfd.getText());
                                            vec.add(NFCSecureTransmission_GUI.fmr.acc_type_cbx.getSelectedItem());
                                            if(!NFCSecureTransmission_GUI.fmr.num_txfd.getText().equals("")){
                                                NFCSecureTransmission_GUI.fmr.num_txfd.setBackground(Color.WHITE);
                                                vec.add(NFCSecureTransmission_GUI.fmr.num_txfd.getText());
                                                if(!NFCSecureTransmission_GUI.fmr.id_txfd.getText().equals("")){
                                                    NFCSecureTransmission_GUI.fmr.id_txfd.setBackground(Color.LIGHT_GRAY);
                                                    vec.add(NFCSecureTransmission_GUI.fmr.id_txfd.getText());
                                                    vec.add(NFCSecureTransmission_GUI.fmr.auth_nm_cbx.getSelectedItem());
                                                    return vec;
                                                }else{
                                                    NFCSecureTransmission_GUI.fmr.id_txfd.setBackground(Color.RED);
                                                    JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                                                }
                                            }else{
                                                NFCSecureTransmission_GUI.fmr.num_txfd.setBackground(Color.RED);
                                                JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                                            }
                                        }else{
                                            NFCSecureTransmission_GUI.fmr.email_txfd.setBackground(Color.RED);
                                            JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Inserire un indirizzo email valido");
                                        }
                                    }else{
                                        NFCSecureTransmission_GUI.fmr.email_txfd.setBackground(Color.RED);
                                        JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                                    }
                                }else{
                                    NFCSecureTransmission_GUI.fmr.age_txfd.setBackground(Color.RED);
                                    JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore inserito non corretto");
                                }
                            }else{
                                NFCSecureTransmission_GUI.fmr.age_txfd.setBackground(Color.RED);
                                JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                            }
                        }else{
                            NFCSecureTransmission_GUI.fmr.surname_txfd.setBackground(Color.RED);
                            JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                        }
                    }else{
                        NFCSecureTransmission_GUI.fmr.name_txfd.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                    }
                    break;
                
            case 2: vec.add(p);
                    if(!NFCSecureTransmission_GUI.fmr.auth_nm_txfd.getText().equals("")){
                        NFCSecureTransmission_GUI.fmr.auth_nm_txfd.setBackground(Color.WHITE);
                        vec.add(NFCSecureTransmission_GUI.fmr.auth_nm_txfd.getText());
                        vec.add(NFCSecureTransmission_GUI.fmr.auth_prio_cbx.getSelectedIndex());
                        return vec;
                    }else{
                        NFCSecureTransmission_GUI.fmr.auth_nm_txfd.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                    }
                    break;
                
            case 3: vec.add(p);
                    vec.add(NFCSecureTransmission_GUI.fmr.loc_ct_cbx.getSelectedItem());
                    if(!NFCSecureTransmission_GUI.fmr.loc_nm_txfd.getText().equals("")){
                        NFCSecureTransmission_GUI.fmr.loc_nm_txfd.setBackground(Color.WHITE);
                        vec.add(NFCSecureTransmission_GUI.fmr.loc_nm_txfd.getText());
                        return vec;
                    }else{
                        NFCSecureTransmission_GUI.fmr.loc_nm_txfd.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                    }
                    break;
                
            case 4: vec.add(p);
                        vec.add(NFCSecureTransmission_GUI.fmr.loc_ct_cbx.getSelectedItem());
                        vec.add(NFCSecureTransmission_GUI.fmr.loc_nm_cbx.getSelectedItem());
                        if(!NFCSecureTransmission_GUI.fmr.acs_nm_txfd.getText().equals("")){
                            NFCSecureTransmission_GUI.fmr.acs_nm_txfd.setBackground(Color.WHITE);
                            vec.add(NFCSecureTransmission_GUI.fmr.acs_nm_txfd.getText());
                            vec.add(NFCSecureTransmission_GUI.fmr.auth_nm_cbx.getSelectedItem());
                            return vec;
                        }else{
                            NFCSecureTransmission_GUI.fmr.acs_nm_txfd.setBackground(Color.RED);
                            JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                        }
                    break;
            case 5: vec.add(p);
                    if(!NFCSecureTransmission_GUI.fmr.grp_nm_txfd.getText().equals("")){
                        NFCSecureTransmission_GUI.fmr.grp_nm_txfd.setBackground(Color.WHITE);
                        vec.add(NFCSecureTransmission_GUI.fmr.grp_nm_txfd.getText());
                        vec.add(NFCSecureTransmission_GUI.fmr.grp_admin_cbx.getSelectedItem());
                        int grp_lenght=-1;
                        java.util.Date dnow=new java.util.Date();
                        int month_end=NFCSecureTransmission_GUI.fmr.month2_cbx.getSelectedIndex();
                        int month_start=NFCSecureTransmission_GUI.fmr.month1_cbx.getSelectedIndex();
                        int day_end=(int)NFCSecureTransmission_GUI.fmr.day2_cbx.getSelectedItem();
                        int day_start=(int)NFCSecureTransmission_GUI.fmr.day1_cbx.getSelectedItem();
                        int hour_end=(int)NFCSecureTransmission_GUI.fmr.hour2_cbx.getSelectedItem();
                        int hour_start=(int)NFCSecureTransmission_GUI.fmr.hour1_cbx.getSelectedItem();
                        int minute_end=(int)NFCSecureTransmission_GUI.fmr.minute2_cbx.getSelectedItem();
                        int minute_start=(int)NFCSecureTransmission_GUI.fmr.minute1_cbx.getSelectedItem();
                        if(dnow.getMonth()>=month_start||dnow.getDate()-1>=day_start||dnow.getHours()>=hour_start||dnow.getMinutes()>=minute_start){
                            month_start=dnow.getMonth()+1;
                            day_start=dnow.getDate();
                            hour_start=dnow.getHours();
                            minute_start=dnow.getMinutes();
                            System.out.println("Ora modificata");
                        }
                        if(!NFCSecureTransmission_GUI.fmr.month2_cbx.getSelectedItem().equals("Indefinito"))
                            grp_lenght=((month_end-month_start)*30*24*60)+((day_end-day_start)*24*60)+((hour_end-hour_start)*60)+(minute_end-minute_start);   
                        else
                            grp_lenght=-1;
                        vec.add(grp_lenght);
                        vec.add(NFCSecureTransmission_GUI.fmr.loc_ct_cbx.getSelectedItem());
                        vec.add(NFCSecureTransmission_GUI.fmr.loc_nm_cbx.getSelectedItem());
                        vec.add("users");
                        if(!NFCSecureTransmission_GUI.fmr.avoid_user.isEmpty()){
                            for(int i=0;i<NFCSecureTransmission_GUI.fmr.avoid_user.size();i++)
                                vec.add(NFCSecureTransmission_GUI.fmr.avoid_user.get(i));
                            vec.add("access");
                            if(!NFCSecureTransmission_GUI.fmr.avoid_access.isEmpty()){
                                for(int i=0;i<NFCSecureTransmission_GUI.fmr.avoid_access.size();i++)
                                    vec.add(NFCSecureTransmission_GUI.fmr.avoid_access.get(i));
                                return vec;
                            }else{
                                JOptionPane.showMessageDialog(null,"Nessun accesso selezionato");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Nessun utente selezionato");
                        }
                    }else{
                        NFCSecureTransmission_GUI.fmr.grp_nm_txfd.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(NFCSecureTransmission_GUI.fmr,"Valore non inserito");
                    }
                break;
        }
        return null;
    }
    public Boolean SendTo(Socket sock,Vector vec){
        try{
            ObjectOutputStream oos=new ObjectOutputStream(sock.getOutputStream());
            oos.writeObject(vec);
            return true;
        }catch (IOException e){
            return false;
        }
    }
    public Vector ReceiveFrom(Socket sock){
        Vector vec=new Vector();
        try{
            ObjectInputStream ois=new ObjectInputStream(sock.getInputStream());
            vec=(Vector) ois.readObject();
            System.out.println("Ricevuto: "+vec.get(0));
            return vec;
        }catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
}
