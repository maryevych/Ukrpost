package ua.pp.a_i.ukrpost.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Xml;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by em on 08.04.2014.
 */
public class Parcel {
    private int id;
    private String name;
    private String barcode;
    private String status;
    private Date date;

    private static final String TABLE_NAME="Parcels";
    private static ParcelsDbHelper dbHelper=new ParcelsDbHelper();
    private static DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return dateFormat.format(date);
    }

    public void setDate(String date) {
        try {
            this.date = dateFormat.parse(date);
        }
        catch(Exception e){
            this.date=null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Parcel(String name, String barcode, String status, Date date) {
        this.name = name;
        this.barcode = barcode;
        this.status = status;
        this.date = date;
    }

    public Parcel(String name, String barcode, String status, String date) {
        this.id=-1;
        this.name = name;
        this.barcode = barcode;
        this.status = status;
        try {
            this.date = dateFormat.parse(date);
        }
        catch (Exception e){
            this.date=null;
        }
    }

    public Parcel(int id, String name, String barcode, String status, String date) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.status = status;
        try {
            this.date = dateFormat.parse(date);
        }
        catch (Exception e){
            this.date=null;
        }
    }

    public void save() throws Exception{
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Name",this.getName());
        values.put("Barcode",this.getBarcode());
        values.put("Status",this.getStatus());
        values.put("StatusDate",this.getDate());
        db.insert(TABLE_NAME,"",values);
        db.close();
    }

    public static List<Parcel> getParcels() {
        List<Parcel> parcels=new ArrayList<Parcel>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{"Id", "Name", "Barcode", "Status", "StatusDate"}, null, null, null,null,null);
        boolean read = c.moveToFirst();
        while (read) {
            int id=c.getInt(c.getColumnIndex("Id"));
            String name=c.getString(c.getColumnIndex("Name"));
            String barcode=c.getString(c.getColumnIndex("Barcode"));
            String status=c.getString(c.getColumnIndex("Status"));
            String date=c.getString(c.getColumnIndex("StatusDate"));
            parcels.add(new Parcel(id,name,barcode,status,date));
            read=c.moveToNext();
        }
        return parcels;
    }



    public static Parcel Get(final String barcode) throws Exception {

        HttpRequest request=new HttpPost();
        HttpClient client=new DefaultHttpClient();
        HttpParams params=new BasicHttpParams();
        params.setParameter("barcode",barcode);
        params.setParameter("culture","uk");
        params.setParameter("guid","fcc8d9e1-b6f9-438f-9ac8-b67ab44391dd");
        request.setParams(params);
            HttpResponse response=client.execute(new HttpHost("http://services.ukrposhta.com/barcodestatistics/barcodestatistics.asmx/GetBarcodeInfo"),request);
            InputStream stream=response.getEntity().getContent();
        XmlPullParser parser=Xml.newPullParser();
        parser.setInput(stream,Xml.Encoding.UTF_8.name());
        return new Parcel("","","",new Date());
    }




    private static class ParcelsDbHelper extends SQLiteOpenHelper{
        private ParcelsDbHelper() {
            super(UkrpostApp.getContext(), "Ukrpost", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+TABLE_NAME+" (Id int primary key auto_increment, Name text, Barcode text, Status text, StatusDate datetime)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table "+TABLE_NAME);
            onCreate(db);
        }
    }

}
