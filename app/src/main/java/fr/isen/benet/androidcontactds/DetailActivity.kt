package fr.isen.benet.androidcontactds

import CircleTransform
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.benet.androidcontactds.databinding.ActivityDetailBinding
import fr.isen.benet.androidcontactds.model.ContactAffiche
import java.time.LocalDate

class DetailActivity : AppCompatActivity() {

    private lateinit var contact : ContactAffiche

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //on recupere le contact envoye par l'activite Home

        this.contact = (intent.extras!!.getBinder("Contact") as ObjectWrapperForBinder?)!!.data as ContactAffiche

        binding.nomProfil.text = contact.name + " " + contact.lastName.uppercase()

        binding.mailProfil.text = contact.email

        binding.telProfil.text = contact.phone.replace("-", " ")

        binding.adresseProfil.text = contact.location.street.number.toString() + " " + contact.location.street.name

        binding.villeProfil.text = contact.location.city

        val date = contact.birthday.take(10).split("-").reversed().joinToString("/")

        binding.dateProfil.text = date



        var image = contact.picture

        if (image =="") { //si l'url est vide, on met du texte pour ne pas faire planter l'appli
            image = "a"
        }

        val imageProfil = binding.imageProfil

        Picasso.get().load(image)
            .error(R.drawable.icone_vide) //si url invalide, image par défaut
            .centerCrop()
            .fit()
            .transform(CircleTransform())
            .into(imageProfil)


    }
}