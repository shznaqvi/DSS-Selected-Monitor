package edu.aku.hassannaqvi.dss_census_mother_monitor.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.FormsContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MembersContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MembersContract.singleMember;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MotherContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.MotherContract.MotherTB;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.UsersContract;
import edu.aku.hassannaqvi.dss_census_mother_monitor.contracts.UsersContract.singleUser;
import edu.aku.hassannaqvi.dss_census_mother_monitor.otherClasses.MothersLst;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String SQL_CREATE_USERS = "CREATE TABLE " + singleUser.TABLE_NAME + "("
            + singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + singleUser.ROW_USERNAME + " TEXT,"
            + singleUser.ROW_PASSWORD + " TEXT,"
            + singleUser.FULL_NAME + " TEXT,"
            + singleUser.REGION_DSS + " TEXT );";
    public static final String DATABASE_NAME = "dss-census_mother_monitor.db";
    public static final String DB_NAME = "dss-census_mother_monitor_copy.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_FORMS = "CREATE TABLE "
            + FormsContract.FormsTable.TABLE_NAME + "("
            + FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsContract.FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_UID + " TEXT," +
            FormsTable.COLUMN_IS_NEW + " TEXT," +
            FormsContract.FormsTable.COLUMN_DSSID + " TEXT," +
            FormsContract.FormsTable.COLUMN_FORMDATE + " TEXT," +
            FormsTable.COLUMN_USER + " TEXT," +
            FormsContract.FormsTable.COLUMN_SA + " TEXT," +
            FormsTable.COLUMN_SD + " TEXT," +
            FormsTable.COLUMN_SE + " TEXT," +
            FormsContract.FormsTable.COLUMN_SF + " TEXT," +
            FormsTable.COLUMN_SG + " TEXT," +
            FormsContract.FormsTable.COLUMN_SH + " TEXT," +
            FormsContract.FormsTable.COLUMN_SI + " TEXT," +
            FormsContract.FormsTable.COLUMN_SJ + " TEXT," +
            FormsTable.COLUMN_SK + " TEXT," +
            FormsContract.FormsTable.COLUMN_SL + " TEXT," +
            FormsContract.FormsTable.COLUMN_SM + " TEXT," +
            FormsContract.FormsTable.COLUMN_ISTATUS + " TEXT," +
            FormsContract.FormsTable.COLUMN_GPSLAT + " TEXT," +
            FormsContract.FormsTable.COLUMN_GPSLNG + " TEXT," +
            FormsContract.FormsTable.COLUMN_GPSDATE + " TEXT," +
            FormsContract.FormsTable.COLUMN_GPSACC + " TEXT," +
            FormsContract.FormsTable.COLUMN_DEVICEID + " TEXT," +
            FormsContract.FormsTable.COLUMN_DEVICETAGID + " TEXT," +
            FormsContract.FormsTable.COLUMN_SYNCED + " TEXT," +
            FormsContract.FormsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";
    private static final String SQL_CREATE_MEMBERS = "CREATE TABLE "
            + singleMember.TABLE_NAME + "("
            + singleMember.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            singleMember.COLUMN_DSS_ID_MEMBER + " TEXT," +
            singleMember.COLUMN_DSS_ID_HH + " TEXT," +
            singleMember.COLUMN_NAME + " TEXT," +
            singleMember.COLUMN_DOB + " TEXT," +
            singleMember.COLUMN_GENDER + " TEXT," +
            singleMember.COLUMN_MEMBER_TYPE + " TEXT" +
