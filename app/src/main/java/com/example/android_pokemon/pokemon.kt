package com.example.android_pokemon

import android.location.Location

class pokemon {
    var name:String?=null
    var des:String?=null
    var image:Int?=null
    var power:Double?=null
    var location:Location?=null
    var iscatch:Boolean?=false
    constructor(image: Int, name: String, des: String, power: Double, lat: Double,long: Double){
        this.name=name
        this.des=des
        this.image=image
        this.power=power
        this.location=Location(name)
        this.location!!.latitude=lat
        this.location!!.latitude=long
    }
}