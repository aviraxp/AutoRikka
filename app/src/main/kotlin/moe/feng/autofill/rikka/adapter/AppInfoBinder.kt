package moe.feng.autofill.rikka.adapter

import android.content.pm.ApplicationInfo
import moe.feng.autofill.rikka.R
import moe.feng.autofill.rikka.databinding.ItemAppInfoBinding
import moe.feng.autofill.rikka.util.Settings
import moe.feng.common.arch.list.BindingItemBinder
import moe.feng.common.arch.list.IBindingViewHolder

class AppInfoBinder
    : BindingItemBinder<ApplicationInfo, ItemAppInfoBinding>(R.layout.item_app_info) {

    override fun onViewHolderCreated(holder: IBindingViewHolder<ApplicationInfo, ItemAppInfoBinding>) {
        holder.binding.root.setOnClickListener { holder.binding.checkbox.performClick() }
        holder.binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            Settings.getInstance().setAppEnabled(holder.currentItem!!.packageName, isChecked)
        }
    }

}
