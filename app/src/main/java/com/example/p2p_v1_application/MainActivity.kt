package com.example.p2p_v1_application

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : ComponentActivity() {

    private lateinit var textView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var aSwitch: Button
    private lateinit var discoverButton: Button
    private lateinit var listView: ListView
    private lateinit var typeMsg: EditText
    private lateinit var sendButton: Button

    private lateinit var manager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var receiver: WifiDirectBroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    private var peers:List<WifiP2pDevice> = ArrayList<WifiP2pDevice>()
    String[] device

    // Definir el ActivityResultLauncher
    private lateinit var wifiSettingsLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar elementos UI y WifiP2pManager
        initialWork()

        // Inicializar el ActivityResultLauncher para abrir la configuración de Wi-Fi
        wifiSettingsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Manejar el resultado si es necesario
            }
        }

        // Configurar los listeners
        exqListener()
    }

    private fun exqListener() {
        aSwitch.setOnClickListener {
            // Lanza la configuración de Wi-Fi cuando se hace clic
            val intent: Intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            wifiSettingsLauncher.launch(intent)
        }

        discoverButton.setOnClickListener {
            // Iniciar la búsqueda de dispositivos Wi-Fi disponibles
            manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    // La búsqueda de dispositivos Wi-Fi se ha iniciado con éxito
                    connectionStatus("Buscando dispositivos Wi-Fi...")
                }

                override fun onFailure(p0: Int) {
                    // La búsqueda de dispositivos Wi-Fi ha fallado
                    connectionStatus("Error al buscar dispositivos Wi-Fi")
                }
            })
        }
    }

    private fun initialWork() {
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
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
    }

    // Función para actualizar el estado de la conexión
    private fun connectionStatus(message: String) {
        textView.text = message
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
}
