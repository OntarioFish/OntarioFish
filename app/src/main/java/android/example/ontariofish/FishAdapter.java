package android.example.ontariofish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.MyViewHolder> {

    private Context mContext;
    private OnCardListener mOnCardListener;
    private List<Fish> fishList;

    public FishAdapter(Context mContext, List<Fish> fishList, OnCardListener onCardListener){
        this.mContext = mContext;
        this.fishList = fishList;
        this.mOnCardListener = onCardListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView fishName;
        public ImageView fishPhoto;
        public OnCardListener onCardListener;

        public MyViewHolder(View view, OnCardListener onCardListener){
            super(view);
            fishName = (TextView) view.findViewById(R.id.fish_name);
            fishPhoto = (ImageView) view.findViewById(R.id.photo_fish);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fish_card, parent, false);

        return new MyViewHolder(itemView, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.fishName.setText(fish.getName());

        Glide.with(mContext).load(fish.getPhoto()).into(holder.fishPhoto);

    }

    public int getItemCount(){
        return fishList.size();
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }

}
