package com.mathelete.app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.mathelete.app.AppViewModel
import com.mathelete.app.Screen

@Composable
fun MatheleteApp(vm: AppViewModel) {
    BackHandler(enabled = vm.backStack.size > 1) { vm.back() }
    when (val screen = vm.backStack.last()) {
        Screen.Home -> HomeScreen(vm)
        Screen.Sets -> SetsScreen(vm)
        is Screen.Exam -> ExamScreen(vm, screen.session)
        is Screen.Results -> ResultsScreen(vm, screen.session)
        Screen.Topics -> TopicsScreen(vm)
        Screen.Drills -> DrillsScreen(vm)
        is Screen.Practice -> PracticeScreen(vm, screen.session)
        Screen.Stats -> StatsScreen(vm)
    }
}
