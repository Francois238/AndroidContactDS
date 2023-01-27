package fr.isen.benet.androidcontactds.model

import fr.isen.benet.androidcontactds.model.Contact

data class ContactRecu (
    var results : ArrayList<Contact>,
    var info : Info
    )