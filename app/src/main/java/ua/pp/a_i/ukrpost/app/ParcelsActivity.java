package ua.pp.a_i.ukrpost.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class ParcelsActivity extends ActionBarActivity {



    void ProcessParcel(Parcel parcel){
        if(parcel!=null){

        }
        else{
            AlertDialog dialog=new AlertDialog.Builder(this).setMessage("Try again?").setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("Error").setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ParcelsActivity.this,"Trying again",Toast.LENGTH_LONG).show();

                        }
                    }).setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ParcelsActivity.this,"Canceled",Toast.LENGTH_LONG).show();
                        }
                    }).setCancelable(true).create();
            dialog.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadParcels(){
        try{
            new AsyncTask<String,Void,Parcel>() {
                @Override
                protected Parcel doInBackground(String... params) {
                    Parcel parcel=null;
                    try {
                        parcel = Parcel.Get("hfg");
                    }
                    catch (Exception e){

                    }
                    return parcel;
                }

                @Override
                protected void onPostExecute(Parcel parcel) {
                    ProcessParcel(parcel);
                }
            }.execute();
        }
        catch (Exception e){
            Toast toast=new Toast(this);
            toast.setGravity(1,getWindowManager().getDefaultDisplay().getWidth()/2,getWindowManager().getDefaultDisplay().getHeight()/2);
            toast.setText(e.getMessage());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_parcel:

                break;
            case R.id.action_settings:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
