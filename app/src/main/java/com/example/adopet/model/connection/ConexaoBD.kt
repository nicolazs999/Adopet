package com.example.adopet.model.connection

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION

class ConexaoBD(contexto: Context) : SQLiteOpenHelper(contexto, NAME, null, VERSION) {
    companion object {
        private const val NAME = "bdAdopet"
        private const val VERSION = 4
    }

    override fun onCreate(bdAdopet: SQLiteDatabase?) {
        bdAdopet!!.execSQL(
            "create table tb_pessoa(" +
                    "pkidpessoa integer primary key autoincrement," +
                    "nome varchar(100) not null," +
                    "cpf bigint not null unique," +
                    "telefone varchar(45) not null," +
                    "usuario varchar(20) not null unique," +
                    "senha varchar(16) not null)"
        )

        bdAdopet!!.execSQL(
            "create table tb_animal(" +
                    "pkidanimal integer primary key autoincrement," +
                    "nomeAnimal varchar(100) not null," +
                    "telefone varchar(45) not null," +
                    "raca varchar(20) not null," +
                    "endereco varchar(50) not null," +
                    "idade varchar(20) not null," +
                    "caminhoImagem varchar(255) not null)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {

            db?.execSQL("ALTER TABLE tb_animal ADD COLUMN idade TEXT")
        }
        if (oldVersion < 3) {

            db?.execSQL("ALTER TABLE tb_animal ADD COLUMN caminhoImagem TEXT")
        }


        if (oldVersion < 4) {
            db?.execSQL("CREATE TABLE tb_animal_temp AS SELECT * FROM tb_animal")
            db?.execSQL("DROP TABLE tb_animal")
            db?.execSQL(
                "CREATE TABLE tb_animal(" +
                        "pkidanimal INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nomeAnimal VARCHAR(100) NOT NULL," +
                        "telefone VARCHAR(45) NOT NULL," +
                        "raca VARCHAR(20) NOT NULL," +
                        "endereco VARCHAR(50) NOT NULL," +
                        "idade VARCHAR(20) NOT NULL," +
                        "caminhoImagem VARCHAR(255) NOT NULL)"
            )
            db?.execSQL("INSERT INTO tb_animal SELECT * FROM tb_animal_temp")
            db?.execSQL("DROP TABLE tb_animal_temp")
        }
    }

}
