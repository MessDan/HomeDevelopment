package com.danilo.nfcsecuretransmission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.*;
import android.os.*;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.nio.charset.Charset;

public class Send_NFC_Data extends Activity implements NfcAdapter.CreateNdefMessageCallback{
    NfcAdapter nfcadapter;
    public Toast message;
    TelephonyManager devID;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_nfc_data);
        message=null;
        if(checkForNFC()==false) {
            if (message == null) {
                message = Toast.makeText(Send_NFC_Data.this, "Abilita l'NFC e riavvia l'applicazione", Toast.LENGTH_SHORT);
                message.show();
            }
            finish();
        }else{
            nfcadapter.setNdefPushMessageCallback(this,this);
        }
    }

    private boolean checkForNFC(){
        //Verifica se il dispositivo ha l'NFC
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)){
            String text= new String("NFC non presente");
            message=Toast.makeText(Send_NFC_Data.this,text,Toast.LENGTH_SHORT);
            message.show();
            return false;
            //Verifica se la versione Android supporta Android Beam
        }else if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR1){
            String text= new String("Android Beam non supportato");
            message=Toast.makeText(Send_NFC_Data.this, text, Toast.LENGTH_SHORT);
            message.show();
            return false;
        }else{
            nfcadapter=NfcAdapter.getDefaultAdapter(this);
            //Verifica che l'NFC sia attivo
            if(nfcadapter!=null && nfcadapter.isEnabled()){
                String text= new String("NFC abilitato");
                message=Toast.makeText(Send_NFC_Data.this, text, Toast.LENGTH_SHORT);
                message.show();
                return true;
            }else{
                return false;
            }
        }
    }

    protected void onResume(){
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED==(getIntent().getAction()));
    }

        @Override
        public NdefMessage createNdefMessage(NfcEvent event){
            devID=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            token=devID.getDeviceId();
            NdefRecord record=NdefRecord.createMime("text/plain", token.getBytes(Charset.forName("US-ASCII")));
            NdefMessage mess=new NdefMessage(record);
            return mess;
        }
}
