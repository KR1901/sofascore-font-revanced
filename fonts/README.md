# Fonts

The patch replaces **all nine** fonts Sofascore bundles. There are two ways to
choose the replacement, and the first one needs no rebuild at all.

## 1. Set it in ReVanced Manager (no rebuild)

The patch has a **Font family** option. Type a
[Google Fonts](https://fonts.google.com) family name into it — `Inter`,
`Roboto Slab`, `JetBrains Mono`, whatever — and patch. The font is downloaded
and applied on your phone while patching.

To try a different font, change the option and patch again. No workflow run, no
refreshing the source, no rebuild.

This needs a network connection while patching.

### Getting to the option

1. Enable **Settings → Advanced → Safeguards → "Allow changing patch selection
   and options"** and accept the warning. Manager blocks option editing by
   default; without this the screen opens read-only and the field takes no input.
2. Select the app first — pick Sofascore under **Apps**.
3. Find **Replace Sofascore fonts** under the **Sofascore Font ReVanced** bundle
   and tap the small **gear icon** on its row.
4. Type the family name into **Font family**.

If typing does nothing, tick the **checkbox to the left of "Font family"** — that
is what enables the field.

Note that the options live in the patch selector. **"Applied patches"** is a
separate, read-only screen that only lists what is already applied.

## 2. Bundle it from this repository (fallback)

Leave the **Font family** option blank and the patch falls back to the font
bundled inside it, which is chosen here:

1. Put the family name — exactly as it reads on fonts.google.com — in
   [`font.txt`](font.txt).
2. Run **Actions → Build Sofascore Font Patch → Run workflow**.
3. Refresh the source in Manager.

You can also type the family name into the workflow's `font` box instead of
editing the file; the build writes the name it used back into `font.txt`.

Use this if you want a build that works offline, or that is reproducible.

The current bundled font is **Inter**.

## What gets replaced

Every font resource in Sofascore's APK, by weight:

| APK resource                                   | Weight |
| ---------------------------------------------- | ------ |
| `res/font/sofascore_sans_regular.otf`           | 400    |
| `res/font/sofascore_sans_medium.otf`            | 500    |
| `res/font/sofascore_sans_bold.otf`              | 700    |
| `res/font/sofascore_sans_regular_condensed.otf` | 400    |
| `res/font/sofascore_sans_medium_condensed.otf`  | 500    |
| `res/font/sofascore_sans_bold_condensed.otf`    | 700    |
| `res/font/sofascore_mono_regular.otf`           | 400    |
| `res/font/roboto_medium_numbers.ttf`            | 500    |
| `res/font/optimistic_vf.ttf`                    | 400    |

If the family does not have a weight, the nearest one it does have is used — a
single-weight font such as Lobster simply uses its one weight everywhere.

## Notes

- **Condensed text stops being narrow.** Google Fonts families are not
  condensed, so the three condensed slots get the regular width. Text that was
  squeezed now takes full width, which can shift some layouts.
- **Variable-only families** come back as a single file. Android renders it at
  its default instance, so all nine slots get that one weight.
- **Prefer static files if you supply your own** (below).

## Overriding by hand

Anything in this folder ending in `.ttf` or `.otf` overrides the bundled font of
the same name, so you can replace a single slot without changing the family for
the rest. Valid names: `regular`, `medium`, `bold`, `regular-condensed`,
`medium-condensed`, `bold-condensed`, `mono`, `roboto-numbers`, `optimistic`
(e.g. `bold.ttf`). The extension on disk does not matter — Android reads the
file's contents, not its name.

These only apply when the **Font family** option is left blank.

## Licensing

Whatever font you choose gets bundled into the patched app. Check its licence
allows redistribution — everything on Google Fonts does, most under the SIL Open
Font License. `OFL.txt` in this folder covers the Google Sans Code default that
was previously used.
