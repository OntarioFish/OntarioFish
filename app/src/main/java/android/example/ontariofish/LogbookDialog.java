package android.example.ontariofish;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class LogbookDialog extends AppCompatDialogFragment {

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
        View view = inflater.inflate(R.layout.entry_dialog, null);
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
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //keep empty
                    }
                });
//                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //add code
//                        //load entry with info from recyclerview
//                        getActivity().startActivity(new Intent(getActivity(), EnterLogEntry.class));
//                    }
//                });

        fishType = view.findViewById(R.id.fish_dialog_type);
        date = view.findViewById(R.id.fish_dialog_date);
        fishSize = view.findViewById(R.id.fish_dialog_size);
        fishWeight = view.findViewById(R.id.fish_dialog_weight);
        location = view.findViewById(R.id.fish_dialog_location);

        fishType.setText(mEntryList.get(position).getFishType());
        date.setText(mEntryList.get(position).getFishDate());
        fishSize.setText(mEntryList.get(position).getFishSize());
        fishWeight.setText(mEntryList.get(position).getFishWeight());
        location.setText(mEntryList.get(position).getFishLocation());

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

    public interface LogbookDialogListener{
        //void setTexts(String one, String two, String three, String four, String five);
    }

    public void setPosition(int i) {
        position = i;
    }

}