package webike.webike.utils;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Juan on 11/17/2017.
 */

public interface SingleValueActions<DataType> {
    void onReceiveSingleValue( DataType data , DatabaseReference reference);
    void onCancel(DatabaseError error );
}