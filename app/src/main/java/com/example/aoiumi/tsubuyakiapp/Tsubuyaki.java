package com.example.aoiumi.tsubuyakiapp;

import com.orm.SugarRecord;

/**
 * Created by Hitoshi on 16/05/11.
 */
public class Tsubuyaki extends SugarRecord {

    // ID(連番)
    public long id;

    // コメント
    public String comment;

    public Tsubuyaki() {}

    public Tsubuyaki(long id, String comment) {
        this.id = id;
        this.comment = comment;
    }
}