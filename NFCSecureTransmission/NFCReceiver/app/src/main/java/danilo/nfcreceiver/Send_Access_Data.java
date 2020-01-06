package danilo.nfcreceiver;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Send_Access_Data extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra=getIntent().getExtras();
        Object[] info=null;
        String SEND_LINK=null;
        if(extra.getString("type").equals("verify")) {
            info=new Object[6];
            info[0] = extra.getString("type");
            info[1] = extra.getString("dev_id");
            info[2] = extra.getString("access_name");
            info[3] = extra.getString("location_city");
            info[4] = extra.getString("location_name");
            info[5] = extra.getString("ip_server");
            SEND_LINK = "http://" + info[5] + "/NFCSecureTransmission_DBManager/VerifyAccess.php";
        }else if(extra.getString("type").equals("newcode")){
            info=new Object[3];
            info[0] = extra.getString("type");
            info[1] = extra.getString("dev_id");
            info[2] = extra.getString("ip_server");
            SEND_LINK = "http://" + info[2] + "/NFCSecureTransmission_DBManager/VerifyAccess.php";
        }
        try {
            AsyncTask task = new External_Connection(SEND_LINK, info);
            String resp = task.get().toString();
            if (resp.equals("true")) {
                setContentView(R.layout.success_insert);
            } else {
                setContentView(R.layout.failed_insert);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
