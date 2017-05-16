package com.tencent.neilchen.edittextdialog;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditTextDialog editTextDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_bl = (TextView) findViewById(R.id.tv_informatino);
        final TextView textView = (TextView) findViewById(R.id.tv_text);
        editTextDialog = new EditTextDialog("说出你的故事");
        tv_bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTextDialog.show(getSupportFragmentManager(),"dialog");
            }
        });

        editTextDialog.addOnSendClickListener(new EditTextDialog.onSendClickListener() {
            @Override
            public void send(final String information) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editTextDialog.dismissProgressDialog();
                        editTextDialog.dismiss();
                        textView.setText(information);
                    }
                },1500);


            }
        });
    }
}
