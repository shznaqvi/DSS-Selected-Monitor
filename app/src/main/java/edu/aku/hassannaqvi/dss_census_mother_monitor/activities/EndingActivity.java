package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.dss_census_mother_monitor.R;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.DatabaseHelper;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.MainApp;

public class EndingActivity extends Activity {

    private static final String TAG = EndingActivity.class.getSimpleName();
    @BindView(R.id.scrollView01)
    ScrollView scrollView01;
    @BindView(R.id.dcstatus)
    RadioGroup dcstatus;
    @BindView(R.id.dcstatus01)
    RadioButton dcstatus01;
    @BindView(R.id.dcstatus02)
    RadioButton dcstatus02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
        ButterKnife.bind(this);

        Boolean check = getIntent().getExtras().getBoolean("check");

        if (check) {
            dcstatus01.setEnabled(true);
            dcstatus02.setEnabled(false);

            MainApp.endFlag = false;

        } else {
            //fldGrpmn0823Reason.setVisibility(View.GONE);
            dcstatus01.setEnabled(false);
            dcstatus02.setEnabled(true);

            MainApp.endFlag = true;
        }

    }

    @OnClick(R.id.btn_End)
    void onBtnEndClick() {

        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {

                finish();

                if (!MainApp.endFlag || MainApp.currentMotherStatusCount == 0) {

                    MainApp.lstSelectedMothers.clear();
                    MainApp.memFlag = 0;

                    MainApp.NoMembersCount = 0;
                    MainApp.NoMaleCount = 0;
                    MainApp.NoFemaleCount = 0;
                    MainApp.NoBoyCount = 0;
                    MainApp.NoGirlCount = 0;

                    MainApp.TotalMWRACount = 0;
                    MainApp.TotalMaleCount = 0;
                    MainApp.TotalFemaleCount = 0;
                    MainApp.TotalBoyCount = 0;
                    MainApp.TotalGirlCount = 0;

//    Total No of Alive selectedMothers got from Section B
                    MainApp.currentMotherStatusCount = 0;
                    MainApp.currentDeceasedCheck = 0;
                    MainApp.currentMotherCheck = 0;

                    MainApp.selectedPos = 0;

                    MainApp.randID = 1;

                    MainApp.isRsvp = false;
                    MainApp.isHead = false;

                    MainApp.endFlag = false;

                    Intent endSec = new Intent(this, MainActivity.class);
                    startActivity(endSec);
                }
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        MainApp.mc.setIstatus(dcstatus01.isChecked() ? "1" : dcstatus02.isChecked() ? "2" : "0");

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();
    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateEnding();
        /*if (MainApp.memFlag != 0) {
            db.updateCensus();
        }
        if (MainApp.currentDeceasedCheck != 0) {
            db.updateDeceased();
        }
        if (MainApp.currentMotherCheck != 0) {
            db.updateMother();
        }
        if (MainApp.totalChild != 0) {
            db.updateIM();
        }*/

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean formValidation() {
        Toast.makeText(this, "Validating This Section ", Toast.LENGTH_SHORT).show();

        if (dcstatus.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(Not Selected): " + getString(R.string.dcstatus), Toast.LENGTH_LONG).show();
            dcstatus02.setError("Please Select One");    // Set Error on last radio button
            Log.i(TAG, "dcstatus: This data is Required!");
            return false;
        } else {
            dcstatus02.setError(null);
        }


        return true;
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }


}
