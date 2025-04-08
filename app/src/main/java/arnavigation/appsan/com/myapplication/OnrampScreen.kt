package arnavigation.appsan.com.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * Enum class representing supported cryptocurrency codes.
 */
enum class CryptoCode(val code: String, val displayName: String) {
    ETH("ETH", "Ethereum"),
    USDT("USDT", "Tether USD"),
    MATIC("MATIC", "Polygon"),
    BNB("BNB", "Binance Coin"),
    SOL("SOL", "Solana"),
    AVAX("AVAX", "Avalanche"),
    EOS("EOS", "EOS"),
    XTZ("XTZ", "Tezos"),
    NEAR("NEAR", "NEAR Protocol"),
    ALGO("ALGO", "Algorand"),
    DOT("DOT", "Polkadot"),
    CELO("CELO", "Celo");

    override fun toString(): String = code
}

/**
 * Enum class representing supported network codes.
 */
enum class NetworkCode(val code: String, val displayName: String) {
    ERC20("ERC20", "Ethereum"),
    MATIC20("MATIC20", "Polygon"),
    BEP20("BEP20", "Binance Smart Chain"),
    BEP2("BEP2", "Binance Chain"),
    SPL("SPL", "Solana"),
    TRC20("TRC20", "Tron"),
    AVAXC("AVAXC", "Avalanche C-Chain"),
    EOS("EOS", "EOS"),
    XTZ("XTZ", "Tezos"),
    NEAR("NEAR", "NEAR Protocol"),
    ALGO("ALGO", "Algorand"),
    STATEMINT("STATEMINT", "Polkadot Statemint"),
    ARBITRUM("ARBITRUM", "Arbitrum"),
    OPTIMISM("OPTIMISM", "Optimism"),
    CELO("CELO", "Celo");

    override fun toString(): String = code
}

/**
 * Data class for network options.
 */
data class NetworkOption(
    val name: String,
    val coinCode: CryptoCode,
    val network: NetworkCode,
    val description: String,
    val category: String,
)

// List of all supported networks
val networkOptions = listOf(
    // Ethereum Ecosystem
    NetworkOption(
        "Ethereum",
        CryptoCode.ETH,
        NetworkCode.ERC20,
        "Most established network",
        "Ethereum Ecosystem"
    ),
    NetworkOption(
        "USDT on Ethereum",
        CryptoCode.USDT,
        NetworkCode.ERC20,
        "Ethereum stablecoin",
        "Ethereum Ecosystem"
    ),

    // Layer 2 Solutions
    NetworkOption(
        "Polygon",
        CryptoCode.MATIC,
        NetworkCode.MATIC20,
        "Fast & low fees",
        "Layer 2 Solutions"
    ),
    NetworkOption(
        "Arbitrum",
        CryptoCode.ETH,
        NetworkCode.ARBITRUM,
        "High performance L2",
        "Layer 2 Solutions"
    ),
    NetworkOption(
        "Optimism",
        CryptoCode.ETH,
        NetworkCode.OPTIMISM,
        "Fast & secure L2",
        "Layer 2 Solutions"
    ),

    // Binance Ecosystem
    NetworkOption(
        "BNB Smart Chain",
        CryptoCode.BNB,
        NetworkCode.BEP20,
        "Binance Smart Chain",
        "Binance Ecosystem"
    ),
    NetworkOption(
        "BNB Chain",
        CryptoCode.BNB,
        NetworkCode.BEP2,
        "Binance Chain",
        "Binance Ecosystem"
    ),
    NetworkOption(
        "USDT on BSC",
        CryptoCode.USDT,
        NetworkCode.BEP20,
        "BSC stablecoin",
        "Binance Ecosystem"
    ),

    // Other Major Networks
    NetworkOption(
        "Solana",
        CryptoCode.SOL,
        NetworkCode.SPL,
        "Ultra fast & low cost",
        "Other Major Networks"
    ),
    NetworkOption(
        "USDT on Tron",
        CryptoCode.USDT,
        NetworkCode.TRC20,
        "Tron network",
        "Other Major Networks"
    ),
    NetworkOption(
        "Avalanche",
        CryptoCode.AVAX,
        NetworkCode.AVAXC,
        "Fast & scalable",
        "Other Major Networks"
    ),

    // Alternative Networks
    NetworkOption(
        "EOS",
        CryptoCode.EOS,
        NetworkCode.EOS,
        "High performance blockchain",
        "Alternative Networks"
    ),
    NetworkOption(
        "Tezos",
        CryptoCode.XTZ,
        NetworkCode.XTZ,
        "Self-amending blockchain",
        "Alternative Networks"
    ),
    NetworkOption(
        "NEAR",
        CryptoCode.NEAR,
        NetworkCode.NEAR,
        "Sharded blockchain",
        "Alternative Networks"
    ),
    NetworkOption(
        "Algorand",
        CryptoCode.ALGO,
        NetworkCode.ALGO,
        "Pure proof of stake",
        "Alternative Networks"
    ),
    NetworkOption(
        "Polkadot",
        CryptoCode.DOT,
        NetworkCode.STATEMINT,
        "Parachain",
        "Alternative Networks"
    ),
    NetworkOption(
        "Celo",
        CryptoCode.CELO,
        NetworkCode.CELO,
        "Mobile-first blockchain",
        "Alternative Networks"
    )
)

/**
 * Utility functions for wallet address validation
 */
