package android.example.ontariofish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    private ArrayList<MyEntry> mEntryList;
    private ViewHolder.OnEntryListener mOnEntryListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextViewLine1;
        public TextView mTextViewLine2;
        public OnEntryListener onEntryListener;

        public ViewHolder(View itemView, OnEntryListener onEntryListener) {
            super(itemView);
            mTextViewLine1 = itemView.findViewById(R.id.textview_line1);
            mTextViewLine2 = itemView.findViewById(R.id.textview_line_2);
            this.onEntryListener = onEntryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEntryListener.OnEntryClick(getAdapterPosition());
        }

        public interface OnEntryListener {
            void OnEntryClick(int position);
        }

    }

    public EntryAdapter(ArrayList<MyEntry> exampleList, ViewHolder.OnEntryListener onEntryListener) {
        mEntryList = exampleList;
        this.mOnEntryListener = onEntryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logbook_entry_text, parent, false);
        return new ViewHolder(v, mOnEntryListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyEntry currentItem = mEntryList.get(position);

        String type = currentItem.getFishType();
        String loc = currentItem.getFishLocation();
        String date = currentItem.getFishDate();
        String size = currentItem.getFishSize();
        String weight = currentItem.getFishWeight();

        String[] entries = new String[] {loc, date, type, size, weight};
        ArrayList<String> display = new ArrayList<>();

        for (int i = 0; i < entries.length; i ++) {
            if (!entries[i].equals("")) {
                if (i == 3) {
                    display.add(entries[i] + " inches");
                } else if (i == 4) {
                    display.add(entries[i] + " lbs");
                } else {
                    display.add(entries[i]);
                }
            }
        }

        String title;
        String subtitle = "";

        if (display.size() == 1) {
            title = display.get(0);
        } else if (display.size() == 2) {
            title = display.get(0) + " · " + display.get(1);
        } else if (display.size() == 3) {
            title = display.get(0) + " · " + display.get(1);
            subtitle = display.get(2);
        } else if (display.size() == 4) {
            title = display.get(0) + " · " + display.get(1);
            subtitle = display.get(2) + " · " + display.get(3);
        } else {
            title = display.get(0) + " · " + display.get(1);
            subtitle = display.get(2) + " · " + display.get(3) + " · " + display.get(4);
        }

        holder.mTextViewLine1.setText(title);
        holder.mTextViewLine2.setText(subtitle);

    }

    @Override
    public int getItemCount() {
        return mEntryList.size();
    }
}