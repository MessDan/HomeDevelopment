package danilo.nfcreceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NfcA;
import android.os.*;
import android.view.*;
import android.nfc.*;
import android.widget.*;

import java.io.IOException;
import java.nio.charset.Charset;


public class MainActivity extends Activity {
    TextView token;
    EditText city,location,access,imeii,ipserver;
    public Toast message;
    NfcAdapter nfcadapter;
    Intent sendnfc,sendaccessdata;
    int control;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        token=(TextView) findViewById(R.id.Token);
        city=(EditText)findViewById(R.id.CityMain);
        location=(EditText)findViewById(R.id.LocationMain);
        access=(EditText)findViewById(R.id.AccessMain);
        imeii=(EditText)findViewById(R.id.IMEIIMain);
        ipserver=(EditText)findViewById(R.id.IPServerMain);
        sendnfc=new Intent(this,Send_NFC.class);
        sendaccessdata=new Intent(this,Send_Access_Data.class);
        try{
            bundle = getIntent().getExtras();
            control=(bundle.getBoolean("sended")) ? bundle.getInt("control") : 0;
        }catch (NullPointerException e){
            control=0;
        }
        message=null;
        if(control!=1)
        {
            if(checkForNFC()==false){
                if(message==null){
                    message=Toast.makeText(this,"Attiva l'NFC e riavvia l'applicazione",Toast.LENGTH_SHORT);
                    message.show();
                }
                finish();
            }
        }else{
            token.setText(bundle.getString("received"));
        }
        Button verifydata=(Button)findViewById(R.id.SendAccess);
        Button sendcode=(Button)findViewById(R.id.SendCode);
        verifydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                sendaccessdata.putExtra("type","verify");
                try{
                if(!bundle.getString("received").isEmpty())
                    sendaccessdata.putExtra("dev_id",bundle.getString("received"));
                }catch(Exception e){
                    sendaccessdata.putExtra("dev_id",imeii.getText().toString());
                }
                sendaccessdata.putExtra("access_name",access.getText().toString());
                sendaccessdata.putExtra("location_city",city.getText().toString());
                sendaccessdata.putExtra("location_name",location.getText().toString());
                sendaccessdata.putExtra("ip_server",ipserver.getText().toString());
                startActivity(sendaccessdata);
            }
        });
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendaccessdata.putExtra("type","newcode");
                try{
                    if(!bundle.getString("received").isEmpty())
                        sendaccessdata.putExtra("dev_id",bundle.getString("received"));
                }catch(Exception e){
                    sendaccessdata.putExtra("dev_id",imeii.getText().toString());
                }
                sendaccessdata.putExtra("ip_server",ipserver.getText().toString());
            }
        });
    }

    protected void onResume(){
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED==(getIntent().getAction()))
            startActivity(sendnfc);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private boolean checkForNFC(){
        //Verifica se il dispositivo ha l'NFC
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            String text= new String("NFC non presente");
            message=Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT);
            message.show();
            finish();
            return false;
            //Verifica se la versione Android supporta Android Beam
        } else if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR1){
            String text= new String("Android Beam non supportato");
            message=Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
            message.show();
            finish();
            return false;
        } else {
            nfcadapter=NfcAdapter.getDefaultAdapter(this);
            //Verifica che l'NFC sia attivo
            if(nfcadapter!=null && nfcadapter.isEnabled()){
                String text= new String("NFC abilitato");
                message=Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                message.show();
                return true;
            }
            else{
                return false;
            }
        }
    }
}
