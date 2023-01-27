package fr.isen.benet.androidcontactds

import CircleTransform
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.benet.androidcontactds.model.ContactAffiche

class ContactAdapter (private val listContact: List<ContactAffiche>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    // Define the listener interface
    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    // Define listener member variable
    private lateinit var listener: OnItemClickListener

    // Define the method that allows the parent activity or fragment to define the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = itemView.findViewById(R.id.name)
        val rue: TextView = itemView.findViewById(R.id.address_rue)
        val ville: TextView = itemView.findViewById(R.id.ville)
        val mail : TextView = itemView.findViewById(R.id.mail)
        val image : ImageView = itemView.findViewById(R.id.icone_contact)


        init {
            // Setup the click listener
            itemView.setOnClickListener {
                // Triggers click upwards to the adapter on click
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(itemView, position)
                }
            }
        }
    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_contact, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ContactAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val contact: ContactAffiche = listContact[position]
        // Set item views based on your views and data model
        val nameView = viewHolder.name

        val name = contact.name + " " + contact.lastName.uppercase()
        nameView.text = name

        val rueView = viewHolder.rue

        val address = contact.location.street.number.toString() + " " + contact.location.street.name
        rueView.text = address

        val villeView = viewHolder.ville
        villeView.text = contact.location.city

        val mailView = viewHolder.mail
        mailView.text = contact.email

        val imageView = viewHolder.image

        var image = contact.picture

        if (image =="") { //si l'url est vide, on met du texte pour ne pas faire planter l'appli
            image = "a"
        }


        Picasso.get().load(image)
            .error(R.drawable.icone_vide) //si url invalide, image par defaut
            .centerCrop()
            .fit()
            .transform(CircleTransform())
            .into(imageView)



    }
        // Returns the total count of items in the list
        override fun getItemCount(): Int {
            return listContact.size
        }

}