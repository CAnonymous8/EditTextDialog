package com.tencent.neilchen.edittextdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by neil.chen on 2017/4/7.
 */

public class EditTextDialog extends DialogFragment {
    private ProgressDialog progressDialog;
    private String hint;
    private EditTextDialog.onSendClickListener onSendClickListener;

    public interface onSendClickListener{
       void send(String information);
    }

    @SuppressLint("ValidFragment")
    public EditTextDialog(String hint) {
        this.hint = hint;
    }

    public void addOnSendClickListener(onSendClickListener onSendClickListener){

        this.onSendClickListener = onSendClickListener;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.editTextStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = View.inflate(getActivity(), R.layout.layout_edittext, null);
        final EditText editText = (EditText) view.findViewById(R.id.et);
        editText.setHint(hint);
        final TextView tv_send = (TextView) view.findViewById(R.id.send);

        dialog.setContentView(view);

        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0.6f;
        lp.gravity = Gravity.BOTTOM;
        lp.alpha = 1f;

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        editText.requestFocus();
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0){
                    tv_send.setBackgroundResource(R.drawable.text_bg);
                }else {
                    tv_send.setBackgroundResource(R.drawable.text_bg2);
                }
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length() > 0){
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    progressDialog.setContentView(R.layout.layout_progressdialog);
                    if (onSendClickListener != null){
                        onSendClickListener.send(editText.getText().toString());
                    }

                }else {
                    Toast.makeText(getActivity(),"给点内容吧",Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                hideKeyBoard();
            }
        });


        return dialog;
    }

    public void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
