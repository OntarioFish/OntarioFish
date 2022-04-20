package android.example.ontariofish.dataset;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class NonAnglingMethods
{
    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "Season")
    private String season;

    @CsvBindByName(column = "Non Angling Method Gear")
    private String nonAnglingMethodGear;

    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "Zone Note")
    private String zoneNote;

    @CsvBindByName(column = "Non Angling Method Limit")
    private String nonAnglingMethodLimit;

    @CsvBindByName(column = "Effective Date")
    @CsvDate("mm/dd/yyyy")
    private Date effectiveDate;

    @CsvBindByName(column = "Expiry Date")
    @CsvDate("mm/dd/yyyy")
    private Date expiryDate;

    // Getters and setters go here

    public String getSpecies() {
        return species;
    }

    public String getSeason() {
        return season;
    }

    public String getNonAnglingMethodGear() {
        return nonAnglingMethodGear;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getZoneNote() {
        return zoneNote;
    }

    public String getNonAnglingMethodLimit() {
        return nonAnglingMethodLimit;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
