package com.darkwinter.bookfilms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ConfirmInfoActivity extends AppCompatActivity {

    private TextView Film, Date, Cine, Slot, Seat;
    private String Room;
    private Button Confirm;
    private DatabaseReference mUserRef;
    private DatabaseReference mRoomRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_info);
        setupWidget();
        Intent receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getBundleExtra("infoticket");
        Film.setText(bundle.getString("Film"));
        Date.setText(bundle.getString("Date"));
        Cine.setText(bundle.getString("Cinema"));
        Slot.setText(bundle.getString("Slot"));
        Room = bundle.getString("Room");
        Seat.setText(bundle.getString("Seat"));

    }

    private void setupWidget(){
        Film = (TextView)findViewById(R.id.txtFilm);
        Date = (TextView)findViewById(R.id.txtDate);
        Cine = (TextView)findViewById(R.id.txtCinema);
        Slot = (TextView)findViewById(R.id.txtSlot);
        Seat = (TextView)findViewById(R.id.txtSeat);
        Confirm = (Button)findViewById(R.id.btnConfirm);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserTicket();
            }
        });
    }

    private void updateUserTicket() {
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Tickets");
        mRoomRef = FirebaseDatabase.getInstance().getReference().child("Roms").child(Room)
                .child("Dates").child(Date.getText().toString())
                .child(Slot.getText().toString());
        HashMap<String, Object> tmp = new HashMap<>();
        tmp.put(Seat.getText().toString(), "0");
        mRoomRef.updateChildren(tmp);
        HashMap<String, Object> ticket = new HashMap<>();
        ticket.put("Film", Film.getText().toString());
        ticket.put("Date", Date.getText().toString());
        ticket.put("Cinema", Cine.getText().toString());
        ticket.put("Slot", Slot.getText().toString());
        ticket.put("Room", Room);
        ticket.put("Seat", Seat.getText().toString());
        mUserRef.push().updateChildren(ticket).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent fini = new Intent(ConfirmInfoActivity.this, MainActivity.class);
                    startActivity(fini);
                    finish();
                }
            }
        });
    }
}
