package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class FishSanctuaries 
{
    @CsvBindByName(column = "Fish Sanctuary OGF ID")
    private int fishSanctuaryOgfId;

    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "Waterbody Description")
    private String waterbodyDescription;

    @CsvBindByName(column = "Fish Sanctuary")
    private String fishSanctuary;

    @CsvBindByName(column = "Effective Date")
    @CsvDate("mm/dd/yyyy")
    private Date effectiveDate;

    @CsvBindByName(column = "Expiry Date")
    @CsvDate("mm/dd/yyyy")
    private Date expiryDate;

    // Getters and setters go here

    public int getFishSanctuaryOgfId() {
        return fishSanctuaryOgfId;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getWaterbodyDescription() {
        return waterbodyDescription;
    }

    public String getFishSanctuary() {
        return fishSanctuary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    
}
