package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class WaterbodyExceptions
{
    @CsvBindByName(column = "Waterbody OGF ID")
    private int waterbodyOgfId;

    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "Waterbody Description")
    private String waterbodyDescription;

    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "Season")
    private String season;

    @CsvBindByName(column = "Size Limit Only")
    private String sizeLimitOnly;
    
    @CsvBindByName(column = "Limit")
    private String limit;

    @CsvBindByName(column = "Bait Restriction")
    private String baitRestriction;

    @CsvBindByName(column = "Gear Restriction")
    private String gearRestriction;

    @CsvBindByName(column = "Impounding Device Restriction")
    private String impoundingDeviceRestriction;

    @CsvBindByName(column = "Time of Day Restriction")
    private String timeOfDayRestriction;

    @CsvBindByName(column = "Special Licence Tag")
    private String specialLicenceTag;

    @CsvBindByName(column = "General Catch and Possession Restriction")
    private String generalCatchAndPossessionRestriction;
    
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

    public String getSpecies() {
        return species;
    }

    public String getSeason() {
        return season;
    }

    public String getSizeLimitOnly() {
        return sizeLimitOnly;
    }

    public String getLimit() {
        return limit;
    }

    public String getBaitRestriction() {
        return baitRestriction;
    }

    public String getGearRestriction() {
        return gearRestriction;
    }

    public String getImpoundingDeviceRestriction() {
        return impoundingDeviceRestriction;
    }

    public String getTimeOfDayRestriction() {
        return timeOfDayRestriction;
    }

    public String getSpecialLicenceTag() {
        return specialLicenceTag;
    }

    public String getGeneralCatchAndPossessionRestriction() {
        return generalCatchAndPossessionRestriction;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    

}
