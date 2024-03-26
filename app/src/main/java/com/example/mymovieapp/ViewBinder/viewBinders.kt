package com.example.mymovieapp.viewBinder

import android.media.Image
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage

@BindingAdapter("srcUrl")
fun ImageView.bindSrcUrl(url: String?){
  Glide
      .with(this)
      .load(url)
      .fitCenter()
      .into(this)
}
@BindingAdapter("carouselUrl")
fun ImageCarousel.imageList(imageList: List<CarouselItem>?){
    imageList?.let {
        this.setData(it)
    }
}

