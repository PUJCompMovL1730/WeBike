package webike.webike.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Juan on 10/28/2017.
 */

public class Validator {

    public static boolean validateEmail( String email ) {
        boolean isValid;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean validateRegisterName( String s ){
        if ( stringContainsChars( s ,  "\"\n\t#$%&/()=!¿¡'.-\\?") )
            return false;
        return true;
    }

    public static boolean validateAge( String age ){
        try{
            int a = Integer.parseInt(age);
            return true;
        }catch ( Exception e ){
            return false;
        }
    }

    public static boolean validatePassword( String s ){
        if ( s.length() < 6 ){
            return false;
        }
        return true;
    }

    public static boolean stringContainsChars( String s , String chars ){
        boolean ret = false;
        for ( char c : chars.toCharArray() ) {
            if ( s.contains( c + "")){
                ret = true;
            }
        }
        return ret;
    }

    public static ArrayList<Response> validateCredentials(String email , String password ){
        ArrayList<Response> errors = new ArrayList<>();
        if( !validateEmail(email) ){
            errors.add(Response.INVALID_EMAIL);
        }
        if( !validatePassword(password) ){
            errors.add( Response.INVALID_PASSWORD );
        }
        return errors;
    }

    public static ArrayList<Response> validateRegisterFields( ArrayList<String> fields ){
        ArrayList<Response> res = new ArrayList<>();

        for ( int i = 0 ; i < fields.size() ; i++ ){
            String s = fields.get(i);
            if( s.isEmpty() ){
                res.add(Response.EMPTY_FIELD);
            }else{
                res.add( validateField( s , i ) );
            }
        }

        return res;
    }

    public static Response validateField ( String field , int i ){
        Response res = Response.FIELD_OK;
        switch (i){
            case 0:
                //firstName
                if( !validateRegisterName(field) ) {
                    res = Response.INVALID_NAME;
                }
                break;
            case 1:
                //lastName
                if ( ! validateRegisterName(field) ){
                    res = Response.INVALID_LAST;
                }
                break;
            case 2:
                //email
                if ( !validateEmail(field) ){
                    res = Response.INVALID_EMAIL;
                }
                break;
            case 3:
                //password
                if( !validatePassword(field) ){
                    res = Response.INVALID_PASSWORD;
                }
                break;
            case 4:
                //age
                if( !validateAge(field) ){
                    res = Response.INVALID_AGE;
                }
                break;
        }
        return res;
    }
}
