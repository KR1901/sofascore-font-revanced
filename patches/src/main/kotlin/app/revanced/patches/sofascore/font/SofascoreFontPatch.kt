package app.revanced.patches.sofascore.font

import app.revanced.patcher.patch.resourcePatch

@Suppress("unused")
val sofascoreGoogleSansCodePatch = resourcePatch(
    name = "Use Google Sans Code for Sofascore",
    description = "Replaces Sofascore's bundled regular sans font with Google Sans Code NFP Regular.",
) {
    compatibleWith("com.sofascore.results"("26.09.14"))

    apply {
        val target = get("res/font/sofascore_sans_regular.otf")

        val replacement = requireNotNull(
            object {}.javaClass.classLoader.getResourceAsStream(
                "fonts/GoogleSansCodeNerdFontPropo-Regular.ttf",
            ),
        ) {
            "Replacement font resource is missing."
        }

        replacement.use { input ->
            target.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}
