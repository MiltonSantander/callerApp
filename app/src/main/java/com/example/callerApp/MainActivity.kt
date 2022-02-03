package com.example.callerApp

import MyAdapter
import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Contacts(
    var name : String? = null,
    var number : String? = null
)

class MainActivity : AppCompatActivity() {

    lateinit var recyclerVariable : RecyclerView

    private val permissions: Array<String> = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)
    private val requestCode: Int = 1

    lateinit var myadapter: MyAdapter
    lateinit var contactList: ArrayList<Contacts>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //desactiva el modo noche

        validatePermissions()
    }

    private fun readContacts(): ArrayList<Contacts> {
        val contactIdCol = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val nameCol =  ContactsContract.Contacts.DISPLAY_NAME
        val numberCol = ContactsContract.CommonDataKinds.Phone.NUMBER

        val projection = arrayOf(contactIdCol, nameCol, numberCol)

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection, null, null, null
        )

        val contactIdIdx = cursor!!.getColumnIndex(contactIdCol)
        val nameIdx = cursor.getColumnIndex(nameCol)
        val numberIdx = cursor.getColumnIndex(numberCol)
        var contactsList  = arrayListOf<Contacts>()

        while (cursor.moveToNext()) {
            val contactId = cursor.getString(contactIdIdx)
            val name = cursor.getString(nameIdx)
            val number = cursor.getString(numberIdx)

            contactsList += arrayListOf(Contacts(name, number))

        }

        cursor.close()

        return contactsList
    }

    /*show search bar*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Asociar la configuracion searchable.xml con el SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
            isSubmitButtonEnabled = true
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Toast.makeText(this, query, Toast.LENGTH_SHORT).show()

                var queryResult = arrayListOf<Contacts>()

                for (contactInfo in contactList){
                    if(query.lowercase() == contactInfo.name?.lowercase()){
                        queryResult += arrayListOf(Contacts(contactInfo.name, contactInfo.number))
                    }
                    myadapter.reloadList(queryResult)
                }
            }
        }
    }

    private fun validatePermissions(){
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, permissions, requestCode)
            validatePermissions()
        } else {
            renderContactList()
        }
    }

    private fun renderContactList() {
        contactList = readContacts()
        myadapter = MyAdapter(contactList){
                nombre, numero ->
            val singlePersonIntent = Intent(this, SinglePersonActivity::class.java)
            singlePersonIntent.putExtra("number", numero)
            singlePersonIntent.putExtra("name", nombre)
            startActivity(singlePersonIntent)
        }

        recyclerVariable = findViewById(R.id.recycler_de_lista)
        recyclerVariable.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerVariable.adapter = myadapter

        handleIntent(intent)
    }
}