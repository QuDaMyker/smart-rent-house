package com.example.renthouse.FCM;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.renthouse.OOP.AccountClass;
import com.example.renthouse.OOP.Device;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class TokenUpdateTask extends AsyncTask<Void, Void, Void> {
    AccountClass user;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            String token = task.getResult();
                            Log.e("Token", token);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference accRef = database.getReference("Accounts");
                            Query query = accRef.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        user = snapshot.getValue(AccountClass.class);
                                        DatabaseReference devicesRef = database.getReference().child("Devices");

                                        devicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot deviceSnapshot : dataSnapshot.getChildren()) {
                                                    Device device = deviceSnapshot.getValue(Device.class);

                                                    if (device.getUser().getEmail().equals(user.getEmail()) && device.getToken().equals(token)) {
                                                        return;
                                                    } else if (device.getUser().getEmail().equals(user.getEmail())) {
                                                        deviceSnapshot.getRef().child("token").setValue(token);
                                                        return;
                                                    }
                                                }
                                                DatabaseReference newDeviceRef = devicesRef.push();
                                                newDeviceRef.child("user").setValue(user);
                                                newDeviceRef.child("token").setValue(token);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.e("TAG", "Error checking device token in Firebase", databaseError.toException());
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                                }
                            });
                        }
                    });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
