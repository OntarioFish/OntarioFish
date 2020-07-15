package android.example.ontariofish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.logbook_entry, parent, false);
        return new ViewHolder(v, mOnEntryListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyEntry currentItem = mEntryList.get(position);
        String title = currentItem.getFishType() + " · " + currentItem.getFishLocation();
        String subtitle = currentItem.getFishDate() + " · " +
                currentItem.getFishSize() + " inches · " + currentItem.getFishWeight() + " lbs";

        holder.mTextViewLine1.setText(title);
        holder.mTextViewLine2.setText(subtitle);

    }

    @Override
    public int getItemCount() {
        return mEntryList.size();
    }
}