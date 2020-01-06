
package nfcsecuretransmission_server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryDB {
    int control;
    Statement nfc_stm;
    ObjectOutputStream output;
    Vector update=new Vector();
    Socket client;
    public QueryDB(Socket client,ObjectOutputStream out,Statement nfc,Vector register){
        control=(int)register.get(0);
        this.nfc_stm=nfc;
        this.output=out;
        this.client=client;
    }
    public Boolean insertInfo(int control,Vector vector) throws SQLException{
        ResultSet rs;
        String querySel,queryIns;
        int id_loc;
        int id_grp=-1;
            switch(control){
                case 1: try{
                            queryIns="INSERT INTO token(device_code)VALUES('"+vector.get(7)+"')";
                            System.out.println(queryIns);
                            nfc_stm.executeUpdate(queryIns);
                            querySel="SELECT id_tkn FROM token WHERE device_code='"+vector.get(7)+"'";
                            System.out.println(querySel);
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            int id_tkn=-1;
                            while(rs.next())
                                id_tkn=rs.getInt("id_tkn");
                            if(id_tkn==-1)
                                throw new Exception();
                            querySel="SELECT id_authorization FROM authorization WHERE authorization_name='"+vector.get(8)+"'";
                            System.out.println(querySel);
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            int id_auth=-1;
                            while(rs.next())
                                id_auth=rs.getInt("id_authorization");
                            if(id_auth==-1)
                                throw new Exception();
                            java.util.Date dnow=new java.util.Date();
                            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            char[] NametoUpper=vector.get(1).toString().toCharArray();
                            char[] SurnametoUpper=vector.get(2).toString().toCharArray();
                            if(NametoUpper[0]>=97&&NametoUpper[0]<=122)
                                NametoUpper[0]-=32;
                            if(SurnametoUpper[0]>=97&&SurnametoUpper[0]<=122)
                                SurnametoUpper[0]-=32;
                            String newNameUpper=new String(NametoUpper);
                            String newSurnameUpper=new String(SurnametoUpper);
                            vector.removeElementAt(1);
                            vector.insertElementAt(newNameUpper, 1);
                            vector.removeElementAt(2);
                            vector.insertElementAt(newSurnameUpper, 2);
                            queryIns="INSERT INTO account(account_name,account_surname,account_age,account_email,account_type,account_number,insert_date,id_token,id_authorization)"
                                +"VALUES('"+vector.get(1)+"','"+vector.get(2)+"','"+vector.get(3)+"','"+vector.get(4)+"','"+vector.get(5)+"',"+vector.get(6)+",'"+sdf.format(dnow)+"',"+id_tkn+","+id_auth+")";
                            System.out.println(queryIns);
                            nfc_stm.executeUpdate(queryIns);
                            return true;
                        }catch(Exception ex){
                            queryIns="DELETE FROM token WHERE device_code='"+vector.get(7)+"'";
                            nfc_stm.executeUpdate(queryIns);
                            return false;
                        }
                case 2: try{
                            queryIns="INSERT INTO authorization(authorization_name,authorization_priority) VALUES('"+vector.get(1)+"','"+vector.get(2)+"')";
                            nfc_stm.executeUpdate(queryIns);
                            return true;
                        }catch(Exception ex){
                            return false;
                        }
                case 3: try{
                            queryIns="INSERT INTO location(location_name,location_city) VALUES('"+vector.get(2)+"','"+vector.get(1)+"')";
                            nfc_stm.executeUpdate(queryIns);
                            return true;
                        }catch(Exception ex){
                            return false;
                        }
                case 4: try{
                            querySel="SELECT id_location FROM location WHERE location_city='"+vector.get(1)+"' AND location_name='"+vector.get(2)+"'";
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            id_loc=-1;
                            while(rs.next())
                                id_loc=rs.getInt("id_location");
                            if(id_loc==-1)
                                throw new Exception();
                            querySel="SELECT authorization_priority FROM authorization WHERE authorization_name='"+vector.get(4)+"'";
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            int min_auth=-1;
                            while(rs.next())
                                min_auth=rs.getInt("authorization_priority");
                            if(min_auth==-1)
                                throw new Exception();
                            queryIns="INSERT INTO access(access_name,id_location,min_auth) VALUES('"+vector.get(3)+"',"+id_loc+","+min_auth+")";
                            nfc_stm.executeUpdate(queryIns);
                            return true;
                        }catch(Exception ex){
                            return false;
                        }
                case 5: try{
                            querySel="SELECT id_location FROM location WHERE location_city='"+vector.get(4)+"' AND location_name='"+vector.get(5)+"'";
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            id_loc=-1;
                            while(rs.next())
                                id_loc=rs.getInt("id_location");
                            if(id_loc==-1)
                                throw new Exception();
                            String admin=(String)vector.get(2);
                            String[] admin_name=admin.split(" ");
                            querySel="SELECT id_account FROM account WHERE account_name='"+admin_name[1]+"' AND account_surname='"+admin_name[0]+"'";
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            int id_account=-1;
                            while(rs.next())
                                id_account=rs.getInt("id_account");
                            if(id_account==-1)
                                throw new Exception();
                            queryIns="INSERT INTO groups_list(grp_name,grp_admin,grp_lenght,id_location) VALUES('"+vector.get(1)+"',"+id_account+",'"+vector.get(3)+"',"+id_loc+")";
                            nfc_stm.executeUpdate(queryIns);
                            querySel="SELECT id_grp FROM groups_list WHERE grp_name='"+vector.get(1)+"' AND id_location="+id_loc+"";
                            nfc_stm.executeQuery(querySel);
                            rs=nfc_stm.getResultSet();
                            id_grp=-1;
                            while(rs.next())
                                id_grp=rs.getInt("id_grp");
                            if(id_grp==-1)
                                throw new Exception();
                            int user,access;
                            for(user=0;!vector.get(user).equals("users");user++);
                            for(access=0;!vector.get(access).equals("access");access++);
                            for(int i=user+1;i<access;i++){
                                String users=(String)vector.get(i);
                                String[] name=users.split(" ");
                                querySel="SELECT id_account FROM account WHERE account_name='"+name[1]+"' AND account_surname='"+name[0]+"' AND account_email='"+name[2]+"'";
                                nfc_stm.executeQuery(querySel);
                                rs=nfc_stm.getResultSet();
                                id_account=-1;
                                while(rs.next())
                                    id_account=rs.getInt("id_account");
                                if(id_account==-1)
                                    throw new Exception();
                                queryIns="INSERT INTO user_group_list(id_account,id_group) VALUES("+id_account+","+id_grp+")";
                                nfc_stm.executeUpdate(queryIns);
                            }
                            for(int i=access+1;i<vector.size();i++){
                                querySel="SELECT id_access FROM access WHERE access_name='"+vector.get(i)+"' AND id_location="+id_loc;
                                nfc_stm.executeQuery(querySel);
                                rs=nfc_stm.getResultSet();
                                int id_access=-1;
                                while(rs.next())
                                    id_access=rs.getInt("id_access");
                                if(id_access==-1)
                                    throw new Exception();
                                queryIns="INSERT INTO access_group_list(id_group,id_access) VALUES("+id_grp+","+id_access+")";
                                nfc_stm.executeUpdate(queryIns);
                            }
                            new Grp_Manager(nfc_stm,id_grp);
                            return true;
                        }catch(Exception ex){
                            queryIns="DELETE FROM groups_list WHERE id_grp="+id_grp;
                            nfc_stm.executeUpdate(queryIns);
                            queryIns="DELETE FROM user_group_list WHERE id_group="+id_grp;
                            nfc_stm.executeUpdate(queryIns);
                            queryIns="DELETE FROM access_group_list WHERE id_group="+id_grp;
                            nfc_stm.executeUpdate(queryIns);
                            return false;
                        }
            }
        return false;
    }
    public void SendUpdate(int vec) throws SQLException{
        String querySel;
        ResultSet rs;
        switch(vec){
            case 0: nfc_stm.executeQuery("SELECT nome_città FROM città ORDER BY nome_città ASC");
                    rs=nfc_stm.getResultSet();
                    update.add("city");
                    while(rs.next())
                        update.add(rs.getString("nome_città"));
                    try {
                        output=new HandleConnection(client).SendObject(update);
                        update.clear();
                    } catch (IOException ex) {
                        Logger.getLogger(QueryDB.class.getName()).log(Level.SEVERE, null, ex);
                    } break;
            case 1: querySel="SELECT account_name,account_surname,account_email FROM account WHERE account_type='Dirigente' OR account_type='Professore' ORDER BY account_surname ASC";
                    nfc_stm.executeQuery(querySel);
                    rs=nfc_stm.getResultSet();
                    update.add("admin");
                    while(rs.next()){
                        update.add(rs.getString("account_surname"));
                        update.add(rs.getString("account_name"));
                        update.add(rs.getString("account_email"));
                    }
                    try {
                        output=new HandleConnection(client).SendObject(update);
                        update.clear();
                    } catch (IOException ex) {
                        Logger.getLogger(QueryDB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    querySel="SELECT account_name,account_surname,account_email FROM account WHERE account_type='Dipendente' OR account_type='Studente' ORDER BY account_surname ASC";
                    nfc_stm.executeQuery(querySel);
                    rs=nfc_stm.getResultSet();
                    update.add("user");
                    while(rs.next()){
                        update.add(rs.getString("account_surname"));
                        update.add(rs.getString("account_name"));
                        update.add(rs.getString("account_email"));
                    }
                    try {
                        output=new HandleConnection(client).SendObject(update);
                        update.clear();
                    } catch (IOException ex) {
                        Logger.getLogger(QueryDB.class.getName()).log(Level.SEVERE, null, ex);
                    } break;
            case 2: querySel="SELECT authorization_name FROM authorization ORDER BY authorization_priority ASC";
                    nfc_stm.executeQuery(querySel);
                    rs=nfc_stm.getResultSet();
                    update.add("authorization");
                    while(rs.next())
                        update.add(rs.getString("authorization_name"));
                    try {
                        output=new HandleConnection(client).SendObject(update);
                        update.clear();
                    } catch (IOException ex) {
                        Logger.getLogger(QueryDB.class.getName()).log(Level.SEVERE, null, ex);
                    } break;
            case 3: querySel="SELECT location_name FROM location ORDER BY location_name ASC";
                    nfc_stm.executeQuery(querySel);
                    rs=nfc_stm.getResultSet();
                    update.add("location");
                    while(rs.next())
                        update.add(rs.getString("location_name"));
                    try{
                        output=new HandleConnection(client).SendObject(update);
                        update.clear();
                    }catch(IOException ex){
                        Logger.getLogger(QueryDB.class.getName()).log(Level.SEVERE, null, ex);
                    } break;
            case 4: nfc_stm.executeQuery("SELECT access_name FROM access ORDER BY access_name");
                    rs=nfc_stm.getResultSet();
                    update.add("access");
                    while(rs.next())
                        update.add(rs.getString("access_name"));
                    try{
                        output=new HandleConnection(client).SendObject(update);
                        update.clear();
                    }catch(IOException ex){
                        Logger.getLogger(QueryDB.class.getName()).log(Level.SEVERE, null, ex);
                    } break;
        }
        
    }
}