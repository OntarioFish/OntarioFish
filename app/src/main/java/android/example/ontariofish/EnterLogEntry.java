package android.example.ontariofish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EnterLogEntry extends AppCompatActivity {

    public ArrayList<MyEntry> mEntryList;
    public String[] fishes = new String[]{"Brook Trout", "Channel Catfish", "Common Carp",
            "Lake Trout", "Largemouth Bass", "Muskellunge", "Northern Pike", "Pumpkinseed",
    "Rainbow Trout", "Rock Bass", "Sauger", "Smallmouth Bass", "Walleye", "White Crappie",
    "Yellow Perch"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_log_entry);
        setSaveButton();
        loadData();
    }

    private void setSaveButton() {
        Button buttonSave = findViewById(R.id.save_button);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner line1 = findViewById(R.id.fish_spinner);
                EditText line2 = findViewById(R.id.editTextLocation);
                EditText line3 = findViewById(R.id.editTextSize);
                EditText line4 = findViewById(R.id.editTextWeight);
                EditText line5 = findViewById(R.id.editTextDate);
                if (!line1.getSelectedItem().toString().matches("") &&
                        !line2.getText().toString().matches("") &&
                        !line3.getText().toString().matches("") &&
                        !line4.getText().toString().matches("") &&
                        !line5.getText().toString().matches("")) {
                    saveData(line1.getSelectedItem().toString(), line2.getText().toString(),
                            line3.getText().toString(), line4.getText().toString(),
                            line5.getText().toString());
                    line1.setSelection(0);
                    line2.getText().clear();
                    line3.getText().clear();
                    line4.getText().clear();
                    line5.getText().clear();

                    Intent intent = new Intent(EnterLogEntry.this, Logbook.class);
                    startActivity(intent);
                }
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

    private void saveData(String line1, String line2, String line3, String line4, String line5) {
        mEntryList.add(new MyEntry(line1, line2, line3, line4, line5));
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mEntryList);
        editor.putString("task list", json);
        editor.apply();
    }

}
