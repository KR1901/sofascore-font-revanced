# Custom fonts

Drop font files in **this folder** to change what the patch installs. The build
copies any `.ttf` or `.otf` here into the patch bundle, overriding the built-in
defaults by filename. No code change is needed — add the file, re-run the
workflow, and refresh the source in ReVanced Manager.

## Which file replaces what

Each name below maps to one of Sofascore's own font resources. Only the first
three exist by default; the rest are applied only if you provide them.

| File to add here         | Replaces in the app                             | Default |
| ------------------------ | ----------------------------------------------- | ------- |
| `regular.ttf`            | `res/font/sofascore_sans_regular.otf`            | yes     |
| `medium.ttf`             | `res/font/sofascore_sans_medium.otf`             | yes     |
| `bold.ttf`               | `res/font/sofascore_sans_bold.otf`               | yes     |
| `regular-condensed.ttf`  | `res/font/sofascore_sans_regular_condensed.otf`  | no      |
| `medium-condensed.ttf`   | `res/font/sofascore_sans_medium_condensed.otf`   | no      |
| `bold-condensed.ttf`     | `res/font/sofascore_sans_bold_condensed.otf`     | no      |
| `mono.ttf`               | `res/font/sofascore_mono_regular.otf`            | no      |
| `roboto-numbers.ttf`     | `res/font/roboto_medium_numbers.ttf`             | no      |

The extension does not matter — Android reads the file's contents, not its name,
so a `.ttf` dropped in as `regular.ttf` is fine even though the app's resource
ends in `.otf`.

## Using a different font

1. Get the font you want, ideally one file per weight (Regular, Medium, Bold).
2. Rename them to `regular.ttf`, `medium.ttf`, `bold.ttf` and add them here.
3. Re-run **Actions → Build Sofascore Font Patch → Run workflow**.

Prefer static (non-variable) `.ttf`/`.otf` files. A variable font will render at
whatever its default instance is.

## Coverage

Sofascore uses several fonts. Anything you do not override keeps the original:

- `regular` / `medium` / `bold` cover the bulk of the interface.
- The **condensed** variants are used where space is tight. Google Sans Code has
  no condensed width, so they are left alone by default. If you would rather
  everything match, add `regular.ttf` copies under the condensed names too —
  the text will no longer be narrow, which can shift layouts.
- `mono.ttf` and `roboto-numbers.ttf` are used for scores and tables.

## Licensing

Whatever font you add here gets bundled into the patch and into the patched app.
Make sure its licence allows redistribution. The default font (Google Sans Code)
is under the SIL Open Font License 1.1 — see `OFL.txt` in this folder.
