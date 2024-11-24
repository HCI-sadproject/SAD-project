// Generated by view binder compiler. Do not edit!
package com.example.hci.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hci.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDiaryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView dateHeader;

  @NonNull
  public final TextView dateText;

  @NonNull
  public final EditText diaryContent;

  @NonNull
  public final TextView diaryHeader;

  @NonNull
  public final Button moodAngry;

  @NonNull
  public final LinearLayout moodContainer;

  @NonNull
  public final Button moodHappy;

  @NonNull
  public final TextView moodHeader;

  @NonNull
  public final Button moodNormal;

  @NonNull
  public final Button moodSad;

  private FragmentDiaryBinding(@NonNull ConstraintLayout rootView, @NonNull TextView dateHeader,
      @NonNull TextView dateText, @NonNull EditText diaryContent, @NonNull TextView diaryHeader,
      @NonNull Button moodAngry, @NonNull LinearLayout moodContainer, @NonNull Button moodHappy,
      @NonNull TextView moodHeader, @NonNull Button moodNormal, @NonNull Button moodSad) {
    this.rootView = rootView;
    this.dateHeader = dateHeader;
    this.dateText = dateText;
    this.diaryContent = diaryContent;
    this.diaryHeader = diaryHeader;
    this.moodAngry = moodAngry;
    this.moodContainer = moodContainer;
    this.moodHappy = moodHappy;
    this.moodHeader = moodHeader;
    this.moodNormal = moodNormal;
    this.moodSad = moodSad;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDiaryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDiaryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_diary, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDiaryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.date_header;
      TextView dateHeader = ViewBindings.findChildViewById(rootView, id);
      if (dateHeader == null) {
        break missingId;
      }

      id = R.id.date_text;
      TextView dateText = ViewBindings.findChildViewById(rootView, id);
      if (dateText == null) {
        break missingId;
      }

      id = R.id.diary_content;
      EditText diaryContent = ViewBindings.findChildViewById(rootView, id);
      if (diaryContent == null) {
        break missingId;
      }

      id = R.id.diary_header;
      TextView diaryHeader = ViewBindings.findChildViewById(rootView, id);
      if (diaryHeader == null) {
        break missingId;
      }

      id = R.id.mood_angry;
      Button moodAngry = ViewBindings.findChildViewById(rootView, id);
      if (moodAngry == null) {
        break missingId;
      }

      id = R.id.mood_container;
      LinearLayout moodContainer = ViewBindings.findChildViewById(rootView, id);
      if (moodContainer == null) {
        break missingId;
      }

      id = R.id.mood_happy;
      Button moodHappy = ViewBindings.findChildViewById(rootView, id);
      if (moodHappy == null) {
        break missingId;
      }

      id = R.id.mood_header;
      TextView moodHeader = ViewBindings.findChildViewById(rootView, id);
      if (moodHeader == null) {
        break missingId;
      }

      id = R.id.mood_normal;
      Button moodNormal = ViewBindings.findChildViewById(rootView, id);
      if (moodNormal == null) {
        break missingId;
      }

      id = R.id.mood_sad;
      Button moodSad = ViewBindings.findChildViewById(rootView, id);
      if (moodSad == null) {
        break missingId;
      }

      return new FragmentDiaryBinding((ConstraintLayout) rootView, dateHeader, dateText,
          diaryContent, diaryHeader, moodAngry, moodContainer, moodHappy, moodHeader, moodNormal,
          moodSad);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}