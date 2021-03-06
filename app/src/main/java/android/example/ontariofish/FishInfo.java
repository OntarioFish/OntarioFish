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

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            switch(extras.getString("SOURCE")){
                case "sunfish":
                    prepareSunFish();
                    break;
                case "pacific_salmon":
                    preparePacificSalmon();
                    break;
                case "crappie":
                    prepareCrappie();
                    break;
            }
        } else {
            prepareFish();
        }

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


    @Override
    protected void onResume() {
        super.onResume();
        editText.getText().clear();
    }

    private void prepareFish(){


        //Section definitely needs improvement in making adding and removing fish easier.
        Fish atlanticSalmon = new Fish("Atlantic Salmon", "atlantic_salmon");
        Fish blackCrappie = new Fish("Black Crappie", "black_crappie");
        Fish blueGill = new Fish("Bluegill", "bluegill");
        Fish brookTrout = new Fish("Brook Trout", "brook_trout");
        Fish brownTrout = new Fish("Brown Trout", "brown_trout");
        Fish catFish = new Fish("Channel Catfish", "channel_catfish");
        Fish chinookSalmon = new Fish("Chinook Salmon", "chinook_salmon");
        Fish cohoSalmon = new Fish("Coho Salmon", "coho_salmon");
        Fish carp = new Fish("Common Carp", "common_carp");
        Fish lakeHerring = new Fish("Lake Herring", "lake_herring");
        Fish lakeSturgeon = new Fish("Lake Sturgeon", "lake_sturgeon");
        Fish lakeTrout = new Fish("Lake Trout", "lake_trout");
        Fish lakeWhiteFish = new Fish("Lake Whitefish", "lake_whitefish");
        Fish largeMouthBass = new Fish("Largemouth Bass", "largemouth_bass");
        Fish muskellunge = new Fish("Muskellunge", "muskellunge");
        Fish northernPike = new Fish("Northern Pike", "northern_pike");
        Fish pumpkinseed = new Fish("Pumpkinseed", "pumpkinseed");
        Fish rainbowTrout = new Fish("Rainbow Trout",  "rainbow_trout");
        Fish rockBass = new Fish("Rock Bass", "rock_bass");
        Fish sauger = new Fish("Sauger", "sauger");
        Fish smallmouthBass = new Fish("Smallmouth Bass",  "smallmouth_bass");
        Fish splake = new Fish("Splake", "splake");
        Fish walleye = new Fish("Walleye", "walleye");
        Fish whiteCrappie = new Fish("White Crappie", "white_crappie");
        Fish yellowPerch = new Fish("Yellow Perch", "yellow_perch");

        fishList.add(atlanticSalmon);
        fishList.add(blackCrappie);
        fishList.add(blueGill);
        fishList.add(brookTrout);
        fishList.add(brownTrout);
        fishList.add(catFish);
        fishList.add(chinookSalmon);
        fishList.add(cohoSalmon);
        fishList.add(carp);
        fishList.add(lakeHerring);
        fishList.add(lakeSturgeon);
        fishList.add(lakeTrout);
        fishList.add(lakeWhiteFish);
        fishList.add(largeMouthBass);
        fishList.add(muskellunge);
        fishList.add(northernPike);
        fishList.add(pumpkinseed);
        fishList.add(rainbowTrout);
        fishList.add(rockBass);
        fishList.add(sauger);
        fishList.add(smallmouthBass);
        fishList.add(splake);
        fishList.add(walleye);
        fishList.add(whiteCrappie);
        fishList.add(yellowPerch);

        adapter.notifyDataSetChanged();

    }

    private void prepareSunFish(){
        Fish pumpkinseed = new Fish("Pumpkinseed", "pumpkinseed");
        Fish blueGill = new Fish("Bluegill", "bluegill");
        fishList.add(pumpkinseed);
        fishList.add(blueGill);

    }

    private void prepareCrappie(){
        Fish whiteCrappie = new Fish("White Crappie", "white_crappie");
        Fish blackCrappie = new Fish("Black Crappie", "black_crappie");
        fishList.add(blackCrappie);
        fishList.add(whiteCrappie);
        adapter.notifyDataSetChanged();
    }

    private void preparePacificSalmon(){
        Fish chinookSalmon = new Fish("Chinook Salmon", "chinook_salmon");
        Fish cohoSalmon = new Fish("Coho Salmon", "coho_salmon");

        fishList.add(chinookSalmon);
        fishList.add(cohoSalmon);
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
