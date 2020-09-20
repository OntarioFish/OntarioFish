package android.example.ontariofish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WaterbodyRecyclerAdapter extends RecyclerView.Adapter<WaterbodyRecyclerAdapter.MyViewHolder> {

    Context context;
    private DatabaseHelper DB;
    private WaterbodyExceptionSample currentLake;
    private String[][] waterbodyList;
    private String[] waterbodyInfoArray;
    private boolean expandedPosition = false;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lakeName, info2, info3;
        private LinearLayout moreInfo;

        public MyViewHolder(View v) {
            super(v);
            moreInfo = (LinearLayout) v.findViewById(R.id.linear_waterbody_info);
            lakeName = (TextView) v.findViewById(R.id.waterbody_text_view_recycler);
            info2 = (TextView) v.findViewById(R.id.waterbody_info2);
            info3 = (TextView) v.findViewById(R.id.waterbody_info3);

        }
    }

    public WaterbodyRecyclerAdapter(String[][] list) {
        this.waterbodyList = list;
    }

    @NonNull
    @Override
    public WaterbodyRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.waterbody_recycler_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.lakeName.setText(waterbodyList[position][1]);
        if(waterbodyList[position][0].equals("1")){
            if(waterbodyList[position][2].equals("NA"))
                holder.info2.setVisibility(View.GONE);
            holder.moreInfo.setVisibility(View.VISIBLE);
            holder.info2.setText(setInfoText(position)[0]);
            holder.info3.setText(setInfoText(position)[1].substring(0, setInfoText(position)[1].length() - 2));
        } else {
            holder.moreInfo.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(waterbodyList[position][0]);
                if(waterbodyList[position][0].equals("1")){
                    waterbodyList[position][0] = "0";

                } else {
                    waterbodyList[position][0] = "1";
                }

                notifyItemChanged(position);
            }
        });

    }

    public String[] setInfoText(int position){
        String[] result = {"", ""};
        int i;
        if(waterbodyList[position][2].equals("NA")){
            i = 3;
        } else {
            i=3;
            result[0] = waterbodyList[position][2];
        }

        while(i < 15){
            if(waterbodyList[position][i].equals("NA")){
                break;
            } else {
                    result[1] = result[1] + waterbodyList[position][i] + "\n\n" ;
            }
            i++;
        }
        System.out.println(result);
        return result;
    }


    @Override
    public int getItemCount() {
        int i;
        for(i = 0; i < waterbodyList.length; i++){
            if(waterbodyList[i][1] == null)
                break;
        }

        return i;
    }



}
