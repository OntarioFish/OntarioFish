package android.example.ontariofish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FishInfo extends AppCompatActivity implements FishAdapter.OnFishListener {

    private RecyclerView recyclerView;
    private FishAdapter adapter;
    private ArrayList<Fish> fishList;
    private RecyclerView.LayoutManager layoutManager;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_info);



//        toolbar = findViewById(R.id.info_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editText = (EditText) findViewById(R.id.search_fish);



        fishList = new ArrayList<>(39);
        adapter = new FishAdapter(fishList, this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        prepareFish();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.filterList(s.toString());
            }
        });


    }



    private void prepareFish(){


        //Section definitely needs improvement in making adding and removing fish easier.
        Fish brookTrout = new Fish("Brook Trout", "brook_trout");
        Fish catFish = new Fish("Channel Catfish", "channel_catfish");
        Fish carp = new Fish("Common Carp", "common_carp");
        Fish lakeTrout = new Fish("Lake Trout", "lake_trout");
        Fish lakeWhiteFish = new Fish("Lake Whitefish", "lake_whitefish");
        Fish largeMouthBass = new Fish("Largemouth Bass", "largemouth_bass");
        Fish muskellunge = new Fish("Muskellunge", "muskellunge");
        Fish northernPike = new Fish("Northern Pike", "northern_pike");
        Fish pumpkinseed = new Fish("Pumpkinseed", "pumpkinseed");
        Fish rainbowTrout = new Fish("Rainbow Trout",  "rainbow_trout");
        Fish rockBass = new Fish("Rock Bass", "rock_bass");
        Fish sauger = new Fish("Sauger", "sauger");
        Fish atlanticSalmon = new Fish("Smallmouth Bass",  "smallmouth_bass");
        Fish walleye = new Fish("Walleye", "walleye");
        Fish whiteCrappie = new Fish("White Crappie", "white_crappie");
        Fish yellowPerch = new Fish("Yellow Perch", "yellow_perch");


        fishList.add(brookTrout);
        fishList.add(catFish);
        fishList.add(carp);
        fishList.add(lakeTrout);
        fishList.add(lakeWhiteFish);
        fishList.add(largeMouthBass);
        fishList.add(muskellunge);
        fishList.add(northernPike);
        fishList.add(pumpkinseed);
        fishList.add(rainbowTrout);
        fishList.add(rockBass);
        fishList.add(sauger);
        fishList.add(atlanticSalmon);
        fishList.add(walleye);
        fishList.add(whiteCrappie);
        fishList.add(yellowPerch);

        adapter.notifyDataSetChanged();

    }

    public void onFishClick(Fish fish) {
        Intent intent = new Intent(this, FishDetails.class);
        intent.putExtra("FISHES", fish);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
