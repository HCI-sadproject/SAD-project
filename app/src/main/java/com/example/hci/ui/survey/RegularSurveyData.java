package com.example.hci.ui.survey;

import java.util.Date;

public class RegularSurveyData {
    private String wakeupDifficulty;
    private Integer sleepHours;
    private String moodLevel;
    private Date submissionDate;

    public RegularSurveyData(String wakeupDifficulty, Integer sleepHours, String moodLevel) {
        this.wakeupDifficulty = wakeupDifficulty;
        this.sleepHours = sleepHours;
        this.moodLevel = moodLevel;
        this.submissionDate = new Date();
    }

    // Getters
    public String getWakeupDifficulty() { return wakeupDifficulty; }
    public Integer getSleepHours() { return sleepHours; }
    public String getMoodLevel() { return moodLevel; }
    public Date getSubmissionDate() { return submissionDate; }
}