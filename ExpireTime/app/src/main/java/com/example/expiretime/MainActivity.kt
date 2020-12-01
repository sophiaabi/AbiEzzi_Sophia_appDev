package com.example.expiretime

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 1
    //list of food item object to hold data on each food added
    private var foodList = arrayListOf<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateRecycler()
        //start add activity when fab is clicked
        fab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }


    }

    //tutorial I used for the swipe to delete method: https://www.youtube.com/watch?v=rcSNkSJ624U
    var swipeCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        //nothing implemented in this function- this is for if we wanted to have the user move the position of elements
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        //deleting element of the list when swiped, also creating the snackbar for the user to undo the action
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if(direction == ItemTouchHelper.LEFT){
                val position = viewHolder.adapterPosition
                //parse string to get name of food to display in the undo snackbar
                val movieObjDeleted = foodList.get(position)
                var movieDeleted = movieObjDeleted.foodText
                val foodName = movieDeleted.split("-").map { it.trim() }[0]
                //delete movie from list
                foodList.removeAt(position)
                updateRecycler()

                //create undo snackbar and implement putting deleted element back into the array
                var snack = Snackbar.make(recyclerView, "$foodName removed.",Snackbar.LENGTH_LONG)
                snack.setAction("Undo", View.OnClickListener {
                    foodList.add(position,movieObjDeleted)
                    updateRecycler()
                })
                snack.show()
            }
        }

        //implementing library that will turn the background red when swiped
        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
            //library source code: https://github.com/xabaras/RecyclerViewSwipeDecorator
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(
                    Color.parseColor("#e52626")
                )
                .addActionIcon(R.drawable.ic_baseline_delete_outline_24)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

    }

    //capture data that was passed back from user input and add to list
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((requestCode == REQUEST_CODE) && (resultCode == Activity.RESULT_OK)){
            if (data != null) {
                val foodWriting = data.getStringExtra("foodString")!!
                val timeLeft = data.getIntExtra("daysLeft", -1)
                val foodColor = data.getIntExtra("color", -1)
                //add to list and sort in ascending order of days until expiration
                foodList.add(FoodItem(foodWriting, foodColor, timeLeft))
                foodList.sortBy{it.numDays}
                //update UI to reflect the addition to the list
                updateRecycler()
            }
        }
    }

    //called whenever a change to the food list is made
    fun updateRecycler(){
        //create & connect adapter to recycler view
        val mAdapter = FoodListAdapter(this, foodList)
        recyclerView.adapter = mAdapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        //attaching the swipe callback to recycler view for swipe to delete functionality
        val swipeHelper = ItemTouchHelper(swipeCallback)
        swipeHelper.attachToRecyclerView(recyclerView)

        //adding the line underneath each element
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        if(foodList.isEmpty()){
            noFoodTextView.text = getString(R.string.noFoodMessage)
        }
        else{
            noFoodTextView.text = ""
        }

    }

    //resource I used to save and restore instance state of list of objects: https://bugsdb.com/_en/debug/0cdc6f1139350926d520769e261de27e
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("food list", foodList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        foodList = savedInstanceState.getParcelableArrayList<FoodItem>("food list")!!
        updateRecycler()
    }

}