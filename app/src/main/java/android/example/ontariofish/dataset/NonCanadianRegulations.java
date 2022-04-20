package android.example.ontariofish.dataset;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class NonCanadianRegulations
{
    @CsvBindByName(column = "Waterbody OGF ID")
    private int waterbodyOgfId;

    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "Waterbody Description")
    private String waterbodyDescription;

    @CsvBindByName(column = "General Catch and Possession Restriction")
    private String generalCatchAndPossessionRestriction;

    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "Season")
    private String season;

    @CsvBindByName(column = "Limit")
    private String limit;

    @CsvBindByName(column = "Limit Type")
    private String limitType;

    @CsvBindByName(column = "Effective Date")
    @CsvDate("mm/dd/yyyy")
    private Date effectiveDate;

    @CsvBindByName(column = "Expiry Date")
    @CsvDate("mm/dd/yyyy")
    private Date expiryDate;

    // Getters and setters go here

    public int getWaterbodyOgfId() {
        return waterbodyOgfId;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getWaterbodyDescription() {
        return waterbodyDescription;
    }

    public String getGeneralCatchAndPossessionRestriction() {
        return generalCatchAndPossessionRestriction;
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

    public String getLimitType() {
        return limitType;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    

}
