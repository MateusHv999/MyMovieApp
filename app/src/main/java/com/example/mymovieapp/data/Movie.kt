    package com.example.mymovieapp.data

    import androidx.room.Entity
    import androidx.room.PrimaryKey
    import com.example.mymovieapp.api.Credentials
    import com.squareup.moshi.JsonClass

    @Entity
    @JsonClass(generateAdapter = true)
    class Movie () {
        @PrimaryKey
        var id: Int? = null
        var overview: String? = null
        var title: String? = null
        var poster_path: String? = null
        fun getContent(): String {
            val overview = overview
            return when {
                overview?.isNotEmpty() == true -> overview
                else -> "Conteudo nao disponivel"
            }
        }
        fun getMovieTitle(): String {
            val title = title
            return when {
                title?.isNotEmpty() == true -> title
                else -> "titulo nao disponivel"
            }
        }
        fun getImage() : String? = Credentials.imgUrl + poster_path
    }
