package com.example.hassanproject.MQTT;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.hassanproject.exchange_values;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttConnection implements MqttCallbackExtended {

    String topic        = "Android TEST";
    String content_msg      = "Sent from Android:";
    int qos             = 2;
    String broker       = "";
    String clientId ;
    MemoryPersistence persistence = new MemoryPersistence();
    String username="";
    String password="";

    MqttMessage message;

    Handler handler;

    MqttClient sampleClient;
    MqttConnectOptions connOpts;
    boolean cleanSession=false;

    String TAG="TAG";
    Context context;

    exchange_values exhange;

    public String voltage;
    public String current;
    public String ex;

    public String vibration;
    public String temperature;
    public String flow;

    public  MqttConnection(Context context, exchange_values exhange){
        handler=new Handler();
        this.context=context;
        this.exhange=exhange;
        setConnection();
    }
    public void setConnection() {
        topic        = "android";
        content_msg      = "";
        broker       = "tcp://postman.cloudmqtt.com:10196";
        username="lbgmgldq";
        password="RI3KTzmAKpi_";
        qos=0;
        connect();
    }
    private void connect() {


        try {
            clientId=MqttClient.generateClientId();
            sampleClient = new MqttClient(broker, clientId, persistence);
            connOpts = new MqttConnectOptions();

            connOpts.setCleanSession(cleanSession);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            //connOpts.setConnectionTimeout(2);
            Log.d("TAG","ClientId: "+clientId);


            sampleClient.setCallback(this);

            Log.d("TAG","Connecting to broker: "+broker);
            sampleClient.connect(connOpts);

        } catch(MqttException me) {
            Log.d("TAG","Error: "+me.getReasonCode());
            Log.d("TAG","Error: "+me.getMessage());
            Log.d("TAG","Error: "+me.getLocalizedMessage());
            Log.d("TAG","Error: "+me.getCause());
            Log.d("TAG","Error: "+me);
            me.printStackTrace();
        }
    }


    @Override
    public void connectComplete(boolean reconnect, String serverURI) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("TAG","Connected*************: ");
                    sampleClient.subscribe(new String[]{"current","ex","volt","vibration","temperature","flow"},
                            new int[]{0,0,0,0,0,0});
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(topic.equals("volt")){
                    voltage=message.toString();
                }else
                if(topic.equals("current")){
                    current=message.toString();
                }else
                if(topic.equals("ex")){
                    ex=message.toString();
                }else
                if(topic.equals("vibration")){
                    vibration=message.toString();
                }else
                if(topic.equals("temperature")){
                    if(message.toString().toLowerCase().equals("nan")){
                        temperature ="0.0";
                    }else {
                        temperature = message.toString();
                    }
                }else
                if(topic.equals("flow")){
                    flow=message.toString();
                }
                exhange.update_values();
                Log.d("TAG","Topic: "+topic+" Message: "+message);
            }
        });

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
