package com.saivittalb.covsense.dbhelpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.saivittalb.covsense.NearbyTrackingService;
import com.saivittalb.covsense.R;
import com.saivittalb.covsense.models.Meet;
import com.saivittalb.covsense.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class FirebaseDatabaseHelper extends NearbyTrackingService {
    private static final String TAG = "FirebaseDatabaseHelper";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
    private CollectionReference usersCollection, meetingsCollection;

    private FirebaseDatabaseHelper() {
        usersCollection = db.collection("users");
        meetingsCollection = db.collection("users_meetings");
    }

    public static FirebaseDatabaseHelper getInstance() {
        return firebaseDatabaseHelper;
    }

    public interface DataStatus {
        void Success();

        void Fail();
    }
    
    public void addUser(User user, final Context context, final DataStatus status) {
        final DocumentReference newUserRef = usersCollection.document();
        newUserRef.set(user)
                .addOnSuccessListener(aVoid -> {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(context.getString(R.string.UID), newUserRef.getId());
                    Log.d(TAG, "Added: " + newUserRef.getId());
                    editor.commit();
                    status.Success();
                })
                .addOnFailureListener(e -> status.Fail()).addOnCanceledListener(() -> status.Fail());
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        Log.d(TAG,"Latitude: " + location.getLatitude());
    }

    public void addMeeting(String myUserUID, String metUserUID, Meet meet, final DataStatus status) {
        meetingsCollection.document(myUserUID).collection("meetings").document(metUserUID).set(meet)
                .addOnSuccessListener(aVoid -> status.Success())
                .addOnFailureListener(e -> status.Fail());
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        DocumentReference meetToUpdate = meetingsCollection.document(myUserUID).collection("meetings").document(metUserUID);
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("latitude", String.valueOf(location.getLatitude()));
        updatedFields.put("longitude", String.valueOf(location.getLongitude()));
        updatedFields.put("date", df.format(time));
        meetToUpdate.update(updatedFields)
                .addOnSuccessListener(aVoid -> status.Success())
                .addOnFailureListener(e -> status.Fail());
        Log.d(TAG, "Added meeting between : " + myUserUID + "and" + metUserUID);
    }

    public void updateMeetingEnding(String myUserID, String metUserUID, FieldValue endingTimestamp, final DataStatus status) {
        DocumentReference meetToUpdate = meetingsCollection.document(myUserID).collection("meetings").document(metUserUID);
        long duration = contactDuration;
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("lostTimestamp", endingTimestamp);
        updatedFields.put("status", "ended");
        updatedFields.put("duration", String.valueOf(duration));
        meetToUpdate.update(updatedFields)
                .addOnSuccessListener(aVoid -> status.Success())
                .addOnFailureListener(e -> status.Fail());
    }

    public void updateUserStatus(String myUserID, String newStatus, final DataStatus status) {
        DocumentReference userToUpdate = usersCollection.document(myUserID);
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("status", newStatus);
        userToUpdate.update(updatedFields)
                .addOnSuccessListener(aVoid -> status.Success())
                .addOnFailureListener(e -> status.Fail());
    }

    public void updateDeviceToken(String myUserID, String deviceToken, final DataStatus status) {
        DocumentReference userToUpdate = usersCollection.document(myUserID);
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("token", deviceToken);
        userToUpdate.update(updatedFields)
                .addOnSuccessListener(aVoid -> status.Success())
                .addOnFailureListener(e -> status.Fail());

    }

    public void getEncounteredUserInfo(String myUserID, String metUserUID) {

        DocumentReference meetToUpdate = meetingsCollection.document(myUserID).collection("meetings").document(metUserUID);
        Query query = usersCollection.whereEqualTo("users", metUserUID);

        meetToUpdate
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                });
    }
}
