import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.callerApp.Contacts
import com.example.callerApp.R

class MyAdapter(var contactsList: ArrayList<Contacts>, var callback: (nombre:String, numero:String) -> Unit) : RecyclerView.Adapter<MyAdapter.MyHolder>(){

    inner class MyHolder(view: View): RecyclerView.ViewHolder(view){
        var contactName: TextView = view.findViewById(R.id.name)
        var contactNumber: TextView = view.findViewById(R.id.number)
        var itemRecycler : View = view.findViewById(R.id.item_recycler)

        fun bind(elementList: Contacts){
            contactName.text = elementList.name
            contactNumber.text = elementList.number
        }

        init {
            itemRecycler.setOnClickListener{
                callback(contactName.text.toString(), contactNumber.text.toString())
            }
        }
    }

    fun reloadList(filteredList : ArrayList<Contacts>){
        this.contactsList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler, parent, false)

        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }


}