package com.example.adopet.model.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.adopet.model.connection.ConexaoBD
import com.example.adopet.model.dto.Animal
import kotlinx.coroutines.selects.select

class AnimalDAO(contexto : Context) {
    private val conexaoBD : ConexaoBD = ConexaoBD(contexto)
    private val bdAdopet : SQLiteDatabase = conexaoBD.writableDatabase

    fun cadastrarAnimal(objAnimal: Animal) {
        val valoresCampos = ContentValues()
        valoresCampos.put("nomeAnimal", objAnimal.nomeAnimal)
        valoresCampos.put("telefone", objAnimal.telefone)
        valoresCampos.put("raca", objAnimal.raca)
        valoresCampos.put("endereco", objAnimal.endereco)
        valoresCampos.put("idade", objAnimal.idade)
        valoresCampos.put("caminhoImagem", objAnimal.caminhoImagem)

        this.bdAdopet.insert("tb_animal", null, valoresCampos)
    }


    fun listarAnimais(): List<Animal> {
        val todosAnimais: MutableList<Animal> = ArrayList()

        val campos = arrayOf("pkidanimal", "nomeAnimal", "telefone", "raca", "endereco", "idade", "caminhoImagem")

        val cursor = this.bdAdopet.query("tb_animal", campos, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val objAnimal : Animal = Animal()
            objAnimal.idAnimal = cursor.getInt(0)
            objAnimal.nomeAnimal = cursor.getString(1)
            objAnimal.telefone = cursor.getString(2)
            objAnimal.raca = cursor.getString(3)
            objAnimal.endereco = cursor.getString(4)
            objAnimal.idade = cursor.getString(5)
            objAnimal.caminhoImagem = cursor.getString(6)

            todosAnimais.add(objAnimal)
        }
        cursor.close()
        return todosAnimais
    }
    fun alterarAnimal(animalAAlterar : Animal){

        val valoresCampos = ContentValues()

        valoresCampos.put("nomeAnimal",animalAAlterar.nomeAnimal)
        valoresCampos.put("telefone",animalAAlterar.telefone)
        valoresCampos.put("raca",animalAAlterar.raca)
        valoresCampos.put("endereco",animalAAlterar.endereco)
        valoresCampos.put("idade",animalAAlterar.idade)
        valoresCampos.put("caminhoImagem",animalAAlterar.caminhoImagem)

        val id = arrayOf(animalAAlterar.idAnimal.toString())

        this.bdAdopet.update("tb_animal", valoresCampos, "pkidanimal = ?", id)
    }


    fun excluirAnimal(animalAExcluir:Animal){

        Log.d("AnimalDAO", "Excluir Animal: $animalAExcluir")
        val id = arrayOf(animalAExcluir.idAnimal.toString())
        this.bdAdopet.delete("tb_animal","pkidanimal=?",id)
    }
    fun fecharBanco() {
        bdAdopet.close()
    }
}