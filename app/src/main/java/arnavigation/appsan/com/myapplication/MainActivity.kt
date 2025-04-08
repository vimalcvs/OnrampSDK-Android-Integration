package arnavigation.appsan.com.myapplication

import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import arnavigation.appsan.com.myapplication.ui.theme.MyApplicationTheme
import com.buyhatke.onrampsdk.OnrampSDK
import com.buyhatke.onrampsdk.listeners.OnrampSDKEventListener
import com.buyhatke.onrampsdk.ui.WidgetResponse

/**
 * MainActivity implements the OnrampSDKEventListener to handle responses from the Onramp Money SDK.
 * This activity provides a UI for users to buy different cryptocurrencies on various networks.
 */
class MainActivity : ComponentActivity(), OnrampSDKEventListener {
    // WebView reference to handle cleanup
    private var webView: WebView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OnrampScreen(
                        modifier = Modifier.padding(innerPadding),
                        onLaunchOnramp = { coinCode, network, walletAddress  -> launchOnramp(coinCode, network, walletAddress) }
                    )
                }
            }
        }
    }

    /**
     * Launches the Onramp Money SDK with the specified cryptocurrency and network.
     *
     * @param coinCode The cryptocurrency code (e.g., "USDT", "ETH", "MATIC")
     * @param network The network code (e.g., "TRC20", "ERC20", "MATIC20")
     *
     * Usage:
     * - Call this function when a user selects a cryptocurrency option
     * - The function handles all the SDK initialization and launch
     * - Error handling is included to show user-friendly messages
     */
    private fun launchOnramp(coinCode: CryptoCode, network: NetworkCode, walletAddress: String) {
        try {
            OnrampSDK.startOnrampSDK(
                context = this,
                appId = 1, // TODO: Replace with your actual app ID from Onramp Money
                walletAddress = walletAddress, // TODO: Replace with user's wallet address
                flowType = 1, // 1 = onramp, 2 = offramp, 3 = Merchant checkout
                coinCode = coinCode.code,
                network = network.code,
                coinAmount = null, // Optional: Specify amount of crypto to buy
                fiatAmount = null, // Optional: Specify amount in fiat currency
                merchantRecognitionId = null, // Optional: For tracking transactions
                paymentMethod = 1, // 1 = UPI, 2 = Bank transfer
                authToken = null, // Optional: For authentication
                assetDescription = null, // Optional: Description of the asset
                assetImage = null, // Optional: URL of the asset image
                paymentAddress = null, // Optional: Payment address
                fiatType = 1, // 1 = INR, 2 = TRY
                phoneNumber = null, // Optional: User's phone number
                lang = "en" // Language code
            )

//            OnrampSDK.startOnrampSDK()
//            OnrampSDK.stopOnrampSDK()
//            OnrampSDK.startOnrampLogin()
//            OnrampSDK.initiateOnrampKyc()
//            OnrampSDK.registerOnrampSDKEventListener()

        } catch (e: Exception) {
            Toast.makeText(this, "Error launching Onramp: ${e.message}", Toast.LENGTH_LONG).show()
        }


    }

    /**
     * Handles responses from the Onramp Money SDK.
     * This function is called when the SDK sends back transaction updates.
     *
     * @param data WidgetResponse containing transaction details
     *
     * The function handles different transaction statuses:
     * - SUCCESS: Shows transaction details including amounts and payment method
     * - FAILED: Shows error message
     * - CANCELLED: Shows cancellation message
     * - Other statuses: Shows status and message
     */
    override fun onDataReceived(data: WidgetResponse) {
        try {
            when (data.type) {
                "SUCCESS" -> {
                    // Build a detailed success message
                    val message = buildString {
                        append("Transaction successful!\n")
                        data.data.msg?.let { append("Message: $it\n") }
                        data.data.fiatAmount?.let { append("Fiat Amount: $it\n") }
                        data.data.cryptoAmount?.let { append("Crypto Amount: $it\n") }
                        data.data.coinRate?.let { append("Coin Rate: $it\n") }
                        data.data.paymentMethod?.let { append("Payment Method: $it") }
                    }
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }

                "FAILED" -> {
                    val errorMessage = data.data.msg ?: "Transaction failed"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }

                "CANCELLED" -> {
                    Toast.makeText(this, "Transaction cancelled by user", Toast.LENGTH_LONG).show()
                }

                else -> {
                    val statusMessage = buildString {
                        append("Status: ${data.type}\n")
                        data.data.msg?.let { append("Message: $it") }
                    }
                    Toast.makeText(this, statusMessage, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error processing response: ${e.message}", Toast.LENGTH_LONG)
                .show()
        }
    }

    /**
     * Cleanup function to properly handle WebView resources
     * This prevents memory leaks and crashes when the activity is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        webView?.let {
            it.stopLoading()
            it.clearHistory()
            it.clearCache(true)
            it.destroy()
            webView = null
        }
    }


}
