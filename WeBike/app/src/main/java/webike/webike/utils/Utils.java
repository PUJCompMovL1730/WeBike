package webike.webike.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Juan on 10/28/2017.
 */

public class Utils {
    public static void shortToast(Activity activity , String text ){
        anyToast(activity , text , Toast.LENGTH_SHORT);
    }

    public static void longToast(Activity activity , String text){
        anyToast(activity , text , Toast.LENGTH_LONG);
    }

    public static void anyToast(Activity activity , String text , int duration){
        Toast.makeText(activity.getBaseContext() , text , duration).show();
    }
}