//            singleMember.COLUMN_CHILD_DOB + " TEXT" +
            /*
            singleMember.COLUMN_DSS_ID_F + " TEXT," +
            singleMember.COLUMN_DSS_ID_M + " TEXT," +
            singleMember.COLUMN_DSS_ID_H + " TEXT," +
            singleMember.COLUMN_CHILD_NAME + " TEXT," +
            singleMember.COLUMN_SITE_CODE + " TEXT," +
            singleMember.COLUMN_AGE + " TEXT," +
            singleMember.COLUMN_IS_HEAD + " TEXT," +
            singleMember.COLUMN_RELATION_HH + " TEXT," +
            singleMember.COLUMN_CURRENT_STATUS + " TEXT," +
            singleMember.COLUMN_CURRENT_DATE + " TEXT," +
            singleMember.COLUMN_DOD + " TEXT," +
            singleMember.COLUMN_M_STATUS + " TEXT," +
            singleMember.COLUMN_EDUCATION + " TEXT," +
            singleMember.COLUMN_OCCUPATION + " TEXT," +*/

            " );";

    private static final String SQL_CREATE_MOTHER = "CREATE TABLE "
            + MotherTB.TABLE_NAME + "("
            + MotherTB.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MotherTB.COLUMN_PROJECT_NAME + " TEXT," +
            MotherTB.COLUMN_DEVICETAGID + " TEXT," +
            MotherTB.COLUMN_UID + " TEXT," +
            MotherTB.COLUMN_FORMDATE + " TEXT," +
            MotherTB.COLUMN_USER + " TEXT," +
            MotherTB.COLUMN_CHILDID + " TEXT," +
            MotherTB.COLUMN_DSSID + " TEXT," +
            MotherTB.COLUMN_MOTHERID + " TEXT," +
            MotherTB.COLUMN_ISTATUS + " TEXT," +
            MotherTB.COLUMN_SF + " TEXT," +
            MotherTB.COLUMN_SG + " TEXT," +
            MotherTB.COLUMN_SH + " TEXT," +
            MotherTB.COLUMN_SI + " TEXT," +
            MotherTB.COLUMN_SJ + " TEXT," +
            MotherTB.COLUMN_SL + " TEXT," +
            MotherTB.COLUMN_SM + " TEXT," +
            MotherTB.COLUMN_DEVICEID + " TEXT," +
            MotherTB.COLUMN_SYNCED + " TEXT," +
            MotherTB.COLUMN_SYNCED_DATE + " TEXT" +

            " );";

    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + singleUser.TABLE_NAME;
    private static final String SQL_DELETE_FORMS =
            "DROP TABLE IF EXISTS " + FormsTable.TABLE_NAME;
    private static final String SQL_DELETE_MEMBERS =
            "DROP TABLE IF EXISTS " + singleMember.TABLE_NAME;
    private static final String SQL_DELETE_MOTHER =
            "DROP TABLE IF EXISTS " + MotherTB.TABLE_NAME;

    private static final String SQL_SELECT_MOTHER_BY_CHILD =
            "SELECT c.agem cm, c.agey cy, c.aged cd, c.gender, c.serial serial, m.serial serialm, c.name child_name, c.dss_id_member child_id, m.name mother_name, c.dss_id_member mother_id, c.dob date_of_birth FROM census C join census m on c.dss_id_m = m.dss_id_member where c.member_type =? and c.uuid = m.uuid and c.current_status IN ('1', '2') and c.uuid = ? group by mother_id order by substr(c.dob, 7) desc, substr(c.dob, 4,2) desc, substr(c.dob, 1,2) desc;";
    private static final String SQL_SELECT_CHILD =
            "SELECT * from census where member_type =? and dss_id_hh =? and uuid =? and current_status IN ('1', '2')";


    private final String TAG = "DatabaseHelper";


    public String spDateT = new SimpleDateFormat("dd-MM-yy").format(new Date().getTime());


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_MEMBERS);
        db.execSQL(SQL_CREATE_MOTHER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_FORMS);
        db.execSQL(SQL_DELETE_MEMBERS);
        db.execSQL(SQL_DELETE_MOTHER);
    }

    public void syncUser(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.singleUser.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(singleUser.ROW_USERNAME, user.getUserName());
                values.put(singleUser.ROW_PASSWORD, user.getPassword());
                values.put(singleUser.FULL_NAME, user.getFULL_NAME());
                values.put(singleUser.REGION_DSS, user.getREGION_DSS());
                db.insert(singleUser.TABLE_NAME, null, values);
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
        } finally {
            db.close();
        }
    }

    public void syncMembers(JSONArray memberlist) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MembersContract.singleMember.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = memberlist;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectMember = jsonArray.getJSONObject(i);

                MembersContract member = new MembersContract();
                member.Sync(jsonObjectMember);
                ContentValues values = new ContentValues();

                values.put(singleMember.COLUMN_DSS_ID_MEMBER, member.getDss_id_member());
                values.put(singleMember.COLUMN_NAME, member.getName());
                values.put(singleMember.COLUMN_DOB, member.getDob());
                values.put(singleMember.COLUMN_DSS_ID_HH, member.getDss_id_hh());
                values.put(singleMember.COLUMN_GENDER, member.getGender());
                values.put(singleMember.COLUMN_MEMBER_TYPE, member.getMember_type());
