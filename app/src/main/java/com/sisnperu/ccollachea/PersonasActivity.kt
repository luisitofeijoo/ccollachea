package com.sisnperu.ccollachea

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sisnperu.ccollachea.adapter.PersonaAdapter
import com.sisnperu.ccollachea.database.AppDatabase
import com.sisnperu.ccollachea.database.entities.PersonaEntity
import com.sisnperu.ccollachea.databinding.ActivityPersonasBinding
import com.sisnperu.ccollachea.model.Persona
import kotlinx.coroutines.launch

class PersonasActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPersonasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityPersonasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPersonas)

// Configura el LinearLayoutManager para el RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

// Crea una lista de ejemplo de personas

        lifecycleScope.launch {
            val personaDao = AppDatabase.getInstance(this@PersonasActivity).personaDao()
            val personas = personaDao.getAllPersonas()

// Crea una instancia del adaptador y asigna la lista de personas
            val adapter = PersonaAdapter(personas)
            recyclerView.adapter = adapter
        }




      /*  val navController = findNavController(R.id.nav_host_fragment_content_personas)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
*/
       /* binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }*/
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_personas)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}