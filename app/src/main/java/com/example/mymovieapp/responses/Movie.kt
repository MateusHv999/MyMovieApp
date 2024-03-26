    package com.example.mymovieapp.responses

    import android.provider.ContactsContract.CommonDataKinds.Im
    import com.example.mymovieapp.Credentials
    import com.squareup.moshi.JsonClass
    import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

    @JsonClass(generateAdapter = true)
    data class Movie (
        val id: Int?,
        val overview: String?,
        val title: String?,
        val poster_path: String?,
        val backdrops: String?,
        val thumbnail: Image?,
        val images: List<Image>?,
    ) {
        fun getContent(): String {
            return when {
                overview?.isNotEmpty() == true -> overview
                else -> "Conteudo nao disponivel"
            }
        }
        fun getMovieTitle(): String {
            return when {
                title?.isNotEmpty() == true -> title
                else -> "titulo nao disponivel"
            }
        }
        fun getImage() = Credentials.imgUrl + poster_path
    }
