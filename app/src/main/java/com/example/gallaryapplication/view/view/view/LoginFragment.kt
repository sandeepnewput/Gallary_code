package com.example.gallaryapplication.view.view.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gallaryapplication.R
import com.example.gallaryapplication.view.view.viewmodel.ImageVideoViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
//class LoginFragment : Fragment(),LogoutListener{
class LoginFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val myapp = MyApp()
    private val viewModel by lazy { ViewModelProvider(this).get(ImageVideoViewModel::class.java) }


    val c = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)+1
    val day = c.get(Calendar.DAY_OF_MONTH)

    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)
    val seconds = c.get(Calendar.SECOND)

    val date1 = getCurrentDateTime1()

    val dateInString = date1.toString()
    fun getCurrentDateTime1(): Date {
        return Calendar.getInstance().time
    }
    fun getCurrentDateTime2(): Date {
        return Calendar.getInstance().time
    }

    var localtime1:Long? = null

//    val diff: Long = date1.getTime() - date2.getTime()
//    val seconds1 = diff / 1000
//    val minutes = seconds / 60
//    val hours = minutes / 60
//    val days = hours / 24

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.updateflag(0)
  //      viewModel.getFlagvalue()

//        viewModel.flagValue.observe(viewLifecycleOwner, Observer {flag ->
//            Log.d("flagvalue","$flag")

            loginbutton.setOnClickListener {
                viewModel.updateflag(1)
                val action = LoginFragmentDirections.actionlogintophotofragment()
                Navigation.findNavController(it).navigate(action)

//                myapp?.registerSessionListener(this)
//                myapp?.startUserSession()
                viewModel.saveLoggedinTime(date1)
                viewModel.getLoggedintime()
                myapp?.startUserSession()
//                viewModel.getLoggedintime()
//
//                  viewModel.localtime.observe(viewLifecycleOwner, Observer { localtime->
//                      Log.d("localtime","$localtime is")
//                      localtime1 = localtime
//                  })
            }//end of loginbutton
//        })



//        viewModel.updateflag(0)
//        viewModel.getFlagvalue()
//        viewModel.getLoggedintime()
//        viewModel.localtime.observe(viewLifecycleOwner, Observer {localtime->
//            Log.d("time","time is $localtime")
//        })
    }//end of onViewCreated


//    override fun onSessionLogout() {
//        val navController = findNavController()
//        navController.navigate(R.id.loginFragment)
//    }
//
//    override fun matchtime(time: Long) {
//
//        viewModel.getLoggedintime()
//
//        viewModel.localtime.observe(viewLifecycleOwner, Observer { localtime->
//            Log.d("localtime","$localtime is")
//            localtime1 = localtime
//        })
//
//
//            Log.d("localtime","$localtime1 is")
////            localtime1 = localtime
//
//            Log.d("localtime","$localtime1 is")
//            var difftime  = (time - localtime1!!)/60000
//            var minute = difftime/60000
//            if(difftime < 1){
//                Toast.makeText(context,"your session is expired in ${1-difftime}",Toast.LENGTH_LONG).show()
//            }else{
//                onSessionLogout()
//                viewModel.updateflag(0)
//                myapp?.cancelTimer()
//            }
//
//
//
//
//
//
//    }//end of matchtime


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }


}