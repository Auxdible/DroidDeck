package com.droiddeck.launcher.gpu

import com.droiddeck.launcher.core.FileUtils
import java.io.File

object Xclipse {
    private val MODELS = mapOf(
        "s5e9925" to 920, "s5e9935" to 920, "s5e8845" to 530, "s5e9945" to 940,
        "s5e8855" to 540, "s5e9955" to 950,
    )

    // GPUs the RADV Xclipse port (github.com/JimVulkan/radv-xclipse) runs on.
    val DRIVER_MODELS = setOf(530, 920, 940)

    fun model(soc: String): Int = MODELS[soc.trim().lowercase()] ?: 0

    fun isKnownSoc(soc: String): Boolean = MODELS.containsKey(soc.trim().lowercase())

    @JvmStatic @JvmOverloads
    fun detect(soc: String = SocNames.model()): Boolean = isKnownSoc(soc) || renderNode() != null

    @JvmStatic
    fun renderNode(): File? {
        val nodes = File("/dev/dri").listFiles { f -> f.name.startsWith("renderD") }?.sortedBy { it.name }.orEmpty()
        return nodes.firstOrNull { driverName(it.name)?.contains("sgpu") == true }
            ?: File("/dev/dri/renderD128").takeIf { it.exists() && isKnownSoc(SocNames.model()) }
    }

    fun driverName(node: String): String? {
        val uevent = FileUtils.readString(File("/sys/class/drm/$node/device/uevent")) ?: return null
        return uevent.lineSequence().firstOrNull { it.startsWith("DRIVER=") }?.removePrefix("DRIVER=")?.trim()
    }
}
