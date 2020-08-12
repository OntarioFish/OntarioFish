package android.example.ontariofish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class LogbookEntryDialog extends AppCompatDialogFragment {

    private LogbookDialogListener listener;
    ArrayList<MyEntry> mEntryList;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences sp = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("task list", null);
        Type type = new TypeToken<ArrayList<MyEntry>>() {}.getType();
        mEntryList = gson.fromJson(json, type);
        if (mEntryList == null) {
            mEntryList = new ArrayList<>();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        final View view = inflater.inflate(R.layout.logbook_add_entry_dialog, null);

        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Spinner line1 = view.findViewById(R.id.fish_spinner2);
                        EditText line2 = view.findViewById(R.id.editTextLocation2);
                        EditText line3 = view.findViewById(R.id.editTextSize2);
                        EditText line4 = view.findViewById(R.id.editTextWeight2);
                        EditText line5 = view.findViewById(R.id.editTextDate2);


                        if (!line1.getSelectedItem().toString().matches("") ||
                                !line2.getText().toString().matches("") ||
                                !line3.getText().toString().matches("") ||
                                !line4.getText().toString().matches("") ||
                                !line5.getText().toString().matches("")) {
                            saveData(line1.getSelectedItem().toString(), line2.getText().toString(),
                                    line3.getText().toString(), line4.getText().toString(),
                                    line5.getText().toString());
                        }
                        getActivity().finish();
                        getActivity().overridePendingTransition(0, 0);
                        startActivity(getActivity().getIntent());
                        getActivity().overridePendingTransition(0, 0);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (LogbookDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LogbookDialogListener");
        }
    }

    public interface LogbookDialogListener{}

    private void saveData(String line1, String line2, String line3, String line4, String line5) {
        mEntryList.add(new MyEntry(line1, line2, line3, line4, line5));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mEntryList);
        editor.putString("task list", json);
        editor.apply();
    }

}