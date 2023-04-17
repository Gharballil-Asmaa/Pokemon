package com.example.android_pokemon

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.android_pokemon.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        loadPokemon()
        checkpermission()
    }
    val accesslocation=123
    fun checkpermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),accesslocation)
            }
        }
        getuserlocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            accesslocation->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getuserlocation()
                }else{
                    Toast.makeText(this,"location is deny",Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    fun getuserlocation(){
        Toast.makeText(this,"location access now",Toast.LENGTH_LONG).show()
        val mylocation=MylocationListener()
        val locationManager=getSystemService(LOCATION_SERVICE)as LocationManager
        //locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,3,3f,mylocation)
      /*  if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) */



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.marioicon)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14f))
    }
    var mylocation:Location?=null
    inner class MylocationListener:LocationListener{
        constructor(){
            mylocation=Location("me")
             mylocation!!.longitude=0.0
            mylocation!!.latitude=0.0}


        override fun onLocationChanged(location: Location) {
            mylocation=location
            TODO("Not yet implemented")
        }

    }
    var oldlocation:Location?=null
    inner class myThread:Thread{
        constructor():super(){
            oldlocation=Location("oldlocation")
            oldlocation!!.longitude=0.0
            oldlocation!!.latitude=0.0}
        }

         fun run() {
            while (true){
            try{
                if (oldlocation!!.distanceTo(mylocation!!)==0f){
                    continue
                }
                oldlocation=mylocation
            runOnUiThread(){
                mMap!!.clear()
                val sydney = LatLng(mylocation!!.latitude, mylocation!!.longitude)
                mMap.addMarker(MarkerOptions().position(sydney).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.marioicon)))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14f))
                for(i in 0..listOfPokemon.size-1 ){
                    var newpokemon=listOfPokemon[i]
                    if(newpokemon.iscatch==false){
                        val pocklocation = LatLng(mylocation!!.latitude, mylocation!!.longitude)
                        mMap.addMarker(MarkerOptions().position(sydney).title(newpokemon.name).snippet(newpokemon.des + "power"+newpokemon.power).icon(BitmapDescriptorFactory.fromResource(newpokemon.image!!)))

                    }
                }
            }

                Thread.sleep(1000)
            }
            catch (ex:Exception){

            }
        }
    }
    var listOfPokemon=ArrayList<pokemon>()
    fun loadPokemon(){
        fun loadPokemon(){
            listOfPokemon.add(pokemon(R.drawable.charmander,"charamender","charamender",31.63166107120333, 31.69121010347244, -8.105466302799071))
            listOfPokemon.add(pokemon(R.drawable.bulbasor,"bulbasor","bulbasor",30.46804797059603, 31.632441660616934, -8.080505035553834))
            listOfPokemon.add(pokemon(R.drawable.squirtle,"squirtle","squirtle",31.63166107120333, 33.5708639699732, -7.676168030727189))

        }
    }
}