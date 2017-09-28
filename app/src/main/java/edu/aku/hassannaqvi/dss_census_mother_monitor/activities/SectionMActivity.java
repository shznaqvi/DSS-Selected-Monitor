package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.dss_census_mother_monitor.R;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.DatabaseHelper;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.MainApp;

public class SectionMActivity extends Activity {

    private static final String TAG = SectionMActivity.class.getSimpleName();

    @BindView(R.id.dcm13)
    RadioGroup dcm13;
    @BindView(R.id.dcm1301)
    RadioButton dcm1301;
    @BindView(R.id.dcm1302)
    RadioButton dcm1302;
    @BindView(R.id.dcm1303)
    RadioButton dcm1303;
    @BindView(R.id.dcm1304)
    RadioButton dcm1304;
    @BindView(R.id.dcm1305)
    RadioButton dcm1305;
    @BindView(R.id.dcm1396)
    RadioButton dcm1396;
    @BindView(R.id.dcm1396x)
    EditText dcm1396x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_m);
        ButterKnife.bind(this);

//        13
        dcm1396.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dcm1396x.setVisibility(View.VISIBLE);
                } else {
                    dcm1396x.setVisibility(View.GONE);
                    dcm1396x.setText(null);
                }
            }
        });

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

                finish();

                Intent secNext = new Intent(this, EndingActivity.class);
                secNext.putExtra("check",true);
                startActivity(secNext);
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.btn_End)
    void onBtnEndClick() {

        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Complete Sections", Toast.LENGTH_SHORT).show();
//        finish();
//        Intent endSec = new Intent(this, EndingActivity.class);
//        endSec.putExtra("check", false);
//        startActivity(endSec);

        MainApp.endActivity(this,this);

    }

    private boolean UpdateDB() {

        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSM();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean formValidation() {
        Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        13
        if (dcm13.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcm13), Toast.LENGTH_SHORT).show();
            dcm1396.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "dcm13: This data is Required!");
            return false;
        } else {
            dcm1396.setError(null);
        }
        if (dcm1396.isChecked() && dcm1396x.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcm13), Toast.LENGTH_SHORT).show();
            dcm1396x.setError("This data is Required! Please enter some value or zero");

            Log.i(TAG, "dcm1396x: This data is Required!");
            return false;
        } else {
            dcm1396x.setError(null);
        }
        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sM = new JSONObject();

        sM.put("dcm13", dcm1301.isChecked() ? "1" : dcm1302.isChecked() ? "2" : dcm1303.isChecked() ? "3"
                : dcm1304.isChecked() ? "4" : dcm1305.isChecked() ? "5" : dcm1396.isChecked() ? "96" : "0");
        sM.put("dcm1396x", dcm1396x.getText().toString());

        MainApp.mc.setsM(String.valueOf(sM));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }
}