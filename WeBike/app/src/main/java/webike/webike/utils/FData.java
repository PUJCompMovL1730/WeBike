package webike.webike.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import webike.webike.logic.AbstractPublication;
import webike.webike.logic.Group;
import webike.webike.logic.Message;
import webike.webike.logic.PlacePromotion;
import webike.webike.logic.PlannedRoute;
import webike.webike.logic.Publicacion;
import webike.webike.logic.Route;
import webike.webike.logic.SpecialPublication;
import webike.webike.logic.User;

public class FData {

    private FirebaseDatabase database;
    public final static String PATH_TO_USERS = "/users";
    public final static String PATH_TO_PUBS  = "/publications";
    public final static String PATH_TO_GROUPS = "/groups";
    public final static String PATH_TO_SPECIAL_PUBS = "/special_publications";
    public final static String PATH_TO_PLANNED_ROUTES = "/special_publications/planned_routes";
    public final static String PATH_TO_PLACE_PROMOTIONS = "/special_publications/place_promotions";

    public FData(){
        this.database = FirebaseDatabase.getInstance();
    }

    public void postUser( User user ){
        DatabaseReference ref = database.getReference(PATH_TO_USERS + "/" + user.getKey() );
        ref.setValue(user);
    }

    public static void postUser( FirebaseDatabase database , User user ){
        DatabaseReference ref = database.getReference(PATH_TO_USERS + "/" + user.getKey() );
        ref.setValue(user);
    }

    public void postPub( Publicacion p ){
        DatabaseReference ref = database.getReference(PATH_TO_PUBS);
        String auxKey = ref.push().getKey();
        ref = database.getReference(PATH_TO_PUBS + "/" + auxKey );
        ref.setValue( p );
    }

    public void postMessage( Message msg ){
        String src = msg.getSender();
        String dst = msg.getReceiver();

        DatabaseReference sRef = database.getReference(PATH_TO_USERS + "/" + src + "/mailbox/sent" );
        String srcKey = sRef.push().getKey();

        DatabaseReference dRef = database.getReference(PATH_TO_USERS + "/" + dst + "/mailbox/received" );
        String dstKey = dRef.push().getKey();

        sRef = database.getReference( PATH_TO_USERS + "/" + src + "/mailbox/sent/" + srcKey );
        dRef = database.getReference( PATH_TO_USERS + "/" + dst + "/mailbox/received/" + dstKey );

        sRef.setValue(msg);
        dRef.setValue(msg);
    }

    public static void postMessage( FirebaseDatabase database , Message msg ){
        String src = msg.getSender();
        String dst = msg.getReceiver();

        DatabaseReference sRef = database.getReference(PATH_TO_USERS + "/" + src + "/mailbox/sent" );
        String srcKey = sRef.push().getKey();

        DatabaseReference dRef = database.getReference(PATH_TO_USERS + "/" + dst + "/mailbox/received" );
        String dstKey = dRef.push().getKey();

        sRef = database.getReference( PATH_TO_USERS + "/" + src + "/mailbox/sent/" + srcKey );
        dRef = database.getReference( PATH_TO_USERS + "/" + dst + "/mailbox/received/" + dstKey );

        sRef.setValue(msg);
        dRef.setValue(msg);
    }

    public void postGroup( Group g ){
        DatabaseReference sRef = database.getReference(PATH_TO_GROUPS );
        String srcKey = sRef.push().getKey();

        DatabaseReference ref = database.getReference(PATH_TO_GROUPS + "/" + srcKey );
        ref.setValue(g);
    }

