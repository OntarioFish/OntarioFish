package android.example.ontariofish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FishInfo extends AppCompatActivity implements FishAdapter.OnCardListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Fish> fishList;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_info);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.statusBarColor));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        fishList = new ArrayList<>(39);
        mAdapter = new FishAdapter(this, fishList, this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        prepareFish();

    }


    private void prepareFish(){
        int[] fish = new int[]{R.drawable.northern_pike, R.drawable.largemouth_bass, R.drawable.walleye, R.drawable.muskellunge, R.drawable.rock_bass2, R.drawable.yellow_perch,
                               R.drawable.white_crappie, R.drawable.freshwater_drum, R.drawable.common_carp, R.drawable.channel_catfish, R.drawable.atlantic_salmon, R.drawable.brook_trout, R.drawable.rainbow_trout
                               };

        //Section definitely needs improvement in making adding and removing fish easier.
        Fish northernPike = new Fish("Northern Pike", "42.1 Pounds", fish[0], "northern_pike");
        Fish largeMouthBass = new Fish("Large Mouth Bass", "10.4 pounds", fish[1], "largemouth_bass");
        Fish walleye = new Fish("Walleye", "22.3 pounds", fish[2], "walleye");
        Fish muskellunge = new Fish("Muskellunge", "65.0 pounds", fish[3], "muskellunge");
        Fish rockBass = new Fish("Rock Bass", "3.0 pounds", fish[4], "rock_bass");
        Fish yellowPerch = new Fish("Yellow Perch", "2.4 pounds", fish[5], "yellow_perch");
        Fish whiteCrappie = new Fish("White Crappie", "No Record", fish[6], "white_crappie");
        Fish freshwaterDrum = new Fish("Freshwater Drum", "No Record", fish[7], "freshwater_drum");
        Fish carp = new Fish("Common Carp", "38.5 pounds", fish[8], "common_carp");
        Fish catFish = new Fish("Channel Catfish", "38.5 pounds", fish[9], "channel_catfish");
        Fish atlanticSalmon = new Fish("Atlantic Salmon", "None", fish[10], "atlantic_salmon");
        Fish brookTrout = new Fish("Brook Trout", "14.5 pound", fish[11], "brook_trout");
        Fish rainbowTrout = new Fish("Rainbow Trout", "40.7 pounds", fish[12], "rainbow_trout");

        fishList.add(northernPike);
        fishList.add(largeMouthBass);
        fishList.add(walleye);
        fishList.add(muskellunge);
        fishList.add(rockBass);
        fishList.add(yellowPerch);
        fishList.add(whiteCrappie);
        fishList.add(freshwaterDrum);
        fishList.add(carp);
        fishList.add(catFish);
        fishList.add(atlanticSalmon);
        fishList.add(brookTrout);
        fishList.add(rainbowTrout);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCardClick(int position) {
        Log.d("What up gthang", "onCardClick: clicked.");
        System.out.println(fishList.get(position).getName());
        Intent intent = new Intent(FishInfo.this, FishDetails.class);
        intent.putExtra("FISHES", fishList.get(position));
        startActivity(intent);
    }
}
