# Fonts

The patch replaces **all nine** fonts Sofascore bundles. You choose one font from
Google Fonts and the build fetches it, generates the weights Sofascore needs, and
drops them into every slot.

## Choosing the font

Put the family name — exactly as it appears on [fonts.google.com](https://fonts.google.com)
— in [`font.txt`](font.txt):

```
Inter
```

Then run **Actions → Build Sofascore Font Patch → Run workflow**. You can also
type the family name into the workflow's `font` box to try one out without
editing this file; the build writes the name it used back into `font.txt`.

That is the whole process. `Google Sans Code` is the current setting.

## What gets replaced

Every font resource in Sofascore's APK, by weight:

| APK resource                                   | Built from |
| ---------------------------------------------- | ---------- |
| `res/font/sofascore_sans_regular.otf`           | weight 400 |
| `res/font/sofascore_sans_medium.otf`            | weight 500 |
| `res/font/sofascore_sans_bold.otf`              | weight 700 |
| `res/font/sofascore_sans_regular_condensed.otf` | weight 400 |
| `res/font/sofascore_sans_medium_condensed.otf`  | weight 500 |
| `res/font/sofascore_sans_bold_condensed.otf`    | weight 700 |
| `res/font/sofascore_mono_regular.otf`           | weight 400 |
| `res/font/roboto_medium_numbers.ttf`            | weight 500 |
| `res/font/optimistic_vf.ttf`                    | weight 400 |

If the family you pick does not have a weight, the nearest one it does have is
used and the build log says so — a single-weight font such as Lobster simply
uses its one weight everywhere.

## Notes

- **Condensed text stops being narrow.** Google Fonts families are not
  condensed, so the three condensed slots get the regular width. Text that was
  squeezed now takes full width, which can shift some layouts.
- **Variable fonts** are handled: if Google returns one variable file rather
  than per-weight files, it is pinned to each weight automatically.
- **Prefer static files if you supply your own** (below). A variable font will
  otherwise render at its default instance.

## Overriding by hand

Anything in this folder ending in `.ttf` or `.otf` overrides the generated font
of the same name, so you can replace a single slot without changing the family
for the rest. Valid names: `regular`, `medium`, `bold`, `regular-condensed`,
`medium-condensed`, `bold-condensed`, `mono`, `roboto-numbers`, `optimistic`
(e.g. `bold.ttf`). The extension on disk does not matter — Android reads the
file's contents, not its name.

## Licensing

Whatever font you choose gets bundled into the patch and into the patched app.
Check its licence allows redistribution — everything on Google Fonts does, most
under the SIL Open Font License. The previous default, Google Sans Code, is
under the OFL; see [`OFL.txt`](OFL.txt) in this folder.
