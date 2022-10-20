package com.greymatter.brandke.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    Context _activity;
    public static final String PREFER_NAME = "jana";
    final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public Session(Context activity) {
        try {
            this._activity = activity;
            pref = _activity.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void add_news_jobsSession(String jobtitle, String comp_name, String comp_address,
                                       String salary, String emp_qual, String emp_exp, String venue,
                                       String mail, String phone_no, String interview_time, String interview_date,
                                       String more_details, String link,String language)
    {

        editor.putString(Constant.Job_Title, jobtitle);
        editor.putString(Constant.Company_Address, comp_address);
        editor.putString(Constant.Company_name,comp_name );
        editor.putString(Constant.Salary, salary);
        editor.putString(Constant.Emp_Qualification, emp_qual);
        editor.putString(Constant.Emp_Experience, emp_exp);
        editor.putString(Constant.Venue, venue);
        editor.putString(Constant.Mail, mail);
        editor.putString(Constant.Phone_no, phone_no);
        editor.putString(Constant.Interview_Time, interview_time);
        editor.putString(Constant.Interview_Date, interview_date);
        editor.putString(Constant.Link, link);
        editor.putString(Constant.More_details, more_details);
        editor.putString(Constant.Language, language);
        editor.commit();
    }

    public String getData(String id) {
        return pref.getString(id, "");
    }
//    public void setUserData(String profile, String id, String firstname,String lastname, String mobile, String password) {
//
//        editor.putString(Constant.USER_ID, id);
//        editor.putString(Constant.NAME, name);
//        editor.putString(Constant.EMAIL, email);
//        editor.putString(Constant.COUNTRY_CODE, country_code);
//        editor.putString(Constant.PROFILE, profile);
//        editor.putString(Constant.MOBILE, mobile);
//        editor.putString(Constant.BALANCE, balance);
//        editor.putString(Constant.REFERRAL_CODE, referral_code);
//        editor.putString(Constant.FRIEND_CODE, friends_code);
//        editor.putString(Constant.FCM_ID, fcm_id);
//        editor.putString(Constant.STATUS, status);
//        editor.commit();
//    }

    public void setBoolean(String id, boolean val) {
        editor.putBoolean(id, val);
        editor.commit();
    }

    public boolean getBoolean(String id) {
        return pref.getBoolean(id, false);
    }
}
