package com.example.testing.bitcoincoursenavigation;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.testing.bitcoincoursenavigation.Fragments.BlankFragment.BlankFragment;
import com.example.testing.bitcoincoursenavigation.Fragments.ConvertFragment.ConvertCryptoListFragment;
import com.example.testing.bitcoincoursenavigation.Fragments.ConvertFragment.ConvertFragment;
import com.example.testing.bitcoincoursenavigation.Fragments.ListFragment.ListItemFragment;
import com.example.testing.bitcoincoursenavigation.Fragments.SelectFragment.SelectFragment;
import com.example.testing.bitcoincoursenavigation.Fragments.SortFragment.Sort;
import com.example.testing.bitcoincoursenavigation.Fragments.ThemesFragment.ThemesFragment;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoData;
import com.example.testing.bitcoincoursenavigation.ObjectsPojo.CryptoWallet;
import com.example.testing.bitcoincoursenavigation.adapter.CryptoRecycleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SelectFragment.OnListFragmentInteractionListener,
        Sort.OnSortInteraction,
        ConvertCryptoListFragment.OnListConvertListener,
        ThemesFragment.OnThemeListener
{
    private RecyclerView CourseList;
    private RecyclerView.Adapter CAdapter;
    private RecyclerView.LayoutManager CLayoutManager;
    private SharedPreferences Spref;
    private SparseBooleanArray SelectedValues;
    public static List<CryptoWallet> Courses;
    private Handler mHandler;
    private android.support.v4.app.Fragment fragment;
    private android.support.v4.app.Fragment sortFragment;
    private final String KEY_SAVE = "CryptoSave";
    private final String SORT_TAG = "Sort_Fragment";
    private final String DAY_UP = "DAY_UP";
    private String SortKey;
    private Menu menu;
    public static Drawable background;
    private int backgroundId;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        backgroundId = R.drawable.background_blue;
        LoadWallets();
        CourseInit();
        UpdatingThread();

        this.setContentView(R.layout.activity_main);
        background = (Drawable) findViewById(R.id.main_layout).getBackground();
        RecycleCryptoInit(Courses);
        navInit(true);
        FabClickListener();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void UpdatingThread()
    {
        mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LoadDataFromAPI();
                Toast.makeText(MainActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();

            }
        };
        mHandler.postAtTime(runnable, System.currentTimeMillis()+1000);
        mHandler.postDelayed(runnable, 0);

    }
    private void CourseInit()
    {
        if(Courses == null) {
            Courses = getSelectedCryptos(SelectedValues);
        }
    }
    private  void LoadDataFromAPI()
    {
        final String sourceURL = "https://api.coinmarketcap.com/v2/ticker/";
        JsonObjectRequest DataRequest = new JsonObjectRequest(
                Request.Method.GET, sourceURL,
                null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mData = response.getJSONObject("data");
                    int Max = MainActivity.Courses.size();
                    List<CryptoWallet> ShortCourses = MainActivity.Courses;
                    for(int i = 0; i < Max; i++) {
                        switch (ShortCourses.get(i).getShortName()) {
                            case "BTC":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "1", mData);
                                break;

                            case "ETH":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "1027", mData);
                                break;
                            case "EOS":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "1765", mData);
                                break;
                            case "ADA":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "2010", mData);
                                break;
                            case "LTC":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "2", mData);
                                break;
                            case "IOTA":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "1720", mData);

                                break;
                            case "XEM":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "873", mData);
                                break;
                            case "XMR":
                                CryptoData.SetValues(MainActivity.Courses.get(i), "328", mData);
                                break;
                        }
                    }

                    if(CAdapter != null)
                    {
                        CAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue MQueue = Volley.newRequestQueue(this);
        MQueue.add(DataRequest);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        LoadWallets();
    }

    public void RecycleCryptoInit(List<CryptoWallet> cryptoWalletList)
    {
        CourseList = (RecyclerView) findViewById(R.id.crypto_recycle);
        if(CourseList != null && cryptoWalletList != null) {
            CourseList.setHasFixedSize(true);
            CLayoutManager = new LinearLayoutManager(this);
            CourseList.setLayoutManager(CLayoutManager);
            CAdapter = new CryptoRecycleAdapter(cryptoWalletList);
            CourseList.setAdapter(CAdapter);
            CourseList.addOnScrollListener(new RecyclerView.OnScrollListener()
            {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                {
                    super.onScrolled(recyclerView, dx, dy);
                    if(fab != null) {
                        if (dy > 0) {
                            fab.hide();
                        }
                        else {
                            fab.show();
                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState)
                {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
    }
    private FloatingActionButton fab;

    private SparseBooleanArray SelectInit(SparseBooleanArray BoolArray)
    {
        if(BoolArray == null) {
            BoolArray = new SparseBooleanArray(CryptoWallet.GetCryptos().size());
            for(int i = 0; i < CryptoWallet.GetCryptos().size(); i++) {
                BoolArray.put(i, true);
            }
        }
        return BoolArray;
    }
    public void navInit(boolean Toggle)
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(Toggle) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    private List<CryptoWallet> getSelectedCryptos(SparseBooleanArray Selection)
    {
        List<CryptoWallet> SelectedCryptoWallets = new ArrayList<CryptoWallet>();
        for (int i = 0; i < SelectInit(Selection).size(); i++) {
            if(SelectInit(Selection).get(i)) {
                SelectedCryptoWallets.add(CryptoWallet.GetCryptos().get(i));
            }
        }
        return SelectedCryptoWallets;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        SaveWallets();
        saveSort();
    }


    public void FabClickListener()
    {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                fragment = new SelectFragment();
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.select_fragment, fragment, "Select_Fragment");
              //  ft.addToBackStack(null);
                ft.commit();
                if(fab != null) {
                    fab.hide();
                }

          //      setToolbar(false);
                fragment.setRetainInstance(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                navInit(false);

            }
        });
    }
    public void OpenSort()
    {
        sortFragment = new Sort();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_sort, sortFragment, SORT_TAG);
        ft.commit();
        sortFragment.setRetainInstance(true);
        setMarginList(70);
    }
    public void OpenConvert()
    {
        Fragment convertFragment = new ConvertFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.select_fragment, convertFragment, "Convert");
        ft.commit();

        convertFragment.setRetainInstance(true);
        if(fab != null) {
            fab.hide();
            fab = null;
        }
    }
    private void setMarginList(int dpValue)
    {
       // int dpValue = 70; // margin in dips
        float d = this.getResources().getDisplayMetrics().density;

        int margin = (int)(dpValue * d); // margin in pixels
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)CourseList.getLayoutParams();
        layoutParams.setMargins(0,margin,0,0);
    }
    public void LoadWallets()
    {
        Spref = getSharedPreferences(KEY_SAVE, MODE_PRIVATE);
        if(Spref != null) {
            SelectedValues = new SparseBooleanArray(CryptoWallet.GetCryptos().size());
            for(int i = 0; i < CryptoWallet.GetCryptos().size(); i++) {
                SelectedValues.put(i, Spref.getBoolean(Integer.toString(i), true));
            }
            backgroundId = Spref.getInt("BackgroundId", R.drawable.background_blue);
            background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), backgroundId));
            if(background != null)
            {
                ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
                if(mainLayout != null)
                {
                    mainLayout.setBackground(background);
                }
            }
        }
    }
    public void SaveWallets()
    {
        Spref = getSharedPreferences(KEY_SAVE, MODE_PRIVATE);
        SharedPreferences.Editor EdPref = Spref.edit();
        if(SelectedValues != null)
        {
            for(int i = 0; i < CryptoWallet.GetCryptos().size(); i++) {
                EdPref.putBoolean(Integer.toString(i), SelectedValues.get(i));
            }
        }
        EdPref.putInt("BackgroundId", backgroundId);
        EdPref.apply();
    }

    public void setToolbar(boolean TrueOrFalse)
    {
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        if(!TrueOrFalse) {
            toolbar.setVisibility(View.GONE);
        }
        else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v4.app.Fragment selectFragment = (SelectFragment)getSupportFragmentManager().findFragmentByTag("Select_Fragment");
        Fragment convertFragment = (ConvertFragment)getSupportFragmentManager().findFragmentByTag("Convert");
        Fragment convertListFragment = (ConvertCryptoListFragment)getSupportFragmentManager().findFragmentByTag("ConvertList");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(selectFragment != null && selectFragment.isVisible()){
            goBackSelect();

        }
        else if(convertListFragment != null && convertListFragment.isVisible()) {
            closeList();
        }
        else if(convertFragment != null && convertFragment.isVisible()) {
            CloseConvert();

        }
        else {
            super.onBackPressed();
        }
    }

    private void goBackSelect()
    {
        android.support.v4.app.Fragment blank = new BlankFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.select_fragment, blank, "Blank");

        ft.commit();
        if(fab != null) {
            fab.show();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        LoadDataFromAPI();
        Courses = getSelectedCryptos(SelectedValues);
        RecycleCryptoInit(Courses);
        navInit(true);

    }
    public void CloseSort()
    {
        Fragment blankFragment = new BlankFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_sort, blankFragment, "blank");
        fragmentTransaction.commit();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)CourseList.getLayoutParams();

        layoutParams.setMargins(0,0,0,0);

    }
    public void CloseConvert()
    {
        Fragment blankFragment = new BlankFragment();
        FragmentTransaction myFM = getSupportFragmentManager().beginTransaction();
        myFM.replace(R.id.select_fragment, blankFragment, "blanke");
        myFM.commit();
        blankFragment.setRetainInstance(true);
        if(fab != null) {
            fab.show();
        }
        else  {
            fab = (FloatingActionButton)findViewById(R.id.fab);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        android.support.v4.app.Fragment selectFragment = (SelectFragment)getSupportFragmentManager().findFragmentByTag("Select_Fragment");
     //   Fragment sortFragment = (Sort)getSupportFragmentManager().findFragmentByTag(SORT_TAG);
        android.support.v4.app.Fragment sortFragment = (Sort)getSupportFragmentManager().findFragmentByTag(SORT_TAG);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == android.R.id.home && selectFragment != null && selectFragment.isVisible()) {
            goBackSelect();
        }
        else if(id == R.id.action_sort) {
            if(sortFragment == null) {
                OpenSort();
            }
            else if(sortFragment != null && sortFragment.isVisible()){
                CloseSort();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        if (id == R.id.nav_favorite) {
            // Handle the camera action
            CloseConvert();

        } else if (id == R.id.nav_list) {
            OpenList();

        } else if (id == R.id.nav_convert) {
            OpenConvert();
        }  else if (id == R.id.nav_themes) {
            OpenThemes();
        }

        return true;
    }
    public void OpenThemes()
    {
        Fragment themeFragment = new ThemesFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.select_fragment, themeFragment, "Theme");
        ft.commit();
        themeFragment.setRetainInstance(true);
        // ft.addToBackStack(null);
        if(fab != null) {
            fab.hide();
        }
        ft.addToBackStack(null);
    }
    public void OpenList()
    {
        Fragment ListFragment = new ListItemFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.select_fragment, ListFragment, "List");
        ft.commit();
        ListFragment.setRetainInstance(true);
        // ft.addToBackStack(null);
        if(fab != null) {
            fab.hide();

        }

        ft.addToBackStack(null);

    }

    @Override
    public void onListFragmentInteraction(SparseBooleanArray checkState)
    {
        SelectedValues = checkState;

    }

    @Override
    public void onFragmentInteraction(String KeySelect)
    {
        ImageView arrowRank = (ImageView)findViewById(R.id.rank_down);
        ImageView arrowPrice = (ImageView)findViewById(R.id.price_down);
        ImageView arrowHour = (ImageView)findViewById(R.id.hour_down);
        ImageView arrowDay = (ImageView)findViewById(R.id.day_down);
        ImageView arrowWeek = (ImageView)findViewById(R.id.week_down);
        // texts
        TextView textRank = (TextView)findViewById(R.id.rank_text);
        ImageView iconUSD = (ImageView)findViewById(R.id.usd_icon);
        TextView textHour = (TextView)findViewById(R.id.hour_text);
        TextView textDay = (TextView)findViewById(R.id.day_text);
        TextView textWeek = (TextView)findViewById(R.id.week_text);
        List<ImageView> arrowList = new ArrayList<ImageView>(5);
        arrowList.add(0, arrowRank);
        arrowList.add(1, arrowPrice);
        arrowList.add(2, arrowHour);
        arrowList.add(3, arrowDay);
        arrowList.add(4, arrowWeek);
        // View List
        List<View> textList = new ArrayList<View>(5);
        textList.add(0, textRank);
        textList.add(1, iconUSD);
        textList.add(2, textHour);
        textList.add(3, textDay);
        textList.add(4, textWeek);
        if(KeySelect == "Rank Button") {
            ArrowChange(textList.get(0), arrowList.get(0), arrowList, textList, "Rank");
        }
        else if(KeySelect == "Price Button") {
            ArrowChange(textList.get(1), arrowList.get(1), arrowList, textList, "Price");
        }
        else if(KeySelect == "Hour Button") {
            ArrowChange(textList.get(2), arrowList.get(2), arrowList, textList, "Hour");
        }
        else if(KeySelect == "Day Button") {
            ArrowChange(textList.get(3), arrowList.get(3), arrowList, textList, "Day");
        }
        else if(KeySelect == "Week Button") {
            ArrowChange(textList.get(4), arrowList.get(4), arrowList, textList, "Week");
        }
    }
    private void setMarginSortText(int dpValue, View myText) {
        // int dpValue = 70; // margin in dips
        float d = this.getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)myText.getLayoutParams();
        layoutParams.setMargins(0,0,0,margin);
    }
    public void ArrowChange(View Text, ImageView arrowImg, List<ImageView> myArrowList, List<View> textList, String Key_FOR) {
        setMarginSortText(10, Text);
        if(arrowImg.getTag() != "Pressed") {
            for (int i = 0; i < myArrowList.size(); i++) {
                if (myArrowList.get(i).getTag() == "Pressed") {
                    myArrowList.get(i).setVisibility(View.GONE);
                    myArrowList.get(i).setTag("DONE");
                    setMarginSortText(0, textList.get(i));
                }
            }
        }
        if(Text.getTag() == "UP") {
            arrowImg.setVisibility(View.VISIBLE);
            arrowImg.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_down_arrow));
            Text.setTag("DOWN");
            Toast.makeText(this, "Up", Toast.LENGTH_SHORT).show();
            arrowImg.setTag("Pressed");
            if(Courses != null) {
                if(Key_FOR == "Rank") {
                    SortKey = "CAP_DOWN";
                    Courses = SortCryptoWallets(Courses, "CAP_DOWN");
                }
                else if(Key_FOR == "Price") {
                    SortKey = "PRICE_DOWN";
                    Courses = SortCryptoWallets(Courses, "PRICE_DOWN");
                }
                else if(Key_FOR == "Hour") {
                    SortKey = "HOUR_DOWN";
                    Courses = SortCryptoWallets(Courses, "HOUR_DOWN");
                }
                else if(Key_FOR == "Day") {
                    SortKey = "DAY_DOWN";
                    Courses = SortCryptoWallets(Courses, "DAY_DOWN");
                }
                else if(Key_FOR == "Week") {
                    SortKey = "WEEK_DOWN";
                    Courses = SortCryptoWallets(Courses, "WEEK_DOWN");
                }
                RecycleCryptoInit(Courses);
            }
        }
        else {
            arrowImg.setVisibility(View.VISIBLE);
            arrowImg.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_top_arrow));
            //    arrowDown.setVisibility(View.VISIBLE);
            //  arrowUp.setVisibility(View.GONE);
            Text.setTag("UP");
            Toast.makeText(this, "Down", Toast.LENGTH_SHORT).show();
            arrowImg.setTag("Pressed");
            if(Courses != null) {
                if(Key_FOR == "Rank") {
                    SortKey = "CAP_UP";
                    Courses = SortCryptoWallets(Courses, "CAP_UP");
                }
                else if(Key_FOR == "Price") {
                    SortKey = "PRICE_UP";
                    Courses = SortCryptoWallets(Courses, "PRICE_UP");
                }
                else if(Key_FOR == "Hour") {
                    SortKey = "HOUR_UP";
                    Courses = SortCryptoWallets(Courses, "HOUR_UP");
                }
                else if(Key_FOR == "Day") {
                    SortKey = DAY_UP;
                    Courses = SortCryptoWallets(Courses, DAY_UP);
                }
                else if(Key_FOR == "Week") {
                    SortKey = "WEEK_UP";
                    Courses = SortCryptoWallets(Courses, "WEEK_UP");
                }
                RecycleCryptoInit(Courses);
            }
        }
    }
    public void saveSort()
    {
        SharedPreferences SortPref = getSharedPreferences("SORT", MODE_PRIVATE);
        SharedPreferences.Editor Ed = SortPref.edit();
        Ed.putString("SORT_KEY", SortKey);
        Ed.apply();
    }

    public List<CryptoWallet> SortCryptoWallets(List<CryptoWallet> myList, String myKey)
    {
        List<CryptoWallet> sortedCryptos = new ArrayList<>();
        sortedCryptos.addAll(myList);
        if(myKey == "CAP_DOWN") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_CAP_DOWN);
        }
        else if(myKey == "CAP_UP") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_CAP_UP);
        }
        else if(myKey == "PRICE_DOWN") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_PRICE_DOWN);
        }
        else if(myKey == "PRICE_UP") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_PRICE_UP);
        }
        else if(myKey == "HOUR_DOWN") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_HOUR_DOWN);
        }
        else if(myKey == "HOUR_UP") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_HOUR_UP);
        }
        else if(myKey == "DAY_DOWN") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_DAY_DOWN);
        }
        else if(myKey == DAY_UP) {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_DAY_UP);
        }
        else if(myKey == "WEEK_DOWN") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_WEEK_DOWN);
        }
        else if(myKey == "WEEK_UP") {
            Collections.sort(sortedCryptos, CryptoWallet.COMPARE_WEEK_UP);
        }
        return sortedCryptos;
    }

    @Override
    public void OnListConvertListener(String item)
    {
    Fragment mConvertFragmen = getSupportFragmentManager().findFragmentByTag("Convert");
    if(mConvertFragmen != null) {
        TextView mText = (TextView)  mConvertFragmen.getView().findViewById(R.id.CButton_text);
        mText.setText(item);
    }
    closeList();

    }
    public void closeList()
    {
        Fragment blank = new BlankFragment();
        FragmentTransaction myFM = getSupportFragmentManager().beginTransaction();
        myFM.replace(R.id.listFragment, blank, "blanke");
        myFM.commit();
        blank.setRetainInstance(true);
    }

    @Override
    public void OnThemeSelectListener(Integer item)
    {
        ConstraintLayout mainLayout = (ConstraintLayout)findViewById(R.id.main_layout);
        RecyclerView themeLayout = (RecyclerView)findViewById(R.id.list);
        backgroundId = item;
        background = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), item));
        if(mainLayout != null) {
            mainLayout.setBackground(background);
        }
        if(themeLayout != null) {
            themeLayout.setBackground(background);
        }
    }
}
