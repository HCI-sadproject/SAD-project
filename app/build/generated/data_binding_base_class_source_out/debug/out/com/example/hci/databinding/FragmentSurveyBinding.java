// Generated by view binder compiler. Do not edit!
package com.example.hci.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hci.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSurveyBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final MaterialButton initialSurveyButton;

  @NonNull
  public final CardView initialSurveyCard;

  @NonNull
  public final MaterialButton regularSurveyButton;

  @NonNull
  public final CardView regularSurveyCard;

  private FragmentSurveyBinding(@NonNull ScrollView rootView,
      @NonNull MaterialButton initialSurveyButton, @NonNull CardView initialSurveyCard,
      @NonNull MaterialButton regularSurveyButton, @NonNull CardView regularSurveyCard) {
    this.rootView = rootView;
    this.initialSurveyButton = initialSurveyButton;
    this.initialSurveyCard = initialSurveyCard;
    this.regularSurveyButton = regularSurveyButton;
    this.regularSurveyCard = regularSurveyCard;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSurveyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSurveyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_survey, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSurveyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.initialSurveyButton;
      MaterialButton initialSurveyButton = ViewBindings.findChildViewById(rootView, id);
      if (initialSurveyButton == null) {
        break missingId;
      }

      id = R.id.initialSurveyCard;
      CardView initialSurveyCard = ViewBindings.findChildViewById(rootView, id);
      if (initialSurveyCard == null) {
        break missingId;
      }

      id = R.id.regularSurveyButton;
      MaterialButton regularSurveyButton = ViewBindings.findChildViewById(rootView, id);
      if (regularSurveyButton == null) {
        break missingId;
      }

      id = R.id.regularSurveyCard;
      CardView regularSurveyCard = ViewBindings.findChildViewById(rootView, id);
      if (regularSurveyCard == null) {
        break missingId;
      }

      return new FragmentSurveyBinding((ScrollView) rootView, initialSurveyButton,
          initialSurveyCard, regularSurveyButton, regularSurveyCard);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
