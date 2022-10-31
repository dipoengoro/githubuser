package id.dipoengoro.githubuser.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import id.dipoengoro.githubuser.model.ListUser
import id.dipoengoro.githubuser.model.User
import java.text.NumberFormat

@BindingAdapter("listData")
fun bindingRecyclerView(recyclerView: RecyclerView, data: ListUser?) = data?.let {
    (recyclerView.adapter as ListUserAdapter).submitList(it.users)
}

@SuppressLint("DiscouragedApi")
@BindingAdapter("setImg")
fun bindingCircleImageView(circleImageView: CircleImageView, data: String?) = data?.let {
    val context = circleImageView.context
    val resId = context.resources.getIdentifier(it, "drawable", context.packageName)
    circleImageView.setImageDrawable(ContextCompat.getDrawable(context, resId))
}

@BindingAdapter("intAdapter")
fun bindingTextView(materialTextView: MaterialTextView, data: Int?) = data?.let {
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
fun bindingNameText(materialTextView: MaterialTextView, data: String?) = data?.let {
    materialTextView.text = formatName(data)
}

@BindingAdapter("detailUser")
fun bindingDetailUser(materialTextView: MaterialTextView, data: User?) = data?.let {
    materialTextView.text = formatDetailUser(it)
}

fun formatName(string: String): String {
    val name = string.split(" ")
    var finalName = ""
    name.forEach {
        finalName += it.lowercase().replaceFirstChar { firstChar -> firstChar.uppercase() } + " "
    }
    return finalName.trim()
}

fun formatDetailUser(user: User): String = """
        Username:   ${user.username}
        Name:       ${user.name}
        Company:    ${user.company}
        Location:   ${user.location}
        Repository: ${formatNumber(user.repository)}
        Follower:   ${formatNumber(user.follower)}
        Following:  ${formatNumber(user.following)}
    """.trimIndent()

fun formatNumber(int: Int): String =
    NumberFormat.getIntegerInstance().format(int)


inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

