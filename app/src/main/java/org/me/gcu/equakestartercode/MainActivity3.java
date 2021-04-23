package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

        boolean landscape_flag = false;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            Toast.makeText(getApplicationContext(),
                    "In landscape mode: Loading another XML file",
                    Toast.LENGTH_SHORT).show();
            landscape_flag = true;
        } else {
            //if (landscape_flag == true)
            //{
                Toast.makeText(getApplicationContext(),
                        "Portrait mode: Loading portrait xml",
                        Toast.LENGTH_SHORT).show();
            //}
            landscape_flag = false;
            // In portrait
        }

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Displaying updated list of earthquake details",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), MainActivity.class);



                view.getContext().startActivity(intent);}
        });
    }
}