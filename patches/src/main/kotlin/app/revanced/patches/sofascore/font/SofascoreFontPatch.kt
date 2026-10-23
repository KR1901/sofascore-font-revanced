package app.revanced.patches.sofascore.font

import app.revanced.patcher.patch.resourcePatch

@Suppress("unused")
val sofascoreGoogleSansCodePatch = resourcePatch(
    name = "Use Google Sans Code for Sofascore",
    description = "Replaces Sofascore's bundled regular sans font with Google Sans Code Regular.",
) {
    compatibleWith("com.sofascore.results"("26.09.14", "26.10.12"))

    execute {
        val fontPath = "res/font/sofascore_sans_regular.otf"

        // get() copies the file out of the APK if present, but does not fail when it
        // is absent, so check explicitly instead of silently writing a stray resource.
        val target = get(fontPath)

        check(target.exists()) {
            "Could not find '$fontPath' in the APK. Sofascore may have renamed or " +
                "removed its bundled font; this patch needs updating for this app version."
        }

        val replacement = requireNotNull(
            object {}.javaClass.classLoader.getResourceAsStream(
                "fonts/GoogleSansCode-Regular.ttf",
            )
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