//                values.put(singleMember.COLUMN_CHILD_DOB, member.getChildDob());
                /*
                values.put(singleMember.COLUMN_DSS_ID_F, member.getDss_id_f());
                values.put(singleMember.COLUMN_DSS_ID_M, member.getDss_id_m());
                values.put(singleMember.COLUMN_DSS_ID_H, member.getDss_id_h());
                values.put(singleMember.COLUMN_CHILD_NAME, member.getChild_name());
                values.put(singleMember.COLUMN_SITE_CODE, member.getSite_code());
                values.put(singleMember.COLUMN_AGE, member.getAge());
                values.put(singleMember.COLUMN_IS_HEAD, member.getIs_head());
                values.put(singleMember.COLUMN_RELATION_HH, member.getRelation_hh());
                values.put(singleMember.COLUMN_CURRENT_STATUS, member.getCurrent_status());
                values.put(singleMember.COLUMN_CURRENT_DATE, member.getCurrent_date());
                values.put(singleMember.COLUMN_DOD, member.getDod());
                values.put(singleMember.COLUMN_M_STATUS, member.getM_status());
                values.put(singleMember.COLUMN_EDUCATION, member.getEducation());
                values.put(singleMember.COLUMN_OCCUPATION, member.getOccupation());*/

                db.insert(singleMember.TABLE_NAME, null, values);
            }


        } catch (Exception e) {
            Log.d(TAG, "syncMember(e): " + e);
        } finally {
            db.close();
        }
    }

    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + singleUser.TABLE_NAME + " WHERE " + singleUser.ROW_USERNAME + "=? AND " + singleUser.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {

                if (mCursor.moveToFirst()) {
                    MainApp.regionDss = mCursor.getString(mCursor.getColumnIndex("region_dss"));
                }
                return true;
            }
        }
        return false;
    }

    public Collection<MembersContract> getSelectedMothersByDSS01(String dssID) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT m.*, max(c.dob) child_dob, c.name as childName FROM selectedMembers m join selectedMembers c on m.dss_id_member = substr(c.dss_id_member,1,12) where m.member_type = 'M' and c.member_type = 'CH' and m.dss_id_hh =? group by m.dss_id_member order by m.dss_id_hh,date(c.dob) DESC;"
                , new String[]{dssID});
/*        Cursor mCursor = db.rawQuery("SELECT m.* from selectedMembers m where m.member_type = 'M' and m.dss_id_hh =? group by m.dss_id_member order by m.dss_id_hh;"
                , new String[]{dssID});*/

        Collection<MembersContract> memList = new ArrayList<>();

        try {
            if (mCursor != null) {
                if (mCursor.getCount() > 0) {
                    while (mCursor.moveToNext()) {
                        MembersContract mc = new MembersContract();
                        memList.add(mc.Hydrate(mCursor));
                    }
                }
            }
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return memList;
    }

    public Collection<MembersContract> getSelectedMothersByDSS(String dssID) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleMember.COLUMN_ID,
                singleMember.COLUMN_DSS_ID_MEMBER,
                singleMember.COLUMN_DSS_ID_HH,
                singleMember.COLUMN_NAME,
                singleMember.COLUMN_DOB,
                singleMember.COLUMN_GENDER,
                singleMember.COLUMN_MEMBER_TYPE,
