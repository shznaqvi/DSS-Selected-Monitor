package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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

public class SectionJActivity extends Activity {

    private static final String TAG = SectionJActivity.class.getSimpleName();


    @BindView(R.id.dcj01)
    RadioGroup dcj01;
    @BindView(R.id.dcj0101)
    RadioButton dcj0101;
    @BindView(R.id.dcj0102)
    RadioButton dcj0102;
    @BindView(R.id.dcj0199)
    RadioButton dcj0199;
    @BindView(R.id.dcj02)
    RadioGroup dcj02;
    @BindView(R.id.dcj0201)
    RadioButton dcj0201;
    @BindView(R.id.dcj0202)
    RadioButton dcj0202;
    @BindView(R.id.dcj0299)
    RadioButton dcj0299;
    @BindView(R.id.dcj03)
    RadioGroup dcj03;
    @BindView(R.id.dcj0301)
    RadioButton dcj0301;
    @BindView(R.id.dcj0302)
    RadioButton dcj0302;
    @BindView(R.id.dcj0399)
    RadioButton dcj0399;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_j);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_End)
    void onBtnEndClick() {
        //TODO implement

        Toast.makeText(this, "Not Processing This Section", Toast.LENGTH_SHORT).show();
//        finish();
        MainApp.endActivity(this, this);
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
//                finish();

//                if (MainApp.totalChild > 0) {
//                    startActivity(new Intent(this, SectionKActivity.class));
//                } else {
//                    startActivity(new Intent(this, SectionLActivity.class));
//                }
                startActivity(new Intent(this, SectionMActivity.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public boolean formValidation() {
        Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

//        01
        if (dcj01.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcj01), Toast.LENGTH_SHORT).show();
            dcj0199.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "dcg01: This data is Required!");
            return false;
        } else {
            dcj0199.setError(null);
        }

//        02
        if (dcj02.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcj02), Toast.LENGTH_SHORT).show();
            dcj0299.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "dcg02: This data is Required!");
            return false;
        } else {
            dcj0299.setError(null);
        }

//        03
        if (dcj03.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dcj03), Toast.LENGTH_SHORT).show();
            dcj0399.setError("This data is Required!");    // Set Error on last radio button

            Log.i(TAG, "dcg03: This data is Required!");
            return false;
        } else {
            dcj0399.setError(null);
        }

        return true;
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sJ = new JSONObject();

//        01
        sJ.put("dcj01", dcj0101.isChecked() ? "1" : dcj0102.isChecked() ? "2"
                : dcj0199.isChecked() ? "99" : "0");
//        02
        sJ.put("dcj02", dcj0201.isChecked() ? "1" : dcj0202.isChecked() ? "2"
                : dcj0299.isChecked() ? "99" : "0");
//        03
        sJ.put("dcj03", dcj0301.isChecked() ? "1" : dcj0302.isChecked() ? "2"
                : dcj0399.isChecked() ? "99" : "0");

        MainApp.mc.setsJ(String.valueOf(sJ));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSJ();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
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
