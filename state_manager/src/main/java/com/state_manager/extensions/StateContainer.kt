package com.state_manager.extensions

import com.state_manager.events.AppEvent
import com.state_manager.managers.Manager
import com.state_manager.side_effects.SideEffect
import com.state_manager.state.AppState
import kotlinx.coroutines.channels.Channel

class StateContainer<S : AppState, E : AppEvent, SIDE_EFFECT : SideEffect>(val manager: Manager<S, E, SIDE_EFFECT>){
    var channel: Channel<S> = Channel(Channel.UNLIMITED)


}