object WalletAddressValidator {
    private val ETH_ADDRESS_REGEX = Regex("^0x[a-fA-F0-9]{40}$")
    private val SOL_ADDRESS_REGEX = Regex("^[1-9A-HJ-NP-Za-km-z]{32,44}$")
    private val TRC20_ADDRESS_REGEX = Regex("^T[a-zA-Z0-9]{33}$")
    private val BEP20_ADDRESS_REGEX = Regex("^0x[a-fA-F0-9]{40}$")
    private val BEP2_ADDRESS_REGEX = Regex("^[a-zA-Z0-9]{39}$")
    private val XTZ_ADDRESS_REGEX = Regex("^tz[1-9A-HJ-NP-Za-km-z]{33}$")
    private val NEAR_ADDRESS_REGEX = Regex("^[a-zA-Z0-9._-]+$")
    private val ALGO_ADDRESS_REGEX = Regex("^[A-Z2-7]{58}$")
    private val DOT_ADDRESS_REGEX = Regex("^[1-9A-HJ-NP-Za-km-z]{47,48}$")
    private val CELO_ADDRESS_REGEX = Regex("^0x[a-fA-F0-9]{40}$")

    fun validateAddress(address: String, network: NetworkCode): Boolean {
        return when (network) {
            NetworkCode.ERC20, NetworkCode.ARBITRUM, NetworkCode.OPTIMISM -> ETH_ADDRESS_REGEX.matches(address)
            NetworkCode.SPL -> SOL_ADDRESS_REGEX.matches(address)
            NetworkCode.TRC20 -> TRC20_ADDRESS_REGEX.matches(address)
            NetworkCode.BEP20 -> BEP20_ADDRESS_REGEX.matches(address)
            NetworkCode.BEP2 -> BEP2_ADDRESS_REGEX.matches(address)
            NetworkCode.XTZ -> XTZ_ADDRESS_REGEX.matches(address)
            NetworkCode.NEAR -> NEAR_ADDRESS_REGEX.matches(address)
            NetworkCode.ALGO -> ALGO_ADDRESS_REGEX.matches(address)
            NetworkCode.STATEMINT -> DOT_ADDRESS_REGEX.matches(address)
            NetworkCode.CELO -> CELO_ADDRESS_REGEX.matches(address)
            else -> false
        }
    }

    fun getErrorMessage(network: NetworkCode): String {
        return when (network) {
            NetworkCode.ERC20, NetworkCode.ARBITRUM, NetworkCode.OPTIMISM -> 
                "Invalid Ethereum address format (should start with 0x and be 42 characters long)"
            NetworkCode.SPL -> 
                "Invalid Solana address format (should be 32-44 characters long)"
            NetworkCode.TRC20 -> 
                "Invalid TRON address format (should start with T and be 34 characters long)"
            NetworkCode.BEP20 -> 
                "Invalid BEP20 address format (should start with 0x and be 42 characters long)"
            NetworkCode.BEP2 -> 
                "Invalid BEP2 address format (should be 39 characters long)"
            NetworkCode.XTZ -> 
                "Invalid Tezos address format (should start with tz and be 36 characters long)"
            NetworkCode.NEAR -> 
                "Invalid NEAR address format"
            NetworkCode.ALGO -> 
                "Invalid Algorand address format (should be 58 characters long)"
            NetworkCode.STATEMINT -> 
                "Invalid Polkadot address format (should be 47-48 characters long)"
            NetworkCode.CELO -> 
                "Invalid Celo address format (should start with 0x and be 42 characters long)"
            else -> "Invalid address format"
        }
    }
}

@Composable
fun WalletAddressDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    network: NetworkCode
) {
    var walletAddress by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Enter ${network.displayName} Wallet Address",
                    style = MaterialTheme.typography.titleLarge
                )
                
                OutlinedTextField(
                    value = walletAddress,
                    onValueChange = { 
                        walletAddress = it
                        isError = false
                        errorMessage = ""
                    },
                    label = { Text("Wallet Address") },
                    isError = isError,
                    supportingText = if (isError) {
                        { Text(errorMessage) }
                    } else null,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (walletAddress.isBlank()) {
                                isError = true
                                errorMessage = "Please enter a wallet address"
                            } else if (!WalletAddressValidator.validateAddress(walletAddress, network)) {
                                isError = true
                                errorMessage = WalletAddressValidator.getErrorMessage(network)
                            } else {
                                onConfirm(walletAddress)
                            }
                        }
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

@Composable
fun NetworkOptionCard(
    option: NetworkOption,
    onSelect: (CryptoCode, NetworkCode, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        WalletAddressDialog(
            onDismiss = { showDialog = false },
            onConfirm = { walletAddress ->
                showDialog = false
                onSelect(option.coinCode, option.network, walletAddress)
            },
            network = option.network
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = option.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = option.description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun CategoryHeader(
    category: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = category,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun OnrampScreen(
    modifier: Modifier = Modifier,
    onLaunchOnramp: (CryptoCode, NetworkCode, String) -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Select Network & Token",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            networkOptions
                .groupBy { it.category }
                .forEach { (category, options) ->
                    item {
                        CategoryHeader(category = category)
                    }

                    items(
                        items = options,
                        key = { "${it.coinCode}_${it.network}" }
                    ) { option ->
                        NetworkOptionCard(
                            option = option,
                            onSelect = onLaunchOnramp
                        )
                    }
                }
        }
    }
}


