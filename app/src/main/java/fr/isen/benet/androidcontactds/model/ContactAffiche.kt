package fr.isen.benet.androidcontactds.model

data class ContactAffiche(
    val name : String,
    val lastName : String,
    val email : String,
    val phone : String,
    val picture : String,
    val birthday : String,
    val location: Location
)
