package com.gamr.gamr.ServerRepresentations;

/**
 * Created by Thomas on 3/21/2015.
 */
public class MessageJson {
    public String message_id;
    public String from_id;
    public String to_id;
    public String date;
    public String text;

    public MessageJson(){}

    @Override
    public String toString(){
        return "From: " + from_id + "\n" + "To: " + to_id + "\n" + text;
    }
}
