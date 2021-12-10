package com.confession.app.service

import com.confession.app.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AccomplishViewModel() {

    private val _tasksState: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasksState: StateFlow<List<Task>> = _tasksState

    public fun addTask(task: Task) {
        val mutableList = _tasksState.value.toMutableList()
        mutableList.add(task)
        _tasksState.value = mutableList
    }

    public fun removeTask(task: Task) {
        val mutableList = _tasksState.value.toMutableList()
        mutableList.remove(task)
        _tasksState.value = mutableList
    }

    public fun editTask(task: Task) {
        val mutableList = _tasksState.value.toMutableList()
        mutableList[tasksState.value.indexOf(task)] = task
        _tasksState.value = mutableList
    }

    public fun reorder() {}

}