package com.example.perimetergates.Intel;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SupSess {
    private static final String preference = "Suppress";
    private static final String ksupp_id = "supp_id";
    private static final String kfname = "fname";
    private static final String klname = "lname";
    private static final String kusername = "username";
    private static final String kcontact = "contact";
    private static final String kemail = "email";
    private static final String kstatus = "status";
    private static final String kreg_date = "reg_date";
    private static final String keyoff = "expires";
    private static final String keyempty = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    //staff_id,fname,lname,username,contact,role,email,status,reg_date
    public SupSess(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(preference, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param fname
     * @param email
     */
    //staff_id,fname,lname,username,contact,role,email,status,reg_date
    public void logSup(String supp_id, String fname, String lname, String username, String contact, String email,
                       String status, String reg_date) {
        mEditor.putString(ksupp_id, supp_id);
        mEditor.putString(kfname, fname);
        mEditor.putString(klname, lname);
        mEditor.putString(kusername, username);
        mEditor.putString(kcontact, contact);
        mEditor.putString(kemail, email);
        mEditor.putString(kstatus, status);
        mEditor.putString(kreg_date, reg_date);

        Date date = new Date();

        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(keyoff, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean loggedSup() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(keyoff, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public SupMod getSupDetails() {

        if (!loggedSup()) {
            return null;
        }
        SupMod custModel = new SupMod();
        custModel.setSupp_id(mPreferences.getString(ksupp_id, keyempty));
        custModel.setFname(mPreferences.getString(kfname, keyempty));
        custModel.setLname(mPreferences.getString(klname, keyempty));
        custModel.setUsername(mPreferences.getString(kusername, keyempty));
        custModel.setContact(mPreferences.getString(kcontact, keyempty));
        custModel.setEmail(mPreferences.getString(kemail, keyempty));
        custModel.setStatus(mPreferences.getString(kstatus, keyempty));
        custModel.setReg_date(mPreferences.getString(kreg_date, keyempty));

        //staff_id,fname,lname,username,contact,role,email,status,reg_date
        return custModel;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutSup() {
        mEditor.clear();
        mEditor.commit();
    }
}
