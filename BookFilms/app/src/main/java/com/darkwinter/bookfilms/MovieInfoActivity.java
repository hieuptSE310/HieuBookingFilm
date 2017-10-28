package com.darkwinter.bookfilms;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MovieInfoActivity extends AppCompatActivity {


    ImageView imageView;
    //private TypedArray image;
    TextView movie_des, movieName;
    Button btnTrailer, btnBooking;
    private FirebaseAuth mAuth;

    private void setupRef(){
        //mStorageRef = Fireb
    }

    private void setupWidget(){
        imageView= findViewById(R.id.movie_view);
        movie_des = findViewById(R.id.txtMovieDes);
        movieName = findViewById(R.id.txtMoiveName);
        btnTrailer = findViewById(R.id.btnTrailer);
        btnBooking = findViewById(R.id.btnBooking);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        mAuth = FirebaseAuth.getInstance();
        setupWidget();
        final Films film = (Films) getIntent().getSerializableExtra("Film");
        //Set movie poster
        //image = getResources().obtainTypedArray(R.array.movie_poster);
        Picasso.with(this.getApplicationContext()).load(film.getImage()).into(imageView);
        //Set movie description, name
        movie_des.setText(film.getDescrip());
        movieName.setText(film.getName());
        movie_des.setMovementMethod(new ScrollingMovementMethod());
        //Set trailer button
        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String url = getResources().getStringArray(R.array.movie_trailer)[pos];
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://")));
            }
        });
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                //Toast.makeText(MovieInfoActivity.this, currentUser.getUid(), Toast.LENGTH_LONG).show();
                if(currentUser == null){
                    Intent loginIntent = new Intent(MovieInfoActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }else if(currentUser!=null){
                    Intent booking = new Intent(MovieInfoActivity.this, BookingTicketActivity.class);
                    booking.putExtra("Film", film);
                    startActivity(booking);
                }
            }
        });




    }
}
