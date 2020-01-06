
package nfcsecuretransmission_server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class SendToClient{
    ServerSocket server;
    Socket client;
    Statement location,nfcst;
    public SendToClient(Statement location,Statement nfcst) throws IOException{
        server=new ServerSocket(7777);
        this.location=location;
        this.nfcst=nfcst;
        WaitClient(server,this.location,this.nfcst);
    }
    public void WaitClient(ServerSocket server,Statement location,Statement nfcst){
        while(true){
            System.out.println("In attesa...");
            try{
                client=server.accept();
                System.out.println("Connesso con: "+client.getInetAddress());
                new HandleConnection(client,this.location,this.nfcst);
            }catch(IOException e){
            }
        }
    }
    
}
class HandleConnection implements Runnable{
    Socket client;
    Vector city,register,admin,user,auth,loc,access;
    Statement nfcst,location;
    public HandleConnection(Socket client){
        this.client=client;
    }
    public HandleConnection(Socket client,Statement location,Statement nfcst){
        this.nfcst=nfcst;
        this.location=location;
        this.client=client;
        new Thread(this).start();
    }
    public ObjectOutputStream SendObject(Vector vec) throws IOException{
        System.out.println("Avvio OutpuStream");
        ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
        oos.writeObject(vec);
        oos.flush();
        return oos;
    }
    public ObjectInputStream ReceiveInfo() throws IOException, ClassNotFoundException, SocketException{
        System.out.println("Avvio InputStream");
        ObjectInputStream ois=new ObjectInputStream(client.getInputStream());
        register=(Vector) ois.readObject();
        return ois;
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream output=null;
            ObjectInputStream input=null;
            if(client.isConnected())
                updateVector(output);
            while(client.isConnected()){
                if(client.isConnected())
                    input=ReceiveInfo();
                if(register!=null){
                    Boolean executed=fillDB(output,nfcst,register,"insert");
                    Vector response=new Vector();
                    response.add(executed);
                    SendObject(response);
                    if(executed==true){
                        fillDB(output,nfcst,register,"update");
                        System.out.println("Inserimento Effettuato");
                    }
                    else{
                        System.out.println("Problema durante l'inserimento");
                    }
                    register.clear();
                }
            }
        } catch (SocketException ex){
            System.out.println("Connessione con Client terminata");
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HandleConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateVector(ObjectOutputStream out) throws SQLException{
        Vector temp=new Vector();
        temp.add(0);
        fillDB(out,location,temp,"update");
        temp.setElementAt(1,0);
        fillDB(out,nfcst,temp,"update");
        temp.setElementAt(2,0);
        fillDB(out,nfcst,temp,"update");
        temp.setElementAt(3,0);
        fillDB(out,nfcst,temp,"update");
        temp.setElementAt(4,0);
        fillDB(out,nfcst,temp,"update");
    }
    
    private Boolean fillDB(ObjectOutputStream out,Statement nfcst,Vector vec,String todo) throws SQLException{
        QueryDB query=new QueryDB(client,out,nfcst,vec);
        if(todo.equals("insert")){
            Boolean done=query.insertInfo(query.control,vec);
            return done;
        }
        else if(todo.equals("update")){
            int pos=(int)vec.get(0);
            query.SendUpdate(pos);
            return true;
        }
        return false;
    }
}
