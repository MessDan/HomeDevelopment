package nfcsecuretransmission_gui;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Vector;

public class Frame extends JFrame{
    JFrame frame=new JFrame("NFC Secure Transmission GUI");
    JFrame add_user_fmr=new JFrame();
    //Vectors
    Vector avoid_user=new Vector();
    Vector avoid_access=new Vector();
    //Panels
    JPanel main_pnl=new JPanel();
    JPanel usr_pnl=new JPanel();
    JPanel auth_pnl=new JPanel();
    JPanel auth_date_pnl=new JPanel();
    JPanel loc_pnl=new JPanel();
    JPanel acs_pnl=new JPanel();
    JPanel grp_pnl=new JPanel();
    JPanel user_grp_pnl=new JPanel();
    JPanel end=new JPanel();
    //TextFields
    TextField name_txfd=new TextField(30);
    TextField surname_txfd=new TextField(30);
    TextField age_txfd=new TextField(30);
    TextField email_txfd=new TextField(30);
    TextField num_txfd=new TextField(30);
    TextField id_txfd=new TextField(30);
    TextField auth_nm_txfd=new TextField(30);
    TextField loc_nm_txfd=new TextField(30);
    TextField acs_nm_txfd=new TextField(30);
    TextField grp_nm_txfd=new TextField(30);
    //ComboBoxes
    JComboBox acc_type_cbx=new JComboBox();
    JComboBox auth_prio_cbx=new JComboBox();
    JComboBox day1_cbx=new JComboBox();
    JComboBox month1_cbx=new JComboBox();
    JComboBox day2_cbx=new JComboBox();
    JComboBox month2_cbx=new JComboBox();
    JComboBox hour1_cbx=new JComboBox();
    JComboBox minute1_cbx=new JComboBox();
    JComboBox hour2_cbx=new JComboBox();
    JComboBox minute2_cbx=new JComboBox();
    JComboBox loc_ct_cbx=new JComboBox();
    JComboBox grp_admin_cbx=new JComboBox();
    JComboBox grp_user_cbx=new JComboBox();
    JComboBox auth_nm_cbx=new JComboBox();
    JComboBox loc_nm_cbx=new JComboBox();
    JComboBox acs_list_cbx=new JComboBox();
    //ListModel
    DefaultListModel sel_user_lm=new DefaultListModel();
    DefaultListModel avoid_user_lm=new DefaultListModel();
    DefaultListModel sel_access_lm=new DefaultListModel();
    DefaultListModel avoid_access_lm=new DefaultListModel();
    //List
    JList sel_user_ls=new JList(sel_user_lm);
    JList avoid_user_ls=new JList(avoid_user_lm);
    JList sel_access_ls=new JList(sel_access_lm);
    JList avoid_access_ls=new JList(avoid_access_lm);
    //ScrollPanes
    JScrollPane sel_user_sp=new JScrollPane(sel_user_ls);
    JScrollPane avoid_user_sp=new JScrollPane(avoid_user_ls);
    JScrollPane sel_access_sp=new JScrollPane(sel_access_ls);
    JScrollPane avoid_access_sp=new JScrollPane(avoid_access_ls);
    //Buttons
    JButton add_usr=new JButton("Aggiungi utenti");
    JButton manual_id=new JButton("ID Manuale");
    JButton add_auth=new JButton("Aggiungi autorizzazioni");
    JButton add_loc=new JButton("Aggiungi location");
    JButton add_acs=new JButton("Aggiungi accesso");
    JButton add_group=new JButton("Aggiungi gruppo");
    JButton add_user=new JButton("- >");
    JButton rem_user=new JButton("< -");
    JButton add_acs_user=new JButton("- >");
    JButton rem_acs_user=new JButton("< -");
    JButton confirm=new JButton("Conferma");
    JButton cancel=new JButton("Annulla");
    JButton usr_list=new JButton("Aggiungi utenti e accessi");
    JButton confirm_add=new JButton("Conferma Inserimento");
    
