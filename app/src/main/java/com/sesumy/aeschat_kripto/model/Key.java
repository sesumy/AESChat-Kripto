package com.sesumy.aeschat_kripto.model;

import java.io.Serializable;

/**
 * Created by sesumy on 24.12.2016.
 */

public class Key implements Serializable{

    public Key(){}

    public Key(String keyUserid, String key) {
        this.keyUserid = keyUserid;
        this.key=key;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyUserid() {
        return keyUserid;
    }
    public void setKeyUserid(String keyUserid) {
        this.keyUserid = keyUserid;
    }


    public String keyUserid;
    public String key;



}
