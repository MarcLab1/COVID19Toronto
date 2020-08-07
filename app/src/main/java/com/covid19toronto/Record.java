package com.covid19toronto;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.covid19toronto.helpers.HelperDate;

@Entity
public class Record implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String _id;

    private String OutbreakAssociated, AgeGroup, NeighbourhoodName, FSA, SourceOfInfection, Classification, EpisodeDate, ReportedDate, ClientGender, Outcome,
            CurrentlyHospitalized, CurrentlyInICU, CurrentlyIncubated, EverHospitalized, EverInICU, EverIntubated;
    private long episodeDateLong;

    public Record() {

    }

    public Record(String AgeGroup, String NeighbourhoodName,String Outcome, String ClientGender, String Classification, String FSA, String CurrentlyHospitalized, String EpisodeDate, String EverInICU, String OutbreakAssociated, String ReportedDate, String CurrentlyInICU,
                  String SourceOfInfection, String _id, String EverIncubated, String EverHospitalized, String CurrentlyIncubated  ) {

        this._id = _id;
        if(AgeGroup.equals("null"))
            this.AgeGroup = "N/A";
        else
            this.AgeGroup= AgeGroup;
        if(NeighbourhoodName.equals("null"))
            this.NeighbourhoodName = "N/A";
        else
            this.NeighbourhoodName = NeighbourhoodName;
        this.Outcome = Outcome;
        this.ClientGender = ClientGender;
        this.Classification = Classification;
        if(FSA.equals("null"))
            this.FSA = "N/A";
        else
            this.FSA = FSA;
        this.CurrentlyHospitalized = CurrentlyHospitalized;
        this.EpisodeDate = EpisodeDate;
        this.EverInICU = EverInICU;
        this.OutbreakAssociated = OutbreakAssociated;
        this.ReportedDate = ReportedDate;
        this.CurrentlyInICU = CurrentlyInICU;
        this.SourceOfInfection = SourceOfInfection;
        this.EverIntubated = EverIncubated;
        this.EverHospitalized = EverHospitalized;
        this.CurrentlyIncubated = CurrentlyIncubated;
        episodeDateLong = HelperDate.getDateLongFromString(EpisodeDate);
    }

    protected Record(Parcel in) {
        _id = in.readString();
        OutbreakAssociated = in.readString();
        AgeGroup = in.readString();
        NeighbourhoodName = in.readString();
        FSA = in.readString();
        SourceOfInfection = in.readString();
        Classification = in.readString();
        EpisodeDate = in.readString();
        ReportedDate = in.readString();
        ClientGender = in.readString();
        Outcome = in.readString();
        CurrentlyHospitalized = in.readString();
        CurrentlyInICU = in.readString();
        CurrentlyIncubated = in.readString();
        EverHospitalized = in.readString();
        EverInICU = in.readString();
        EverIntubated = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(OutbreakAssociated);
        dest.writeString(AgeGroup);
        dest.writeString(NeighbourhoodName);
        dest.writeString(FSA);
        dest.writeString(SourceOfInfection);
        dest.writeString(Classification);
        dest.writeString(EpisodeDate);
        dest.writeString(ReportedDate);
        dest.writeString(ClientGender);
        dest.writeString(Outcome);
        dest.writeString(CurrentlyHospitalized);
        dest.writeString(CurrentlyInICU);
        dest.writeString(CurrentlyIncubated);
        dest.writeString(EverHospitalized);
        dest.writeString(EverInICU);
        dest.writeString(EverIntubated);
    }

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOutbreakAssociated() {
        return OutbreakAssociated;
    }

    public void setOutbreakAssociated(String outbreakAssociated) {
        OutbreakAssociated = outbreakAssociated;
    }

    public String getAgeGroup() {
        return AgeGroup;
    }

    public void setAgeGroup(String ageGroup) {
        AgeGroup = ageGroup;
    }

    public String getNeighbourhoodName() {
        return NeighbourhoodName;
    }

    public void setNeighbourhoodName(String neighbourhoodName) {
        NeighbourhoodName = neighbourhoodName;
    }

    public String getFSA() {
        return FSA;
    }

    public void setFSA(String FSA) {
        this.FSA = FSA;
    }

    public String getSourceOfInfection() {
        return SourceOfInfection;
    }

    public void setSourceOfInfection(String sourceOfInfection) {
        SourceOfInfection = sourceOfInfection;
    }

    public String getClassification() {
        return Classification;
    }

    public void setClassification(String classification) {
        Classification = classification;
    }

    public String getEpisodeDate() {
        return EpisodeDate;
    }

    public void setEpisodeDate(String episodeDate) {
        EpisodeDate = episodeDate;
    }

    public String getReportedDate() {
        return ReportedDate;
    }

    public void setReportedDate(String reportedDate) {
        ReportedDate = reportedDate;
    }

    public String getClientGender() {
        return ClientGender;
    }

    public void setClientGender(String clientGender) {
        ClientGender = clientGender;
    }

    public String getOutcome() {
        return Outcome;
    }

    public void setOutcome(String outcome) {
        Outcome = outcome;
    }

    public String getCurrentlyHospitalized() {
        return CurrentlyHospitalized;
    }

    public void setCurrentlyHospitalized(String currentlyHospitalized) {
        CurrentlyHospitalized = currentlyHospitalized;
    }

    public String getCurrentlyInICU() {
        return CurrentlyInICU;
    }

    public void setCurrentlyInICU(String currentlyInICU) {
        CurrentlyInICU = currentlyInICU;
    }

    public String getCurrentlyIncubated() {
        return CurrentlyIncubated;
    }

    public void setCurrentlyIncubated(String currentlyIncubated) {
        CurrentlyIncubated = currentlyIncubated;
    }

    public String getEverHospitalized() {
        return EverHospitalized;
    }

    public void setEverHospitalized(String everHospitalized) {
        EverHospitalized = everHospitalized;
    }

    public String getEverInICU() {
        return EverInICU;
    }

    public void setEverInICU(String everInICU) {
        EverInICU = everInICU;
    }

    public String getEverIntubated() {
        return EverIntubated;
    }

    public void setEverIntubated(String everIntubated) {
        EverIntubated = everIntubated;
    }

    public long getEpisodeDateLong() {
        return episodeDateLong;
    }

    public void setEpisodeDateLong(long episodeDateLong) {
        this.episodeDateLong = episodeDateLong;
    }

    public int getYear()
    {
        return HelperDate.getDateYearFromLong(episodeDateLong);
    }

    public int getMonth()
    {
        return HelperDate.getDateMonthFromLong(episodeDateLong);
    }

    public int getDay()
    {
        return HelperDate.getDateDayFromLong(episodeDateLong);
    }

}
