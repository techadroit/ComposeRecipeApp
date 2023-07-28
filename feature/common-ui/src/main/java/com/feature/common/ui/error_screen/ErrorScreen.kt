package com.feature.common.ui.error_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.core.platform.exception.ClientRequestErrorResult
import com.core.platform.exception.ErrorResult
import com.core.platform.exception.NetworkUnavailable
import com.core.platform.exception.NoDataError
import com.core.platform.exception.ServerResponseErrorResult
import com.core.themes.spacerSmall
import com.feature.common.ui.R
import com.feature.common.ui.containers.FullScreenBox

typealias OnRetry = () -> Unit

/**
 * Represents the Error Screen. The text and the button are shown according to the error result.
 *
 * @param errorResult The error result received. Can be null if there's no error.
 * @param retry Callback to be invoked when the user clicks the retry button.
 */
@Composable
fun ErrorScreen(errorResult: ErrorResult?, retry: OnRetry) {
    FullScreenBox {
        errorResult?.let {
            val (text, showRetryOption) = when (it) {
                is NoDataError -> stringResource(id = R.string.no_data_available) to false
                is NetworkUnavailable -> stringResource(id = R.string.no_network_available) to true
                is ClientRequestErrorResult -> stringResource(id = R.string.request_param_error) to true
                is ServerResponseErrorResult -> stringResource(id = R.string.server_error) to true
                else -> stringResource(id = R.string.unknown_error) to true
            }
            ErrorWithRetry(text = text, showRetryOption) {
                retry() // Call the provided callback function to retry the operation.
            }
        }
    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(
//                horizontal = MaterialTheme.dimension().paddingContent,
//                vertical = MaterialTheme.dimension().paddingContent,
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        // Check if there's an error result and display the appropriate error message and retry button.
//        errorResult?.let {
//            val (text, showRetryOption) = when (it) {
//                is NoDataError -> stringResource(id = R.string.no_data_available) to false
//                is NetworkUnavailable -> stringResource(id = R.string.no_network_available) to true
//                is ClientRequestErrorResult -> stringResource(id = R.string.request_param_error) to true
//                is ServerResponseErrorResult -> stringResource(id = R.string.server_error) to true
//                else -> stringResource(id = R.string.unknown_error) to true
//            }
//            ErrorWithRetry(text = text, showRetryOption) {
//                retry() // Call the provided callback function to retry the operation.
//            }
//        }
//    }
}

/**
 * Represents the Error with Retry options.
 *
 * @param text The error message text to be displayed.
 * @param showRetryOptions Boolean flag to indicate whether to show the retry button.
 * @param retry Callback to be invoked when the user clicks the retry button.
 */
@Composable
fun ErrorWithRetry(text: String, showRetryOptions: Boolean, retry: OnRetry) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.spacerSmall())
        if (showRetryOptions)
            Button(onClick = { retry() }) {
                Text(text = "Retry", style = MaterialTheme.typography.labelMedium)
            }
    }
}
