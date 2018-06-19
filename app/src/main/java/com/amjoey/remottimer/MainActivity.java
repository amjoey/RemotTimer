package com.amjoey.remottimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import simpletcp.*;

public class MainActivity extends AppCompatActivity {
    public static final int TCP_PORT = 21111;
    private SimpleTcpServer simpleTcpServer;

    private TextView textViewTime;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime  = findViewById(R.id.timeText);
        buttonSend = findViewById(R.id.buttonSend);



        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                // Send message and waiting for callback
                SimpleTcpClient.send("Return\r\n", "192.168.1.44", TCP_PORT, new SimpleTcpClient.SendCallback() {
                    public void onReturn(String tag) {
                        String[] arr_state = tag.split(",");
                        textViewTime.setText(arr_state[1]+"--"+arr_state[2]);
                    }

                    public void onFailed(String tag) {
                        Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    }
                }, "TAG");

            }
        });

        simpleTcpServer = new SimpleTcpServer(TCP_PORT);
        simpleTcpServer.setOnDataReceivedListener(new SimpleTcpServer.OnDataReceivedListener() {
            public void onDataReceived(String message, String ip) {

                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
