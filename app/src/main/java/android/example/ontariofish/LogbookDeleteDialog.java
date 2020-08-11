package android.example.ontariofish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class LogbookDeleteDialog extends AppCompatDialogFragment {

    private TextView fishType;
    private TextView date;
    private TextView fishSize;
    private TextView fishWeight;
    private TextView location;
    private LogbookDialogListener listener;
    private int position;
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

        final SharedPreferences.Editor editor = sp.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.logbook_entry_click_dialog, null);
        builder.setView(view)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mEntryList.remove(position);
                        editor.clear();
                        Gson gson = new Gson();
                        String json = gson.toJson(mEntryList);
                        editor.putString("task list", json);
                        editor.apply();

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

        location = view.findViewById(R.id.fish_dialog_location);
        date = view.findViewById(R.id.fish_dialog_date);
        fishType = view.findViewById(R.id.fish_dialog_type);
        fishSize = view.findViewById(R.id.fish_dialog_size);
        fishWeight = view.findViewById(R.id.fish_dialog_weight);

        if (mEntryList.get(position).getFishLocation().equals("")) {
            location.setText("Location: Unknown");
        } else {
            location.setText("Location: " + mEntryList.get(position).getFishLocation());
        }

        if (mEntryList.get(position).getFishDate().equals("")) {
            date.setText("Date: Unknown");
        } else {
            date.setText("Date: " + mEntryList.get(position).getFishDate());
        }

        if (mEntryList.get(position).getFishType().equals("")) {
            fishType.setText("Type: Unknown");
        } else {
            fishType.setText("Type: " + mEntryList.get(position).getFishType());
        }

        if (mEntryList.get(position).getFishSize().equals("")) {
            fishSize.setText("Size: Unknown");
        } else {
            fishSize.setText("Size: " + mEntryList.get(position).getFishSize() + " inches");
        }

        if (mEntryList.get(position).getFishWeight().equals("")) {
            fishWeight.setText("Weight: Unknown");
        } else {
            fishWeight.setText("Weight: " + mEntryList.get(position).getFishWeight() + " lbs");
        }

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

    public void setPosition(int i) {
        position = i;
    }

}