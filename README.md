# OnrampSDK Integration Guide

This repository demonstrates the integration of OnrampSDK for cryptocurrency transactions in an Android application. The SDK provides functionality for buying, selling, and swapping cryptocurrencies.

## Features

- Buy Cryptocurrency
- Sell Cryptocurrency
- Swap Cryptocurrency
- Real-time transaction status updates
- Multiple payment methods support
- Multi-language support

## Setup

1. Add the OnrampSDK dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.buyhatke:onrampsdk:latest_version")
}
```

2. Initialize the SDK in your Activity:

```kotlin
// Register the event listener
OnrampSDK.registerOnrampSDKEventListener(this)
```

3. Clean up in onDestroy:

```kotlin
override fun onDestroy() {
    super.onDestroy()
    OnrampSDK.stopOnrampSDK()
}
```

## Usage Examples

### 1. Buy Cryptocurrency

```kotlin
startOnrampSDK(
    context = activity,
    appId = YOUR_APP_ID,
    walletAddress = "YOUR_WALLET_ADDRESS",
    flowType = 1, // 1 = Buy crypto
    coinCode = "ETH",
    network = "Ethereum",
    fiatAmount = 100.0,
    merchantRecognitionId = "user_123",
    paymentMethod = 1, // 1 = UPI, 2 = Bank Transfer, 3 = Card
    authToken = "user_auth_token_here",
    lang = "en"
)
```

### 2. Sell Cryptocurrency

```kotlin
startOnrampSDK(
    context = activity,
    appId = YOUR_APP_ID,
    walletAddress = "YOUR_WALLET_ADDRESS",
    flowType = 2, // 2 = Sell crypto
    coinCode = "USDC",
    network = "Polygon",
    coinAmount = 50.0,
    paymentAddress = "user_bank_account_id",
    fiatType = 2, // 2 = INR
    phoneNumber = "+919876543210",
    lang = "hi"
)
```

### 3. Swap Cryptocurrency

```kotlin
// Step 1: Sell USDC
startOnrampSDK(
    context = activity,
    appId = YOUR_APP_ID,
    walletAddress = "YOUR_WALLET_ADDRESS",
    flowType = 2,
    coinCode = "USDC",
    network = "Polygon",
    coinAmount = 100.0
)

// Step 2: Buy ETH
startOnrampSDK(
    context = activity,
    appId = YOUR_APP_ID,
    walletAddress = "YOUR_WALLET_ADDRESS",
    flowType = 1,
    coinCode = "ETH",
    network = "Ethereum"
)
```

## Transaction Status Handling

Implement the `OnrampSDKEventListener` interface to receive transaction updates:

```kotlin
override fun onDataReceived(data: WidgetResponse) {
    when (data.type) {
        "ONRAMP_WIDGET_TX_SELLING" -> {
            // Handle selling in progress
        }
        "ONRAMP_WIDGET_TX_SENT" -> {
            // Handle transaction sent
        }
        "ONRAMP_WIDGET_TX_COMPLETED" -> {
            // Handle transaction completed
        }
    }
}
```

## Parameters

### Common Parameters
- `appId`: Your application ID
- `walletAddress`: User's wallet address
- `flowType`: Transaction type (1 = Buy, 2 = Sell)
- `coinCode`: Cryptocurrency code (e.g., "ETH", "USDC")
- `network`: Blockchain network (e.g., "Ethereum", "Polygon")
- `lang`: Language code (e.g., "en", "hi")

### Buy-specific Parameters
- `fiatAmount`: Amount in fiat currency
- `merchantRecognitionId`: User tracking ID
- `paymentMethod`: Payment method (1 = UPI, 2 = Bank Transfer, 3 = Card)
- `authToken`: Authentication token for KYC

### Sell-specific Parameters
- `coinAmount`: Amount of cryptocurrency to sell
- `paymentAddress`: Bank account for fiat payment
- `fiatType`: Fiat currency type (2 = INR)
- `phoneNumber`: Phone number for OTP verification

## UI Components

The repository includes a sample UI implementation using Jetpack Compose:

```kotlin
@Composable
fun SellScreen(
    transactionStatus: String,
    transactionData: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sell Crypto",
            style = MaterialTheme.typography.headlineMedium
        )
        // Transaction status and data display
    }
}
```

## Security Considerations

1. Never hardcode sensitive information like API keys or wallet addresses
2. Implement proper authentication and authorization
3. Handle user data securely
4. Follow best practices for cryptocurrency transactions

## Support

For additional support or questions, please contact:
- Email: support@buyhatke.com
- Documentation: [BuyHatke Documentation](https://docs.buyhatke.com)

## License

This project is licensed under the MIT License - see the LICENSE file for details. 
