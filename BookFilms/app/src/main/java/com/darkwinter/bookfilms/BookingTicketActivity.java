package com.darkwinter.bookfilms;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BookingTicketActivity extends AppCompatActivity {

    private Spinner Cinemas, Seats, Times, Dates;
    private Button Booking;
    private DatabaseReference mFilmRef, mCineRef, mRoomRef;
    private Films film;
    private TextView dates, cine, seat, slot;
    private String Room;
    private ArrayList<String> ListCinemas = new ArrayList<>();
    private ArrayList<String> ListDates = new ArrayList<>();
    private ArrayList<String> ListSlots = new ArrayList<>();
    private ArrayList<String> ListSeats = new ArrayList<>();
    private ArrayAdapter<String> CinemaAdater, DateAdapter, SlotAdapter, SeatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_ticket);
        film = (Films) getIntent().getSerializableExtra("Film");
        new AsyncLoadDatesFirebase().execute();
        setupWidget();
        Dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tmpDate = adapterView.getItemAtPosition(i).toString();
                dates.setText(tmpDate);
                loadCinemasFromFirebase(tmpDate);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Cinemas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cinema = adapterView.getItemAtPosition(i).toString();
                cine.setText(cinema);
                loadSlotFirebase(cinema, film.getId(), dates.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Times.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String slot1 =adapterView.getItemAtPosition(i).toString();
                slot.setText(slot1);
                Room = loadRoomFirebase(cine.getText().toString(),film.getId(),dates.getText().toString(),slot1);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),"====room"+Room+""+cine.getText()+", "+film.getId()+","+dates.getText()+"+"+slot1,Toast.LENGTH_LONG).show();
                //loadSeatsFromFirebase(room , dates.getText().toString(), slot1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Seats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                seat.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void loadCinemasFromFirebase(String date){
        ListCinemas.clear();
        mFilmRef = FirebaseDatabase.getInstance().getReference().child("Films").child(film.getId()).child("Dates").child(date);
        mFilmRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String Cinema = dataSnapshot.getKey();
                ListCinemas.add(Cinema);
                CinemaAdater.notifyDataSetChanged();
                SlotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void loadSeatsFromFirebase(String room, final String date, String time){
        ListSeats.clear();
        mRoomRef = FirebaseDatabase.getInstance().getReference()
                .child("Rooms").child(room)
                .child("Dates").child(date).child(time);
        mRoomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String isEmp = dataSnapshot.getValue().toString();
                if (isEmp.equals("1")){
                    String seat = dataSnapshot.getKey().toString();
                    ListSeats.add(seat);
                    notifychange();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getKey().toString();
                if(value.equals("0")){
                    ListSeats.remove(dataSnapshot.getKey().toString());
                    notifychange();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ListSeats.remove(dataSnapshot.getKey().toString());
                notifychange();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String loadRoomFirebase(String cine, String Film, String date, String time){
        final String[] room = {""};
        mCineRef = FirebaseDatabase.getInstance().getReference()
                .child("Cinemas").child(cine)
                .child("Films").child(Film)
                .child(date).child(time);
        mCineRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                room[0] = dataSnapshot.getKey();
                Toast.makeText(getApplicationContext(),"====vb"+ room[0],Toast.LENGTH_LONG).show();
                notifychange();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return room[0];
    }

    private class AsyncLoadDatesFirebase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            mFilmRef = FirebaseDatabase.getInstance().getReference().child("Films").child(film.getId()).child("Dates");
            mFilmRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Date tmp = null;
                    try {
                        tmp = sdf.parse(dataSnapshot.getKey());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(Calendar.getInstance().getTime().before(tmp)) {
                        ListDates.add(dataSnapshot.getKey());
                        notifychange();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    ListDates.remove(dataSnapshot.getKey());
                    notifychange();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    private void loadSlotFirebase(String cine, String Film, String date){

        ListSlots.clear();
        mCineRef = FirebaseDatabase.getInstance().getReference()
                .child("Cinemas").child(cine)
                .child("Films").child(Film)
                .child(date);
        mCineRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String slot = dataSnapshot.getKey();
                ListSlots.add(slot);
                notifychange();

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ListSlots.remove(dataSnapshot.getKey());
                notifychange();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void setupWidget(){
        Cinemas = (Spinner) findViewById(R.id.spinCinema);
        Seats = (Spinner)findViewById(R.id.spinSeat);
        Times = (Spinner)findViewById(R.id.spinTime);
        Dates = (Spinner) findViewById(R.id.spinDate);
        dates = (TextView) findViewById(R.id.textDates);
        Booking = (Button) findViewById(R.id.btnBookAction);
        cine = (TextView)findViewById(R.id.textCinema);
        seat = (TextView)findViewById(R.id.textSeat);
        slot = (TextView)findViewById(R.id.textTime);

        DateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListDates);
        DateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Dates.setAdapter(DateAdapter);

        CinemaAdater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListCinemas);
        CinemaAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cinemas.setAdapter(CinemaAdater);

        SlotAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListSlots);
        SlotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Times.setAdapter(SlotAdapter);

        SeatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ListSeats);
        SeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Seats.setAdapter(SeatAdapter);
    }
    private void notifychange(){
        DateAdapter.notifyDataSetChanged();
        CinemaAdater.notifyDataSetChanged();
        SlotAdapter.notifyDataSetChanged();
        SeatAdapter.notifyDataSetChanged();
    }
}
