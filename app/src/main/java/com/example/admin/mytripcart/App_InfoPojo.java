package com.example.admin.mytripcart;

/**
 * Created by Kamal on 01-10-2015.
 */
public class App_InfoPojo {

    String opcode;
    String operator;


    public App_InfoPojo(){

    }

    public App_InfoPojo(String opcode, String operator){

        this.opcode=opcode;
        this.operator=operator;


    }

//    public  App_InfoPojo(String opcode,String operator){
//        this.opcode=opcode;
//        this.operator=operator;
//    }

    public String getOpcode() {

        return this.opcode;
    }

    public void setOpcode(String opcode ){
        this.opcode=opcode;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    }