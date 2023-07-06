package com.sisnperu.ccollachea

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.sisnperu.ccollachea.database.AppDatabase
import com.sisnperu.ccollachea.database.entities.PersonaEntity
import com.sisnperu.ccollachea.databinding.ActivityMainBinding
import com.sisnperu.ccollachea.model.Persona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

/*
        val personaDao = AppDatabase.getInstance(this@MainActivity).personaDao()
        val personas = personaDao.getAllPersonas()

        for (persona in personas) {
            Log.d("MainActivity", "Persona: $persona")
        }
*/

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_actualizar -> {
                actualizarTablas();
                true
            }
            R.id.action_enviar -> {

                lifecycleScope.launch {
                    val personaDao = AppDatabase.getInstance(this@MainActivity).personaDao()
                    val personas = personaDao.getAllPersonas()

                    for (persona in personas) {
                        Log.d("MainActivity", "Persona: $persona")
                    }
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private suspend fun guardarPersonasEnDao(personas: List<Persona>) {
        val personaDao = AppDatabase.getInstance(this@MainActivity).personaDao()


        for (persona in personas) {

            //personaDao.insertPersona(persona)
        }
    }

    private fun actualizarTablas() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        lifecycleScope.launch {
            try {
                val personas = ApiClient.personasApiService.obtenerPersonas()
                var personaDao = AppDatabase.getInstance(this@MainActivity).personaDao()

                withContext(Dispatchers.IO) {
                    personaDao.eliminarRegistros()
                }

                //AppDatabase.getInstance(this@MainActivity).clearAllTables();
                personas.forEach { persona ->
                    val personaEntity = PersonaEntity(
                        id = persona.id,
                        dni = persona.dni,
                        nombres = persona.nombres
                    )
                    personaDao.insertPersona(personaEntity)
                }

            } catch (e: Exception) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Ocurrio un error en el servidor. "+e)
                builder.setPositiveButton("Aceptar") { dialog, which ->  }
                builder.create().show()
            } finally {
                dialog.dismiss()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}