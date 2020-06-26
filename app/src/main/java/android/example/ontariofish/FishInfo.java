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

        int[] fish = new int[]{R.drawable.brook_trout, R.drawable.channel_catfish, R.drawable.common_carp, R.drawable.lake_trout, R.drawable.largemouth_bass, R.drawable.muskellunge,
                R.drawable.northern_pike, R.drawable.pumpkinseed, R.drawable.rainbow_trout, R.drawable.rock_bass2, R.drawable.sauger, R.drawable.smallmouth_bass,
                R.drawable.walleye, R.drawable.white_crappie, R.drawable.yellow_perch};

        //Section definitely needs improvement in making adding and removing fish easier.
        Fish brookTrout = new Fish("Brook Trout",  fish[0], "brook_trout");
        Fish catFish = new Fish("Channel Catfish", fish[1], "channel_catfish");
        Fish carp = new Fish("Common Carp", fish[2], "common_carp");
        Fish lakeTrout = new Fish("Lake Trout", fish[3], "lake_trout");
        Fish largeMouthBass = new Fish("Largemouth Bass", fish[4], "largemouth_bass");
        Fish muskellunge = new Fish("Muskellunge", fish[5], "muskellunge");
        Fish northernPike = new Fish("Northern Pike", fish[6], "northern_pike");
        Fish pumpkinseed = new Fish("Pumpkinseed", fish[7], "pumpkinseed");
        Fish rainbowTrout = new Fish("Rainbow Trout",  fish[8], "rainbow_trout");
        Fish rockBass = new Fish("Rock Bass", fish[9], "rock_bass");
        Fish sauger = new Fish("Sauger", fish[10], "sauger");
        Fish atlanticSalmon = new Fish("Smallmouth Bass",  fish[11], "smallmouth_bass");
        Fish walleye = new Fish("Walleye", fish[12], "walleye");
        Fish whiteCrappie = new Fish("White Crappie", fish[13], "white_crappie");
        Fish yellowPerch = new Fish("Yellow Perch", fish[14], "yellow_perch");

        fishList.add(brookTrout);
        fishList.add(catFish);
        fishList.add(carp);
        fishList.add(lakeTrout);
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

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(FishInfo.this, FishDetails.class);
        intent.putExtra("FISHES", fishList.get(position));
        startActivity(intent);
    }
}
