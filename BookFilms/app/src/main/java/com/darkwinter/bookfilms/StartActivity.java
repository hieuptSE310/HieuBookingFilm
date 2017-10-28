package com.darkwinter.bookfilms;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    private static ViewPager viewPager;
    private boolean check;
    private Toolbar mToolbar;
    private SwipeActivity swipeActivity;
    private LinearLayout dotsLayout;
    private Context context;
    private TextView[] dots;
    int[] colorsInactive, colorsActive;
    private static String[] array_movies;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListeners;
    private ArrayList<Films> ListofFilms = new ArrayList<>();

    private DatabaseReference mFilmReference;
    private FirebaseUser currentUser;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //mToolbar = (Toolbar) findViewById();
        mToolbar = findViewById(R.id.main_tool_bar);
//        mToolbar.setTitle("Booking");
//        Toast.makeText(MainActivity.this, R.id.main_tool_bar+"", Toast.LENGTH_LONG).show();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Booking");
        swipeActivity = new SwipeActivity(this, ListofFilms);
        context = this.getApplicationContext();
        viewPager = findViewById(R.id.view_paper);
        dotsLayout = findViewById(R.id.layoutDots);
        //Show slider
        array_movies = viewPager.getResources().getStringArray(R.array.movie_eng_name);
        swipeActivity = new SwipeActivity(this, ListofFilms);
        //swipeAdapter = new SwipeAdapter(this, array_movies);
        viewPager.setAdapter(swipeActivity);
        //set color array for dots and dots array size, must be put before call addBottomDots to prevent null
        colorsInactive = viewPager.getResources().getIntArray(R.array.array_dot_inactive);
        colorsActive = viewPager.getResources().getIntArray(R.array.array_dot_active);
        dots = new TextView[colorsActive.length];
        //create dots at bottom
        addBottomDots(context);

        setupDB();
        setViewPagerAction();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.mnLogout){
            FirebaseAuth.getInstance().signOut();
            Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }else if(item.getItemId() == R.id.mnProfile){
            Intent profileIntent  = new Intent(StartActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
        }
        return true;
    }
    private void setupDB(){
        mFilmReference = FirebaseDatabase.getInstance().getReference().child("Films");
        mFilmReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String id = data.getKey();
                    String name = data.child("Name").getValue().toString();
                    String Image = data.child("Picture").child("image1").getValue().toString();
                    String Descrip = data.child("Description").getValue().toString();
                    String Duration = data.child("Duration").getValue().toString();
                    String Producer = data.child("Producer").getValue().toString();
                    String Rating = data.child("Rating").getValue().toString();
                    String Trailer = data.child("Trailers").getValue().toString();
                    Films film = new Films(id, name, Image, Descrip, Duration, Producer, Rating, Trailer);
                    for (int i = 0; i < ListofFilms.size(); i++) {
                        if (id.toString().equals(ListofFilms.get(i).getId())) {
                            Films film1 = ListofFilms.get(i);
                            film1.setId(id);
                            film1.setName(name);
                            film1.setImage(Image);
                            film1.setDescrip(Descrip);
                            film1.setDuration(Duration);
                            film1.setProducer(Producer);
                            film1.setRating(Rating);
                            film1.setTrailer(Trailer);
                            check = true;
                            break;
                        }
                    }
                    if (check) {
                        check = false;
                    } else {
                        ListofFilms.add(film);
                    }
                    swipeActivity.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /**
     * All action related to view paper (slider)
     * */
    private void setViewPagerAction(){

        //set action for dot state change
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setActive(position, 5, dots);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Set the on click listener for view paper
        final GestureDetector tapGestureDetector = new GestureDetector(this, new StartActivity.TapGestureListener());
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tapGestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
    }
    public static ViewPager getViewPager() {
        return viewPager;
    }



    public static String[] getArray_movies() {
        return array_movies;
    }

    /**
     * Set the color for the dots
     * */
    private void setActive(int position, int length, TextView[] dot) {
        for (int i = 0; i < dots.length; i++) {
            dots[i].setTextColor(colorsInactive[position]);
            if (dots.length > 0)
                dots[position].setTextColor(colorsActive[position]);
        }
    }

    /**
     * Function to add dot to view paper for scroll
     * */
    private void addBottomDots(Context context) {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews();
        }
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(context);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
    }

    /**
     * Class help on click on view paper
     * */
    class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int pos = viewPager.getCurrentItem();
            //System.out.println(pos);//return the current item position
            //System.out.println(array_movies[pos]);
            Intent info = new Intent(StartActivity.this, MovieInfoActivity.class);
            Films chose = ListofFilms.get(pos);
            info.putExtra("Film", chose);
            startActivity(info);
            return false;
        }
    }

}