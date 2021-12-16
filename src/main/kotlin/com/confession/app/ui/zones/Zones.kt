package com.confession.app.ui.zones

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.confession.app.service.AccomplishViewModel
import com.confession.app.service.MoodViewModel
import com.confession.app.service.ReflectViewModel
import com.confession.app.service.RemarkViewModel
import com.confession.app.ui.main.zones.*
import com.confession.app.ui.zones.Zone.*
import org.koin.core.KoinApplication
import java.lang.Integer.max
import java.lang.Integer.min

interface ZonesScope {
    val next: () -> Unit
    val previous: () -> Unit
    val goTo: (Zone) -> Unit
    val index: Int
}

data class ZonesScopeImpl(
    val nextF: () -> Unit,
    val previousF: () -> Unit,
    val goToF: (Zone) -> Unit,
    override val index: Int,
): ZonesScope {
    override val next: () -> Unit
        get() = nextF
    override val previous : () -> Unit
        get() = previousF
    override val goTo: (Zone) -> Unit
        get() = goToF
}

@Composable
fun Zones(
    koinApplication: KoinApplication,
    content: @Composable ZonesScope.() -> Unit
) {
    val zones = values()
    val index = remember { mutableStateOf(0) }

    val nextZone = {
        index.value = min(index.value + 1, zones.size - 1)
    }

    val prevZone = {
        index.value = max(index.value - 1, 0)
    }

    val goTo: (zone: Zone) -> Unit = { zone ->
        index.value = zones.indexOf(zone)
    }

    val scope = ZonesScopeImpl(
        nextZone,
        prevZone,
        goTo,
        index.value
    )

    Column {
        scope.content()

        //view models
        val moodViewModel = koinApplication.koin.get<MoodViewModel>()
        val reflectViewModel = koinApplication.koin.get<ReflectViewModel>()
        val remarkViewModel = koinApplication.koin.get<RemarkViewModel>()
        val accomplishViewModel = koinApplication.koin.get<AccomplishViewModel>()

        Spacer(Modifier.size(20.dp))

        when(zones[index.value]) {
            Recognize -> {
                RecognizeZone(moodViewModel)
            }
            Reflect -> {
                ReflectZone(
                    moodViewModel,
                    reflectViewModel
                )
            }
            Become -> {
                BecomeZone(moodViewModel)
            }
            Remark -> {

                RemarkZone(remarkViewModel, moodViewModel)
            }
            Accomplish -> {

                AccomplishZone(accomplishViewModel)
            }
            Preview -> {
                PreviewZone()
            }
            Export -> {
                ExportZone()
            }
        }
    }
}