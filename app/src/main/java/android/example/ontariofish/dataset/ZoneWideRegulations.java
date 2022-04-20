package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class ZoneWideRegulations
{
    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "General Catch and Possession Restriction")
    private String generalCatchAndPossessionRestriction;

    @CsvBindByName(column = "Bait Restriction")
    private String baitRestriction;

    @CsvBindByName(column = "Gear Restriction")
    private String gearRestriction;

    @CsvBindByName(column = "Effective Date")
    @CsvDate("mm/dd/yyyy")
    private Date effectiveDate;

    @CsvBindByName(column = "Expiry Date")
    @CsvDate("mm/dd/yyyy")
    private Date expiryDate;

    // Getters and setters go here

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getGeneralCatchAndPossessionRestriction() {
        return generalCatchAndPossessionRestriction;
    }

    public String getBaitRestriction() {
        return baitRestriction;
    }

    public String getGearRestriction() {
        return gearRestriction;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
