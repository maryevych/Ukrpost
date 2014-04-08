package ua.pp.a_i.ukrpost.app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ParcelsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    super.onPostExecute(parcel);
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
