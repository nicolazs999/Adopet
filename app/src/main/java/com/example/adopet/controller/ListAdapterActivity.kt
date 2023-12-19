package com.example.adopet.controller

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.adopet.R
import com.example.adopet.databinding.ActivityAnimalBinding
import com.example.adopet.databinding.ActivityMainBinding
import com.example.adopet.model.dto.Animal
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ListAdapterActivity(context: Context, animalArrayList: ArrayList<Animal>) :
    ArrayAdapter<Animal>(context, R.layout.animal_item, animalArrayList) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val animal = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.animal_item, parent, false)
        }
        val imageViewAnimalItem = convertView!!.findViewById<CircleImageView>(R.id.profile_pic)
        val animalName = convertView!!.findViewById<TextView>(R.id.AnimalName)
        val raca = convertView.findViewById<TextView>(R.id.raca)
        val idade = convertView.findViewById<TextView>(R.id.idade)
        val telefone = convertView.findViewById<TextView>(R.id.telefoneCadastro)
        val endereco = convertView.findViewById<TextView>(R.id.endereco_animal)

        val imagePath = animal?.caminhoImagem

        if (imagePath != null && imagePath.isNotEmpty()) {

            Picasso.get().load("file://$imagePath").into(imageViewAnimalItem)
        } else {

            imageViewAnimalItem.setImageResource(R.drawable.cc5)
        }

        animalName?.text = animal?.nomeAnimal
        raca?.text = animal?.raca
        idade?.text = animal?.idade ?: ""
        telefone?.text = animal?.telefone ?: ""
        endereco?.text = animal?.endereco ?: ""

        return convertView
    }


}
