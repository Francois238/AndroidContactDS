package fr.isen.benet.androidcontactds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.benet.androidcontactds.databinding.ActivityHomeBinding
import fr.isen.benet.androidcontactds.model.ContactAffiche
import fr.isen.benet.androidcontactds.model.ContactRecu
import fr.isen.benet.androidcontactds.tool.ContactAdapter
import fr.isen.benet.androidcontactds.tool.ObjectWrapperForBinder


class HomeActivity : AppCompatActivity() {

    var listContact = ArrayList<ContactAffiche>()

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        this.getDataApi()

    }

    private fun getDataApi(){  //Fonction pour recuperer les donnees de l'api
        val requestQueue = Volley.newRequestQueue(applicationContext)

        var contacts : ContactRecu

        val url = "https://randomuser.me/api/?results=10&nat=fr"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val gson = Gson()

                contacts =  gson.fromJson(response.toString(), ContactRecu::class.java)

                this.displayContacts(contacts) //on affiche les contacts


            }
        ) { println("Error getting response") }
        requestQueue.add(jsonObjectRequest)


    }

    private fun displayContacts(contacts : ContactRecu){

        //on ajoute les contacts dans la liste en ne gardant que les informations utiles
        //d'où la création de la classe ContactAffiche
        for(contact in contacts.results){

            listContact.add(
                ContactAffiche(contact.name.first, contact.name.last, contact.email,
                                contact.phone,  contact.picture.large, contact.dob.date, contact.location)
            )
        }


        //Creation de l'adapter
        val adapter = ContactAdapter(listContact)

        val displayContact =findViewById<View>(R.id.liste_contact) as RecyclerView

        // Faire le lien entre l adapter et le recycler view
        displayContact.adapter = adapter
        // Faire le lien entre le layout manager et le recycler view
        displayContact.layoutManager = LinearLayoutManager(this)

        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View?, position: Int) {
                val contact = listContact[position]

                val bundle = Bundle()
                bundle.putBinder("Contact", ObjectWrapperForBinder(contact))
            //on passe l objet a l activite suivante
                startActivity(Intent(this@HomeActivity, DetailActivity::class.java).putExtras(bundle))
            }
        })
    }
}