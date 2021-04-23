package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.me.gcu.equakestartercode.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//public class MainActivity extends AppCompatActivity implements OnClickListener
public class MainActivity extends ListActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String text_tag = "";
    private String url1="";
    private String lat = "";
    private String longi = "";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    private String KEY_ITEM = "item"; // parent node
    static final String KEY_ID = "title";
    static final String KEY_NAME = "description";
    static final String KEY_COST = "pubDate";
    static final String KEY_DESC = "category";
    static final String KEY_LAT = "geo:lat";
    static final String KEY_LONG = "geo:long";



    //ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //String xml = parser.getXmlFromUrl(urlSource); // getting XML
        Log.d("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        //rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);

        startProgress();

        Log.d("\nFinished", "Fininshed");
        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Log.d("MyTag","after startButton");
        // More Code goes here
    }

    public void onClick(View aview)
    {
        Log.d("MyTag","in onClick");
        startProgress();
        Log.d("MyTag","after startProgress");
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.d("MyTag","in run");

            try
            {
                Log.d("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.d("MyTag","after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.d("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.d("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {

                String description;
                public void run() {

                    Log.d("UI thread", "I am the UI thread");

                    ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
                    XMLParser parser = new XMLParser();
                    Document doc = parser.getDomElement(result); // getting DOM element
                    NodeList nl = doc.getElementsByTagName(KEY_ITEM);
                    // looping through all item nodes <item>
                    for (int i = 0; i < nl.getLength(); i++) {
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        Element e = (Element) nl.item(i);
                        // adding each child node to HashMap key => value
                        map.put(KEY_ID, parser.getValue(e, KEY_ID));

                        description = parser.getValue(e, KEY_NAME);

                        String[] separated = description.split(";");
                        separated[1] = separated[1].trim();
                        String[] separated2 = separated[1].split(":");
                        String location = separated2[1];
                        location = location.trim();

                        separated[4] = separated[4].trim();
                        String[] mag_array = separated[4].split(":");
                        String magnitude = (mag_array[1].trim());

                        //map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
                        map.put(KEY_NAME, location);
                        map.put(KEY_COST, "Magnitude: " + magnitude);
                        //map.put(KEY_COST, "Magnitude" + parser.getValue(e, KEY_COST));

                        map.put(KEY_DESC, description);
                        //map.put(KEY_LAT, parser.getValue(e, KEY_LAT));
                        //map.put(KEY_LONG, parser.getValue(e, KEY_LONG));

                        lat = parser.getValue(e, KEY_LAT);
                        longi = parser.getValue(e, KEY_LONG);

                        // adding HashList to ArrayList
                        menuItems.add(map);
                    }


                    //rawDataDisplay.setText(text_tag);
                    // Adding menuItems to ListView
                    ListAdapter adapter = new SimpleAdapter(MainActivity.this, menuItems,
                            R.layout.list_item,
                            //new String[] { KEY_NAME, KEY_DESC, KEY_COST }, new int[] {
                            new String[] { KEY_NAME, KEY_COST }, new int[] {
                            //R.id.name, R.id.desciption, R.id.cost });
                            R.id.name, R.id.desciption });

                    setListAdapter(adapter);

                    // selecting single ListView item
                    ListView lv = getListView();

                    Log.d("oneLat", lat);
                    Log.d("onelong", longi);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            HashMap<String, String> hm = menuItems.get(position);
                            String total_desc = hm.get(KEY_DESC);

                            // getting values from selected ListItem
                            String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                            //String cost = ((TextView) view.findViewById(R.id.cost)).getText().toString();
                            //String description5 = ((TextView) view.findViewById(R.id.desciption)).getText().toString();

                            Toast.makeText(getApplicationContext(),
                                    "Displaying details of selected earthquake",
                                    Toast.LENGTH_LONG).show();

                            // Starting new intent
                            Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
                            //in.putExtra(KEY_NAME, name);
                            in.putExtra(KEY_NAME, description);

                            //in.putExtra(KEY_COST, cost);
                            in.putExtra(KEY_DESC, total_desc);
                            startActivity(in);

                        }
                    });


                }
            });
        }

    }


}