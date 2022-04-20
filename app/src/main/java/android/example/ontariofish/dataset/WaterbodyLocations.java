package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class WaterbodyLocations
{
    @CsvBindByName(column = "Waterbody OGF ID")
    private int waterbodyOgfId;

    @CsvBindByName(column = "Waterbody Location Identifier")
    private String waterbodyLocationIdentifier;

    @CsvBindByName(column = "Official Name")
    private String officialName;

    @CsvBindByName(column = "Unofficial Name")
    private String unofficialName;

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

    public int getWaterbodyOgfId() {
        return waterbodyOgfId;
    }

    public String getWaterbodyLocationIdentifier() {
        return waterbodyLocationIdentifier;
    }

    public String getOfficialName() {
        return officialName;
    }

    public String getUnofficialName() {
        return unofficialName;
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
