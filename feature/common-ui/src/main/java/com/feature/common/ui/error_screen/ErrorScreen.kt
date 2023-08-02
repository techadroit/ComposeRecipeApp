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
import com.core.platform.exception.Failure
import com.core.themes.spacerSmall
import com.feature.common.ui.R
import com.feature.common.ui.containers.FullScreenBox
import com.state_manager.side_effects.SideEffect

typealias OnRetry = () -> Unit

data class ErrorSideEffect(val failure: Failure?) : SideEffect

/**
 * Represents the Error Screen. The text and the button are shown according to the error result.
 *
 * @param errorResult The error result received. Can be null if there's no error.
 * @param retry Callback to be invoked when the user clicks the retry button.
 */
@Composable
fun ErrorScreen(errorResult: Failure?, retry: OnRetry) {
    FullScreenBox {
        errorResult?.let {
            val (text, showRetryOption) = when (it) {
                is Failure.NoDataFailure -> stringResource(id = R.string.no_data_available) to false
                is Failure.NetworkConnectionFailure -> stringResource(id = R.string.no_network_available) to true
                is Failure.ApiRequestFailure -> stringResource(id = R.string.request_param_error) to true
                is Failure.ServerFailure -> stringResource(id = R.string.server_error) to true
                else -> stringResource(id = R.string.unknown_error) to true
            }
            ErrorWithRetry(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                showRetryOption
            ) {
                retry() // Call the provided callback function to retry the operation.
            }
        }
    }
}

/**
 * Represents the Error with Retry options.
 *
 * @param text The error message text to be displayed.
 * @param showRetryOptions Boolean flag to indicate whether to show the retry button.
 * @param retry Callback to be invoked when the user clicks the retry button.
 */
@Composable
fun ErrorWithRetry(modifier: Modifier, text: String, showRetryOptions: Boolean, retry: OnRetry) {
    Column(
        modifier = modifier,
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
