package app.revanced.patches.sofascore.font

import app.revanced.patcher.patch.ResourcePatchContext
import app.revanced.patcher.patch.resourcePatch
import app.revanced.patcher.patch.stringOption
import java.net.URL
import java.net.URLEncoder
import kotlin.math.abs

/**
 * Every font resource Sofascore ships, the weight we want for it, and the name of the
 * replacement bundled inside the patch (used when no family name is given).
 */
private class FontTarget(
    val resource: String,
    val weight: Int,
    val bundled: String,
)

private val FONT_TARGETS = listOf(
    FontTarget("res/font/sofascore_sans_regular.otf", 400, "regular.ttf"),
    FontTarget("res/font/sofascore_sans_medium.otf", 500, "medium.ttf"),
    FontTarget("res/font/sofascore_sans_bold.otf", 700, "bold.ttf"),
    FontTarget("res/font/sofascore_sans_regular_condensed.otf", 400, "regular-condensed.ttf"),
    FontTarget("res/font/sofascore_sans_medium_condensed.otf", 500, "medium-condensed.ttf"),
    FontTarget("res/font/sofascore_sans_bold_condensed.otf", 700, "bold-condensed.ttf"),
    FontTarget("res/font/sofascore_mono_regular.otf", 400, "mono.ttf"),
    FontTarget("res/font/roboto_medium_numbers.ttf", 500, "roboto-numbers.ttf"),
    FontTarget("res/font/optimistic_vf.ttf", 400, "optimistic.ttf"),
)

/**
 * Google Fonts serves TrueType rather than woff2 to clients that do not advertise
 * woff2 support, which is what we want: Android cannot load woff2.
 */
private const val USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64)"

/** One @font-face per weight, or a single range for variable families. */
private val FONT_FACE = Regex(
    """font-weight:\s*(\d+)(?:\s+(\d+))?;\s*src:\s*url\((https://[^)]+)\)""",
)

@Suppress("unused")
val sofascoreFontPatch = resourcePatch(
    name = "Replace Sofascore fonts",
    description = "Replaces every font Sofascore bundles with a font from Google Fonts.",
) {
    compatibleWith("com.sofascore.results"("26.09.14", "26.10.12"))

    val fontFamily = stringOption(
        name = "Font family",
        default = "",
        description = "A Google Fonts family name, for example Inter or JetBrains Mono. " +
            "It is downloaded while patching, so no rebuild is needed. " +
            "Leave blank to use the font bundled with this patch.",
        required = false,
    )

    execute {
        val family = fontFamily.value.orEmpty().trim()
        var replaced = 0

        if (family.isEmpty()) {
            val classLoader = object {}.javaClass.classLoader

            for (target in FONT_TARGETS) {
                val bytes = classLoader
                    .getResourceAsStream("fonts/${target.bundled}")
                    ?.use { it.readBytes() }
                    ?: continue

                if (replaceFont(target.resource, bytes)) replaced++
            }
        } else {
            val fonts = try {
                downloadFonts(family)
            } catch (exception: Exception) {
                throw IllegalStateException(
                    "Could not fetch '$family' from Google Fonts. Check that the family " +
                        "name matches fonts.google.com exactly and that you are online.",
                    exception,
                )
            }

            val available = fonts.keys.sorted()

            for (target in FONT_TARGETS) {
                val bytes = fonts[nearestWeight(available, target.weight)] ?: continue

                if (replaceFont(target.resource, bytes)) replaced++
            }
        }

        check(replaced > 0) {
            "No fonts were replaced. Either Sofascore's font resources moved, or no " +
                "replacement fonts were available; this patch needs updating for this version."
        }
    }
}

private fun ResourcePatchContext.replaceFont(resourcePath: String, bytes: ByteArray): Boolean {
    // get() copies the file out of the APK if present, but does not fail when it is
    // absent, so check explicitly instead of silently writing a stray resource.
    val file = get(resourcePath)
    if (!file.exists()) return false

    file.outputStream().use { it.write(bytes) }

    return true
}

/**
 * Ask Google Fonts for the weights Sofascore uses and return what it gives back,
 * keyed by weight.
 */
private fun downloadFonts(family: String): Map<Int, ByteArray> {
    val cssUrl = "https://fonts.googleapis.com/css2?family=" +
        URLEncoder.encode(family, "UTF-8") +
        ":wght@400;500;700"

    val css = String(fetch(cssUrl), Charsets.UTF_8)

    val urls = mutableMapOf<Int, String>()
    var variableUrl: String? = null

    for (match in FONT_FACE.findAll(css)) {
        val low = match.groupValues[1].toInt()
        val high = match.groupValues[2]
        val url = match.groupValues[3]

        if (high.isEmpty()) {
            urls.putIfAbsent(low, url)
        } else if (variableUrl == null) {
            variableUrl = url
        }
    }

    // A family that only exists as a variable font comes back as one file. Android
    // renders it at its default instance, so every slot gets that same file.
    if (urls.isEmpty() && variableUrl != null) {
        return mapOf(400 to fetch(variableUrl!!))
    }

    check(urls.isNotEmpty()) {
        "Google Fonts returned no font files for '$family'."
    }

    return urls.mapValues { (_, url) -> fetch(url) }
}

private fun fetch(url: String): ByteArray {
    val connection = URL(url).openConnection()

    connection.setRequestProperty("User-Agent", USER_AGENT)
    connection.connectTimeout = 30_000
    connection.readTimeout = 60_000

    return connection.getInputStream().use { it.readBytes() }
}

/** The weight a family actually offers that is closest to the one we asked for. */
private fun nearestWeight(available: List<Int>, wanted: Int): Int =
    available.minByOrNull { abs(it - wanted) } ?: wanted
