package moe.feng.autofill.rikka.adapter

import moe.feng.autofill.rikka.BR
import moe.feng.common.arch.list.BindingRecyclerViewAdapter
import moe.feng.common.arch.list.bind

class EnabledAppListAdapter : BindingRecyclerViewAdapter(BR.item) {

    init {
        bind(AppInfoBinder())
    }

    companion object {

        @JvmStatic fun newInstance() = EnabledAppListAdapter()

    }

}