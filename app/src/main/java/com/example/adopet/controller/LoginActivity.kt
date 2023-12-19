package com.example.adopet.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adopet.R
import com.example.adopet.databinding.ActivityLoginBinding
import com.example.adopet.model.dao.PessoaDAO
import com.example.adopet.model.dto.Animal

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var objPessoaDAO : PessoaDAO
    private lateinit var l : List<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        objPessoaDAO = PessoaDAO(this@LoginActivity)

        binding.textViewCadastre.setOnClickListener {

            startActivity(Intent(this@LoginActivity,CadastroPessoaActivity::class.java))
        }
        binding.buttonEntrar.setOnClickListener {

            val user = binding.usuarioLogin.text.toString()
            val pwd = binding.passwordLogin.text.toString()

            if (objPessoaDAO.autenticarUsuario(user, pwd)) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }

    }
}