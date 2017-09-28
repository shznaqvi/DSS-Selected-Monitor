package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

public class SectionHActivity extends Activity {

    private static final String TAG = SectionHActivity.class.getSimpleName();


    @BindView(R.id.dch01)
    RadioGroup dch01;
    @BindView(R.id.dch0101)
    RadioButton dch0101;
    @BindView(R.id.dch0102)
    RadioButton dch0102;
    @BindView(R.id.dch05)
    EditText dch05;
    @BindView(R.id.dch08)
    RadioGroup dch08;
    @BindView(R.id.dch0801)
    RadioButton dch0801;
    @BindView(R.id.dch0802)
    RadioButton dch0802;
    @BindView(R.id.dch0803)
    RadioButton dch0803;
    @BindView(R.id.dch0804)
    RadioButton dch0804;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_h);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_End)
    void onBtnEndClick() {
        Toast.makeText(this, "Not Processing This Section", Toast.LENGTH_SHORT).show();
//        finish();
        MainApp.endActivity(this, this);
    }


    @OnClick(R.id.btn_Continue)
    void onBtnContinueClick() {
        Toast.makeText(this, "Processing This Section", Toast.LENGTH_SHORT).show();
        if (ValidateForm()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                Toast.makeText(this, "Starting Next Section", Toast.LENGTH_SHORT).show();

                finish();

                Intent secNext = new Intent(this, SectionIActivity.class);
                startActivity(secNext);
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean UpdateDB() {
        DatabaseHelper db = new DatabaseHelper(this);

        int updcount = db.updateSH();

        if (updcount == 1) {
            Toast.makeText(this, "Updating Database... Successful!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void SaveDraft() throws JSONException {
        Toast.makeText(this, "Saving Draft for  This Section", Toast.LENGTH_SHORT).show();

        JSONObject sH = new JSONObject();

        // Radio Group
        sH.put("dch01", dch0101.isChecked() ? "1" : dch0102.isChecked() ? "2" : "0");
        sH.put("dch05", dch05.getText().toString());
        sH.put("dch08", dch0801.isChecked() ? "1" : dch0802.isChecked() ? "2" : dch0803.isChecked() ? "3" : "0");

        MainApp.mc.setsH(String.valueOf(sH));

        Toast.makeText(this, "Validation Successful! - Saving Draft...", Toast.LENGTH_SHORT).show();

    }

    public boolean ValidateForm() {

        // ====================== Q 1 ==============
        if (dch01.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dch01), Toast.LENGTH_SHORT).show();
            dch0102.setError("This data is Required!");
            Log.i(TAG, "dch01: This data is Required!");
            return false;
        } else {
            dch0102.setError(null);
        }

        if (dch05.getText().toString().isEmpty()) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dch05), Toast.LENGTH_SHORT).show();
            dch05.setError("This data is Required!");
            Log.i(TAG, "dch05: This data is Required!");
            return false;
        } else {
            dch05.setError(null);
        }

        // =================== Q 10 ==================
        if (dch08.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "ERROR(empty): " + getString(R.string.dch08), Toast.LENGTH_SHORT).show();
            dch0804.setError("This data is Required!");
            Log.i(TAG, "dch08: This data is Required!");
            return false;
        } else {
            dch0804.setError(null);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "You Can't go back", Toast.LENGTH_LONG).show();
    }
}
