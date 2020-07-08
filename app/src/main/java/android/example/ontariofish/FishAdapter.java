package android.example.ontariofish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.MyViewHolder>{

    private ArrayList<Fish> fishList;
    private ArrayList<Fish> fishListFull;
    private OnFishListener mOnFishListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView fishName;
        public OnFishListener onFishListener;

        public MyViewHolder (View v, OnFishListener onFishListener) {
            super(v);
            fishName = (TextView) v.findViewById(R.id.fish_name);
            this.onFishListener = onFishListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onFishListener.onFishClick(getAdapterPosition());
        }
    }

    public FishAdapter(ArrayList<Fish> fishList, OnFishListener onFishListener) {
        this.fishList = fishList;
        this.fishListFull = fishList;
        this.mOnFishListener = onFishListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_textview, parent, false);
        return new MyViewHolder(v, mOnFishListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fishName.setText(fishList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    public void filterList(String text){
        System.out.println(text);
        ArrayList<Fish> filteredList = new ArrayList<>();
        for(Fish fish : fishListFull){
            if(fish.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(fish);
            }
        }
        fishList = filteredList;
        notifyDataSetChanged();
    }

    public interface OnFishListener{
        void onFishClick(int position);
    }




}

