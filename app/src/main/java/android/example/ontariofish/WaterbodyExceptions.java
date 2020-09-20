package android.example.ontariofish;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaterbodyExceptions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaterbodyExceptions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ZONE_NUMBER = "ZONE";

    // TODO: Rename and change types of parameters
    private int regionNumber;

    private DatabaseHelper DB;
    private String[][] waterbodies;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView presentWaterbodies;

    public WaterbodyExceptions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment WaterbodyExceptions.
     */
    // TODO: Rename and change types and number of parameters
    public static WaterbodyExceptions newInstance(String param1) {
        WaterbodyExceptions fragment = new WaterbodyExceptions();
        Bundle args = new Bundle();
        args.putString(ZONE_NUMBER, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            regionNumber = getArguments().getInt(ZONE_NUMBER);
        }

        DB = new DatabaseHelper(getActivity());
        waterbodies = DB.getWaterbodyInfo(Integer.toString(regionNumber));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_waterbody_exceptions,null);
        if(waterbodies[0][1] == null) {
            presentWaterbodies = (TextView) root.findViewById(R.id.present_waterbodies);
            presentWaterbodies.setText("No waterbody exceptions for zone");
            return root;
        }
        recyclerView = (RecyclerView) root.findViewById(R.id.waterbody_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new WaterbodyRecyclerAdapter(waterbodies);
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        return root;
    }
}