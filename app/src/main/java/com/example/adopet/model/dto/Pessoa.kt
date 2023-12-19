package com.example.adopet.model.dto

class Pessoa {
    var id: Int = 0
    var nome: String? = null
    var cpf: Long = 0
    var telefone: String? = null
    var usuario: String? = null
    var senha: String? = null


    override fun toString(): String {
        return """
            ID: ${this.id}
            Nome: ${this.nome}
            CPF: ${this.cpf}
            Telefone: ${this.telefone}
            Usuário: ${this.usuario}
            Senha:  ${this.senha}
        """.trimIndent()
    }




}