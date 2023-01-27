package fr.isen.benet.androidcontactds.model

data class Contact(
    var gender : String,
    var name : Name,
    var location : Location,
    var email : String,
    var login : Login,
    var dob : Dob,
    var registered : Registered,
    var phone : String,
    var cell : String,
    var id : Id,
    var picture : Picture,
    var nat : String

)
