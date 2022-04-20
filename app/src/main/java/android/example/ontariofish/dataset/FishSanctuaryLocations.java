package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class FishSanctuaryLocations
{
    @CsvBindByName(column = "Fish Sanctuary OGF ID")
    private int fishSanctuaryOgfId;

    @CsvBindByName(column = "Latitude")
    private double latitude;

    @CsvBindByName(column = "Longitude")
    private double longitude;

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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
    
}
