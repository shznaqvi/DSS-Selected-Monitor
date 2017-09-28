package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.dss_census_mother_monitor.R;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MembersContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.DatabaseHelper;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.MainApp;

public class SectionFActivity extends Activity {


    private static final String TAG = SectionFActivity.class.getSimpleName();

    @BindView(R.id.dcf01)
    EditText dcf01;
    @BindView(R.id.dcf03)
    EditText dcf03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_f);
        ButterKnife.bind(this);

        MainApp.motherPosition = getIntent().getExtras().getInt("motherPosition");

    }

    @OnClick(R.id.btn_End)
    void onBtnEndClick() {
        //TODO implement
        Toast.makeText(this, "Not Processing This Section", Toast.LENGTH_SHORT).show();

//        finish();
        Toast.makeText(this, "Starting Form Ending Section", Toast.LENGTH_SHORT).show();

        if (UpdateDB()) {
            MainApp.endActivity(this, this);
        }
    }

    @OnClick(R.id.btn_Continue)
    void onBtnContinueClick() {

        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Toast.makeText(this, "Starting Next Section", Toast.LENGTH_SHORT).show();


                MainApp.currentMotherCheck = 1;

                finish();

                startActivity(new Intent(this, SectionGActivity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean formValidation() {
        Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        01
        if (dcf01.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcf01), Toast.LENGTH_SHORT).show();
            dcf01.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "dcf01: This data is Required!");
            return false;
        } else {
            dcf01.setError(null);
        }

//        03
        if (dcf03.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcf03), Toast.LENGTH_SHORT).show();
            dcf03.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "dcf03: This data is Required!");
            return false;
        } else {
            dcf03.setError(null);
        }

        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sF = new JSONObject();

        // Edit Text

//        A
        sF.put("dcfa", MainApp.motherPosition + 1);
//        01
        sF.put("dcf01", dcf01.getText().toString());

//        03
        sF.put("dcf03", dcf03.getText().toString());

        MainApp.mc.setsF(String.valueOf(sF));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }


    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        Long rowcount = db.addMother(MainApp.mc);
        MainApp.mc.set_ID(String.valueOf(rowcount));
        db.close();
        if (rowcount != -1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();

            MainApp.mc.setUID(
                    (MainApp.mc.getDeviceId() + MainApp.mc.get_ID()));
            db.updateMotherID();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }

}
