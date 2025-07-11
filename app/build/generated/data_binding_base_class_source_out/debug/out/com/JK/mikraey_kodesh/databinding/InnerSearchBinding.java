// Generated by view binder compiler. Do not edit!
package com.JK.mikraey_kodesh.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.JK.mikraey_kodesh.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class InnerSearchBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button dialogButtonOK;

  @NonNull
  public final EditText editTextTextToSearch;

  @NonNull
  public final RelativeLayout layoutRoot;

  @NonNull
  public final TextView textView1;

  private InnerSearchBinding(@NonNull RelativeLayout rootView, @NonNull Button dialogButtonOK,
      @NonNull EditText editTextTextToSearch, @NonNull RelativeLayout layoutRoot,
      @NonNull TextView textView1) {
    this.rootView = rootView;
    this.dialogButtonOK = dialogButtonOK;
    this.editTextTextToSearch = editTextTextToSearch;
    this.layoutRoot = layoutRoot;
    this.textView1 = textView1;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static InnerSearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static InnerSearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.inner_search, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static InnerSearchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dialogButtonOK;
      Button dialogButtonOK = ViewBindings.findChildViewById(rootView, id);
      if (dialogButtonOK == null) {
        break missingId;
      }

      id = R.id.editTextTextToSearch;
      EditText editTextTextToSearch = ViewBindings.findChildViewById(rootView, id);
      if (editTextTextToSearch == null) {
        break missingId;
      }

      RelativeLayout layoutRoot = (RelativeLayout) rootView;

      id = R.id.textView1;
      TextView textView1 = ViewBindings.findChildViewById(rootView, id);
      if (textView1 == null) {
        break missingId;
      }

      return new InnerSearchBinding((RelativeLayout) rootView, dialogButtonOK, editTextTextToSearch,
          layoutRoot, textView1);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
