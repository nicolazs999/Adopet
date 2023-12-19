package com.example.adopet.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.adopet.R
import com.example.adopet.model.dto.Animal
import com.example.adopet.databinding.ActivityMainBinding
import com.example.adopet.model.dao.AnimalDAO
import java.util.Locale
import com.example.adopet.model.dto.Animal as DTOAnimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pesquisar : SearchView
    private lateinit var todosOsAnimais : MutableList<Animal>
    private var animaisFiltrados : MutableList<Animal> = ArrayList()
    private lateinit var objAnimalDAO: AnimalDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getBooleanExtra("atualizarLista", false)) {

            binding.listview.invalidateViews()
        }

        registerForContextMenu(binding.listview)

        objAnimalDAO = AnimalDAO(this@MainActivity)

        todosOsAnimais = objAnimalDAO.listarAnimais().toMutableList()

        this.animaisFiltrados.addAll(todosOsAnimais)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.cadastrarAnimal.setOnClickListener {
            startActivity(Intent(this@MainActivity, CadastroAnimaisActivity::class.java))
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }


        val animalArrayList = ArrayList<Animal>()
        val animalDAO = AnimalDAO(this@MainActivity)
        val animaisCadastrados: List<DTOAnimal> = animalDAO.listarAnimais()


        for (dtoAnimal in animaisCadastrados) {
            val animal = Animal()
            animal.nomeAnimal = dtoAnimal.nomeAnimal
            animal.telefone = dtoAnimal.telefone
            animal.raca = dtoAnimal.raca
            animal.endereco = dtoAnimal.endereco
            animal.idade = dtoAnimal.idade
            animal.caminhoImagem = dtoAnimal.caminhoImagem

            animalArrayList.add(animal)
        }


        val listAdapterActivity = ListAdapterActivity(this@MainActivity, animalArrayList)
        binding.listview.adapter = listAdapterActivity


        binding.listview.isClickable = true
        binding.listview.onItemClickListener =
            AdapterView.OnItemClickListener { _, view, position, _ ->
                val intent = Intent(view.context, AnimalActivity::class.java)
                intent.putExtra("animal", animaisCadastrados[position])
                startActivity(intent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        pesquisar = menu.findItem(R.id.icPesquisar).actionView as SearchView

        pesquisar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivity", "onQueryTextChange: $newText")
                procurarAnimal(newText ?: "")
                return true
            }
        })
        return true
    }


    fun procurarAnimal(query: String) {
        animaisFiltrados.clear()

        if (query.isEmpty()) {
            animaisFiltrados.addAll(todosOsAnimais)
        } else {
            for (animal in todosOsAnimais) {
                if (animal.nomeAnimal?.contains(query, ignoreCase = true) == true ||
                    animal.idade?.contains(query, ignoreCase = true) == true ||
                    animal.raca?.contains(query, ignoreCase = true) == true
                ) {
                    animaisFiltrados.add(animal)
                }
            }
        }

        val novoAdapter = ListAdapterActivity(this@MainActivity, ArrayList(animaisFiltrados))
        binding.listview.adapter = novoAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.icSair -> {
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
