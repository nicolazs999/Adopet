package com.example.adopet.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.adopet.R
import com.example.adopet.databinding.ActivityAnimalBinding
import android.graphics.BitmapFactory
import android.widget.ListView
import android.widget.Toast
import com.example.adopet.model.dao.AnimalDAO
import com.example.adopet.model.dto.Animal

class AnimalActivity : AppCompatActivity() {
    private var binding: ActivityAnimalBinding? = null
    private lateinit var animalDAO: AnimalDAO
    private var animal: Animal? = null

    private lateinit var listAdapterActivity: ListAdapterActivity
    private lateinit var animalArrayList: ArrayList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        animal = intent.getSerializableExtra("animal") as? Animal


        animalDAO = AnimalDAO(this@AnimalActivity)

        animalArrayList = ArrayList(animalDAO.listarAnimais())
        listAdapterActivity = ListAdapterActivity(this@AnimalActivity, animalArrayList)


        try {
            if (animal != null) {
                binding?.nameProfile?.text = animal?.nomeAnimal
                binding?.phoneProfile?.text = animal?.telefone
                binding?.address?.text = animal?.endereco

                val caminhoImagem = animal?.caminhoImagem
                if (caminhoImagem != null && caminhoImagem.isNotEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(caminhoImagem)
                    binding?.profileImage?.setImageBitmap(bitmap)
                } else {
                    binding?.profileImage?.setImageResource(R.drawable.cc5)
                }
            } else {

            }
        } catch (e: Exception) {

        }

        binding?.btnExcluir?.setOnClickListener {

            confirmarExclusao()
        }

        binding?.btnEditar?.setOnClickListener {

            val intent = Intent(this@AnimalActivity, CadastroAnimaisActivity::class.java)
            intent.putExtra("animal", animal)
            startActivity(intent)
        }
    }

    private fun confirmarExclusao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar Exclusão")
        builder.setMessage("Tem certeza que deseja excluir este animal?")
        builder.setPositiveButton("Sim") { _, _ ->
            try {
                animal?.let {
                    animalDAO.excluirAnimal(it)
                    Toast.makeText(this, "Animal removido", Toast.LENGTH_LONG).show()


                    val mainActivityIntent = Intent(this@AnimalActivity, MainActivity::class.java)
                    mainActivityIntent.putExtra("atualizarLista", true)
                    startActivity(mainActivityIntent)

                    finish()
                }
            } catch (e: Exception) {

            } finally {
                animalDAO.fecharBanco()
            }
        }
        builder.setNegativeButton("Não", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
