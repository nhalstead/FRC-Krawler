package com.team2052.frckrawler.activity.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team2052.frckrawler.R;
import com.team2052.frckrawler.database.DBManager;

public class AddGameDialogActivity extends Activity implements OnClickListener {

    private DBManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogactivity_add_game);

        ((Button) findViewById(R.id.cancel)).setOnClickListener(this);
        ((Button) findViewById(R.id.addGame)).setOnClickListener(this);

        dbManager = DBManager.getInstance(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addGame:
                if (dbManager.addGame(((EditText) findViewById(R.id.nameVal)).
                        getText().toString()))
                    finish();
                else
                    Toast.makeText(this, "Could not add game to the Database. " +
                            "Name entered is already taken.", Toast.LENGTH_LONG).show();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
}
