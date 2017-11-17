package webike.webike.utils;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Juan on 11/17/2017.
 */

public interface ListActions<DataType> {
    void onReceiveList(ArrayList<DataType> data , DatabaseReference reference );
    void onCancel(DatabaseError error );
}
