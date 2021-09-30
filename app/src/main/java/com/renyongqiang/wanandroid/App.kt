/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.renyongqiang.wanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

private const val TAG = "wan_android"

class App : Application() {
    companion object {
        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.i(TAG, "onActivityCreated: ${activity::class.java.simpleName}")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.i(TAG, "onActivityStarted: ${activity::class.java.simpleName}")
            }

            override fun onActivityResumed(activity: Activity) {
                Log.i(TAG, "onActivityResumed: ${activity::class.java.simpleName}")
            }

            override fun onActivityPaused(activity: Activity) {
                Log.i(TAG, "onActivityPaused: ${activity::class.java.simpleName}")
            }

            override fun onActivityStopped(activity: Activity) {
                Log.i(TAG, "onActivityStopped: ${activity::class.java.simpleName}")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.i(TAG, "onActivitySaveInstanceState: ${activity::class.java.simpleName}")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.i(TAG, "onActivityDestroyed: ${activity::class.java.simpleName}")
            }
        })
    }
}
