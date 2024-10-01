package com.example.p2p_v1_application

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pManager

/**
 * Esta clase implementa el receptor de difusión (BroadcastReceiver).
 *
 *  -- Variables --
 *
 *  -> manager -> Instancia de WifiP2pManager que se utiliza para gestionar la funcionalidad de Wifi-Direct
 *  -> channel -> Canal de comunicación con el sistema de Wi-Fi Direct
 *  -> activity -> Actividad principal que recibe el mensaje
 *
 *  -- onReceive --
 *
 *  Este método se llama cada vez que se recibe un mensaje de difusión
 *
 *   >  WIFI_P2P_STATE_CHANGED_ACTION -> Se activa cuando el estado del Wi-Fi Direct cambia
 *      En esta parte del código verificamos si Wi-Fi está habilitado y notificar a la actividad correspondiente
 *
 *   >  WIFI_P2P_PEERS_CHANGED_ACTION -> Se activa cuando la lista de dispositivos disponibles cambia
 *      Podemos llamar a WifiP2pManager.requestPeers() para obtener una lista de dispositivos disponibles
 *      y actualizar la interfaz del usuario con esta información
 *
 *   >  WIFI_P2P_CONNECTION_CHANGED_ACTION -> Se activa cuando el estado de la conexión Wi-Fi Direct cambia
 *      En este bloque podemos manejar la lógica para responder a nuevas conexiones o desconexiones
 */

class WifiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: MainActivity
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action: String? = intent?.action
        when (action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                // Check to see if Wi-Fi is enabled and notify appropriate activity.
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    // Wi-Fi Direct está habilitado
                } else {
                    // Wi-Fi Direct no está habilitado
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Call WifiP2pManager.requestPeers() to get a list of current peers
                manager.requestPeers(channel) { peerList ->

                }
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                // Respond to new connection or disconnect
                val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
                if (networkInfo?.isConnected == true) {
                    // Se ha establecido una conexión
                } else {
                    // La conexión se ha perdido
                }
            }
        }
    }
}
