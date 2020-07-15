package android.example.ontariofish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Logbook extends AppCompatActivity implements EntryAdapter.ViewHolder.OnEntryListener, LogbookDialog.LogbookDialogListener {

    ArrayList<MyEntry> mEntryList;

    private RecyclerView mRecyclerView;
    private EntryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logbook);
        loadData();
        buildRecyclerView();
        fabClick();
    }

    private void fabClick() {
        FloatingActionButton fab = findViewById(R.id.button_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Logbook.this, EnterLogEntry.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<MyEntry>>() {}.getType();
        mEntryList = gson.fromJson(json, type);
        if (mEntryList == null) {
            mEntryList = new ArrayList<>();
        }
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new EntryAdapter(mEntryList, this);
        mAdapter.notifyItemInserted(mEntryList.size());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void OnEntryClick(int position) {
        LogbookDialog logbookDialog = new LogbookDialog();
        logbookDialog.show(getSupportFragmentManager(), "logbook dialog");
        logbookDialog.setPosition(position);
    }
}