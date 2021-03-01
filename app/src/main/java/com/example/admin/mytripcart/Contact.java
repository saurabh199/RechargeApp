package com.example.admin.mytripcart;

/**
 * Created by Admin on 12/31/2015.
 */



public class Contact {

    //private variables
    int _id;
    String _username;
    String _password;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String username, String _password){
        this._id = id;
        this._username = username;
        this._password = _password;
    }

    // constructor
    public Contact(String username, String _password){
        this._username = username;
        this._password = _password;
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
    public String get_username(){
        return this._username;
    }

    // setting name
    public void set_username(String username){
        this._username = username;
    }

    // getting phone number
    public String get_password(){
        return this._password;
    }

    // setting phone number
    public void set_password(String _password){
        this._password = _password;
    }
}