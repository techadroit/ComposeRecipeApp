package com.feature.recipe.video.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavGraphBuilder
import com.core.navigtion.navigator.NavComposable
import com.feature.common.fullScreen
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.recipe.app.navigation.intent.VideoPlayerIntent

fun NavGraphBuilder.VideoPlayerScreen(){
    NavComposable(VideoPlayerIntent()) {
        VideoPlayer()
    }
}

@Composable
fun VideoPlayer() {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.packageName)
            )

            val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(
                    Uri.parse(
                        // Big Buck Bunny from Blender Project
                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                    )
                )

            this.prepare(source)
        }
    }
    AndroidView(
        modifier = Modifier.fullScreen(),
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        }
    ) {
        it.player?.playWhenReady = true
    }
}
