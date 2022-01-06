package com.example.gallaryapplication.view.view.view

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.gallaryapplication.view.view.viewmodel.ImageVideoViewModel
import java.util.*


class MyApp : Application()  {

   fun calllatesttime(){
       val date2 = getCurrentDateTime()
//       listener?.matchtime(date2.time)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

 private var listener : LogoutListener? = null

       var timer =  object: CountDownTimer(10000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
             Log.d("seconds remaining: " , "${millisUntilFinished / 1000}")
         }
         override fun onFinish() {
//             listener?.onSessionLogout()
             calllatesttime()
             startUserSession()

         }
     }//end of timer

    fun startUserSession() {
        Log.d("listener","$listener")
       timer.start()
    }//end of startusrSession

      fun cancelTimer() {
              timer.cancel()
    }

    fun registerSessionListener(listener: LogoutListener) {
         this.listener = listener
    }


}//end of my app