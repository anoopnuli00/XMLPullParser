package com.example.xmlpullparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            ArrayList<HashMap<String, String>> userList = new ArrayList<>();
            HashMap<String,String> user = new HashMap<>();
            ListView lv = (ListView) findViewById(R.id.user_list);
            InputStream istream = getAssets().open("userdetails.xml");
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(istream,null);
            String tag = "" , text = "";
            int event = parser.getEventType();
            while (event!= XmlPullParser.END_DOCUMENT){
                tag = parser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(tag.equals("user"))
                            user = new HashMap<>();
                        break;
                    case XmlPullParser.TEXT:
                        text=parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (tag){
                            case "name": user.put("name",text);
                                break;
                            case "trace": user.put("trace",text);
                                break;
                            case "user":
                                if(user!=null)
                                    userList.add(user);
                                break;
                        }
                        break;
                }
                event = parser.next();
            }
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, userList, R.layout.list_row,new String[]{"name","trace"}, new int[]{R.id.name,R.id.trace});
            lv.setAdapter(adapter);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
