package utils;

import java.util.ArrayList;

/**
 * Created by Juan on 10/28/2017.
 */

public class Validator {

    public static boolean validateEmail( String email ) {
        return true;
    }

    public static boolean validateRegisterName( String s ){
        if ( stringContainsChars( s ,  "\"\n\t#$%&/()=!¿¡'.-\\?") || s.isEmpty())
            return false;
        return true;
    }

    public static boolean validatePassword( String s ){
        if ( s.length() < 6 || s.isEmpty() ){
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

    public static ArrayList<Response> validateRegisterFields(){
        return new ArrayList<>();
    }
}
