package com.example.mycomposeapplication

import android.content.Context
import org.pytorch.IValue
import org.pytorch.LiteModuleLoader
import org.pytorch.Module
import org.pytorch.Tensor

class TextEncoder(private val context: Context) {
    private val modelPath = "clip-text-encoder.ptl"
    private var module: Module? = null

    init {
        loadModel()
    }

    private fun loadModel() {
        // loading serialized torchscript module from packaged into app android asset model.pt,
        // app/src/model/assets/model.pt
        module = LiteModuleLoader.load(assetFilePath(context, modelPath))
    }

    fun encode(input: Tensor): Tensor? {
        if (module == null) {
            loadModel()
        }
        return module?.forward(IValue.from(input))?.toTensor()
    }
}