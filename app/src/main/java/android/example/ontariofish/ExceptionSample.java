/*Class here specifically for the purpose of reading from speciesexceptions.txt
***DO NOT EDIT***
Bruce Stuff
 */

package android.example.ontariofish;

class ExceptionSample {

    private String region;
    private String name;
    private String lake;
    private String info;
    private String season;
    private String limits;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) { this.region = region; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLake() {
        return lake;
    }

    public void setLake(String lake) {
        this.lake = lake;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {this.info = info;}

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {this.season = season;}

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {this.limits = limits;}

}