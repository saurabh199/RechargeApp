package com.example.admin.mytripcart;

/**
 * Created by mahabir on 24/01/16.
 */
 class RechargeReport {

    private int amount;

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    private String itemdesc;
    private int rspcomm;
    private int compcomm;
    private int frncomm;
    private int distcomm;
    private String chargestatus;

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public long getChargeno() {
        return chargeno;
    }

    public void setChargeno(long chargeno) {
        this.chargeno = chargeno;
    }

    public String getChargestatus() {
        return chargestatus;
    }

    public void setChargestatus(String chargestatus) {
        this.chargestatus = chargestatus;
    }

    public int getDistcomm() {
        return distcomm;
    }

    public void setDistcomm(int distcomm) {
        this.distcomm = distcomm;
    }

    public int getFrncomm() {
        return frncomm;
    }

    public void setFrncomm(int frncomm) {
        this.frncomm = frncomm;
    }

    public int getCompcomm() {
        return compcomm;
    }

    public void setCompcomm(int compcomm) {
        this.compcomm = compcomm;
    }

    public int getRspcomm() {
        return rspcomm;
    }

    public void setRspcomm(int rspcomm) {
        this.rspcomm = rspcomm;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private String terminalid;
    private  long chargeno;


}
