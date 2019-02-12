package com.example.imyasfinal;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.imyasfinal.Database.Database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ArtistDetail extends AppCompatActivity {

    TextView artist_name, artist_price,artist_description,artist_location,artist_date,artist_time;
    ImageView artist_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String detailId="";

    FirebaseDatabase database;
    DatabaseReference details;


    ArtistPorfolio currentPortfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        database = FirebaseDatabase.getInstance();
        details = database.getReference("ArtistPortfolio");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);

//                btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
//
//                btnCart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        currentPortfolio = new ArtistPorfolio(
//                                detailId,
//                                currentPortfolio.getName(),
//                                numberButton.getNumber(),
//                                currentPortfolio.getPrice(),
//                                currentPortfolio.getDescription()
//                        );
//
//
//
//                Toast.makeText(ArtistDetail.this, "Added to List", Toast.LENGTH_SHORT).show();
//            }
//        });


//        btnCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Database(getBaseContext()).addToCart(new Order(
//                        detailId,
//                        currentPortfolio.getName(),
//                        numberButton.getNumber(),
//                        currentPortfolio.getPrice(),
//                        currentPortfolio.getDescription()
//
//                ));
//
//            }
//        });


        artist_location = (TextView) findViewById(R.id.artist_loc);
        artist_date = (TextView) findViewById(R.id.artist_date);
        artist_time = (TextView) findViewById(R.id.artist_time);
        artist_description = (TextView) findViewById(R.id.artist_description);
        artist_name = (TextView) findViewById(R.id.artist_name);
        artist_price = (TextView) findViewById(R.id.artist_price);
        artist_image = (ImageView) findViewById(R.id.img_artist);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent() !=null)
            detailId = getIntent().getStringExtra("ArtistID");
        if(!detailId.isEmpty())
        {
            getDetailArtist(detailId);
        }
    }

    private void getDetailArtist(String detailId) {
        details.child(detailId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 currentPortfolio = dataSnapshot.getValue(ArtistPorfolio.class);


                Picasso.get().load(currentPortfolio.getImage()).into(artist_image);

                collapsingToolbarLayout.setTitle(currentPortfolio.getName());

                artist_date.setText(currentPortfolio.getCurrentDate());

                artist_time.setText(currentPortfolio.getCurrentTime());

                artist_location.setText("Location: "+currentPortfolio.getLocation());

                artist_price.setText(currentPortfolio.getPrice());

                artist_name.setText(currentPortfolio.getName());

                artist_description.setText(currentPortfolio.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
