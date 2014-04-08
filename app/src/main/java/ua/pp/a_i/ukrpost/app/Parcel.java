package ua.pp.a_i.ukrpost.app;

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
import java.util.Date;

/**
 * Created by em on 08.04.2014.
 */
public class Parcel {
    private int id;
    private String name;
    private String barcode;
    private String status;
    private Date date;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

}
