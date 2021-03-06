package com.example.admin.mytripcart;

/**
 * Created by Admin on 1/9/2016.
 */
public class PostOperator {
    int _id;
    String operator;
    String opcode;

    // Empty constructor
    public PostOperator(){
    }

    public PostOperator(int id, String operator, String opcode){
        this._id = id;
        this.operator = operator;
        this.opcode = opcode;
    }

    // constructor
    public PostOperator(String operator, String opcode){
        this.operator = operator;
        this.opcode = opcode;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getOperator(){
        return this.operator;
    }

    // setting name
    public void setOperator(String operator){
        this.operator = operator;
    }

    // getting phone number
    public String getOpcode(){
        return this.opcode;
    }

    // setting phone number
    public void setOpcode(String opcode){
        this.opcode = opcode;
    }
}

