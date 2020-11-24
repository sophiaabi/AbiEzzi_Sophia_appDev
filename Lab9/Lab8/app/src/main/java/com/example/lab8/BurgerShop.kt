package com.example.lab8

data class BurgerShop(var name:String = "", var url:String = "") {
    fun suggestBurgerStore(position:Int){
        setBurgerStoreName(position)
        setBurgerStoreURL(position)
    }

    private fun setBurgerStoreURL(position:Int) {
        when(position){
            0 -> url = "https://www.google.com/search?q=burger+restaurants+in+boulder+co&rlz=1C5CHFA_enUS760US761&oq=burger+restaurants+in+boulder+co&aqs=chrome..69i57j0i457j0l6.6290j0j7&sourceid=chrome&ie=UTF-8"
            1 -> url = "https://goodtimesburgers.com/"
            2 -> url = "https://www.thesink.com/"
            3 -> url = "http://saltthebistro.com/"
        }

    }

    private fun setBurgerStoreName(position:Int) {
        when(position){
            0 -> name = "choose your own burger shop"
            1 -> name = "Good Times Burgers & Fries"
            2 -> name = "The Sink"
            3 -> name = "SALT Bistro"
        }

    }


}