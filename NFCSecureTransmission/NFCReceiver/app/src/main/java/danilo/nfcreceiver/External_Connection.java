package danilo.nfcreceiver;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.*;

public class External_Connection extends AsyncTask {
    HttpClient client;
    HttpPost hpost;
    Object resp;
    public External_Connection(String Link,Object[]info) throws IOException {
        client=new DefaultHttpClient();
        hpost=new HttpPost(Link);
        execute(info);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        List<NameValuePair> list=new ArrayList<NameValuePair>(params.length);
        if(params[0].toString().equals("verify")){
            list.add(new BasicNameValuePair("id_device",params[1].toString()));
            list.add(new BasicNameValuePair("access_name",params[2].toString()));
            list.add(new BasicNameValuePair("loc_city",params[3].toString()));
            list.add(new BasicNameValuePair("loc_name",params[4].toString()));
        }else if(params[0].toString().equals("newcode"))
            list.add(new BasicNameValuePair("id_device",params[1].toString()));
        try {
            hpost.setEntity(new UrlEncodedFormEntity(list));
            ResponseHandler<String> rhandle=new BasicResponseHandler();
            resp=client.execute(hpost,rhandle);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

}
