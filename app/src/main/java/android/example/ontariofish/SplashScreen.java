package android.example.ontariofish;

import static java.util.stream.Collectors.groupingBy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.example.ontariofish.dataset.*;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // all of this works to generate the maps we will need for the app

        List<WaterbodyLocations> waterbodyLocationsList =
                readCsvData(getResources().openRawResource(R.raw.waterbodylocations), WaterbodyLocations.class);
        // generate map of WaterbodyOgfId : waterbodyLocationIdentifier : WaterbodyLocations entries
        Map<Integer, Map<String, List<WaterbodyLocations>>> waterbodyLocationsMap = waterbodyLocationsList.stream()
                .collect(groupingBy(WaterbodyLocations::getWaterbodyOgfId, groupingBy(WaterbodyLocations::getWaterbodyLocationIdentifier)));
        Log.i("Zack", String.valueOf(waterbodyLocationsMap.size()));

        List<WaterbodyExceptions> waterbodyExceptionsList =
                readCsvData(getResources().openRawResource(R.raw.waterbodyexceptions), WaterbodyExceptions.class);
        // generate map of Zone : WaterbodyLocations entries
        Map<Integer, List<WaterbodyExceptions>> waterbodyExceptionsMap = waterbodyExceptionsList.stream()
                .collect(groupingBy(WaterbodyExceptions::getZoneNumber));
        Log.i("Zack", String.valueOf(waterbodyExceptionsMap.size()));

        List<AdditionalFishingOpportunities> additionalFishingOpportunitiesList =
                readCsvData(getResources().openRawResource(R.raw.additionalfishingopportunity), AdditionalFishingOpportunities.class);
        // generate map of Zone : Species : AdditionalFishingOpportunities entries
        Map<Integer, Map<String, List<AdditionalFishingOpportunities>>> additionalFishingOpportunitiesMap = additionalFishingOpportunitiesList.stream()
                .collect(groupingBy(AdditionalFishingOpportunities::getZoneNumber, groupingBy(AdditionalFishingOpportunities::getSpecies)));
        Log.i("Zack", String.valueOf(additionalFishingOpportunitiesMap.size()));

        List<FishSanctuaries> fishSanctuariesList =
                readCsvData(getResources().openRawResource(R.raw.fishsanctuaries), FishSanctuaries.class);
        // generate map of Zone : FishSanctuaries entries
        Map<Integer, List<FishSanctuaries>> fishSanctuariesMap = fishSanctuariesList.stream()
                .collect(groupingBy(FishSanctuaries::getZoneNumber));
        Log.i("Zack", String.valueOf(fishSanctuariesMap.size()));

        List<FishSanctuaryLocations> fishSanctuaryLocationsList =
                readCsvData(getResources().openRawResource(R.raw.fishsanctuarylocations), FishSanctuaryLocations.class);
        // generate map of fishSanctuaryOgfId : FishSanctuaryLocations entries
        Map<Integer, List<FishSanctuaryLocations>> fishSanctuaryLocationsMap = fishSanctuaryLocationsList.stream()
                .collect(groupingBy(FishSanctuaryLocations::getFishSanctuaryOgfId));
        Log.i("Zack", String.valueOf(fishSanctuaryLocationsMap.size()));

        List<NonAnglingMethods> nonAnglingMethodsList =
                readCsvData(getResources().openRawResource(R.raw.nonanglingmethods), NonAnglingMethods.class);
        // generate map of Zone : NonAnglingMethods entries
        Map<Integer, List<NonAnglingMethods>> nonAnglingMethodsMap = nonAnglingMethodsList.stream()
                .collect(groupingBy(NonAnglingMethods::getZoneNumber));
        Log.i("Zack", String.valueOf(nonAnglingMethodsMap.size()));

        List<NonCanadianRegulations> nonCanadianRegulationsList =
                readCsvData(getResources().openRawResource(R.raw.noncanadianregulations), NonCanadianRegulations.class);
        // generate map of Zone : NonCanadianRegulations entries
        Map<Integer, Map<Integer, List<NonCanadianRegulations>>> nonCanadianRegulationsMap = nonCanadianRegulationsList.stream()
                .collect(groupingBy(NonCanadianRegulations::getZoneNumber, groupingBy(NonCanadianRegulations::getWaterbodyOgfId)));
        Log.i("Zack", String.valueOf(nonCanadianRegulationsMap.size()));

        List<ZoneWideRegulations> zoneWideRegulationsList =
                readCsvData(getResources().openRawResource(R.raw.zonewideregulations), ZoneWideRegulations.class);
        // generate map of Zone : ZoneWideRegulations entries
        Map<Integer, List<ZoneWideRegulations>> zoneWideRegulationsMap = zoneWideRegulationsList.stream()
                .collect(groupingBy(ZoneWideRegulations::getZoneNumber));
        Log.i("Zack", String.valueOf(zoneWideRegulationsMap.size()));

        List<ZoneWideSeasonsAndLimits> zoneWideSeasonsAndLimitsList =
                readCsvData(getResources().openRawResource(R.raw.zonewideseasonsandlimits), ZoneWideSeasonsAndLimits.class);
        // generate map of Zone : Species : ZoneWideSeasonsAndLimits entries
        Map<Integer, Map<String, List<ZoneWideSeasonsAndLimits>>> zoneWideSeasonsAndLimitsMap = zoneWideSeasonsAndLimitsList.stream()
                .collect(groupingBy(ZoneWideSeasonsAndLimits::getZoneNumber, groupingBy(ZoneWideSeasonsAndLimits::getSpecies)));
        Log.i("Zack", String.valueOf(zoneWideSeasonsAndLimitsMap.size()));
            
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        finish();
    }

public <T> List<T> readCsvData(InputStream is, Class<T> tClass)
{
    try
    {
        CsvToBeanBuilder<T> beanBuilder = new CsvToBeanBuilder<>(new InputStreamReader(is));
        beanBuilder.withType(tClass);
        return beanBuilder.build().parse();
    }
    catch (Exception e)
    {
        System.out.println(e);
    }
    return Collections.emptyList();
}


}
