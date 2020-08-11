package android.example.ontariofish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Logbook extends AppCompatActivity implements EntryAdapter.ViewHolder.OnEntryListener, LogbookDeleteDialog.LogbookDialogListener, LogbookEntryDialog.LogbookDialogListener {

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
                LogbookEntryDialog logbookEntryDialog = new LogbookEntryDialog();
                logbookEntryDialog.show(getSupportFragmentManager(), "logbook dialog");
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
        DividerItemDecoration decoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

    }

    @Override
    public void OnEntryClick(int position) {
        LogbookDeleteDialog logbookDeleteDialog = new LogbookDeleteDialog();
        logbookDeleteDialog.show(getSupportFragmentManager(), "logbook dialog");
        logbookDeleteDialog.setPosition(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}