package org.me.gcu.equakestartercode;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SingleMenuItemActivity extends Activity {
	
	// XML node keys
	//static final String KEY_NAME = "pubDate";
	static final String KEY_COST = "category";
	static final String KEY_DESC = "description";
    static final String KEY_LAT = "lat";
    static final String KEY_LONG = "long";
    static final String KEY_MAG = "magnitude";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Toast.makeText(getApplicationContext(),
                    "In landscape mode: Loading another XML file",
                    Toast.LENGTH_SHORT).show();

        } else {
            //if (landscape_flag == true)
            //{
            Toast.makeText(getApplicationContext(),
                    "Portrait mode: Loading portrait xml",
                    Toast.LENGTH_SHORT).show();
            //}

            // In portrait
        }

        TextView t = (TextView)findViewById(R.id.tv);
// setting gravity to "center"
        t.setGravity(Gravity.CENTER);
        t.setBackgroundResource(R.drawable.eq2);
        //t.setText("FOO");

        // getting intent data
        Intent in = getIntent();
        
        // Get XML values from previous intent
        //String name = in.getStringExtra(KEY_NAME);
        String cost = in.getStringExtra(KEY_COST);
        String description = in.getStringExtra(KEY_DESC);
        //String lat = in.getStringExtra(KEY_LAT);
        //String longi = in.getStringExtra(KEY_LONG);
        
        // Displaying all values on the screen

        String[] separated = cost.split(";");
        separated[1] = separated[1].trim();

        String[] separated2 = separated[1].split(":");
        String location = separated2[1];
        t.setText(location.trim());

        String[] lat_long_array = separated[2].split(":");
        String lat_long = lat_long_array[1].trim();

        String[] lat_long_separated = lat_long.split(",");
        String lati = lat_long_separated[0];
        String longi = lat_long_separated[1];

        separated[4] = separated[4].trim();
        String[] mag_array = separated[4].split(":");
        String magnitude = (mag_array[1].trim());

        String[] depth_array = separated[3].split(":");
        String depth = depth_array[1];

        String[] date_time_array = separated[0].split(":", 2);
        String date_time = date_time_array[1];

        Log.d("latitude3", lati);
        Log.d("longitude3", longi);
        Log.d("magnit3", magnitude);
        Log.d("depth4", depth);
        Log.d("date_time", date_time);

        Log.d("location3",location.trim());
        Log.d("where", separated[1].trim());

        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblCost = (TextView) findViewById(R.id.cost_label);
        TextView lblDesc = (TextView) findViewById(R.id.description_label);


        lblName.setText("Date and time: "+date_time.trim());
        lblCost.setText("Depth: "+depth);
        lblDesc.setText("Magnitude: "+magnitude);

/*

        String[] lat_long_array = separated[2].split(":");
        String lat_long = lat_long_array[1].trim();

        String[] lat_long_separated = lat_long.split(",");
        String lati = lat_long_separated[0];
        String longi = lat_long_separated[1];

        separated[4] = separated[4].trim();
        String[] mag_array = separated[4].split(":");
        String magnitude = (mag_array[1].trim());

        Log.d("latitude3", lati);
        Log.d("longitude3", longi);

        Log.d("location3",location.trim());
        Log.d("where", separated[1].trim());
*/
        Button button1 = (Button) findViewById(R.id.button_map);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),
                        "Displaying earthquake location",
                        Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                in.putExtra(KEY_LAT, lati);
                in.putExtra(KEY_LONG, longi);
                in.putExtra(KEY_MAG, magnitude);
                startActivity(in);

                //Intent intent = new Intent(view.getContext(), MapsActivity.class);
                //view.getContext().startActivity(intent);

                // Creates an Intent that will load a map of San Francisco



            }
        });

    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(400);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }
}
