@file:JvmName("ExtraBindingUtils")
package moe.feng.autofill.rikka.util

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import moe.feng.autofill.rikka.R

object ExtraBindingAdapter {

    @BindingAdapter("bind:bitmapLoader")
    @JvmStatic
    fun setBitmapLoader(imageView: ImageView, bitmapLoader: BitmapLoader) {
        imageView.task?.cancel()
        imageView.task = async(UI) {
            imageView.setImageBitmap(async(CommonPool) { bitmapLoader() }.await())
        }
    }

    @BindingAdapter("bind:drawableLoader")
    @JvmStatic
    fun setDrawableLoader(imageView: ImageView, drawableLoader: DrawableLoader) {
        imageView.task?.cancel()
        imageView.task = async(UI) {
            imageView.setImageDrawable(async(CommonPool) { drawableLoader() }.await())
        }
    }

    @BindingAdapter("bind:lazyText")
    @JvmStatic
    fun setLazyTextLoader(textView: TextView, textLoader: LazyTextLoader) {
        textView.task?.cancel()
        textView.task = async(UI) { textView.text = async(CommonPool) { textLoader() }.await() }
    }

    @BindingAdapter("bind:lazyChecked")
    @JvmStatic
    fun setLazyCheckableLoader(checkable: CheckBox, boolLoader: LazyBooleanLoader) {
        checkable.task?.cancel()
        checkable.task = async(UI) {
            checkable.isChecked = async(CommonPool) { boolLoader() }.await()
        }
    }

    private var View.task : Deferred<*>?
        get() = getTag(R.id.deferred_task) as? Deferred<*>
        set(value) { setTag(R.id.deferred_task, value) }

    interface BitmapLoader {

        operator fun invoke(): Bitmap

    }

    interface DrawableLoader {

        operator fun invoke(): Drawable

    }

    interface LazyTextLoader {

        operator fun invoke(): CharSequence

    }

    interface LazyBooleanLoader {

        operator fun invoke(): Boolean

    }

}