package com.example.hci.ui.survey;

import java.util.Date;

public class RegularSurveyData {
    private String wakeupDifficulty;
    private int sleepHours;
    private String socialAnxiety;
    private int socialDays;
    private String depressionLevel;
    private String weightChange;
    private String appetiteChange;
    private String energyLevel;
    private Date submissionDate;

    public RegularSurveyData(String wakeupDifficulty, int sleepHours, 
                            String socialAnxiety, int socialDays,
                            String depressionLevel, String weightChange,
                            String appetiteChange, String energyLevel) {
        this.wakeupDifficulty = wakeupDifficulty;
        this.sleepHours = sleepHours;
        this.socialAnxiety = socialAnxiety;
        this.socialDays = socialDays;
        this.depressionLevel = depressionLevel;
        this.weightChange = weightChange;
        this.appetiteChange = appetiteChange;
        this.energyLevel = energyLevel;
        this.submissionDate = new Date();
    }

    // Getters
    public String getWakeupDifficulty() { return wakeupDifficulty; }
    public int getSleepHours() { return sleepHours; }
    public String getSocialAnxiety() { return socialAnxiety; }
    public int getSocialDays() { return socialDays; }
    public String getDepressionLevel() { return depressionLevel; }
    public String getWeightChange() { return weightChange; }
    public String getAppetiteChange() { return appetiteChange; }
    public String getEnergyLevel() { return energyLevel; }
    public Date getSubmissionDate() { return submissionDate; }
}