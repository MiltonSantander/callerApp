package com.example.callerApp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val NUMBER = "number"
private const val NAME = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [SinglePersonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SinglePersonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var listener: OnFragmentActionsListener? = null
    lateinit var numberTextView: TextView
    lateinit var nameTextView: TextView
    lateinit var callButton : Button
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentActionsListener) {
//            listener = context
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(NUMBER)
            param2 = it.getString(NAME)
            Log.d("param1 number",param1.toString())
            Log.d("param2 name",param2.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberTextView = view.findViewById(R.id.number_text_view)
        nameTextView = view.findViewById(R.id.name_text_view)

        numberTextView.text = param1
        nameTextView.text = param2

        callButton = view.findViewById(R.id.call_button)

        callButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:$param1")
            startActivity(intent)
        }
    }

//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SinglePersonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SinglePersonFragment().apply {
                arguments = Bundle().apply {
                    putString(NUMBER, param1)
                    putString(NAME, param2)
                }
            }
    }
}