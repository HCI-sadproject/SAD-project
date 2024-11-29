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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hci.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRegularSurveyBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final RadioGroup appetiteGroup;

  @NonNull
  public final RadioButton appetiteHigh;

  @NonNull
  public final RadioButton appetiteLow;

  @NonNull
  public final RadioButton appetiteNormal;

  @NonNull
  public final RadioButton appetiteVeryHigh;

  @NonNull
  public final RadioButton appetiteVeryLow;

  @NonNull
  public final RadioButton depressionExtreme;

  @NonNull
  public final RadioGroup depressionGroup;

  @NonNull
  public final RadioButton depressionMild;

  @NonNull
  public final RadioButton depressionModerate;

  @NonNull
  public final RadioButton depressionNone;

  @NonNull
  public final RadioButton depressionSevere;

  @NonNull
  public final RadioGroup energyGroup;

  @NonNull
  public final RadioButton energyHigh;

  @NonNull
  public final RadioButton energyLow;

  @NonNull
  public final RadioButton energyNormal;

  @NonNull
  public final RadioButton energyVeryHigh;

  @NonNull
  public final RadioButton energyVeryLow;

  @NonNull
  public final EditText sleepHours;

  @NonNull
  public final RadioGroup socialAnxietyGroup;

  @NonNull
  public final EditText socialDays;

  @NonNull
  public final RadioButton socialExtreme;

  @NonNull
  public final RadioButton socialMild;

  @NonNull
  public final RadioButton socialModerate;

  @NonNull
  public final RadioButton socialNone;

  @NonNull
  public final RadioButton socialSevere;

  @NonNull
  public final Button submitButton;

  @NonNull
  public final RadioGroup wakeupDifficultyGroup;

  @NonNull
  public final RadioButton wakeupExtreme;

  @NonNull
  public final RadioButton wakeupMild;

  @NonNull
  public final RadioButton wakeupModerate;

  @NonNull
  public final RadioButton wakeupNone;

  @NonNull
  public final RadioButton wakeupSevere;

  @NonNull
  public final RadioGroup weightGroup;

  @NonNull
  public final RadioButton weightHigh;

  @NonNull
  public final RadioButton weightLow;

  @NonNull
  public final RadioButton weightNormal;

  @NonNull
  public final RadioButton weightVeryHigh;

  @NonNull
  public final RadioButton weightVeryLow;

  private FragmentRegularSurveyBinding(@NonNull ScrollView rootView,
      @NonNull RadioGroup appetiteGroup, @NonNull RadioButton appetiteHigh,
      @NonNull RadioButton appetiteLow, @NonNull RadioButton appetiteNormal,
      @NonNull RadioButton appetiteVeryHigh, @NonNull RadioButton appetiteVeryLow,
      @NonNull RadioButton depressionExtreme, @NonNull RadioGroup depressionGroup,
      @NonNull RadioButton depressionMild, @NonNull RadioButton depressionModerate,
      @NonNull RadioButton depressionNone, @NonNull RadioButton depressionSevere,
      @NonNull RadioGroup energyGroup, @NonNull RadioButton energyHigh,
      @NonNull RadioButton energyLow, @NonNull RadioButton energyNormal,
      @NonNull RadioButton energyVeryHigh, @NonNull RadioButton energyVeryLow,
      @NonNull EditText sleepHours, @NonNull RadioGroup socialAnxietyGroup,
      @NonNull EditText socialDays, @NonNull RadioButton socialExtreme,
      @NonNull RadioButton socialMild, @NonNull RadioButton socialModerate,
      @NonNull RadioButton socialNone, @NonNull RadioButton socialSevere,
      @NonNull Button submitButton, @NonNull RadioGroup wakeupDifficultyGroup,
      @NonNull RadioButton wakeupExtreme, @NonNull RadioButton wakeupMild,
      @NonNull RadioButton wakeupModerate, @NonNull RadioButton wakeupNone,
      @NonNull RadioButton wakeupSevere, @NonNull RadioGroup weightGroup,
      @NonNull RadioButton weightHigh, @NonNull RadioButton weightLow,
      @NonNull RadioButton weightNormal, @NonNull RadioButton weightVeryHigh,
      @NonNull RadioButton weightVeryLow) {
    this.rootView = rootView;
    this.appetiteGroup = appetiteGroup;
    this.appetiteHigh = appetiteHigh;
    this.appetiteLow = appetiteLow;
    this.appetiteNormal = appetiteNormal;
    this.appetiteVeryHigh = appetiteVeryHigh;
    this.appetiteVeryLow = appetiteVeryLow;
    this.depressionExtreme = depressionExtreme;
    this.depressionGroup = depressionGroup;
    this.depressionMild = depressionMild;
    this.depressionModerate = depressionModerate;
    this.depressionNone = depressionNone;
    this.depressionSevere = depressionSevere;
    this.energyGroup = energyGroup;
    this.energyHigh = energyHigh;
    this.energyLow = energyLow;
    this.energyNormal = energyNormal;
    this.energyVeryHigh = energyVeryHigh;
    this.energyVeryLow = energyVeryLow;
    this.sleepHours = sleepHours;
    this.socialAnxietyGroup = socialAnxietyGroup;
    this.socialDays = socialDays;
    this.socialExtreme = socialExtreme;
    this.socialMild = socialMild;
    this.socialModerate = socialModerate;
    this.socialNone = socialNone;
    this.socialSevere = socialSevere;
    this.submitButton = submitButton;
    this.wakeupDifficultyGroup = wakeupDifficultyGroup;
    this.wakeupExtreme = wakeupExtreme;
    this.wakeupMild = wakeupMild;
    this.wakeupModerate = wakeupModerate;
    this.wakeupNone = wakeupNone;
    this.wakeupSevere = wakeupSevere;
    this.weightGroup = weightGroup;
    this.weightHigh = weightHigh;
    this.weightLow = weightLow;
    this.weightNormal = weightNormal;
    this.weightVeryHigh = weightVeryHigh;
    this.weightVeryLow = weightVeryLow;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRegularSurveyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRegularSurveyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_regular_survey, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRegularSurveyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appetiteGroup;
      RadioGroup appetiteGroup = ViewBindings.findChildViewById(rootView, id);
      if (appetiteGroup == null) {
        break missingId;
      }

      id = R.id.appetite_high;
      RadioButton appetiteHigh = ViewBindings.findChildViewById(rootView, id);
      if (appetiteHigh == null) {
        break missingId;
      }

      id = R.id.appetite_low;
      RadioButton appetiteLow = ViewBindings.findChildViewById(rootView, id);
      if (appetiteLow == null) {
        break missingId;
      }

      id = R.id.appetite_normal;
      RadioButton appetiteNormal = ViewBindings.findChildViewById(rootView, id);
      if (appetiteNormal == null) {
        break missingId;
      }

      id = R.id.appetite_very_high;
      RadioButton appetiteVeryHigh = ViewBindings.findChildViewById(rootView, id);
      if (appetiteVeryHigh == null) {
        break missingId;
      }

      id = R.id.appetite_very_low;
      RadioButton appetiteVeryLow = ViewBindings.findChildViewById(rootView, id);
      if (appetiteVeryLow == null) {
        break missingId;
      }

      id = R.id.depression_extreme;
      RadioButton depressionExtreme = ViewBindings.findChildViewById(rootView, id);
      if (depressionExtreme == null) {
        break missingId;
      }

      id = R.id.depressionGroup;
      RadioGroup depressionGroup = ViewBindings.findChildViewById(rootView, id);
      if (depressionGroup == null) {
        break missingId;
      }

      id = R.id.depression_mild;
      RadioButton depressionMild = ViewBindings.findChildViewById(rootView, id);
      if (depressionMild == null) {
        break missingId;
      }

      id = R.id.depression_moderate;
      RadioButton depressionModerate = ViewBindings.findChildViewById(rootView, id);
      if (depressionModerate == null) {
        break missingId;
      }

      id = R.id.depression_none;
      RadioButton depressionNone = ViewBindings.findChildViewById(rootView, id);
      if (depressionNone == null) {
        break missingId;
      }

      id = R.id.depression_severe;
      RadioButton depressionSevere = ViewBindings.findChildViewById(rootView, id);
      if (depressionSevere == null) {
        break missingId;
      }

      id = R.id.energyGroup;
      RadioGroup energyGroup = ViewBindings.findChildViewById(rootView, id);
      if (energyGroup == null) {
        break missingId;
      }

      id = R.id.energy_high;
      RadioButton energyHigh = ViewBindings.findChildViewById(rootView, id);
      if (energyHigh == null) {
        break missingId;
      }

      id = R.id.energy_low;
      RadioButton energyLow = ViewBindings.findChildViewById(rootView, id);
      if (energyLow == null) {
        break missingId;
      }

      id = R.id.energy_normal;
      RadioButton energyNormal = ViewBindings.findChildViewById(rootView, id);
      if (energyNormal == null) {
        break missingId;
      }

      id = R.id.energy_very_high;
      RadioButton energyVeryHigh = ViewBindings.findChildViewById(rootView, id);
      if (energyVeryHigh == null) {
        break missingId;
      }

      id = R.id.energy_very_low;
      RadioButton energyVeryLow = ViewBindings.findChildViewById(rootView, id);
      if (energyVeryLow == null) {
        break missingId;
      }

      id = R.id.sleepHours;
      EditText sleepHours = ViewBindings.findChildViewById(rootView, id);
      if (sleepHours == null) {
        break missingId;
      }

      id = R.id.socialAnxietyGroup;
      RadioGroup socialAnxietyGroup = ViewBindings.findChildViewById(rootView, id);
      if (socialAnxietyGroup == null) {
        break missingId;
      }

      id = R.id.socialDays;
      EditText socialDays = ViewBindings.findChildViewById(rootView, id);
      if (socialDays == null) {
        break missingId;
      }

      id = R.id.social_extreme;
      RadioButton socialExtreme = ViewBindings.findChildViewById(rootView, id);
      if (socialExtreme == null) {
        break missingId;
      }

      id = R.id.social_mild;
      RadioButton socialMild = ViewBindings.findChildViewById(rootView, id);
      if (socialMild == null) {
        break missingId;
      }

      id = R.id.social_moderate;
      RadioButton socialModerate = ViewBindings.findChildViewById(rootView, id);
      if (socialModerate == null) {
        break missingId;
      }

      id = R.id.social_none;
      RadioButton socialNone = ViewBindings.findChildViewById(rootView, id);
      if (socialNone == null) {
        break missingId;
      }

      id = R.id.social_severe;
      RadioButton socialSevere = ViewBindings.findChildViewById(rootView, id);
      if (socialSevere == null) {
        break missingId;
      }

      id = R.id.submitButton;
      Button submitButton = ViewBindings.findChildViewById(rootView, id);
      if (submitButton == null) {
        break missingId;
      }

      id = R.id.wakeupDifficultyGroup;
      RadioGroup wakeupDifficultyGroup = ViewBindings.findChildViewById(rootView, id);
      if (wakeupDifficultyGroup == null) {
        break missingId;
      }

      id = R.id.wakeup_extreme;
      RadioButton wakeupExtreme = ViewBindings.findChildViewById(rootView, id);
      if (wakeupExtreme == null) {
        break missingId;
      }

      id = R.id.wakeup_mild;
      RadioButton wakeupMild = ViewBindings.findChildViewById(rootView, id);
      if (wakeupMild == null) {
        break missingId;
      }

      id = R.id.wakeup_moderate;
      RadioButton wakeupModerate = ViewBindings.findChildViewById(rootView, id);
      if (wakeupModerate == null) {
        break missingId;
      }

      id = R.id.wakeup_none;
      RadioButton wakeupNone = ViewBindings.findChildViewById(rootView, id);
      if (wakeupNone == null) {
        break missingId;
      }

      id = R.id.wakeup_severe;
      RadioButton wakeupSevere = ViewBindings.findChildViewById(rootView, id);
      if (wakeupSevere == null) {
        break missingId;
      }

      id = R.id.weightGroup;
      RadioGroup weightGroup = ViewBindings.findChildViewById(rootView, id);
      if (weightGroup == null) {
        break missingId;
      }

      id = R.id.weight_high;
      RadioButton weightHigh = ViewBindings.findChildViewById(rootView, id);
      if (weightHigh == null) {
        break missingId;
      }

      id = R.id.weight_low;
      RadioButton weightLow = ViewBindings.findChildViewById(rootView, id);
      if (weightLow == null) {
        break missingId;
      }

      id = R.id.weight_normal;
      RadioButton weightNormal = ViewBindings.findChildViewById(rootView, id);
      if (weightNormal == null) {
        break missingId;
      }

      id = R.id.weight_very_high;
      RadioButton weightVeryHigh = ViewBindings.findChildViewById(rootView, id);
      if (weightVeryHigh == null) {
        break missingId;
      }

      id = R.id.weight_very_low;
      RadioButton weightVeryLow = ViewBindings.findChildViewById(rootView, id);
      if (weightVeryLow == null) {
        break missingId;
      }

      return new FragmentRegularSurveyBinding((ScrollView) rootView, appetiteGroup, appetiteHigh,
          appetiteLow, appetiteNormal, appetiteVeryHigh, appetiteVeryLow, depressionExtreme,
          depressionGroup, depressionMild, depressionModerate, depressionNone, depressionSevere,
          energyGroup, energyHigh, energyLow, energyNormal, energyVeryHigh, energyVeryLow,
          sleepHours, socialAnxietyGroup, socialDays, socialExtreme, socialMild, socialModerate,
          socialNone, socialSevere, submitButton, wakeupDifficultyGroup, wakeupExtreme, wakeupMild,
          wakeupModerate, wakeupNone, wakeupSevere, weightGroup, weightHigh, weightLow,
          weightNormal, weightVeryHigh, weightVeryLow);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
