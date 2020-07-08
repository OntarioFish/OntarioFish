package android.example.ontariofish;
import android.os.Parcel;
import android.os.Parcelable;



public class Fish implements Parcelable {

    private String name;
    private String resourceName;

    public Fish(String name,  String resourceName) {
        this.name = name;
        this.resourceName = resourceName;

    }

    public Fish(Parcel source){
        this.name = source.readString();
        this.resourceName = source.readString();
    }


    public static final Creator<Fish> CREATOR = new Creator<Fish>() {
        @Override
        public Fish createFromParcel(Parcel in) {
            return new Fish(in);
        }

        @Override
        public Fish[] newArray(int size) {
            return new Fish[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getResourceName() {
        return resourceName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(resourceName);
    }
}
