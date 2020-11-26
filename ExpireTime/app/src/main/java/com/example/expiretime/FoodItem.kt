package com.example.expiretime
import android.os.Parcel
import android.os.Parcelable

//class that holds data pertaining to each food element
//had to make this class extend Parcelable to save the instance state of the list of objects- tutorial here: https://bugsdb.com/_en/debug/0cdc6f1139350926d520769e261de27e
class FoodItem : Parcelable {
    //string that will be displayed in each list element
    var foodText:String
    //color of the list element text
    var textColor:Int
    //holding number of days until expiration for sorting purposes
    var numDays:Int

    constructor(foodText:String, textColor:Int, numDays:Int) {
        this.foodText = foodText
        this.textColor = textColor
        this.numDays = numDays
    }

    private constructor(inside : Parcel) {
        foodText = inside.readString()!!
        textColor = inside.readInt()
        numDays = inside.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$foodText:$textColor:$numDays"
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(foodText)
        out.writeInt(textColor)
        out.writeInt(numDays)
    }

    companion object {
        val CREATOR: Parcelable.Creator<FoodItem> = object : Parcelable.Creator<FoodItem> {
            override fun createFromParcel(`in`: Parcel): FoodItem? {
                return FoodItem(`in`)
            }

            override fun newArray(size: Int): Array<FoodItem?> {
                return arrayOfNulls(size)
            }
        }
    }
}