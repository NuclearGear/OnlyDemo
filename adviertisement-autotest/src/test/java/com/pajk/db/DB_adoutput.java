package com.pajk.db;

import com.pajk.utils.DBhandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DB_adoutput {

    DBhandle adoutput = new DBhandle("jdbc:mysql://testdbmb4-m1.db.pajkdc.com:3306/","adoutput","adoutput","adoutput");

    public void selectByName() throws Exception {
        String querySql =
                "select * from tgt_live_day_report";
        System.out.println(adoutput.query(querySql));

    }

    public static void main(String[] args) throws Exception {
        DB_adoutput adoutput = new DB_adoutput();
        adoutput.selectByName();

    }
}