    public static void getUsers( FirebaseDatabase database , final ListActions<User> actions ){
        final DatabaseReference myRef = database.getReference(FData.PATH_TO_USERS);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> usuarios = new ArrayList<>();
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    User myUser = FData.createUser( singleSnapshot );
                        usuarios.add(myUser);
                }
                actions.onReceiveList( usuarios , myRef );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static <FilterType> void getUsers(FirebaseDatabase database, final FilterType filter , final ListFilteredActions<User,FilterType> actions ){
        final DatabaseReference myRef = database.getReference(FData.PATH_TO_USERS);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> usuarios = new ArrayList<>();
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    User myUser = FData.createUser( singleSnapshot );

                    if (  actions.searchCriteria( myUser , filter) ){
                        usuarios.add(myUser);
                    }
                }
                actions.onReceiveList( usuarios , myRef );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getUserFromId(FirebaseDatabase mData , String userId, final SingleValueActions<User> actions){
        final DatabaseReference ref = mData.getReference(FData.PATH_TO_USERS + "/" + userId );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User myUser = FData.createUser( dataSnapshot );
                actions.onReceiveSingleValue( myUser , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getAllPublications(final FirebaseDatabase database , final ListActions<AbstractPublication> actions){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_SPECIAL_PUBS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<AbstractPublication> pubs = new ArrayList<AbstractPublication>();

                HashMap<String,Object> spubs  = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String,Object> routes = (HashMap<String, Object>) spubs.get("planned_routes");
                ArrayList<String> keys = new ArrayList<String>( routes.keySet() );
                for ( String key : keys ){
                    pubs.add( FData.createPlannedRoute( (HashMap<String, Object>) routes.get(key) ));
                }
                HashMap<String,Object> places = (HashMap<String, Object>) spubs.get("place_promotions");
                keys = new ArrayList<String>(places.keySet());
                for ( String key : keys ){
                    pubs.add( FData.createPlacePromotion( (HashMap<String,Object>)places.get(key) ) );
                }
                DatabaseReference refPubs = database.getReference(FData.PATH_TO_PUBS);
                refPubs.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for ( DataSnapshot snapshot : dataSnapshot.getChildren() ){
                            AbstractPublication pub = snapshot.getValue(Publicacion.class);
                            pubs.add(pub);
                        }
                        actions.onReceiveList( pubs , null );
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        actions.onCancel(databaseError);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getPlannedRoutes( FirebaseDatabase database , final PlannedRoute filter  ,final ListFilteredActions<PlannedRoute,PlannedRoute> actions ){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_SPECIAL_PUBS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<PlannedRoute> pubs = new ArrayList<PlannedRoute>();
                HashMap<String,Object> spubs  = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String,Object> routes = (HashMap<String, Object>) spubs.get("planned_routes");
                ArrayList<String> keys = new ArrayList<String>( routes.keySet() );
                for ( String key : keys ){
                    PlannedRoute plan = FData.createPlannedRoute( (HashMap<String, Object>) routes.get(key) );
                    if( actions.searchCriteria( plan , filter ) ){
                        pubs.add( plan );
                    }
                }
                actions.onReceiveList( pubs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getSpecialPublications( FirebaseDatabase database , final ListActions<SpecialPublication> actions ){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_SPECIAL_PUBS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<SpecialPublication> pubs = new ArrayList<SpecialPublication>();
                HashMap<String,Object> spubs  = (HashMap<String, Object>) dataSnapshot.getValue();
                HashMap<String,Object> routes = (HashMap<String, Object>) spubs.get("planned_routes");
                ArrayList<String> keys = new ArrayList<String>( routes.keySet() );
                for ( String key : keys ){
                    pubs.add( FData.createPlannedRoute( (HashMap<String, Object>) routes.get(key) ));
                }
                HashMap<String,Object> places = (HashMap<String, Object>) spubs.get("place_promotions");
                keys = new ArrayList<String>(places.keySet());
                for ( String key : keys ){
                    pubs.add( FData.createPlacePromotion( (HashMap<String,Object>)places.get(key) ) );
                }
                actions.onReceiveList( pubs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getPublications( FirebaseDatabase database , final ListActions<Publicacion> actions ){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_PUBS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Publicacion> pubs = new ArrayList<Publicacion>();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Publicacion p = ds.getValue(Publicacion.class);
                    pubs.add(p);
                }
                actions.onReceiveList( pubs, ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static <FilterType> void getPublications(FirebaseDatabase database , final FilterType filter , final ListFilteredActions<Publicacion,FilterType> actions){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_PUBS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Publicacion> pubs = new ArrayList<Publicacion>();
                for ( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Publicacion p = ds.getValue(Publicacion.class);
                    if ( actions.searchCriteria( p , filter ) );
                        pubs.add(p);
                }
                actions.onReceiveList( pubs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getReceivedMessages( FirebaseDatabase database , final String userId , final ListActions<Message> actions){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_USERS + "/" + userId + "/mailbox/received" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Message> msgs = new ArrayList<Message>();
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Message msg = FData.createMessage(ds);
                    msgs.add(msg);
                }
                actions.onReceiveList( msgs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static <FilterType> void getReceivedMessages(FirebaseDatabase database , final String userId , final FilterType filter , final ListFilteredActions<Message,FilterType> actions){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_USERS + "/" + userId + "/mailbox/received" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Message> msgs = new ArrayList<Message>();
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Message msg = FData.createMessage(ds);
                    if( actions.searchCriteria( msg , filter ) )
                        msgs.add(msg);
                }
                actions.onReceiveList( msgs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getSentMessages( FirebaseDatabase database , final String userId , final ListActions<Message> actions){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_USERS + "/" + userId + "/mailbox/sent" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Message> msgs = new ArrayList<Message>();
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Message msg = FData.createMessage(ds);
                    msgs.add(msg);
                }
                actions.onReceiveList( msgs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static <FilterType> void getSentMessages(FirebaseDatabase database , final String userId , final FilterType filter , final ListFilteredActions<Message,FilterType> actions){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_USERS + "/" + userId + "/mailbox/sent" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Message> msgs = new ArrayList<Message>();
                for( DataSnapshot ds : dataSnapshot.getChildren() ){
                    Message msg = FData.createMessage(ds);
                    if( actions.searchCriteria( msg , filter ) )
                        msgs.add(msg);
                }
                actions.onReceiveList( msgs , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel(databaseError);
            }
        });
    }

    public static void getRoutes( FirebaseDatabase database , String userId , final ListActions<Route> actions ){
        final DatabaseReference ref = database.getReference(FData.PATH_TO_USERS + "/" + userId + "/history" );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Route> routes = new ArrayList<Route>();
                for ( DataSnapshot singleDatasnapshot : dataSnapshot.getChildren() ){
                    routes.add( FData.createRoute(singleDatasnapshot) );
                }
                actions.onReceiveList( routes , ref );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                actions.onCancel( databaseError );
            }
        });
    }

    public static User createUser( DataSnapshot singleSnapshot ){
        User myUser = new User();
        myUser.setEmail((String)((HashMap<String, Object>)singleSnapshot.getValue()).get("email"));
        myUser.setFirstName( (String)((HashMap<String, Object>)singleSnapshot.getValue()).get("firstName") );
        myUser.setLastName( (String)((HashMap<String, Object>)singleSnapshot.getValue()).get("lastName") );
        myUser.setGender( (String)((HashMap<String, Object>)singleSnapshot.getValue()).get("gender") );
        myUser.setKey( (String)((HashMap<String, Object>)singleSnapshot.getValue()).get("key") );
        myUser.setAge( ((Long)((HashMap<String, Object>)singleSnapshot.getValue()).get("age") ).intValue() );
        myUser.setFriends( (ArrayList<String>) ((HashMap<String, Object>)singleSnapshot.getValue()).get("friends") );
        myUser.setHistory( (ArrayList<Route>) ((HashMap<String, Object>)singleSnapshot.getValue()).get("history") );
        myUser.setGroups( (ArrayList<String>) ((HashMap<String, Object>)singleSnapshot.getValue()).get("groups") );
        return myUser;
    }

    public static Message createMessage( DataSnapshot singlesnapshot ){
        Message msg = new Message();
        HashMap<String,Object> box = (HashMap<String,Object>) singlesnapshot.getValue();
        msg.setMsg( (String) box.get("msg"));
        msg.setSubject( (String) box.get("subject") );
        msg.setReceiver( (String) box.get("receiver") );
        msg.setSender( (String) box.get("sender") );
        return msg;}


    public static PlannedRoute createPlannedRoute( HashMap<String,Object> data ){
        PlannedRoute plannedRoute = new PlannedRoute();
        plannedRoute.setDescripcion( (String)data.get("descripcion") );
        plannedRoute.setDestino( (String)data.get("destino") );
        plannedRoute.setFecha( (String)data.get("fecha") );
        plannedRoute.setNombre( (String)data.get("nombre") );
        plannedRoute.setOrganiza( (String)data.get("organiza") );
        plannedRoute.setOrigen( (String)data.get("origen") );
        plannedRoute.setKey( (String)data.get("key") );
        Log.i("PLANNED ROUTE", "Route: " + plannedRoute);
        return plannedRoute;
    }

    public static PlacePromotion createPlacePromotion( HashMap<String,Object> data ){
        PlacePromotion place = new PlacePromotion();
        place.setOrganiza( (String)data.get("organiza") );
        place.setNombre( (String)data.get("nombre") );
        place.setDescripcion( (String)data.get("descripcion") );
        place.setLugar( (String)data.get("lugar") );
        Log.i("PLACE PROMOTION", "Place: "+place);
        return place;
    }

    public static Route createRoute( DataSnapshot snapshot ){
        HashMap<String,Object> hash = (HashMap<String, Object>) snapshot.getValue();
        return createRoute( hash );
    }

    public static  Route createRoute( HashMap<String,Object> hash ){
        Route route = new Route();
        route.setClima( (String) hash.get("clima") );
        route.setDistancia( (String) hash.get("distancia") );
        route.setDuracion( (String) hash.get("duracion") );
        route.setDestino( (String) hash.get("destino") );
        route.setOrigen( (String) hash.get("origen") );
        route.setFecha( (String) hash.get("fecha") );
        return route;
    }
}
