package danilo.nfcreceiver;

import android.app.Activity;
import android.content.Intent;
import android.nfc.*;
import android.os.*;
import android.widget.TextView;
import android.widget.Toast;

public class Send_NFC extends Activity {
    Intent main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main=new Intent(this,MainActivity.class);
        Parcelable[] record=getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage mess=(NdefMessage) record[0];
        String received=new String(mess.getRecords()[0].getPayload());
        main.putExtra("sended",true);
        main.putExtra("control",1);
        main.putExtra("received",received);
        startActivity(main);
    }
}
