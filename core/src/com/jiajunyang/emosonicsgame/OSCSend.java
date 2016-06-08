package com.jiajunyang.emosonicsgame;

/**
 * Created by jiajunyang on 01/06/16.
 */

import com.badlogic.gdx.Gdx;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by jiajunyang on 29/05/16.
 */

public class OSCSend implements Runnable{
    String myIP, action, model, msg;
    int myPort = 7001;
    OSCPortOut oscPortOut;


    // Updating parameters and setup OSC port out.
    // Takes 5 parameters.
    public OSCSend(String myIP, String model, String action, String msg){
        this.myIP = myIP;
        this.model = model; // model is int from the radiogroup. Hence it needs to be convert to string: vocal an
        this.action = action;
        this.msg = msg;
        try{
            // Connect to IP and port
            this.oscPortOut  = new OSCPortOut(InetAddress.getByName(myIP), myPort);
        } catch(UnknownHostException e) {
            Gdx.app.log("OSCSendInitalisation", "OSC Port Out UnknownHoseException");
        } catch (SocketException e){
            // Report error
            Gdx.app.log("OSCSendInitalisation", "Socket exception error!");
        }
    }

    private void next(){
        String[] sendBang;
        sendBang = new String[1];
        sendBang[0] = action;
        List alist = Arrays.asList(sendBang);
        OSCMessage message = new OSCMessage("/next", alist);
        Gdx.app.log("OSC: ", "Next Sound.");
        try{
            // Send messages
            oscPortOut.send(message);
        } catch (Exception e){
            Gdx.app.log("OSC: ", "Failed to send.");
        }
    }

    private void play(){
        ArrayList sendBang = new ArrayList();
//        String[] sendBang;
//        sendBang = new String[1];
//        sendBang[0] = msg;
//        List alist = Arrays.asList(sendBang);
        sendBang.add(msg);
        OSCMessage message = new OSCMessage("/play", sendBang);
//        Gdx.app.log("OSC: ", msg);
        try{
            oscPortOut.send(message);
        } catch (Exception e){
            Gdx.app.log("OSC: ", "Failed to send.");
        }
    }


    private void init(){
        Gdx.app.log("OSC", "IniTrigger.");

    }

    // Run the thread.
    @Override
    public void run(){
        if (oscPortOut != null){
            // Dont know why swich case doesnt work.
            if (action == "play"){
                play();
            }
            else if (action == "next") {
                next();
            }
             else if (action == "init"){
                init();
            }

            else{
                Gdx.app.log("OSC: ", "Unrecognised action"); // Need to change it to a Toast.
            }
        }
    }
}
