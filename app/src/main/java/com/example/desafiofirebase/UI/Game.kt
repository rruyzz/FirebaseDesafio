package com.example.desafiofirebase.UI

import java.io.Serializable

data class Game(
    var name: String = "",
    var data: String = "",
    var description: String = "",
    var image: String = "",
    var id: String = ""
) : Serializable