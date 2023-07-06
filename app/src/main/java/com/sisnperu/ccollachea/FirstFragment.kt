package com.sisnperu.ccollachea

import android.app.AlertDialog
import android.app.Person
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.sisnperu.ccollachea.databinding.FragmentFirstBinding
import com.sisnperu.ccollachea.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val SCAN_REQUEST_CODE = 123
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     /*   binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/

        binding.btnListaPersonas.setOnClickListener {
            val intent = Intent(requireContext(), PersonasActivity::class.java)
            startActivity(intent)
        }

        binding.btnScanerQr.setOnClickListener {
            escanearCodigo();
        }

        binding.btnSearchNroDocumento.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Ingrese número de DNI")

            val input = EditText(requireContext())
            input.inputType = InputType.TYPE_CLASS_NUMBER
            input.maxLines = 1
            builder.setView(input)

            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val numeroDocumento = input.text.toString()
                buscarPersona(numeroDocumento);
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
            input.requestFocus()
            val delayMillis = 300L // Tiempo de espera en milisegundos
            Handler().postDelayed({
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
            }, delayMillis)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun escanearCodigo() {

        var options = ScanOptions()

        options.setPrompt("Para prender linterna, presione el boton de volumen arriba.");
        options.setBeepEnabled(true) //Sonido de lectura
        options.setOrientationLocked(true)
        options.setCaptureActivity(CaptureAct::class.java)

       barcodeLauncher.launch(options)

    }

    private val barcodeLauncher = registerForActivityResult<ScanOptions, ScanIntentResult>(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            //Toast.makeText(context, "No encontrado", Toast.LENGTH_LONG).show();
        } else {
             try {
                 buscarPersona(result.contents.take(8))
             } catch (e: Exception) {
                 Log.d("Error", e.toString());
             }
        }
    }

    fun buscarPersona(dni:String) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val personaDao = AppDatabase.getInstance(requireActivity()).personaDao()
                val persona = personaDao.buscarPorDni(dni)
                if (persona != null) {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(requireContext(),"REGISTRADO ENCONTRADO ***** => "+persona.nombres,Toast.LENGTH_LONG).show()
                    }
                } else {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(requireContext(),"REGISTRADO NO ENCONTRADO"+dni,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }



}