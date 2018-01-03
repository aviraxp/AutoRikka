package moe.feng.autofill.rikka.service

import android.app.assist.AssistStructure
import android.os.CancellationSignal
import android.service.autofill.*
import android.util.Log
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import moe.feng.autofill.rikka.getRandomRikkaWord
import moe.feng.autofill.rikka.util.Settings
import moe.feng.common.kt.TAG

class AutoRikkaService : AutofillService() {

    override fun onFillRequest(
            request: FillRequest?,
            cancellationSignal: CancellationSignal?,
            callback: FillCallback?
    ) {
        val structure = if (request?.fillContexts?.lastOrNull()?.structure == null) {
            callback?.onFailure("No structure")
            return
        } else {
            request.fillContexts?.lastOrNull()?.structure!!
        }

        val datasetBuilder = Dataset.Builder()
        var hasData = false

        val settingsInstance = Settings.getInstance(this)
        structure.forEach {
            if (settingsInstance.isAppEnabled(it.title.split("/").firstOrNull())) {
                it.rootViewNode.flatNodes { node ->
                    if (node.className == "android.widget.EditText") {
                        hasData = true
                        getRandomRikkaWord().let { word ->
                            datasetBuilder.setValue(
                                    node.autofillId,
                                    AutofillValue.forText(word),
                                    newItemView(word)
                            )
                        }
                    }
                }
            }
        }

        if (hasData) {
            callback?.onSuccess(FillResponse.Builder().addDataset(datasetBuilder.build()).build())
        }
    }

    private fun AssistStructure.forEach(block: (AssistStructure.WindowNode) -> Unit) {
        (0 until this.windowNodeCount).map(this::getWindowNodeAt).forEach(block)
    }

    private fun AssistStructure.ViewNode.flatNodes(block: (AssistStructure.ViewNode) -> Unit) {
        block(this)
        (0 until this.childCount).map(this::getChildAt).forEach { it.flatNodes(block) }
    }

    private fun newItemView(title: String) =
            RemoteViews(packageName, android.R.layout.simple_list_item_1).apply {
                setTextViewText(android.R.id.text1, title)
            }

    private fun debug(message: String) = Log.d(TAG, message)

    override fun onSaveRequest(request: SaveRequest?, callback: SaveCallback?) {

    }

}