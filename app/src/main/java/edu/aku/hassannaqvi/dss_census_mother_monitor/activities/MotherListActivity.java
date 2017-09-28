package edu.aku.hassannaqvi.dss_census_mother_monitor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.aku.hassannaqvi.dss_census_mother_monitor.R;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MembersContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MotherContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.DatabaseHelper;
import edu.aku.hassannaqvi.dss_census_mother_monitor.core.MainApp;

public class MotherListActivity extends Activity {
    @BindView(R.id.membersExists)
    LinearLayout membersExists;
    @BindView(R.id.dca03)
    EditText dca03;
    @BindView(R.id.motherList)
    ListView motherList;
    @BindView(R.id.btn_End)
    Button btn_End;
    @BindView(R.id.lblNoMother)
    TextView lblNoMother;

    @BindView(R.id.checkSelectedHHID)
    Button checkSelectedHHID;

    Collection<MembersContract> selectedMothers;
    DatabaseHelper db;

    boolean checked = false;

    int leftChild = 0;
    int mwras = 0;

    boolean backFlag = false;

    listAdapter motherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_list);
        ButterKnife.bind(this);

        dca03.setText(MainApp.regionDss);
        dca03.setSelection(dca03.getText().length());

        db = new DatabaseHelper(this);


        try {
            /*Collection<MothersLst> mo = db.getMotherByUUID(MainApp.fc.getUID());

            MainApp.lstSelectedChildren = new ArrayList<>();

            for (MothersLst m : mo) {

                Log.d("Mothers", String.valueOf(m));

                if (m.getAgey().equals("") ? checkChild(m.getDate_of_birth()) : checkChildAge(m.getAgey(), m.getAgem(), m.getAged())) {
                    MainApp.lstSelectedChildren.add(new MothersLst(m));
                }
            }

            if (MainApp.lstSelectedChildren.size() == 0) {
//                btn_End.setEnabled(true);
                lblNoMother.setText("No Mother Found");
//                lblNoMother.setVisibility(View.VISIBLE);
            } else {
//                btn_End.setEnabled(false);
                lblNoMother.setText("Child Above Age 2:" + leftChild);
//                lblNoMother.setVisibility(View.GONE);
            }

            MainApp.fc.setsF(String.valueOf(MainApp.lstSelectedChildren.size()));

            db.updateMotherCount(MainApp.fc.getsF(), MainApp.fc.get_ID());

            listAdapter motherAdapter = new listAdapter(this, android.R.layout.simple_list_item_1, MainApp.lstSelectedChildren);

            motherList.setAdapter(motherAdapter);*/
        } catch (Exception ex) {
            Log.e("Fetch mother", ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        dca03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                motherList.setVisibility(View.GONE);
                lblNoMother.setText("No Mother Found");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public Boolean checkChild(String dob) {

        try {

            Date dt = new SimpleDateFormat("yyyy-MM-dd").parse(dob);

            if (MainApp.monthsBetweenDates(dt, new Date()) < MainApp.selectedCHILD) {
                return true;
            } else {
                leftChild++;
                return false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;

    }

    public Boolean checkChildAge(String y, String m, String d) {

        int age = Integer.parseInt(y) * 12 + Integer.parseInt(m) + (Integer.parseInt(d) / 29);

        if (age < MainApp.selectedCHILD) {
            return true;
        } else {
            leftChild++;
            return false;
        }

    }


    @OnClick(R.id.checkSelectedHHID)
    void onCheckSelectedHHIDClick() {

        membersExists.setVisibility(View.VISIBLE);
        MainApp.lstSelectedMothers = new ArrayList<>();
        MainApp.insertPos = new HashMap<>();

        if (!dca03.getText().toString().isEmpty() && dca03.getText().length() == 11) {
            dca03.setError(null);
            selectedMothers = db.getSelectedMothersByDSS01(dca03.getText().toString().toUpperCase());

            mwras = 0;

            if (selectedMothers.size() != 0) {

                motherList.setVisibility(View.VISIBLE);

                for (MembersContract ec : selectedMothers) {
                    if (MainApp.yearsBetweenDates(ec.getDob())) {
                        MainApp.lstSelectedMothers.add(new MembersContract(ec));

                        if (mwras != 0) {
                            MainApp.insertPos.put(mwras, 0);
                        } else {
                            MainApp.insertPos.put(mwras, 1);
                        }

                        mwras++;

                    }
                }

                motherAdapter = new listAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, MainApp.lstSelectedMothers);

                motherList.setAdapter(motherAdapter);

                lblNoMother.setText("Mothers found " + selectedMothers.size() + " - MWRAs : " + mwras);

                Toast.makeText(this, "Mothers Found", Toast.LENGTH_LONG).show();
                MainApp.currentMotherStatusCount = MainApp.lstSelectedMothers.size();
                MainApp.TotalMWRACount = MainApp.lstSelectedMothers.size();

            } else {

                motherList.setVisibility(View.GONE);

                Toast.makeText(this, "No Mother Found", Toast.LENGTH_LONG).show();
            }
        } else {
            dca03.setError("This data is Required!");
        }
        checked = true;
    }


    @OnClick(R.id.btn_End)
    void onBtnEndClick() {
        //TODO implement

//        Toast.makeText(this, "Not Processing This Section", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Starting Form Ending Section", Toast.LENGTH_SHORT).show();

        finish();

//        MainApp.endFlag = false;

        MainApp.insertPos.clear();

        Intent secNext = new Intent(this, MainActivity.class);
        startActivity(secNext);

//        MainApp.endActivity(this,this);
    }

    public class listAdapter extends ArrayAdapter {

        ArrayList<MembersContract> list = new ArrayList<>();

        ProgressDialog progressDialog;

        public listAdapter(Context context, int textViewResourceId, ArrayList<MembersContract> objects) {
            super(context, textViewResourceId, objects);
            list = objects;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

/*        @Override
        public boolean isEnabled(int position) {
            return false;
        }*/


        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View v = view;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.lstview, null);
            final TextView mother_name = (TextView) v.findViewById(R.id.mother_name);
            TextView childName = (TextView) v.findViewById(R.id.childname);
            TextView date_of_birth = (TextView) v.findViewById(R.id.date_of_birth);

            mother_name.setText(list.get(position).getName());
            childName.setText(list.get(position).getChild_name());
            date_of_birth.setText(list.get(position).getChildDob());


            if (MainApp.insertPos.get(position) == 0) {
                v.setEnabled(isEnabled(position));
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            } else if (MainApp.insertPos.get(position) == 2) {
                v.setEnabled(isEnabled(position));
                v.setBackgroundColor(getResources().getColor(R.color.gray));
            }


//            if (MainApp.yearsBetweenDates(list.get(motherPosition).getDob())) {
                /*motherList.getChildAt(motherPosition).setEnabled(false);
                motherList.getChildAt(motherPosition).setBackgroundColor(getResources().getColor(R.color.red));*/
//            } else {
//                mwras++;
//            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    finish();

                    motherList.getChildAt(position).setEnabled(false);
                    motherList.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.gray));

//                    btn_End.setEnabled(true);

                    progressDialog = new ProgressDialog(MotherListActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Searching...");
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {

                                    MainApp.insertPos.put(position + 1, 1);
                                    MainApp.insertPos.put(position, 2);

                                    // On complete call either onLoginSuccess or onLoginFailed
                                    checkChildrens(list.get(position).getDss_id_hh(), list.get(position).getDss_id_member(), list.get(position).getChildDob(),
                                            list.get(position).getChild_name(), position);
                                }
                            }, 1000);


                }
            });

            return v;
        }

        public void checkChildrens(String dssID, String member_id, String childDob, String childName, int position) {

            Collection<MembersContract> children = db.getSelectedChildByMotherID(dssID, member_id, childDob, childName);

            MainApp.lstSelectedChildren = new ArrayList<>();

            if (children.size() > 0) {

                for (MembersContract child : children) {

                    if (checkChild(child.getDob())) {
                        MainApp.lstSelectedChildren.add(new MembersContract(child));
                    }
                }

                MainApp.totalChild = children.size();

                if (MainApp.lstSelectedChildren.size() > 0) {

                    SharedPreferences sharedPref = getSharedPreferences("tagName", MODE_PRIVATE);

                    MainApp.mc = new MotherContract();
                    MainApp.mc.setDevicetagID(sharedPref.getString("tagName", null));
                    MainApp.mc.setFormDate(new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime()));
                    MainApp.mc.setDeviceId(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID));
                    MainApp.mc.setUser(MainApp.userName);
                    MainApp.mc.setMotherID(MainApp.lstSelectedMothers.get(MainApp.motherPosition).getDss_id_member());
                    MainApp.mc.setDssID(MainApp.lstSelectedMothers.get(MainApp.motherPosition).getDss_id_hh());

                    MainApp.currentMotherStatusCount--;

                    Intent cb = new Intent(getApplicationContext(), SectionFActivity.class);
                    cb.putExtra("motherPosition", position);
                    startActivity(cb);
                } else {
                    Toast.makeText(getApplicationContext(), "No child less then 2", Toast.LENGTH_LONG).show();
                }
            }

            progressDialog.dismiss();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (MainApp.endFlag) {

            btn_End.setVisibility(View.VISIBLE);
            dca03.setEnabled(false);
            checkSelectedHHID.setEnabled(false);
            motherList.setVisibility(View.VISIBLE);
//            if (backFlag) {
            motherAdapter.notifyDataSetChanged();
//            }
            backFlag = true;
        }

    }

    @Override
    public void onBackPressed() {
        if (backFlag) {
            Toast.makeText(getApplicationContext(), "You Can't go back!", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
