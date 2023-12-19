package com.example.adopet.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adopet.R
import com.example.adopet.databinding.ActivityCadastroPessoaBinding
import com.example.adopet.model.dao.PessoaDAO
import com.example.adopet.model.dto.Pessoa

class CadastroPessoaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCadastroPessoaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCadastroPessoaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLimparCadastro.setOnClickListener { limparCampos() }
        binding.buttonCadastrar.setOnClickListener { cadastrar() }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this@CadastroPessoaActivity,LoginActivity::class.java))
        }
        binding.listarAnimais.setOnClickListener {
            startActivity(Intent(this@CadastroPessoaActivity,MainActivity::class.java))
        }
    }

    fun limparCampos(){
        binding.nomeCadastro.setText("")
        binding.cpfCadastro.setText("")
        binding.telefoneCadastroPessoa.setText("")
        binding.usuarioCadastro.setText("")
        binding.senhaCadastro.setText("")
        binding.nomeCadastro.requestFocus()
    }

    fun cadastrar(){
        val objPessoa : Pessoa = Pessoa()
        objPessoa.nome = binding.nomeCadastro.text.toString()
        objPessoa.cpf = binding.cpfCadastro.text.toString().toLong()
        objPessoa.telefone = binding.telefoneCadastroPessoa.text.toString()
        objPessoa.usuario = binding.usuarioCadastro.text.toString()
        objPessoa.senha = binding.senhaCadastro.text.toString()

        val objPessoaDAO : PessoaDAO = PessoaDAO(this@CadastroPessoaActivity)

        objPessoaDAO.cadastrarPessoa(objPessoa)

        Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_LONG).show()
    }
}