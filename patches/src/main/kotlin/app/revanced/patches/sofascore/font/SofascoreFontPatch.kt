package app.revanced.patches.sofascore.font

import app.revanced.patcher.patch.resourcePatch

@Suppress("unused")
val sofascoreFontPatch = resourcePatch(
    name = "Replace Sofascore fonts",
    description = "Replaces Sofascore's bundled fonts with your own. Defaults to Google Sans Code.",
) {
    compatibleWith("com.sofascore.results"("26.09.14", "26.10.12"))

    execute {
        // Resource inside the APK  ->  replacement font bundled under fonts/<name>.
        //
        // A target is skipped when no replacement is bundled for it, so you can cover
        // as many or as few of Sofascore's fonts as you like: drop a file with the
        // matching name into the repository's fonts/ folder and it takes effect.
        val targets = mapOf(
            "res/font/sofascore_sans_regular.otf" to "regular.ttf",
            "res/font/sofascore_sans_medium.otf" to "medium.ttf",
            "res/font/sofascore_sans_bold.otf" to "bold.ttf",
            "res/font/sofascore_sans_regular_condensed.otf" to "regular-condensed.ttf",
            "res/font/sofascore_sans_medium_condensed.otf" to "medium-condensed.ttf",
            "res/font/sofascore_sans_bold_condensed.otf" to "bold-condensed.ttf",
            "res/font/sofascore_mono_regular.otf" to "mono.ttf",
            "res/font/roboto_medium_numbers.ttf" to "roboto-numbers.ttf",
        )

        val classLoader = object {}.javaClass.classLoader
        var replaced = 0

        for ((resourcePath, bundledName) in targets) {
            val replacement = classLoader.getResourceAsStream("fonts/$bundledName") ?: continue

            // get() copies the file out of the APK if present, but does not fail when it
            // is absent, so check explicitly instead of silently writing a stray resource.
            val target = get(resourcePath)
            if (!target.exists()) continue

            replacement.use { input ->
                target.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            replaced++
        }

        check(replaced > 0) {
            "No fonts were replaced. Either Sofascore's font resources moved, or no " +
                "replacement fonts were bundled; this patch needs updating for this version."
        }
    }
}
