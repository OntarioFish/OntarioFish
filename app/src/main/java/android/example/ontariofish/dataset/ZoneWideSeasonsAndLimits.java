package android.example.ontariofish.dataset;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class ZoneWideSeasonsAndLimits
{
    @CsvBindByName(column = "Zone Number")
    private int zoneNumber;

    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "Aggregate Trout and Salmon Limit")
    private String aggregateTroutAndSalmonLimit;

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

    public int getZoneNumber() {
        return zoneNumber;
    }

    public String getSpecies() {
        return species;
    }

    public String getAggregateTroutAndSalmonLimit() {
        return aggregateTroutAndSalmonLimit;
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
