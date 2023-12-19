package com.example.adopet.model.dto

import java.io.Serializable

class Animal : Serializable {
    var idAnimal: Int = 0
    var nomeAnimal: String? = null
    var telefone: String? = null
    var raca: String? = null
    var endereco: String? = null
    var idade: String? = null
    var caminhoImagem: String? = null



    override fun toString(): String {
        return """
            ID: ${this.idAnimal}
            Nome: ${this.nomeAnimal}
            Telefone: ${this.telefone}
            Raça: ${this.raca}
            Endereço:  ${this.endereco}
            Idade: ${this.idade}
            Caminho da Imagem: ${this.caminhoImagem}
        """.trimIndent()
    }
}