//                singleMember.COLUMN_CHILD_DOB,
                /*
                singleMember.COLUMN_DSS_ID_F,
                singleMember.COLUMN_DSS_ID_M,
                singleMember.COLUMN_DSS_ID_H,
                singleMember.COLUMN_CHILD_NAME,
                singleMember.COLUMN_SITE_CODE,
                singleMember.COLUMN_AGE,
                singleMember.COLUMN_IS_HEAD,
                singleMember.COLUMN_RELATION_HH,
                singleMember.COLUMN_CURRENT_STATUS,
                singleMember.COLUMN_CURRENT_DATE,
                singleMember.COLUMN_DOD,
                singleMember.COLUMN_M_STATUS,
                singleMember.COLUMN_EDUCATION,
                singleMember.COLUMN_OCCUPATION,*/
        };

        String whereClause = singleMember.COLUMN_DSS_ID_HH + " = ? AND " + singleMember.COLUMN_MEMBER_TYPE + " = ?";
        String[] whereArgs = new String[]{dssID, "M"};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleMember.COLUMN_DSS_ID_MEMBER + " ASC";

        Collection<MembersContract> memList = new ArrayList<MembersContract>();
        try {
            c = db.query(
                    singleMember.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MembersContract mc = new MembersContract();
                memList.add(mc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return memList;
    }

    public Collection<MembersContract> getSelectedChildByMotherID(String dssID, String member_id, String dob,
                                                                  String childName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                singleMember.COLUMN_ID,
                singleMember.COLUMN_DSS_ID_MEMBER,
                singleMember.COLUMN_DSS_ID_HH,
                singleMember.COLUMN_NAME,
                singleMember.COLUMN_DOB,
                singleMember.COLUMN_GENDER,
                singleMember.COLUMN_MEMBER_TYPE,
        };

        String whereClause = singleMember.COLUMN_DSS_ID_HH + " = ? AND " + singleMember.COLUMN_MEMBER_TYPE + " = ? AND "
                + singleMember.COLUMN_DOB + " = ? AND "+ singleMember.COLUMN_NAME + " LIKE ? AND "
                + singleMember.COLUMN_DSS_ID_MEMBER + " LIKE ?";
        String[] whereArgs = new String[]{dssID, "CH", dob, "%" + childName + "%", "%" + member_id + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                singleMember.COLUMN_DSS_ID_MEMBER + " ASC";

        Collection<MembersContract> memList = new ArrayList<MembersContract>();
        try {
            c = db.query(
                    singleMember.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MembersContract mc = new MembersContract();
                memList.add(mc.Hydrate01(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return memList;
    }

    public List<FormsContract> getFormsByDSS(String dssID) {
        List<FormsContract> formList = new ArrayList<FormsContract>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + FormsTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                FormsContract fc = new FormsContract();
                formList.add(fc.Hydrate(c));
            } while (c.moveToNext());
        }

        // return contact list
        return formList;
    }

    public Collection<MothersLst> getMotherByUUID(String uuid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        // COLUMNS RETURNED: child_name, child_id, mother_name, mother_id, date_of_birth, serial
        Collection<MothersLst> memList = new ArrayList<>();
        try {

//            c = db.rawQuery(SQL_SELECT_MOTHER_BY_CHILD, new String[]{"c", "('1', '2')", uuid});
            c = db.rawQuery(SQL_SELECT_MOTHER_BY_CHILD, new String[]{"c", uuid});

            while (c.moveToNext()) {

                MothersLst mc = new MothersLst();
                memList.add(mc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return memList;
    }

    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_PROJECT_NAME, fc.getProjectName());
        values.put(FormsContract.FormsTable.COLUMN_UID, fc.getUID());
        values.put(FormsContract.FormsTable.COLUMN_IS_NEW, fc.getISNEW());
        values.put(FormsContract.FormsTable.COLUMN_DSSID, fc.getDSSID());
        values.put(FormsContract.FormsTable.COLUMN_FORMDATE, fc.getFormDate());
        values.put(FormsContract.FormsTable.COLUMN_USER, fc.getUser());
        values.put(FormsContract.FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsContract.FormsTable.COLUMN_SA, fc.getsA());
        values.put(FormsTable.COLUMN_SD, fc.getsD());
        values.put(FormsContract.FormsTable.COLUMN_SE, fc.getsE());
        values.put(FormsContract.FormsTable.COLUMN_SF, fc.getsF());
        values.put(FormsContract.FormsTable.COLUMN_SG, fc.getsG());
        values.put(FormsContract.FormsTable.COLUMN_SH, fc.getsH());
        values.put(FormsContract.FormsTable.COLUMN_SI, fc.getsI());
        values.put(FormsTable.COLUMN_SJ, fc.getsJ());
        values.put(FormsContract.FormsTable.COLUMN_SK, fc.getsK());
        values.put(FormsContract.FormsTable.COLUMN_SL, fc.getsL());
        values.put(FormsContract.FormsTable.COLUMN_SM, fc.getsM());
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsContract.FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsContract.FormsTable.COLUMN_GPSDATE, fc.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsContract.FormsTable.COLUMN_DEVICETAGID, fc.getDevicetagID());
        values.put(FormsContract.FormsTable.COLUMN_DEVICEID, fc.getDeviceID());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsContract.FormsTable.TABLE_NAME,
                FormsContract.FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addMother(MotherContract mc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_PROJECT_NAME, mc.getProjectName());
        values.put(MotherTB.COLUMN_DEVICETAGID, mc.getDevicetagID());
        values.put(MotherTB.COLUMN_UID, mc.getUID());
        values.put(MotherTB.COLUMN_SM, mc.getsM());
        values.put(MotherTB.COLUMN_FORMDATE, mc.getFormDate());
        values.put(MotherTB.COLUMN_USER, mc.getUser());
        values.put(MotherTB.COLUMN_SF, mc.getsF());
        values.put(MotherTB.COLUMN_SG, mc.getsG());
        values.put(MotherTB.COLUMN_SH, mc.getsH());
        values.put(MotherTB.COLUMN_SI, mc.getsI());
        values.put(MotherTB.COLUMN_SJ, mc.getsJ());
        values.put(MotherTB.COLUMN_SL, mc.getsL());
        values.put(MotherTB.COLUMN_DEVICEID, mc.getDeviceId());

        // SYNCED INFORMATION IS NEVER INSERTED WITH NEW RECORD.
     /*   values.put(MotherTB.COLUMN_SYNCED, mc.getSynced());
        values.put(MotherTB.COLUMN_SYNCED_DATE, mc.getSynced_date());*/

        values.put(MotherTB.COLUMN_CHILDID, mc.getChildID());
        values.put(MotherTB.COLUMN_DSSID, mc.getDssID());
        values.put(MotherTB.COLUMN_MOTHERID, mc.getMotherID());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MotherTB.TABLE_NAME,
                MotherTB.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_SYNCED, true);
        values.put(FormsContract.FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateMotherCount(String memCount, String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_SF, memCount);

// Which row to update, based on the title
        String where = FormsContract.FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsContract.FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateMothers(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SYNCED, true);
        values.put(MotherTB.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MotherTB.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MotherTB.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public void updateMother(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SYNCED, true);
        values.put(MotherTB.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MotherTB.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MotherTB.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_UID, MainApp.fc.getUID());

// Which row to update, based on the ID
        String selection = FormsContract.FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsContract.FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateMotherID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_UID, MainApp.mc.getUID());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public Collection<FormsContract> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsContract.FormsTable._ID,
                FormsContract.FormsTable.COLUMN_UID,
                FormsContract.FormsTable.COLUMN_IS_NEW,
                FormsContract.FormsTable.COLUMN_DSSID,
                FormsContract.FormsTable.COLUMN_FORMDATE,
                FormsContract.FormsTable.COLUMN_USER,
                FormsContract.FormsTable.COLUMN_ISTATUS,
                FormsContract.FormsTable.COLUMN_SA,
                FormsContract.FormsTable.COLUMN_SD,
                FormsContract.FormsTable.COLUMN_SE,
                FormsContract.FormsTable.COLUMN_SF,
                FormsContract.FormsTable.COLUMN_SG,
                FormsContract.FormsTable.COLUMN_SH,
                FormsContract.FormsTable.COLUMN_SI,
                FormsContract.FormsTable.COLUMN_SJ,
                FormsContract.FormsTable.COLUMN_SK,
                FormsContract.FormsTable.COLUMN_SL,
                FormsContract.FormsTable.COLUMN_SM,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsContract.FormsTable.COLUMN_GPSDATE,
                FormsContract.FormsTable.COLUMN_GPSACC,
                FormsContract.FormsTable.COLUMN_DEVICETAGID,
                FormsContract.FormsTable.COLUMN_DEVICEID,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsContract.FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsContract.FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Collection<MotherContract> getUnsyncedMother() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MotherTB.COLUMN_ID,
                MotherTB.COLUMN_PROJECT_NAME,
                MotherTB.COLUMN_UID,
                MotherTB.COLUMN_SM,
                MotherTB.COLUMN_FORMDATE,
                MotherTB.COLUMN_USER,
                MotherTB.COLUMN_SF,
                MotherTB.COLUMN_SG,
                MotherTB.COLUMN_SH,
                MotherTB.COLUMN_SI,
                MotherTB.COLUMN_SJ,
                MotherTB.COLUMN_SL,
                MotherTB.COLUMN_DEVICEID,
                MotherTB.COLUMN_CHILDID,
                MotherTB.COLUMN_DSSID,
                MotherTB.COLUMN_ISTATUS,
                MotherTB.COLUMN_DEVICETAGID,
                MotherTB.COLUMN_MOTHERID
        };
        String whereClause = MotherTB.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                MotherTB.COLUMN_ID + " ASC";

        Collection<MotherContract> allMC = new ArrayList<MotherContract>();
        try {
            c = db.query(
                    MotherTB.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MotherContract mc = new MotherContract();
                allMC.add(mc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMC;
    }

    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_PROJECT_NAME,
                FormsTable.COLUMN_UID,
                FormsContract.FormsTable.COLUMN_IS_NEW,
                FormsContract.FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_USER,
                FormsContract.FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsContract.FormsTable.COLUMN_SG,
                FormsContract.FormsTable.COLUMN_SH,
                FormsContract.FormsTable.COLUMN_SI,
                FormsTable.COLUMN_SJ,
                FormsTable.COLUMN_SK,
                FormsContract.FormsTable.COLUMN_SL,
                FormsContract.FormsTable.COLUMN_SM,
                FormsContract.FormsTable.COLUMN_GPSLAT,
                FormsContract.FormsTable.COLUMN_GPSLNG,
                FormsContract.FormsTable.COLUMN_GPSDATE,
                FormsContract.FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID
        };
        String whereClause = FormsContract.FormsTable.COLUMN_SYNCED + " is null";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsContract.FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> getTodayForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_DSSID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_FORMDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.setDSSID(c.getString(c.getColumnIndex(FormsTable.COLUMN_DSSID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public int updateSA() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_SG, MainApp.fc.getsG());

// Which row to update, based on the ID
        String selection = FormsContract.FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsContract.FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSD() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_SD, MainApp.fc.getsD());
        values.put(FormsContract.FormsTable.COLUMN_UID, MainApp.fc.getUID());


// Which row to update, based on the ID
        String selection = FormsContract.FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsContract.FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSE() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsContract.FormsTable.COLUMN_SE, MainApp.fc.getsE());
        values.put(FormsContract.FormsTable.COLUMN_UID, MainApp.fc.getUID());


// Which row to update, based on the ID
        String selection = FormsContract.FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.fc.get_ID())};

        int count = db.update(FormsContract.FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSF() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SF, MainApp.mc.getsF());
        values.put(MotherTB.COLUMN_UID, MainApp.mc.getUID());


// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSG() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SG, MainApp.mc.getsG());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSH() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SH, MainApp.mc.getsH());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSI() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SI, MainApp.mc.getsI());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSJ() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SJ, MainApp.mc.getsJ());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSL() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SL, MainApp.mc.getsL());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateSM() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_SM, MainApp.mc.getsM());

// Which row to update, based on the ID
        String selection = MotherTB.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.get_ID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_ISTATUS, MainApp.mc.getIstatus());

// Which row to update, based on the ID
        String selection = "id = ?";
        String[] selectionArgs = {MainApp.mc.get_ID()};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }

    public int updateMother() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MotherTB.COLUMN_ISTATUS, MainApp.mc.getIstatus());

// Which row to update, based on the ID
        String selection = " uuid=?";
        String[] selectionArgs = {String.valueOf(MainApp.mc.getUID())};

        int count = db.update(MotherTB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }
}