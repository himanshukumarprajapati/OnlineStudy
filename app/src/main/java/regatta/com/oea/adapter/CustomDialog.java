package regatta.com.oea.adapter;

/**
 * Created by rahul.singh on 5/4/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import regatta.com.oea.R;


public class CustomDialog extends Activity {

    private Button buttonClick;
    TextView dummy;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_main);

        buttonClick = (Button) findViewById(R.id.buttonClick);
        dummy=(TextView) findViewById(R.id.dummy);



        // add listener to button
        buttonClick.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Create custom dialog object
                final Dialog dialog = new Dialog(CustomDialog.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText text = (EditText) dialog.findViewById(R.id.textDialog);
                text.setText("Custom dialog Android example.");
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.backicon);

                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                declineButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                      //  dummy.setText(text.getText().toString());
                        dialog.dismiss();
                    }
                });


            }

        });

    }

}
