package id.dipoengoro.githubuser.adapter

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import id.dipoengoro.githubuser.model.ListUser

@BindingAdapter("listData")
fun bindingRecyclerView(recyclerView: RecyclerView, data: ListUser?) =
    data?.let {
        (recyclerView.adapter as ListUserAdapter).submitList(it.users)
    }

@SuppressLint("DiscouragedApi")
@BindingAdapter("setImg")
fun bindingCircleImageView(circleImageView: CircleImageView, data: String?) =
    data?.let {
        val context = circleImageView.context
        val resId = context.resources.getIdentifier(it, "drawable", context.packageName)
        circleImageView.setImageDrawable(ContextCompat.getDrawable(context, resId))
    }

@BindingAdapter("intAdapter")
fun bindingTextView(materialTextView: MaterialTextView, data: Int?) =
    data?.let {
        val total = data.toString()
        val length = total.length

        materialTextView.text = if (length > 4) {
            val topThree = total.subSequence(0..2).toString()
            val perInt = (topThree.toInt() / 10.0).toString()
            "${perInt}K"
        } else if (length > 3) {
            val topThree = total.subSequence(0..2).toString()
            val perInt = (topThree.toInt() / 100.0).toString()
            "${perInt}K"
        } else {
            total
        }
    }

@BindingAdapter("nameAdapter")
fun bindingNameText(materialTextView: MaterialTextView, data: String?) =
    data?.let {
        materialTextView.text = formatName(data)
    }

fun formatName(string: String): String {
    val name = string.split(" ")
    var finalName = ""
    name.forEach {
        finalName += it.lowercase()
            .replaceFirstChar { firstChar -> firstChar.uppercase() } + " "
    }
    return finalName.trim()
}