    GridBagConstraints c=new GridBagConstraints();
    public Frame(){
        createMainPnl(c);
        end.add(BorderLayout.WEST,confirm);
        end.add(BorderLayout.EAST,cancel);
        frame.add(BorderLayout.CENTER,main_pnl);
        frame.setBounds(50, 50, 550, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    void setGrid(GridBagConstraints c, int y, int x){
        c.gridx=x;
        c.gridy=y;
        c.ipadx=3;
        c.weighty=1;
    }
    void createMainPnl(GridBagConstraints c){
        main_pnl.removeAll();
        main_pnl.setLayout(new GridBagLayout());
        setGrid(c,0,0);
        main_pnl.add(add_usr,c);
        setGrid(c,1,0);
        main_pnl.add(add_auth,c);
        setGrid(c,2,0);
        main_pnl.add(add_loc,c);
        setGrid(c,3,0);
        main_pnl.add(add_group,c);
    }
    void fillAccType(JComboBox cbx){
        cbx.removeAllItems();
        String[] type={"Dirigente","Dipendente","Professore","Studente"};
        for(int i=0;i<type.length;i++)
            cbx.addItem(type[i]);
    }
    void createUserPnl(GridBagConstraints c){
        usr_pnl.removeAll();
        usr_pnl.setLayout(new GridBagLayout());
        setGrid(c,0,0);
        usr_pnl.add(new JLabel("Nome: "),c);
        setGrid(c,0,1);
        name_txfd.setText("");
        name_txfd.setBackground(Color.WHITE);
        usr_pnl.add(name_txfd,c);
        setGrid(c,1,0);
        usr_pnl.add(new JLabel("Cognome: "),c);
        setGrid(c,1,1);
        surname_txfd.setBackground(Color.WHITE);
        surname_txfd.setText("");
        usr_pnl.add(surname_txfd,c);
        setGrid(c,2,0);
        usr_pnl.add(new JLabel("Nato il: "),c);
        setGrid(c,2,1);
        age_txfd.setBackground(Color.WHITE);
        age_txfd.setText("");
        usr_pnl.add(age_txfd,c);
        setGrid(c,3,0);
        usr_pnl.add(new JLabel("email: "),c);
        setGrid(c,3,1);
        email_txfd.setText("");
        email_txfd.setBackground(Color.WHITE);
        usr_pnl.add(email_txfd,c);
        setGrid(c,4,0);
        usr_pnl.add(new JLabel("Tipo di utente: "),c);
        setGrid(c,4,1);
        fillAccType(acc_type_cbx);
        usr_pnl.add(acc_type_cbx,c);
        setGrid(c,5,0);
        usr_pnl.add(new JLabel("Matricola (0 - non presente): "),c);
        setGrid(c,5,1);
        num_txfd.setBackground(Color.WHITE);
        num_txfd.setText("");
        usr_pnl.add(num_txfd,c);
        setGrid(c,6,0);
        usr_pnl.add(new JLabel("ID Dispositivo: "),c);
        setGrid(c,6,1);
        id_txfd.setBackground(Color.LIGHT_GRAY);
        id_txfd.setText("");
        id_txfd.setEditable(false);
        usr_pnl.add(id_txfd,c);
        setGrid(c,6,2);
        usr_pnl.add(manual_id,c);
        setGrid(c,7,0);
        usr_pnl.add(new JLabel("Autorizzazione: "),c);
        setGrid(c,7,1);
        //createAuthLV(auth_nm_cbx);
        auth_nm_cbx.setSelectedIndex(0);
        usr_pnl.add(auth_nm_cbx,c);
    }
    void createAuthDatePnl(JPanel j){
        day1_cbx.removeAllItems();
        day2_cbx.removeAllItems();
        month1_cbx.removeAllItems();
        month2_cbx.removeAllItems();
        hour1_cbx.removeAllItems();
        hour2_cbx.removeAllItems();
        minute1_cbx.removeAllItems();
        minute2_cbx.removeAllItems();
        j.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        Date dnow=new Date();
        String[] month={"Indefinito","Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
        for(int i=0;i<60;i++){
            if(i<31){
                day1_cbx.addItem(i+1);
                day2_cbx.addItem(i+1);
            }
            if(i<13){
                month1_cbx.addItem(month[i]);
                month2_cbx.addItem(month[i]);
            }
            if(i<24){
                hour1_cbx.addItem(i);
                hour2_cbx.addItem(i);
            }
            minute1_cbx.addItem(i);
            minute2_cbx.addItem(i);
        }
        setGrid(c,0,0);
        j.add(new JLabel("Da: "),c);
        setGrid(c,0,1);
        day1_cbx.setSelectedIndex(dnow.getDate()-1);
        j.add(day1_cbx,c);
        setGrid(c,0,2);
        month1_cbx.setSelectedIndex(dnow.getMonth()+1);
        j.add(month1_cbx,c);
        setGrid(c,0,3);
        j.add(new JLabel(" Ore: "),c);
        setGrid(c,0,4);
        hour1_cbx.setSelectedIndex(dnow.getHours());
        j.add(hour1_cbx,c);
        setGrid(c,0,5);
        j.add(new JLabel("  : "),c);
        setGrid(c,0,6);
        minute1_cbx.setSelectedIndex(dnow.getMinutes());
        j.add(minute1_cbx,c);
        setGrid(c,1,0);
        j.add(new JLabel("A (Indefinito): "),c);
        setGrid(c,1,1);
        day2_cbx.setSelectedIndex(dnow.getDate()-1);
        j.add(day2_cbx,c);
        setGrid(c,1,2);
        month2_cbx.setSelectedIndex(dnow.getMonth()+1);
        j.add(month2_cbx,c);
        setGrid(c,1,3);
        j.add(new JLabel(" Ore: "),c);
        setGrid(c,1,4);
        hour2_cbx.setSelectedIndex(dnow.getHours());
        j.add(hour2_cbx,c);
        setGrid(c,1,5);
        j.add(new JLabel("  : "),c);
        setGrid(c,1,6);
        minute2_cbx.setSelectedIndex(dnow.getMinutes());
        j.add(minute2_cbx,c);
    }
    void createAuthPnl(GridBagConstraints c){
        auth_date_pnl.removeAll();
        auth_pnl.removeAll();
        auth_pnl.setLayout(new GridBagLayout());
        setGrid(c,0,0);
        auth_pnl.add(new JLabel("Autorizzazione: "),c);
        setGrid(c,0,1);
        auth_nm_txfd.setText("");
        auth_pnl.add(auth_nm_txfd,c);
        setGrid(c,1,0);
        auth_pnl.add(new JLabel("Priorità: "),c);
        setGrid(c,1,1);
        auth_prio_cbx.removeAllItems();
        createAuthLV(auth_prio_cbx);
        auth_prio_cbx.setSelectedIndex(0);
        auth_pnl.add(auth_prio_cbx,c);
    }
    void fillCBX(JComboBox cbx,Vector vec){
        cbx.removeAllItems();
        if(vec!=null){
            if(!vec.get(0).equals("admin")&&!vec.get(0).equals("user")&&!vec.get(0).equals("access"))
                for(int i=1;i<vec.size();i++)
                    cbx.addItem(vec.get(i));
            else if(vec.get(0).equals("access")){
                for(int i=1;i<vec.size();i++){
                    cbx.addItem(vec.get(i));
                    cbx.addItem(null);
                }
            }
            else{
                for(int i=1;i<vec.size();i+=3){
                    String name=(String)vec.get(i)+" "+(String)vec.get(i+1);
                    cbx.addItem(name);
                    if(vec.get(0).equals("user"))
                        cbx.addItem(vec.get(i+2));
                }     
            }
        }
    }
    void createLocPnl(GridBagConstraints c){
        loc_pnl.removeAll();
        loc_pnl.setLayout(new GridBagLayout());
        setGrid(c,0,0);
        loc_pnl.add(new JLabel("Città: "),c);
        setGrid(c,0,1);
        loc_ct_cbx.setSelectedIndex(0);
        loc_pnl.add(loc_ct_cbx,c);
        setGrid(c,1,0);
        loc_pnl.add(new JLabel("Luogo: "),c);
        setGrid(c,1,1);
        loc_nm_txfd.setText("");
        loc_nm_txfd.setEditable(true);
        loc_pnl.add(loc_nm_txfd,c);
        setGrid(c,2,0);
        loc_pnl.add(add_acs,c);
    }
    void createAccPnl(GridBagConstraints c){
        acs_pnl.removeAll();
        acs_pnl.setLayout(new GridBagLayout());
        setGrid(c,0,0);
        acs_pnl.add(new JLabel("Città: "),c);
        setGrid(c,0,1);
        acs_pnl.add(loc_ct_cbx,c);
        setGrid(c,1,0);
        acs_pnl.add(new JLabel("Luogo: "),c);
        setGrid(c,1,1);
        acs_pnl.add(loc_nm_cbx,c);
        setGrid(c,2,0);
        acs_pnl.add(new JLabel("Nome accesso: "),c);
        setGrid(c,2,1);
        acs_nm_txfd.setText("");
        acs_pnl.add(acs_nm_txfd,c);
        setGrid(c,3,0);
        acs_pnl.add(new JLabel("Lv Autorizzazione minima richiesta: "),c);
        setGrid(c,3,1);
        auth_nm_cbx.setSelectedIndex(0);
        acs_pnl.add(auth_nm_cbx,c);
    }
    void createAuthLV(JComboBox cbx){
        cbx.removeAllItems();
        String[] prio={"Massima","Alta","Media","Bassa"};
        int[] lv_prio=new int[4];
        for(int i=0;i<4;i++){
            cbx.addItem(prio[i]);
            lv_prio[i]=i;
        }
    }
    void createScrollPnl(){
        user_grp_pnl.removeAll();
        sel_user_lm.clear();
        avoid_user_lm.clear();
        sel_access_lm.clear();
        avoid_access_lm.clear();
        user_grp_pnl.setLayout(new GridBagLayout());
        GridBagConstraints grid=new GridBagConstraints();
        setGrid(grid,0,0);
        user_grp_pnl.add(new JLabel("Utenti ammessi nel gruppo: "),grid);
        setGrid(grid,1,0);
        createScrollPaneList(grid,1,user_grp_pnl,grp_user_cbx,sel_user_ls,avoid_user_ls,sel_user_lm,avoid_user_lm,sel_user_sp,avoid_user_sp,add_user,rem_user);
        setGrid(grid,2,0);
        user_grp_pnl.add(new JLabel("Accessi consentiti agli utenti: "),grid);
        setGrid(grid,3,0);
        createScrollPaneList(grid,3,user_grp_pnl,acs_list_cbx,sel_access_ls,avoid_access_ls,sel_access_lm,avoid_access_lm,sel_access_sp,avoid_access_sp,add_acs_user,rem_acs_user);
    }
    void createScrollPaneList(GridBagConstraints gbc,int y,JPanel pnl,JComboBox cbx,JList list1,JList list2,DefaultListModel dlm1,DefaultListModel dlm2,JScrollPane jsp1,JScrollPane jsp2, JButton jb1, JButton jb2){
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setLayoutOrientation(JList.VERTICAL);
        for(int i=0;i<cbx.getItemCount();i+=2){
            if(cbx.getItemAt(i+1)!=null)
                dlm1.addElement(cbx.getItemAt(i)+" "+cbx.getItemAt(i+1));
            else
                dlm1.addElement(cbx.getItemAt(i));
        }
        jsp1.setPreferredSize(new Dimension(250,200));
        pnl.add(jsp1,gbc);
        JPanel addremove=new JPanel();
            addremove.setLayout(new GridBagLayout());
            GridBagConstraints adrem=new GridBagConstraints();
            setGrid(adrem,0,0);
            addremove.add(jb1,adrem);
            setGrid(adrem,2,0);
            addremove.add(jb2,adrem);
        setGrid(gbc,y,1);
        pnl.add(addremove,gbc);
        setGrid(gbc,y,2);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setLayoutOrientation(JList.VERTICAL);
        jsp2.setPreferredSize(new Dimension(250,200));
        pnl.add(jsp2,gbc);
    }
    void createGroupPnl(GridBagConstraints c){
        grp_pnl.removeAll();
        grp_pnl.setLayout(new GridBagLayout());
        setGrid(c,0,0);
        grp_pnl.add(new JLabel("Admin gruppo: "),c);
        setGrid(c,0,1);
        grp_pnl.add(grp_admin_cbx,c);
        setGrid(c,1,0);
        grp_pnl.add(new JLabel("Nome gruppo: "),c);
        setGrid(c,1,1);
        grp_nm_txfd.setBackground(Color.WHITE);
        grp_nm_txfd.setText("");
        grp_pnl.add(grp_nm_txfd,c);
        setGrid(c,2,0);
        grp_pnl.add(new JLabel("Durata: "),c);
        setGrid(c,2,1);
        JPanel temp2=new JPanel();
        createAuthDatePnl(temp2);
        grp_pnl.add(temp2,c);
        setGrid(c,3,0);
        grp_pnl.add(new JLabel("Città: "),c);
        setGrid(c,3,1);
        grp_pnl.add(loc_ct_cbx,c);
        setGrid(c,4,0);
        grp_pnl.add(new JLabel("Luogo: "),c);
        setGrid(c,4,1);
        grp_pnl.add(loc_nm_cbx,c);
        setGrid(c,5,1);
        grp_pnl.add(usr_list,c);
    }

}