package com.shsxt.crm.query;

import com.shsxt.crm.base.BaseQuery;

import java.util.Date;

/**
 * Created by xlf on 2018/7/30.
 */
public class CustomerServeQuery extends BaseQuery {

    private String customer;

    private String myd;

    private String createDate;

    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
