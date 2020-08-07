package com.covid19toronto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FSA implements Parcelable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String FSA;

    private int population, privateDwellings;


    public FSA() {

    }

    public FSA(String FSA, int population, int privateDwellings  ) {

        this.FSA = FSA;
        this.population = population;
        this.privateDwellings = privateDwellings;
    }

    protected FSA(Parcel in) {

        FSA = in.readString();
        population = in.readInt();
        privateDwellings = in.readInt();
    }

    public static final Creator<FSA> CREATOR = new Creator<FSA>() {
        @Override
        public FSA createFromParcel(Parcel in) {
            return new FSA(in);
        }

        @Override
        public FSA[] newArray(int size) {
            return new FSA[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(FSA);
        dest.writeInt(population);
        dest.writeInt(privateDwellings);
    }

    @NonNull
    public String getFSA() {
        return FSA;
    }

    public void setFSA(@NonNull String FSA) {
        this.FSA = FSA;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getPrivateDwellings() {
        return privateDwellings;
    }

    public void setPrivateDwellings(int privateDwellings) {
        this.privateDwellings = privateDwellings;
    }

    public static Creator<com.covid19toronto.FSA> getCREATOR() {
        return CREATOR;
    }
}
