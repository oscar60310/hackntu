package tech.hackntu.msp.teachtech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import data.callback;
import data.web;
import record.teacher;
import record.testrecord;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Activity mac;

    private JSONObject count_data;
    private JSONObject count_data2;
    private List<Map<String, Object>> count_list;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> count_list2;
    private SimpleAdapter adapter2;
    ListView lv;
    ListView lv2;
    Boolean item_clicked = false;
    Boolean item_clicked2 = false;
    Button okBtn;
    Button cancelBtn;
    Button exitBtn;
    AlertDialog dialog_build;
    TextView textTitle, textContent;
    private Button.OnClickListener exitcl = new Button.OnClickListener() {
        public void onClick(View v) {
            dialog_build.dismiss();
        }
    };
    private Button.OnClickListener okcl = new Button.OnClickListener() {
        public void onClick(View v) {
            // start class
            dialog_build.dismiss();
            teacher teacher = new teacher(classroom,mac);
            teacher.start_class();
        }
    };

    private Button.OnClickListener cancelcl = new Button.OnClickListener() {
        public void onClick(View v) {
            dialog_build.dismiss();
        }
    };
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          //  final String uuid = UUID.randomUUID().toString().replaceAll("-", "");
           // Log.d("uuid",uuid);

        mac = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
             //           .setAction("Action", null).show();
              //  testrecord td = new testrecord(mac);
                //td.testMod();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        ((EditText)findViewById(R.id.search_field)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update_list(((EditText)findViewById(R.id.search_field)).getText()+"", count_data);
            }
        });

        //發送GET查詢APP和數量
        final web webget = new web(new callback.AsyncResponse() {
            @Override
            public void processFinish(JSONObject output) {
                //回傳資料
                count_data = output;
                update_list("",output);
                //new AlertDialog.Builder(mac).setMessage(output.toString()).show();

            }
        }
        );
        // init listview
        count_list = new ArrayList<>();
        adapter = new SimpleAdapter(this, count_list, R.layout.list_items,
                new String[] { "check", "app", "count" }, new int[] {
                R.id.list_check, R.id.app, R.id.count });

        lv = (ListView) findViewById(R.id.app_list);
        lv.setAdapter(adapter);
        // set listview setOnItemClickListener
        lv.setOnItemClickListener(detail);


        // init lv2
        count_list2 = new ArrayList<>();
        adapter2 = new SimpleAdapter(this, count_list2, R.layout.list_items,
                new String[] { "name", "read" }, new int[] {
                R.id.app, R.id.count });
        lv2 = (ListView) findViewById(R.id.app_list2);
        lv2.setAdapter(adapter2);
        // set listview setOnItemClickListener
        lv2.setOnItemClickListener(confirm);
        showConnecting();
        webget.execute("http://teachtechgogo.azurewebsites.net/stepone.php");
       // webget.execute("http://goofydog.me/jellyfish/hack/step1.json");
    }
    void showConnecting()
    {
        Snackbar.make(fab, "網路連線中", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    JSONObject classroom; // 用來儲存教學內容
    private ListView.OnItemClickListener confirm = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //web
            //發送GET查詢 詳細資訊
            final web webget = new web(new callback.AsyncResponse() {
                @Override
                public void processFinish(JSONObject output) {
                    //回傳資料
                    classroom = output;
                    LayoutInflater inflater = mac.getLayoutInflater();
                    dialog_build = new AlertDialog.Builder(MainActivity.this)
                            .setView(inflater.inflate(R.layout.activity_custom_dialog,null)).show();

                    okBtn = (Button)dialog_build.findViewById(R.id.btn_confirm);
                    okBtn.setOnClickListener(okcl);
                    cancelBtn = (Button)dialog_build.findViewById(R.id.btn_cancel);
                    cancelBtn.setOnClickListener(cancelcl);
                    exitBtn = (Button)dialog_build.findViewById(R.id.text_close);
                    exitBtn.setOnClickListener(exitcl);

                    String title = new String();
                    String name = new String();
                    String creator = new String();
                    String about = new String();
                    try{

                        title = output.get("app").toString();
                        name = output.get("name").toString();
                        creator = output.get("creator").toString();
                        about = output.get("about").toString();
                        //Log.d("apppppp",title);
                        textTitle = (TextView) dialog_build.findViewById(R.id.text_title);//text_content
                        textTitle.setText(title + " - " + name);
                        textContent = (TextView) dialog_build.findViewById(R.id.text_content);//text_content
                        textContent.setText("作者：" + creator + "\n說明：" + about);
                    }
                    catch (Exception e)
                    {
                        Log.d("e",e.toString());
                    }
                }
            }
            );
            try
            {
                JSONArray arr = (JSONArray)count_data2.get("data");

                webget.execute(((JSONObject)arr.get(position)).get("url")+"");
            }
            catch (Exception e)
            {

            }





        }
    };
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
       /* if(testclass!=null)
        {
            testclass.destory();
        }*/

    }


    // listview setOnItemClickListener
    private ListView.OnItemClickListener detail = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            item_clicked = true;
         //   Log.d("d","clickedededed");
            lv.setVisibility(View.GONE);
            final web webget = new web(new callback.AsyncResponse() {
                @Override
                public void processFinish(JSONObject output) {
                    //回傳資料 step 2 資料
                    count_data2 = output;
                    update_list_step2(output);

                }
            }
            );
            try {
                JSONArray details = (JSONArray)count_data.get("data");
                //webget.execute("http://teachtechwithsql.azurewebsites.net/steptwo.php?app="+((JSONObject)details.get(position)).get("app"));

                webget.execute("http://goofydog.me/jellyfish/hack/step2_"+((JSONObject)details.get(position)).get("app")+".json");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //當使用者輸入搜尋，篩選資料
    void update_list(String filter,JSONObject data)
    {
        if(item_clicked == true) return;
        count_list.clear();


        if(filter == "")
        {
            //No filter
            try
            {
                JSONArray datas = (JSONArray)data.get("data");

                for(int i=0;i<datas.length();i++)
                {
                    Map<String, Object> map = new HashMap<>();
                    String app = ((JSONObject)datas.get(i)).get("app")+"";
                 //   ImageView img = (ImageView) findViewById(R.drawable.correct)
                    if(app.equals("Facebook") || app.equals("LINE")){

                        map.put("check", R.drawable.correct);
                        map.put("app", ((JSONObject)datas.get(i)).get("app"));
                        map.put("count", ((JSONObject)datas.get(i)).get("count"));
                    }
                    else{
                        map.put("check", R.drawable.cancel);
                        map.put("app", ((JSONObject)datas.get(i)).get("app"));
                        map.put("count", ((JSONObject)datas.get(i)).get("count"));
                    }

                    count_list.add(map);
                }
            }
            catch (Exception e)
            {
                Log.d("e",e.toString());
            }

        }
        else
        {
            //全部轉成大寫比較

             filter = filter.toUpperCase();
            try
            {
                JSONArray datas = (JSONArray)data.get("data");

                for(int i=0;i<datas.length();i++)
                {
                    String app =  ((JSONObject)datas.get(i)).get("app").toString().toUpperCase();
                    if(app.startsWith(filter))
                    {
                        Map<String, Object> map = new HashMap<>();
                        map.put("app", ((JSONObject)datas.get(i)).get("app"));
                        map.put("count", ((JSONObject)datas.get(i)).get("count"));
                        count_list.add(map);

                    }
                }
            }
            catch (Exception e)
            {
                Log.d("e",e.toString());
            }
        }

        /// 給資料更新LIST
        Log.d("list",count_list.size()+"");
        adapter.notifyDataSetChanged();
    }


    // step2 列出資料
    void update_list_step2(JSONObject data)
    {
        //if(item_clicked2 == true) return;
        count_list2.clear();

        try
        {
            JSONArray datas = (JSONArray)data.get("data");

            for(int i=0;i<datas.length();i++)
            {
                Map<String, Object> map = new HashMap<>();
                Random r = new Random();
                if(r.nextBoolean()){
                    map.put("read", "已學習");
                }
                else{
                    map.put("read", "");
                }

                map.put("name", ((JSONObject)datas.get(i)).get("name"));
                count_list2.add(map);
            }
        }
        catch (Exception e)
        {
            Log.d("e",e.toString());
        }

        /// 給資料更新LIST
        Log.d("list",count_list2.size()+"");
        adapter2.notifyDataSetChanged();
    }

    public interface AsyncResponse{void processFinish(JSONObject output);}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_test_teach)
        {

            final web webget = new web(new callback.AsyncResponse() {
                @Override
                public void processFinish(JSONObject output) {
                    //回傳資料

                    testclass = new teacher(output,mac);
                    testclass.start_class();
                    //new AlertDialog.Builder(mac).setMessage(output.toString()).show();

                }
            }
            );
            webget.execute("http://goofydog.me/jellyfish/hack/LINE.json");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    teacher testclass;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

