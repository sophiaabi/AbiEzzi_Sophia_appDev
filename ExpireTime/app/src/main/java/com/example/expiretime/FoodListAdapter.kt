package com.example.expiretime

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expiretime.FoodListAdapter.FoodViewHolder
import java.util.*

//code needed to implement recyclerView: used from this tutorial, modified to be in kotlin and use arraylist as data source instead of linked list
//https://google-developer-training.github.io/android-developer-fundamentals-course-practicals/en/Unit%202/44_p_create_a_recycler_view.html
class FoodListAdapter(context: Context?, foodList: ArrayList<FoodItem>) : RecyclerView.Adapter<FoodViewHolder>() {
    //create list to hold foodItems, will be the data source for recycler view
    private val foodList: ArrayList<FoodItem>
    private val mInflater: LayoutInflater

    inner class FoodViewHolder(itemView: View, adapter: FoodListAdapter) : RecyclerView.ViewHolder(itemView) {
        val foodItemView: TextView
        val mAdapter: FoodListAdapter

        init {
            foodItemView = itemView.findViewById<View>(R.id.foodTextView) as TextView
            mAdapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val mItemView = mInflater.inflate(R.layout.foodlist_item, parent, false)
        return FoodViewHolder(mItemView, this)
    }

    //connects the data to the view holder
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        //putting the text into the list
        val mCurrent = foodList[position]
        holder.foodItemView.text = mCurrent.foodText
        holder.foodItemView.setTextColor(mCurrent.textColor)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.foodList = foodList
    }
}