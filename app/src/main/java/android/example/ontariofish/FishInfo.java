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
                               R.drawable.white_crappie, R.drawable.common_carp, R.drawable.channel_catfish, R.drawable.smallmouth_bass, R.drawable.brook_trout, R.drawable.rainbow_trout,
                               R.drawable.pumpkinseed, R.drawable.lake_trout, R.drawable.sauger};

        //Section definitely needs improvement in making adding and removing fish easier.
        Fish northernPike = new Fish("Northern Pike", fish[0], "northern_pike");
        Fish largeMouthBass = new Fish("Largemouth Bass", fish[1], "largemouth_bass");
        Fish walleye = new Fish("Walleye", fish[2], "walleye");
        Fish muskellunge = new Fish("Muskellunge", fish[3], "muskellunge");
        Fish rockBass = new Fish("Rock Bass", fish[4], "rock_bass");
        Fish yellowPerch = new Fish("Yellow Perch", fish[5], "yellow_perch");
        Fish whiteCrappie = new Fish("White Crappie", fish[6], "white_crappie");
        Fish carp = new Fish("Common Carp", fish[7], "common_carp");
        Fish catFish = new Fish("Channel Catfish", fish[8], "channel_catfish");
        Fish atlanticSalmon = new Fish("Smallmouth Bass",  fish[9], "smallmouth_bass");
        Fish brookTrout = new Fish("Brook Trout",  fish[10], "brook_trout");
        Fish rainbowTrout = new Fish("Rainbow Trout",  fish[11], "rainbow_trout");
        Fish pumpkinseed = new Fish("Pumpkinseed", fish[12], "pumpkinseed");
        Fish lakeTrout = new Fish("Lake Trout", fish[13], "lake_trout");
        Fish sauger = new Fish("Sauger", fish[14], "sauger");

        fishList.add(northernPike);
        fishList.add(largeMouthBass);
        fishList.add(walleye);
        fishList.add(muskellunge);
        fishList.add(rockBass);
        fishList.add(yellowPerch);
        fishList.add(whiteCrappie);
        fishList.add(carp);
        fishList.add(catFish);
        fishList.add(atlanticSalmon);
        fishList.add(brookTrout);
        fishList.add(rainbowTrout);
        fishList.add(pumpkinseed);
        fishList.add(lakeTrout);
        fishList.add(sauger);

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
