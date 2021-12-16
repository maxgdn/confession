package com.confession.app.service

import com.confession.app.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Integer.max
import java.lang.Integer.min

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

    public fun reorder(task: Task, distance: Int) {
        val mutableList = _tasksState.value.toMutableList()
        val index = tasksState.value.indexOf(task)
        val target = max(min(index + distance, mutableList.size - 1), 0)
        //everything larger than the target needs to be shifted
        mutableList.remove(task)
        mutableList.add(target, task)
        _tasksState.value = mutableList
    }

    fun reset() {
        _tasksState.value = emptyList()
    }

}