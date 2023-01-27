package fr.isen.benet.androidcontactds

import android.os.Binder

class ObjectWrapperForBinder(val data: Any) : Binder()