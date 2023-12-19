package com.example.adopet.controller

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.adopet.databinding.ActivityCadastroAnimaisBinding
import com.example.adopet.model.dao.AnimalDAO
import com.example.adopet.model.dto.Animal

class CadastroAnimaisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroAnimaisBinding
    private var selectedImagePath: String? = null

    private lateinit var objAnimalDAO: AnimalDAO
    private var objAnimal: Animal? = null


    private val PICK_IMAGE_REQUEST = 1


    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCadastroAnimaisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLimparCadastroAnimal.setOnClickListener { limparCampos() }
        binding.buttonCadastrarAnimal.setOnClickListener {cadastrar()}
        binding.listarAnimais.setOnClickListener {
            startActivity(Intent(this@CadastroAnimaisActivity, MainActivity::class.java))
        }
        binding.loginText.setOnClickListener {
            startActivity(Intent(this@CadastroAnimaisActivity, LoginActivity::class.java))
        }

        objAnimalDAO = AnimalDAO(this@CadastroAnimaisActivity)

        if (intent.hasExtra("animal")) {
            objAnimal = intent.getSerializableExtra("animal") as Animal

            Log.d("CadastroAnimaisActivity", "Objeto Animal inicializado: $objAnimal")

            binding.nomeAnimal.setText(objAnimal!!.nomeAnimal)
            binding.telefoneCadastro.setText(objAnimal!!.telefone)
            binding.racaAnimal.setText(objAnimal!!.raca)
            binding.enderecoAnimal.setText(objAnimal!!.endereco)
            binding.idadeAnimal.setText(objAnimal!!.idade)

            binding.buttonCadastrarAnimal.text = "Alterar"
            binding.textViewCadastroAnimais.text = "Alteração Animal"
            binding.buttonCadastrarAnimal.setOnClickListener {
                if (objAnimal == null) {
                    objAnimal = Animal()
                }


                objAnimal!!.nomeAnimal = binding.nomeAnimal.text.toString()
                objAnimal!!.telefone = binding.telefoneCadastro.text.toString()
                objAnimal!!.raca = binding.racaAnimal.text.toString()
                objAnimal!!.endereco = binding.enderecoAnimal.text.toString()
                objAnimal!!.idade = binding.idadeAnimal.text.toString()


                if (selectedImagePath != null) {
                    objAnimal!!.caminhoImagem = selectedImagePath
                }

                if (objAnimal!!.idAnimal == 0) {


                    objAnimalDAO.cadastrarAnimal(objAnimal!!)
                } else {

                    objAnimalDAO.alterarAnimal(objAnimal!!)
                }
                Toast.makeText(this, "Animal alterado", Toast.LENGTH_LONG).show()
                limparCampos()
            }

        }
        binding.btnSelectImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
            } else {
                if (objAnimal != null) {
                    escolherImagemGaleria()
                } else {

                    objAnimal = Animal()
                    escolherImagemGaleria()
                }
            }
        }


    }


    private fun escolherImagemGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage = data.data

            if (selectedImage != null) {
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(selectedImage, filePathColumn, null, null, null)

                if (cursor != null) {
                    cursor.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    selectedImagePath = cursor.getString(columnIndex)
                    cursor.close()

                    objAnimal?.let {
                        it.caminhoImagem = selectedImagePath
                        binding.btnSelectImage.text = "Imagem Escolhida"
                        Toast.makeText(
                            this,
                            "Imagem escolhida: $selectedImagePath",
                            Toast.LENGTH_SHORT
                        ).show()
                    } ?: run {
                        Toast.makeText(this, "Objeto Animal não inicializado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Falha ao obter caminho da imagem", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Seleção de imagem cancelada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    escolherImagemGaleria()
                } else {

                    Toast.makeText(
                        this,
                        "Permissão de leitura de armazenamento externo negada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    fun limparCampos() {
        binding.nomeAnimal.setText("")
        binding.telefoneCadastro.setText("")
        binding.racaAnimal.setText("")
        binding.enderecoAnimal.setText("")
        binding.idadeAnimal.setText("")
        binding.nomeAnimal.requestFocus()
    }

    fun cadastrar() {
        if (objAnimal == null) {
            objAnimal = Animal()
        }


        objAnimal!!.nomeAnimal = binding.nomeAnimal.text.toString()
        objAnimal!!.telefone = binding.telefoneCadastro.text.toString()
        objAnimal!!.raca = binding.racaAnimal.text.toString()
        objAnimal!!.endereco = binding.enderecoAnimal.text.toString()
        objAnimal!!.idade = binding.idadeAnimal.text.toString()


        if (selectedImagePath != null) {
            objAnimal!!.caminhoImagem = selectedImagePath
        }

        if (objAnimal!!.idAnimal == 0) {


            objAnimalDAO.cadastrarAnimal(objAnimal!!)
        } else {

            objAnimalDAO.alterarAnimal(objAnimal!!)
        }
        Toast.makeText(this, "Animal Cadastrado", Toast.LENGTH_LONG).show()
        limparCampos()
    }
}
