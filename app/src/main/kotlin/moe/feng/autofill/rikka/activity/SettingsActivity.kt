package moe.feng.autofill.rikka.activity

import android.os.Bundle
import moe.feng.autofill.rikka.R
import moe.feng.autofill.rikka.databinding.ActivitySettingsBinding
import moe.feng.common.arch.BindingActivity

class SettingsActivity : BindingActivity<ActivitySettingsBinding>() {

    override val defaultLayoutId: Int = R.layout.activity_settings

    private val viewModel = SettingsViewModel()

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        actionBar.setDisplayHomeAsUpEnabled(true)

        viewModel.loadList(this)
    }

}