package com.coco.exception;

public class ConfirmEmailExpired extends Exception{
    public ConfirmEmailExpired (String msg){
        super(msg);
    }
}
