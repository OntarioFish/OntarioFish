package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;

public class AdditionalFishingOpportunities 
{
    @CsvBindByName(column = "Waterbody OGF ID")
    private int waterbodyOgfId;

    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "Season")
    private String season;

    @CsvBindByName(column = "Limit")
    private String limit;

    @CsvBindByName(column = "Size Limit Only")
    private String sizeLimitOnly;

    @CsvBindByName(column = "Waterbody Description")
    private String waterbodyDescription;

    // Getters and setters go here
    public int getWaterbodyOgfId() {
        return waterbodyOgfId;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getSpecies() {
        return species;
    }

    public String getSeason() {
        return season;
    }

    public String getLimit() {
        return limit;
    }

    public String getSizeLimitOnly() {
        return sizeLimitOnly;
    }

    public String getWaterbodyDescription() {
        return waterbodyDescription;
    }
    
}
