// Generated by view binder compiler. Do not edit!
package com.example.hci.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hci.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentInitialSurveyBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final MonthGridLayoutBinding bestMoodMonthGrid;

  @NonNull
  public final YesNoRadioGroupBinding consecutiveYearsGroup;

  @NonNull
  public final NumberSelectionGridBinding depressionFrequencyGrid;

  @NonNull
  public final YesNoRadioGroupBinding durationQuestionGroup;

  @NonNull
  public final EditText fallSleepHours;

  @NonNull
  public final RadioButton frequency1;

  @NonNull
  public final RadioButton frequency2;

  @NonNull
  public final RadioButton frequency3;

  @NonNull
  public final RadioButton frequency4;

  @NonNull
  public final RadioButton frequency5;

  @NonNull
  public final RadioButton frequencyMoreThan5;

  @NonNull
  public final RadioGroup frequencyQuestionGroup;

  @NonNull
  public final RadioButton gssAppetite0;

  @NonNull
  public final RadioButton gssAppetite1;

  @NonNull
  public final RadioButton gssAppetite2;

  @NonNull
  public final RadioButton gssAppetite3;

  @NonNull
  public final RadioButton gssAppetite4;

  @NonNull
  public final RadioGroup gssAppetiteGroup;

  @NonNull
  public final RadioButton gssEnergy0;

  @NonNull
  public final RadioButton gssEnergy1;

  @NonNull
  public final RadioButton gssEnergy2;

  @NonNull
  public final RadioButton gssEnergy3;

  @NonNull
  public final RadioButton gssEnergy4;

  @NonNull
  public final RadioGroup gssEnergyGroup;

  @NonNull
  public final RadioButton gssMood0;

  @NonNull
  public final RadioButton gssMood1;

  @NonNull
  public final RadioButton gssMood2;

  @NonNull
  public final RadioButton gssMood3;

  @NonNull
  public final RadioButton gssMood4;

  @NonNull
  public final RadioGroup gssMoodGroup;

  @NonNull
  public final RadioButton gssSleep0;

  @NonNull
  public final RadioButton gssSleep1;

  @NonNull
  public final RadioButton gssSleep2;

  @NonNull
  public final RadioButton gssSleep3;

  @NonNull
  public final RadioButton gssSleep4;

  @NonNull
  public final RadioGroup gssSleepGroup;

  @NonNull
  public final RadioButton gssSocial0;

  @NonNull
  public final RadioButton gssSocial1;

  @NonNull
  public final RadioButton gssSocial2;

  @NonNull
  public final RadioButton gssSocial3;

  @NonNull
  public final RadioButton gssSocial4;

  @NonNull
  public final RadioGroup gssSocialGroup;

  @NonNull
  public final RadioButton gssWeight0;

  @NonNull
  public final RadioButton gssWeight1;

  @NonNull
  public final RadioButton gssWeight2;

  @NonNull
  public final RadioButton gssWeight3;

  @NonNull
  public final RadioButton gssWeight4;

  @NonNull
  public final RadioGroup gssWeightGroup;

  @NonNull
  public final MonthGridLayoutBinding highestWeightMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding leastEatingMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding leastSleepMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding leastSocialMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding mostEatingMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding mostSleepMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding mostSocialMonthGrid;

  @NonNull
  public final MonthGridLayoutBinding mostWeightLossMonthGrid;

  @NonNull
  public final YesNoRadioGroupBinding question1Group;

  @NonNull
  public final YesNoRadioGroupBinding question2Group;

  @NonNull
  public final YesNoRadioGroupBinding question3Group;

  @NonNull
  public final YesNoRadioGroupBinding question4Group;

  @NonNull
  public final YesNoRadioGroupBinding question5Group;

  @NonNull
  public final NumberSelectionGridBinding recentDepressionGrid;

  @NonNull
  public final RadioButton seasonFall;

  @NonNull
  public final RadioGroup seasonQuestionGroup;

  @NonNull
  public final RadioButton seasonSpring;

  @NonNull
  public final RadioButton seasonSummer;

  @NonNull
  public final RadioButton seasonWinter;

  @NonNull
  public final YesNoRadioGroupBinding seasonalProblemGroup;

  @NonNull
  public final YesNoRadioGroupBinding seasonalQuestionGroup;

  @NonNull
  public final TextView section2Title;

  @NonNull
  public final YesNoRadioGroupBinding section3Question10Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question11Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question1Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question2Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question3Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question4Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question5Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question6Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question7Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question8Group;

  @NonNull
  public final YesNoRadioGroupBinding section3Question9Group;

  @NonNull
  public final EditText springSleepHours;

  @NonNull
  public final Button submitButton;

  @NonNull
  public final EditText summerSleepHours;

  @NonNull
  public final RadioButton weightChange1;

  @NonNull
  public final RadioButton weightChange2;

  @NonNull
  public final RadioButton weightChange3;

  @NonNull
  public final RadioButton weightChange4;

  @NonNull
  public final RadioButton weightChange5;

  @NonNull
  public final RadioButton weightChange6;

  @NonNull
  public final RadioGroup weightChangeGroup;

  @NonNull
  public final EditText winterSleepHours;

  @NonNull
  public final MonthGridLayoutBinding worstMoodMonthGrid;

  private FragmentInitialSurveyBinding(@NonNull ScrollView rootView,
      @NonNull MonthGridLayoutBinding bestMoodMonthGrid,
      @NonNull YesNoRadioGroupBinding consecutiveYearsGroup,
      @NonNull NumberSelectionGridBinding depressionFrequencyGrid,
      @NonNull YesNoRadioGroupBinding durationQuestionGroup, @NonNull EditText fallSleepHours,
      @NonNull RadioButton frequency1, @NonNull RadioButton frequency2,
      @NonNull RadioButton frequency3, @NonNull RadioButton frequency4,
      @NonNull RadioButton frequency5, @NonNull RadioButton frequencyMoreThan5,
      @NonNull RadioGroup frequencyQuestionGroup, @NonNull RadioButton gssAppetite0,
      @NonNull RadioButton gssAppetite1, @NonNull RadioButton gssAppetite2,
      @NonNull RadioButton gssAppetite3, @NonNull RadioButton gssAppetite4,
      @NonNull RadioGroup gssAppetiteGroup, @NonNull RadioButton gssEnergy0,
      @NonNull RadioButton gssEnergy1, @NonNull RadioButton gssEnergy2,
      @NonNull RadioButton gssEnergy3, @NonNull RadioButton gssEnergy4,
      @NonNull RadioGroup gssEnergyGroup, @NonNull RadioButton gssMood0,
      @NonNull RadioButton gssMood1, @NonNull RadioButton gssMood2, @NonNull RadioButton gssMood3,
      @NonNull RadioButton gssMood4, @NonNull RadioGroup gssMoodGroup,
      @NonNull RadioButton gssSleep0, @NonNull RadioButton gssSleep1,
      @NonNull RadioButton gssSleep2, @NonNull RadioButton gssSleep3,
      @NonNull RadioButton gssSleep4, @NonNull RadioGroup gssSleepGroup,
      @NonNull RadioButton gssSocial0, @NonNull RadioButton gssSocial1,
      @NonNull RadioButton gssSocial2, @NonNull RadioButton gssSocial3,
      @NonNull RadioButton gssSocial4, @NonNull RadioGroup gssSocialGroup,
      @NonNull RadioButton gssWeight0, @NonNull RadioButton gssWeight1,
      @NonNull RadioButton gssWeight2, @NonNull RadioButton gssWeight3,
      @NonNull RadioButton gssWeight4, @NonNull RadioGroup gssWeightGroup,
      @NonNull MonthGridLayoutBinding highestWeightMonthGrid,
      @NonNull MonthGridLayoutBinding leastEatingMonthGrid,
      @NonNull MonthGridLayoutBinding leastSleepMonthGrid,
      @NonNull MonthGridLayoutBinding leastSocialMonthGrid,
      @NonNull MonthGridLayoutBinding mostEatingMonthGrid,
      @NonNull MonthGridLayoutBinding mostSleepMonthGrid,
      @NonNull MonthGridLayoutBinding mostSocialMonthGrid,
      @NonNull MonthGridLayoutBinding mostWeightLossMonthGrid,
      @NonNull YesNoRadioGroupBinding question1Group,
      @NonNull YesNoRadioGroupBinding question2Group,
      @NonNull YesNoRadioGroupBinding question3Group,
      @NonNull YesNoRadioGroupBinding question4Group,
      @NonNull YesNoRadioGroupBinding question5Group,
      @NonNull NumberSelectionGridBinding recentDepressionGrid, @NonNull RadioButton seasonFall,
      @NonNull RadioGroup seasonQuestionGroup, @NonNull RadioButton seasonSpring,
      @NonNull RadioButton seasonSummer, @NonNull RadioButton seasonWinter,
      @NonNull YesNoRadioGroupBinding seasonalProblemGroup,
      @NonNull YesNoRadioGroupBinding seasonalQuestionGroup, @NonNull TextView section2Title,
      @NonNull YesNoRadioGroupBinding section3Question10Group,
      @NonNull YesNoRadioGroupBinding section3Question11Group,
      @NonNull YesNoRadioGroupBinding section3Question1Group,
      @NonNull YesNoRadioGroupBinding section3Question2Group,
      @NonNull YesNoRadioGroupBinding section3Question3Group,
      @NonNull YesNoRadioGroupBinding section3Question4Group,
      @NonNull YesNoRadioGroupBinding section3Question5Group,
      @NonNull YesNoRadioGroupBinding section3Question6Group,
      @NonNull YesNoRadioGroupBinding section3Question7Group,
      @NonNull YesNoRadioGroupBinding section3Question8Group,
      @NonNull YesNoRadioGroupBinding section3Question9Group, @NonNull EditText springSleepHours,
      @NonNull Button submitButton, @NonNull EditText summerSleepHours,
      @NonNull RadioButton weightChange1, @NonNull RadioButton weightChange2,
      @NonNull RadioButton weightChange3, @NonNull RadioButton weightChange4,
      @NonNull RadioButton weightChange5, @NonNull RadioButton weightChange6,
      @NonNull RadioGroup weightChangeGroup, @NonNull EditText winterSleepHours,
      @NonNull MonthGridLayoutBinding worstMoodMonthGrid) {
    this.rootView = rootView;
    this.bestMoodMonthGrid = bestMoodMonthGrid;
    this.consecutiveYearsGroup = consecutiveYearsGroup;
    this.depressionFrequencyGrid = depressionFrequencyGrid;
    this.durationQuestionGroup = durationQuestionGroup;
    this.fallSleepHours = fallSleepHours;
    this.frequency1 = frequency1;
    this.frequency2 = frequency2;
    this.frequency3 = frequency3;
    this.frequency4 = frequency4;
    this.frequency5 = frequency5;
    this.frequencyMoreThan5 = frequencyMoreThan5;
    this.frequencyQuestionGroup = frequencyQuestionGroup;
    this.gssAppetite0 = gssAppetite0;
    this.gssAppetite1 = gssAppetite1;
    this.gssAppetite2 = gssAppetite2;
    this.gssAppetite3 = gssAppetite3;
    this.gssAppetite4 = gssAppetite4;
    this.gssAppetiteGroup = gssAppetiteGroup;
    this.gssEnergy0 = gssEnergy0;
    this.gssEnergy1 = gssEnergy1;
    this.gssEnergy2 = gssEnergy2;
    this.gssEnergy3 = gssEnergy3;
    this.gssEnergy4 = gssEnergy4;
    this.gssEnergyGroup = gssEnergyGroup;
    this.gssMood0 = gssMood0;
    this.gssMood1 = gssMood1;
    this.gssMood2 = gssMood2;
    this.gssMood3 = gssMood3;
    this.gssMood4 = gssMood4;
    this.gssMoodGroup = gssMoodGroup;
    this.gssSleep0 = gssSleep0;
    this.gssSleep1 = gssSleep1;
    this.gssSleep2 = gssSleep2;
    this.gssSleep3 = gssSleep3;
    this.gssSleep4 = gssSleep4;
    this.gssSleepGroup = gssSleepGroup;
    this.gssSocial0 = gssSocial0;
    this.gssSocial1 = gssSocial1;
    this.gssSocial2 = gssSocial2;
    this.gssSocial3 = gssSocial3;
    this.gssSocial4 = gssSocial4;
    this.gssSocialGroup = gssSocialGroup;
    this.gssWeight0 = gssWeight0;
    this.gssWeight1 = gssWeight1;
    this.gssWeight2 = gssWeight2;
    this.gssWeight3 = gssWeight3;
    this.gssWeight4 = gssWeight4;
    this.gssWeightGroup = gssWeightGroup;
    this.highestWeightMonthGrid = highestWeightMonthGrid;
    this.leastEatingMonthGrid = leastEatingMonthGrid;
    this.leastSleepMonthGrid = leastSleepMonthGrid;
    this.leastSocialMonthGrid = leastSocialMonthGrid;
    this.mostEatingMonthGrid = mostEatingMonthGrid;
    this.mostSleepMonthGrid = mostSleepMonthGrid;
    this.mostSocialMonthGrid = mostSocialMonthGrid;
    this.mostWeightLossMonthGrid = mostWeightLossMonthGrid;
    this.question1Group = question1Group;
    this.question2Group = question2Group;
    this.question3Group = question3Group;
    this.question4Group = question4Group;
    this.question5Group = question5Group;
    this.recentDepressionGrid = recentDepressionGrid;
    this.seasonFall = seasonFall;
    this.seasonQuestionGroup = seasonQuestionGroup;
    this.seasonSpring = seasonSpring;
    this.seasonSummer = seasonSummer;
    this.seasonWinter = seasonWinter;
    this.seasonalProblemGroup = seasonalProblemGroup;
    this.seasonalQuestionGroup = seasonalQuestionGroup;
    this.section2Title = section2Title;
    this.section3Question10Group = section3Question10Group;
    this.section3Question11Group = section3Question11Group;
    this.section3Question1Group = section3Question1Group;
    this.section3Question2Group = section3Question2Group;
    this.section3Question3Group = section3Question3Group;
    this.section3Question4Group = section3Question4Group;
    this.section3Question5Group = section3Question5Group;
    this.section3Question6Group = section3Question6Group;
    this.section3Question7Group = section3Question7Group;
    this.section3Question8Group = section3Question8Group;
    this.section3Question9Group = section3Question9Group;
    this.springSleepHours = springSleepHours;
    this.submitButton = submitButton;
    this.summerSleepHours = summerSleepHours;
    this.weightChange1 = weightChange1;
    this.weightChange2 = weightChange2;
    this.weightChange3 = weightChange3;
    this.weightChange4 = weightChange4;
    this.weightChange5 = weightChange5;
    this.weightChange6 = weightChange6;
    this.weightChangeGroup = weightChangeGroup;
    this.winterSleepHours = winterSleepHours;
    this.worstMoodMonthGrid = worstMoodMonthGrid;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentInitialSurveyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentInitialSurveyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_initial_survey, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentInitialSurveyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.best_mood_month_grid;
      View bestMoodMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (bestMoodMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_bestMoodMonthGrid = MonthGridLayoutBinding.bind(bestMoodMonthGrid);

      id = R.id.consecutive_years_group;
      View consecutiveYearsGroup = ViewBindings.findChildViewById(rootView, id);
      if (consecutiveYearsGroup == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_consecutiveYearsGroup = YesNoRadioGroupBinding.bind(consecutiveYearsGroup);

      id = R.id.depression_frequency_grid;
      View depressionFrequencyGrid = ViewBindings.findChildViewById(rootView, id);
      if (depressionFrequencyGrid == null) {
        break missingId;
      }
      NumberSelectionGridBinding binding_depressionFrequencyGrid = NumberSelectionGridBinding.bind(depressionFrequencyGrid);

      id = R.id.duration_question_group;
      View durationQuestionGroup = ViewBindings.findChildViewById(rootView, id);
      if (durationQuestionGroup == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_durationQuestionGroup = YesNoRadioGroupBinding.bind(durationQuestionGroup);

      id = R.id.fall_sleep_hours;
      EditText fallSleepHours = ViewBindings.findChildViewById(rootView, id);
      if (fallSleepHours == null) {
        break missingId;
      }

      id = R.id.frequency_1;
      RadioButton frequency1 = ViewBindings.findChildViewById(rootView, id);
      if (frequency1 == null) {
        break missingId;
      }

      id = R.id.frequency_2;
      RadioButton frequency2 = ViewBindings.findChildViewById(rootView, id);
      if (frequency2 == null) {
        break missingId;
      }

      id = R.id.frequency_3;
      RadioButton frequency3 = ViewBindings.findChildViewById(rootView, id);
      if (frequency3 == null) {
        break missingId;
      }

      id = R.id.frequency_4;
      RadioButton frequency4 = ViewBindings.findChildViewById(rootView, id);
      if (frequency4 == null) {
        break missingId;
      }

      id = R.id.frequency_5;
      RadioButton frequency5 = ViewBindings.findChildViewById(rootView, id);
      if (frequency5 == null) {
        break missingId;
      }

      id = R.id.frequency_more_than_5;
      RadioButton frequencyMoreThan5 = ViewBindings.findChildViewById(rootView, id);
      if (frequencyMoreThan5 == null) {
        break missingId;
      }

      id = R.id.frequency_question_group;
      RadioGroup frequencyQuestionGroup = ViewBindings.findChildViewById(rootView, id);
      if (frequencyQuestionGroup == null) {
        break missingId;
      }

      id = R.id.gss_appetite_0;
      RadioButton gssAppetite0 = ViewBindings.findChildViewById(rootView, id);
      if (gssAppetite0 == null) {
        break missingId;
      }

      id = R.id.gss_appetite_1;
      RadioButton gssAppetite1 = ViewBindings.findChildViewById(rootView, id);
      if (gssAppetite1 == null) {
        break missingId;
      }

      id = R.id.gss_appetite_2;
      RadioButton gssAppetite2 = ViewBindings.findChildViewById(rootView, id);
      if (gssAppetite2 == null) {
        break missingId;
      }

      id = R.id.gss_appetite_3;
      RadioButton gssAppetite3 = ViewBindings.findChildViewById(rootView, id);
      if (gssAppetite3 == null) {
        break missingId;
      }

      id = R.id.gss_appetite_4;
      RadioButton gssAppetite4 = ViewBindings.findChildViewById(rootView, id);
      if (gssAppetite4 == null) {
        break missingId;
      }

      id = R.id.gss_appetite_group;
      RadioGroup gssAppetiteGroup = ViewBindings.findChildViewById(rootView, id);
      if (gssAppetiteGroup == null) {
        break missingId;
      }

      id = R.id.gss_energy_0;
      RadioButton gssEnergy0 = ViewBindings.findChildViewById(rootView, id);
      if (gssEnergy0 == null) {
        break missingId;
      }

      id = R.id.gss_energy_1;
      RadioButton gssEnergy1 = ViewBindings.findChildViewById(rootView, id);
      if (gssEnergy1 == null) {
        break missingId;
      }

      id = R.id.gss_energy_2;
      RadioButton gssEnergy2 = ViewBindings.findChildViewById(rootView, id);
      if (gssEnergy2 == null) {
        break missingId;
      }

      id = R.id.gss_energy_3;
      RadioButton gssEnergy3 = ViewBindings.findChildViewById(rootView, id);
      if (gssEnergy3 == null) {
        break missingId;
      }

      id = R.id.gss_energy_4;
      RadioButton gssEnergy4 = ViewBindings.findChildViewById(rootView, id);
      if (gssEnergy4 == null) {
        break missingId;
      }

      id = R.id.gss_energy_group;
      RadioGroup gssEnergyGroup = ViewBindings.findChildViewById(rootView, id);
      if (gssEnergyGroup == null) {
        break missingId;
      }

      id = R.id.gss_mood_0;
      RadioButton gssMood0 = ViewBindings.findChildViewById(rootView, id);
      if (gssMood0 == null) {
        break missingId;
      }

      id = R.id.gss_mood_1;
      RadioButton gssMood1 = ViewBindings.findChildViewById(rootView, id);
      if (gssMood1 == null) {
        break missingId;
      }

      id = R.id.gss_mood_2;
      RadioButton gssMood2 = ViewBindings.findChildViewById(rootView, id);
      if (gssMood2 == null) {
        break missingId;
      }

      id = R.id.gss_mood_3;
      RadioButton gssMood3 = ViewBindings.findChildViewById(rootView, id);
      if (gssMood3 == null) {
        break missingId;
      }

      id = R.id.gss_mood_4;
      RadioButton gssMood4 = ViewBindings.findChildViewById(rootView, id);
      if (gssMood4 == null) {
        break missingId;
      }

      id = R.id.gss_mood_group;
      RadioGroup gssMoodGroup = ViewBindings.findChildViewById(rootView, id);
      if (gssMoodGroup == null) {
        break missingId;
      }

      id = R.id.gss_sleep_0;
      RadioButton gssSleep0 = ViewBindings.findChildViewById(rootView, id);
      if (gssSleep0 == null) {
        break missingId;
      }

      id = R.id.gss_sleep_1;
      RadioButton gssSleep1 = ViewBindings.findChildViewById(rootView, id);
      if (gssSleep1 == null) {
        break missingId;
      }

      id = R.id.gss_sleep_2;
      RadioButton gssSleep2 = ViewBindings.findChildViewById(rootView, id);
      if (gssSleep2 == null) {
        break missingId;
      }

      id = R.id.gss_sleep_3;
      RadioButton gssSleep3 = ViewBindings.findChildViewById(rootView, id);
      if (gssSleep3 == null) {
        break missingId;
      }

      id = R.id.gss_sleep_4;
      RadioButton gssSleep4 = ViewBindings.findChildViewById(rootView, id);
      if (gssSleep4 == null) {
        break missingId;
      }

      id = R.id.gss_sleep_group;
      RadioGroup gssSleepGroup = ViewBindings.findChildViewById(rootView, id);
      if (gssSleepGroup == null) {
        break missingId;
      }

      id = R.id.gss_social_0;
      RadioButton gssSocial0 = ViewBindings.findChildViewById(rootView, id);
      if (gssSocial0 == null) {
        break missingId;
      }

      id = R.id.gss_social_1;
      RadioButton gssSocial1 = ViewBindings.findChildViewById(rootView, id);
      if (gssSocial1 == null) {
        break missingId;
      }

      id = R.id.gss_social_2;
      RadioButton gssSocial2 = ViewBindings.findChildViewById(rootView, id);
      if (gssSocial2 == null) {
        break missingId;
      }

      id = R.id.gss_social_3;
      RadioButton gssSocial3 = ViewBindings.findChildViewById(rootView, id);
      if (gssSocial3 == null) {
        break missingId;
      }

      id = R.id.gss_social_4;
      RadioButton gssSocial4 = ViewBindings.findChildViewById(rootView, id);
      if (gssSocial4 == null) {
        break missingId;
      }

      id = R.id.gss_social_group;
      RadioGroup gssSocialGroup = ViewBindings.findChildViewById(rootView, id);
      if (gssSocialGroup == null) {
        break missingId;
      }

      id = R.id.gss_weight_0;
      RadioButton gssWeight0 = ViewBindings.findChildViewById(rootView, id);
      if (gssWeight0 == null) {
        break missingId;
      }

      id = R.id.gss_weight_1;
      RadioButton gssWeight1 = ViewBindings.findChildViewById(rootView, id);
      if (gssWeight1 == null) {
        break missingId;
      }

      id = R.id.gss_weight_2;
      RadioButton gssWeight2 = ViewBindings.findChildViewById(rootView, id);
      if (gssWeight2 == null) {
        break missingId;
      }

      id = R.id.gss_weight_3;
      RadioButton gssWeight3 = ViewBindings.findChildViewById(rootView, id);
      if (gssWeight3 == null) {
        break missingId;
      }

      id = R.id.gss_weight_4;
      RadioButton gssWeight4 = ViewBindings.findChildViewById(rootView, id);
      if (gssWeight4 == null) {
        break missingId;
      }

      id = R.id.gss_weight_group;
      RadioGroup gssWeightGroup = ViewBindings.findChildViewById(rootView, id);
      if (gssWeightGroup == null) {
        break missingId;
      }

      id = R.id.highest_weight_month_grid;
      View highestWeightMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (highestWeightMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_highestWeightMonthGrid = MonthGridLayoutBinding.bind(highestWeightMonthGrid);

      id = R.id.least_eating_month_grid;
      View leastEatingMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (leastEatingMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_leastEatingMonthGrid = MonthGridLayoutBinding.bind(leastEatingMonthGrid);

      id = R.id.least_sleep_month_grid;
      View leastSleepMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (leastSleepMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_leastSleepMonthGrid = MonthGridLayoutBinding.bind(leastSleepMonthGrid);

      id = R.id.least_social_month_grid;
      View leastSocialMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (leastSocialMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_leastSocialMonthGrid = MonthGridLayoutBinding.bind(leastSocialMonthGrid);

      id = R.id.most_eating_month_grid;
      View mostEatingMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (mostEatingMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_mostEatingMonthGrid = MonthGridLayoutBinding.bind(mostEatingMonthGrid);

      id = R.id.most_sleep_month_grid;
      View mostSleepMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (mostSleepMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_mostSleepMonthGrid = MonthGridLayoutBinding.bind(mostSleepMonthGrid);

      id = R.id.most_social_month_grid;
      View mostSocialMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (mostSocialMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_mostSocialMonthGrid = MonthGridLayoutBinding.bind(mostSocialMonthGrid);

      id = R.id.most_weight_loss_month_grid;
      View mostWeightLossMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (mostWeightLossMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_mostWeightLossMonthGrid = MonthGridLayoutBinding.bind(mostWeightLossMonthGrid);

      id = R.id.question1_group;
      View question1Group = ViewBindings.findChildViewById(rootView, id);
      if (question1Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_question1Group = YesNoRadioGroupBinding.bind(question1Group);

      id = R.id.question2_group;
      View question2Group = ViewBindings.findChildViewById(rootView, id);
      if (question2Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_question2Group = YesNoRadioGroupBinding.bind(question2Group);

      id = R.id.question3_group;
      View question3Group = ViewBindings.findChildViewById(rootView, id);
      if (question3Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_question3Group = YesNoRadioGroupBinding.bind(question3Group);

      id = R.id.question4_group;
      View question4Group = ViewBindings.findChildViewById(rootView, id);
      if (question4Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_question4Group = YesNoRadioGroupBinding.bind(question4Group);

      id = R.id.question5_group;
      View question5Group = ViewBindings.findChildViewById(rootView, id);
      if (question5Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_question5Group = YesNoRadioGroupBinding.bind(question5Group);

      id = R.id.recent_depression_grid;
      View recentDepressionGrid = ViewBindings.findChildViewById(rootView, id);
      if (recentDepressionGrid == null) {
        break missingId;
      }
      NumberSelectionGridBinding binding_recentDepressionGrid = NumberSelectionGridBinding.bind(recentDepressionGrid);

      id = R.id.season_fall;
      RadioButton seasonFall = ViewBindings.findChildViewById(rootView, id);
      if (seasonFall == null) {
        break missingId;
      }

      id = R.id.season_question_group;
      RadioGroup seasonQuestionGroup = ViewBindings.findChildViewById(rootView, id);
      if (seasonQuestionGroup == null) {
        break missingId;
      }

      id = R.id.season_spring;
      RadioButton seasonSpring = ViewBindings.findChildViewById(rootView, id);
      if (seasonSpring == null) {
        break missingId;
      }

      id = R.id.season_summer;
      RadioButton seasonSummer = ViewBindings.findChildViewById(rootView, id);
      if (seasonSummer == null) {
        break missingId;
      }

      id = R.id.season_winter;
      RadioButton seasonWinter = ViewBindings.findChildViewById(rootView, id);
      if (seasonWinter == null) {
        break missingId;
      }

      id = R.id.seasonal_problem_group;
      View seasonalProblemGroup = ViewBindings.findChildViewById(rootView, id);
      if (seasonalProblemGroup == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_seasonalProblemGroup = YesNoRadioGroupBinding.bind(seasonalProblemGroup);

      id = R.id.seasonal_question_group;
      View seasonalQuestionGroup = ViewBindings.findChildViewById(rootView, id);
      if (seasonalQuestionGroup == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_seasonalQuestionGroup = YesNoRadioGroupBinding.bind(seasonalQuestionGroup);

      id = R.id.section2_title;
      TextView section2Title = ViewBindings.findChildViewById(rootView, id);
      if (section2Title == null) {
        break missingId;
      }

      id = R.id.section3_question10_group;
      View section3Question10Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question10Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question10Group = YesNoRadioGroupBinding.bind(section3Question10Group);

      id = R.id.section3_question11_group;
      View section3Question11Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question11Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question11Group = YesNoRadioGroupBinding.bind(section3Question11Group);

      id = R.id.section3_question1_group;
      View section3Question1Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question1Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question1Group = YesNoRadioGroupBinding.bind(section3Question1Group);

      id = R.id.section3_question2_group;
      View section3Question2Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question2Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question2Group = YesNoRadioGroupBinding.bind(section3Question2Group);

      id = R.id.section3_question3_group;
      View section3Question3Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question3Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question3Group = YesNoRadioGroupBinding.bind(section3Question3Group);

      id = R.id.section3_question4_group;
      View section3Question4Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question4Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question4Group = YesNoRadioGroupBinding.bind(section3Question4Group);

      id = R.id.section3_question5_group;
      View section3Question5Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question5Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question5Group = YesNoRadioGroupBinding.bind(section3Question5Group);

      id = R.id.section3_question6_group;
      View section3Question6Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question6Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question6Group = YesNoRadioGroupBinding.bind(section3Question6Group);

      id = R.id.section3_question7_group;
      View section3Question7Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question7Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question7Group = YesNoRadioGroupBinding.bind(section3Question7Group);

      id = R.id.section3_question8_group;
      View section3Question8Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question8Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question8Group = YesNoRadioGroupBinding.bind(section3Question8Group);

      id = R.id.section3_question9_group;
      View section3Question9Group = ViewBindings.findChildViewById(rootView, id);
      if (section3Question9Group == null) {
        break missingId;
      }
      YesNoRadioGroupBinding binding_section3Question9Group = YesNoRadioGroupBinding.bind(section3Question9Group);

      id = R.id.spring_sleep_hours;
      EditText springSleepHours = ViewBindings.findChildViewById(rootView, id);
      if (springSleepHours == null) {
        break missingId;
      }

      id = R.id.submitButton;
      Button submitButton = ViewBindings.findChildViewById(rootView, id);
      if (submitButton == null) {
        break missingId;
      }

      id = R.id.summer_sleep_hours;
      EditText summerSleepHours = ViewBindings.findChildViewById(rootView, id);
      if (summerSleepHours == null) {
        break missingId;
      }

      id = R.id.weight_change_1;
      RadioButton weightChange1 = ViewBindings.findChildViewById(rootView, id);
      if (weightChange1 == null) {
        break missingId;
      }

      id = R.id.weight_change_2;
      RadioButton weightChange2 = ViewBindings.findChildViewById(rootView, id);
      if (weightChange2 == null) {
        break missingId;
      }

      id = R.id.weight_change_3;
      RadioButton weightChange3 = ViewBindings.findChildViewById(rootView, id);
      if (weightChange3 == null) {
        break missingId;
      }

      id = R.id.weight_change_4;
      RadioButton weightChange4 = ViewBindings.findChildViewById(rootView, id);
      if (weightChange4 == null) {
        break missingId;
      }

      id = R.id.weight_change_5;
      RadioButton weightChange5 = ViewBindings.findChildViewById(rootView, id);
      if (weightChange5 == null) {
        break missingId;
      }

      id = R.id.weight_change_6;
      RadioButton weightChange6 = ViewBindings.findChildViewById(rootView, id);
      if (weightChange6 == null) {
        break missingId;
      }

      id = R.id.weight_change_group;
      RadioGroup weightChangeGroup = ViewBindings.findChildViewById(rootView, id);
      if (weightChangeGroup == null) {
        break missingId;
      }

      id = R.id.winter_sleep_hours;
      EditText winterSleepHours = ViewBindings.findChildViewById(rootView, id);
      if (winterSleepHours == null) {
        break missingId;
      }

      id = R.id.worst_mood_month_grid;
      View worstMoodMonthGrid = ViewBindings.findChildViewById(rootView, id);
      if (worstMoodMonthGrid == null) {
        break missingId;
      }
      MonthGridLayoutBinding binding_worstMoodMonthGrid = MonthGridLayoutBinding.bind(worstMoodMonthGrid);

      return new FragmentInitialSurveyBinding((ScrollView) rootView, binding_bestMoodMonthGrid,
          binding_consecutiveYearsGroup, binding_depressionFrequencyGrid,
          binding_durationQuestionGroup, fallSleepHours, frequency1, frequency2, frequency3,
          frequency4, frequency5, frequencyMoreThan5, frequencyQuestionGroup, gssAppetite0,
          gssAppetite1, gssAppetite2, gssAppetite3, gssAppetite4, gssAppetiteGroup, gssEnergy0,
          gssEnergy1, gssEnergy2, gssEnergy3, gssEnergy4, gssEnergyGroup, gssMood0, gssMood1,
          gssMood2, gssMood3, gssMood4, gssMoodGroup, gssSleep0, gssSleep1, gssSleep2, gssSleep3,
          gssSleep4, gssSleepGroup, gssSocial0, gssSocial1, gssSocial2, gssSocial3, gssSocial4,
          gssSocialGroup, gssWeight0, gssWeight1, gssWeight2, gssWeight3, gssWeight4,
          gssWeightGroup, binding_highestWeightMonthGrid, binding_leastEatingMonthGrid,
          binding_leastSleepMonthGrid, binding_leastSocialMonthGrid, binding_mostEatingMonthGrid,
          binding_mostSleepMonthGrid, binding_mostSocialMonthGrid, binding_mostWeightLossMonthGrid,
          binding_question1Group, binding_question2Group, binding_question3Group,
          binding_question4Group, binding_question5Group, binding_recentDepressionGrid, seasonFall,
          seasonQuestionGroup, seasonSpring, seasonSummer, seasonWinter,
          binding_seasonalProblemGroup, binding_seasonalQuestionGroup, section2Title,
          binding_section3Question10Group, binding_section3Question11Group,
          binding_section3Question1Group, binding_section3Question2Group,
          binding_section3Question3Group, binding_section3Question4Group,
          binding_section3Question5Group, binding_section3Question6Group,
          binding_section3Question7Group, binding_section3Question8Group,
          binding_section3Question9Group, springSleepHours, submitButton, summerSleepHours,
          weightChange1, weightChange2, weightChange3, weightChange4, weightChange5, weightChange6,
          weightChangeGroup, winterSleepHours, binding_worstMoodMonthGrid);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}