package webike.webike.ubicacion;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Carlos on 23/10/2017.
 */

public class Descarga extends AsyncTask<String, Void, String> {

    private GoogleMap googleMap;

    public Descarga(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected String doInBackground(String... url) {

        String data = "";
        try{
            data = downloadUrl(url[0]);
        }catch(Exception e){
            Log.e(Descarga.class.getName(), e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Parser parserTask = new Parser(googleMap);
        parserTask.execute(result);
    }

    public static String downloadUrl(String strUrl) {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            Log.i("INFO_DATABASE", "updateView: "+ data );
            br.close();

        }catch(Exception e){
            Log.e("Exception", e.toString());
        }finally{
            try {
                iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            urlConnection.disconnect();
        }
        return data;
    }
}