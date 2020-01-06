package nfcsecuretransmission_server;

import java.sql.*;

public class DB_connection extends Thread {
    Connection nfc_conn,loc_conn;
    Statement nfc_stm,loc_stm;
    public DB_connection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        nfc_conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/nfc_secure_transmission","root","");
        loc_conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/località","root","");
        nfc_stm=nfc_conn.createStatement();
        loc_stm=loc_conn.createStatement();
    }
}
