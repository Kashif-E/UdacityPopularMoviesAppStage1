package com.infinity.popularmoviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView votes;
    private TextView plot;
    private TextView rating;
    private TextView releaseDate;
    private ImageView backdrop;
    private ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail);
        rating=findViewById (R.id.rating);
        plot=findViewById (R.id.synopsis);
        releaseDate=findViewById (R.id.releaseDate);
        backdrop=findViewById (R.id.backdrop);
        poster=findViewById (R.id.MoviePoster);
        Intent intent=getIntent();
        loadData(intent);
    }

    private void loadData(Intent intent) {

       if(intent.hasExtra ("EXTRA_BACK_DROP")&& intent.hasExtra ("EXTRA_RELEASE_DATE")&& intent.hasExtra ("EXTRA_TITLE")&& intent.hasExtra ("EXTRA_VOTE")&& intent.hasExtra ("EXTRA_PLOT")&& intent.hasExtra ("EXTRA_POSTER") )
       {
           String BackDropUrl= intent.getStringExtra ("EXTRA_BACK_DROP");
           loadImage(BackDropUrl,backdrop);
           String posterUrl= intent.getStringExtra ("EXTRA_POSTER");
           loadImage(posterUrl,poster);
           //////////////////////////////////////////////////////
           String mplot = intent.getStringExtra ("EXTRA_PLOT");
           plot.append (mplot);

           String mVotes= intent.getStringExtra ("EXTRA_VOTE");
           rating.append (mVotes);

           String mTitle = intent.getStringExtra ("EXTRA_TITLE");
           getSupportActionBar ().setTitle (mTitle);
           String mReleaseDate=intent.getStringExtra ("EXTRA_RELEASE_DATE");
           releaseDate.append (mReleaseDate);
       }
       else
       {
           Toast.makeText (this, "DATA MISSING", Toast.LENGTH_SHORT).show ();
       }

    }

    private void loadImage(final  String Url, final ImageView imageView) {


        ////////////////////////////////////////
        Picasso.get ().load(Url).fetch (new Callback () {
            @Override
            public void onSuccess() {
                imageView.setAlpha (0f);
                Picasso.get ().load(Url).into(imageView);
                imageView.animate().setDuration(1000).rotationX (360).alpha (1).start ();

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace ();
            }
        });
    }

}
