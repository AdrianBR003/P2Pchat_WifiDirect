package com.example.p2p_v1_application

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.p2p_v1_application.ui.theme.P2P_v1_ApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var textView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var aSwitch: Button
    private lateinit var discoverButton: Button
    private lateinit var listView: ListView
    private lateinit var typeMsg: EditText
    private lateinit var sendButton: Button

    // Instance of WifiP2pManager to manage Wi-Fi Direct operations.
    private lateinit var manager:WifiP2pManager
    // Channel used for communicating with the Wi-Fi Direct system.
    private lateinit var channel:WifiP2pManager.Channel
    // Custom BroadcastReceiver to handle Wi-Fi Direct state changes.
    private lateinit var receiver:WifiDirectBroadcastReceiver
    // IntentFilter to specify which intents the receiver should respond to.
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
    }

    private fun initialWork(){
        textView = findViewById(R.id.connection_status)
        messageTextView = findViewById(R.id.messageTextView)
        aSwitch = findViewById(R.id.switch1)
        discoverButton = findViewById(R.id.buttondiscover)
        listView = findViewById(R.id.listView)
        typeMsg = findViewById(R.id.editTextTypeMsg)
        sendButton = findViewById(R.id.sendButton)

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)
        receiver = WifiDirectBroadcastReceiver(manager, channel, this)
        intentFilter = IntentFilter()
    }

}