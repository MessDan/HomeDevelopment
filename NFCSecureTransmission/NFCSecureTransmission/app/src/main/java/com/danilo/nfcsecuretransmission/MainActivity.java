package com.danilo.nfcsecuretransmission;

import android.app.*;
import android.content.*;
import android.os.*;


public class MainActivity extends Activity {
    Intent send_nfc_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        send_nfc_data=new Intent(this,Send_NFC_Data.class);
        startActivity(send_nfc_data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
