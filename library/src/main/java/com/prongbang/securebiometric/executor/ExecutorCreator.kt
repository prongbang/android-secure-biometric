package com.prongbang.securebiometric.executor

import android.content.Context
import java.util.concurrent.Executor

interface ExecutorCreator {
    fun create(context: Context): Executor
}