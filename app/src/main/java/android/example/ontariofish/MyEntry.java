package android.example.ontariofish;

import android.os.Parcel;
import android.os.Parcelable;

public class MyEntry implements Parcelable {
    private String fishType;
    private String fishSize;
    private String fishWeight;
    private String fishLocation;
    private String fishDate;

    public MyEntry(String line1, String line2, String line3, String line4, String line5) {
        fishType = line1;
        fishLocation = line2;
        fishSize = line3;
        fishWeight = line4;
        fishDate = line5;
    }

    protected MyEntry(Parcel in) {
        fishType = in.readString();
        fishSize = in.readString();
        fishWeight = in.readString();
        fishLocation = in.readString();
        fishDate = in.readString();
    }

    public static final Creator<MyEntry> CREATOR = new Creator<MyEntry>() {
        @Override
        public MyEntry createFromParcel(Parcel in) {
            return new MyEntry(in);
        }

        @Override
        public MyEntry[] newArray(int size) {
            return new MyEntry[size];
        }
    };

    public String getFishType() {
        return fishType;
    }

    public String getFishLocation() {
        return fishLocation;
    }

    public String getFishDate() {
        return fishDate;
    }

    public String getFishSize() {
        return fishSize;
    }

    public String getFishWeight() {
        return fishWeight;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fishType);
        parcel.writeString(fishSize);
        parcel.writeString(fishWeight);
        parcel.writeString(fishLocation);
        parcel.writeString(fishDate);
    }
}