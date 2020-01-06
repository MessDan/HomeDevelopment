
package nfcsecuretransmission_server;

import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Grp_Manager{
    String querySearch;
    ResultSet result;
    Statement db_stm;
    public Grp_Manager(Statement nfc,int id_grp){
        this.db_stm=nfc;
        querySearch="SELECT grp_lenght FROM groups_list WHERE id_grp="+id_grp;
        try {
            db_stm.executeQuery(querySearch);
        result=db_stm.getResultSet();
        while(result.next())
            new Group(db_stm,result.getInt("grp_lenght"),id_grp);
        } catch (SQLException ex) {
            Logger.getLogger(Grp_Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Grp_Manager(Statement nfc){
        try {
            this.db_stm=nfc;
            querySearch="SELECT id_grp,grp_lenght FROM groups_list";
            db_stm.executeQuery(querySearch);
            result=db_stm.getResultSet();
            int lenght=-1;
            int id_grp=-1;
            while(result.next()){
                id_grp=result.getInt("id_grp");
                lenght=result.getInt("grp_lenght");
                if(lenght!=-1)
                    new Group(db_stm,lenght,id_grp);
            }
        } catch (SQLException ex) {
            System.out.println("Nessun gruppo trovato");
        }
    }
}
class Group implements Runnable{
    int wait_time,grp;
    Statement stm;
    public Group(Statement stm,int time,int id_grp){
        this.stm=stm;
        this.wait_time=time;
        this.grp=id_grp;
        new Thread(this).start();
    }
    @Override
    public void run() {
        String dlt_grp;
        try {
            System.out.println("- Gestisco il gruppo -");
            while(wait_time>0){
                sleep(60000);
                wait_time=wait_time-1;
                dlt_grp="UPDATE groups_list SET grp_lenght="+wait_time+" WHERE id_grp="+grp;
                stm.executeUpdate(dlt_grp);
                System.out.println("La durata del gruppo "+grp+" è stata modificata");
            }
            dlt_grp="DELETE FROM groups_list WHERE id_grp="+grp;
            stm.executeUpdate(dlt_grp);
            dlt_grp="DELETE FROM user_group_list WHERE id_group="+grp;
            stm.executeUpdate(dlt_grp);
            dlt_grp="DELETE FROM access_group_list WHERE id_group="+grp;
            stm.executeUpdate(dlt_grp);
            java.util.Date dnow=new java.util.Date();
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
            System.out.println("Gruppo "+grp+" eliminato alle "+sdf.format(dnow));
        } catch (InterruptedException | SQLException ex) {
            Logger.getLogger(Group.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
