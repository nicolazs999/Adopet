package com.example.adopet.model.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.adopet.model.connection.ConexaoBD
import com.example.adopet.model.dto.Animal
import com.example.adopet.model.dto.Pessoa

class PessoaDAO(contexto : Context) {
    private val conexaoBD : ConexaoBD = ConexaoBD(contexto)
    private val bdAdopet : SQLiteDatabase = conexaoBD.writableDatabase

    fun cadastrarPessoa(objPessoa : Pessoa){
        val valoresCampos = ContentValues()
        valoresCampos.put("nome",objPessoa.nome)
        valoresCampos.put("cpf",objPessoa.cpf)
        valoresCampos.put("telefone",objPessoa.telefone)
        valoresCampos.put("usuario",objPessoa.usuario)
        valoresCampos.put("senha", objPessoa.senha)
        this.bdAdopet.insert("tb_pessoa",null,valoresCampos)
    }

    fun autenticarUsuario(usuario: String, senha: String): Boolean {
        val colunas = arrayOf("usuario", "senha")
        val selecao = "usuario = ? AND senha = ?"
        val selecaoArgs = arrayOf(usuario, senha)
        val cursor = bdAdopet.query("tb_pessoa", colunas, selecao, selecaoArgs, null, null, null)

        val autenticado = cursor.count > 0
        cursor.close()
        return autenticado
    }

}