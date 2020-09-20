package android.example.ontariofish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpeciesRegulations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeciesRegulations extends Fragment {


    private static final String ZONE_NUMBER = "ZONE";


    private int regionNumber;

    private TextView zoneTitle, zoneSeasonInfo, zoneLimitInfo, exceptionInfo,exceptionLocation;
    private ScrollView exceptionLayout;
    private Spinner fishSelect;
    private Fish currentFish;
    private AutoCompleteTextView lakeList;
    private String[] fishInfo = new String[2];
    private String[] lakeException = new String[3];
    private List<String> listLake = new ArrayList<>();
    private DatabaseHelper DB;
    private List<String> regulationFish;
    private FloatingActionButton fabFish;

    public SpeciesRegulations() {
        // Required empty public constructor
    }


    public static SpeciesRegulations newInstance(String param1) {
        SpeciesRegulations fragment = new SpeciesRegulations();
        Bundle args = new Bundle();
        args.putString(ZONE_NUMBER, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("created******************");
        if (getArguments() != null) {
            regionNumber = getArguments().getInt(ZONE_NUMBER);
        }

        DB = new DatabaseHelper(getActivity());

        regulationFish = DB.getRegulationsFish(Integer.toString(regionNumber));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_species_regulations,null);
        exceptionLocation = (TextView) root.findViewById(R.id.exception_location);
        exceptionLayout = (ScrollView) root.findViewById(R.id.scroll_exception);
        lakeList = (AutoCompleteTextView) root.findViewById(R.id.lake_list);
        fishSelect = (Spinner) root.findViewById(R.id.fish_spinner);
        zoneLimitInfo = (TextView) root.findViewById(R.id.zone_limit);
        zoneSeasonInfo = (TextView) root.findViewById(R.id.zone_season);
        exceptionInfo = (TextView) root.findViewById(R.id.exception_info);
        fabFish = (FloatingActionButton) root.findViewById(R.id.fab_fish_info);

        exceptionLayout.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, regulationFish);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fishSelect.setAdapter(adapter);
        fishSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSpinnerClick(parent, view, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lakeList.setThreshold(1);
        lakeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lakeException = DB.getExceptionsInfo(Integer.toString(regionNumber), currentFish.getName(), (String)parent.getItemAtPosition(position));
                exceptionLocation.setText(String.format("%s: %s", parent.getItemAtPosition(position), lakeException[0]));
                if(lakeException[2].equals("Unchanged")) {
                    exceptionInfo.setText(String.format("%s", lakeException[1]));

                } else if (lakeException[1].equals("Unchanged")){
                    exceptionInfo.setText(String.format("%s", lakeException[2]));
                } else {
                    exceptionInfo.setText(String.format("%s\n\n%s", lakeException[1], lakeException[2]));
                }

                exceptionLayout.setVisibility(View.VISIBLE);

                closeKeyboard();
            }
        });

        fabFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fishName = currentFish.getResourceName();

                if(fishName.equals("sunfish")) {
                    Intent intent = new Intent(getActivity(), FishInfo.class);
                    intent.putExtra("SOURCE", "sunfish");
                    startActivity(intent);

                }else if(fishName.equals("crappie")) {
                    Intent intent = new Intent(getActivity(), FishInfo.class);
                    intent.putExtra("SOURCE", "crappie");
                    startActivity(intent);

                }else if (fishName.equals("pacific_salmon")){
                    Intent intent = new Intent(getActivity(), FishInfo.class);
                    intent.putExtra("SOURCE", "pacific_salmon");
                    startActivity(intent);

                } else {
                    System.out.println("FAB clicked");
                    System.out.println(fishName);
                    Intent intent = new Intent(getActivity(), FishDetails.class);
                    intent.putExtra("FISHES", currentFish);
                    startActivity(intent);
                }
            }
        });



        return root;
    }

    public void onSpinnerClick(AdapterView<?> parent, View view, int position){
        fishInfo = DB.getRegulationsInfo(Integer.toString(regionNumber), (String)parent.getItemAtPosition(position));
        zoneLimitInfo.setText(fishInfo[1]);
        zoneSeasonInfo.setText(fishInfo[0]);

        String resourceName = (String)parent.getItemAtPosition(position);
        resourceName = resourceName.toLowerCase();
        resourceName = resourceName.replaceAll("\\s", "_");
        currentFish = new Fish((String)parent.getItemAtPosition(position), resourceName);
        System.out.println(currentFish.getResourceName());
        listLake = DB.getExceptionsLake(Integer.toString(regionNumber), (String)parent.getItemAtPosition(position));
        lakeList.getText().clear();
        exceptionLayout.setVisibility(View.INVISIBLE);


        if(listLake.isEmpty()){
            lakeList.setVisibility(View.INVISIBLE);
            exceptionLayout.setVisibility(View.INVISIBLE);
        } else {
            if(lakeList.getVisibility() == View.INVISIBLE)
                lakeList.setVisibility(View.VISIBLE);

            ArrayAdapter<String> lake = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, listLake);
            lakeList.setAdapter(lake);
        }
    }

    public void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}