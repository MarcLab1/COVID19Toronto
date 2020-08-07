package com.covid19toronto.helpers;

import android.content.Context;
import com.covid19toronto.R;
import com.covid19toronto.Record;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HelperFormatting {

    private Context context;

    public HelperFormatting (Context context)
    {   this.context = context;
    }

    public String getMostCasesFormatted(List<Record> recordsList)
    {
        return HelperDate.getDateStringMostCases(recordsList) + context.getString(R.string.space_open_bracket) +
                NumberFormat.getNumberInstance(Locale.US).format(HelperDate.getNumberMostCases(recordsList)) + context.getString(R.string.close_bracket) ;
    }

    public String getHospitalizationsFormatted(int recordListSize, int hospitalizations)
    {
        return hospitalizations + context.getString(R.string.space_open_bracket) + HelperDate.getPercentageStringFromInt(hospitalizations, recordListSize) +
                context.getString(R.string.close_bracket);
    }

    public String getDeathsFormatted(int recordsListSize, int deaths)
    {
        return deaths + context.getString(R.string.space_open_bracket) + HelperDate.getPercentageStringFromInt(deaths, recordsListSize) +
                context.getString(R.string.close_bracket);
    }

    public String getAgeGroupsFormatted(int ageGroup19Younger, int ageGroup20to29, int ageGroup30to39, int ageGroup40to49,
                                               int ageGroup50to59, int ageGroup60to69, int ageGroup70to79, int ageGroup80to89, int ageGroup90plus)
    {
        return context.getString(R.string.ninteen_and_younger) + ageGroup19Younger + "\n" +
                context.getString(R.string.twenty_to_twentynine) + ageGroup20to29 + "\n" +
                context.getString(R.string.thirty_to_thirtynine) + ageGroup30to39 + "\n" +
                context.getString(R.string.fourty_to_fourtynine) + ageGroup40to49 + "\n" +
                context.getString(R.string.fifty_to_fiftynine) + ageGroup50to59 + "\n" +
                context.getString(R.string.sixty_to_sixtynine) + ageGroup60to69 + "\n" +
                context.getString(R.string.seventy_to_seventynine) + ageGroup70to79 + "\n" +
                context.getString(R.string.eighty_to_eightynine) + ageGroup80to89 + "\n" +
                context.getString(R.string.ninety_plus) + ageGroup90plus;
    }

    public String getGenderFormatted(int maleCases, int femaleCases)
    {
        return context.getString(R.string.male) + maleCases + "\n" +
                context.getString(R.string.female) + femaleCases;
    }

    public String getSourcesOfInfectionFormatted(int closeContactCases, int outbreakCases, int communityCases, int travelCases, int institutionalCases, int healthcareCases)
    {
        return context.getString(R.string.close_contact) + closeContactCases + "\n" +
                context.getString(R.string.outbreak_associated) + outbreakCases + "\n" +
                context.getString(R.string.community_contact) + communityCases + "\n" +
                context.getString(R.string.travel_associated) + travelCases + "\n" +
                context.getString(R.string.institutional_contact) + institutionalCases + "\n" +
                context.getString(R.string.healthcare) + healthcareCases;
    }

    public String getLineDataSetLabel(String FSA)
    {
        return context.getString(R.string.covid_19_cases_in) + FSA;
    }

    public String getNoDataToDisplay()
    {
        return context.getString(R.string.no_data_to_display);
    }

    public String getNumberOfCasesTextFormatted(String FSA)
    {
        return context.getString(R.string.number_of_cases) + context.getString(R.string.space) + FSA + context.getString(R.string.colon);
    }

    public String getNumberFormatted(int numberOfCasesInFSA)
    {
        return NumberFormat.getNumberInstance(Locale.US).format(numberOfCasesInFSA).toString();
    }

    public String getPopulationInFSATextFormatted(String FSA)
    {
        return context.getString(R.string.population) + context.getString(R.string.space) + FSA + context.getString(R.string.colon);
    }

}
