# Sofascore Font ReVanced

A [ReVanced](https://revanced.app) patch that replaces Sofascore's bundled fonts
with your own. It defaults to [Google Sans Code](https://fonts.google.com/specimen/Google+Sans+Code).

## What it does

Sofascore ships its UI fonts inside the APK under `res/font/`. This patch
overwrites them with replacement fonts bundled in the patch.

- **Patch name:** `Replace Sofascore fonts`
- **Package:** `com.sofascore.results`
- **Compatible versions:** `26.09.14`, `26.10.12`
- **Bundle:** `patches-<version>.rvp`

By default it replaces the three sans weights Sofascore uses — regular, medium
and bold. The condensed, mono and numbers fonts are left alone unless you supply
replacements; see [Changing the font](#changing-the-font).

## Changing the font

The font lives in the [`fonts/`](fonts) folder of this repository. Drop a file
in, re-run the workflow, done — no code change:

| File                 | Replaces                           | Default |
| -------------------- | ---------------------------------- | ------- |
| `regular.ttf`        | `sofascore_sans_regular.otf`        | yes     |
| `medium.ttf`         | `sofascore_sans_medium.otf`         | yes     |
| `bold.ttf`           | `sofascore_sans_bold.otf`           | yes     |
| `mono.ttf`           | `sofascore_mono_regular.otf`        | no      |
| `roboto-numbers.ttf` | `roboto_medium_numbers.ttf`         | no      |
| `*-condensed.ttf`    | the three condensed variants        | no      |

Full details, including the condensed caveat, are in [`fonts/README.md`](fonts/README.md).

## Installing with ReVanced Manager (2.6.0+)

1. Open ReVanced Manager and go to **Patches**.
2. Add a new patch source and paste:

   ```
   https://raw.githubusercontent.com/KR1901/sofascore-font-revanced/main/patches.json
   ```

3. Refresh sources, select **Sofascore**, enable **Replace Sofascore fonts** and
   patch as usual.

> ReVanced Manager does not officially support third-party patch sources. If your
> build refuses to load the source, try a manager that allows custom bundles (for
> example URV Manager) or load the `.rvp` from the release page manually.

### Patch a full APK, not a split

Sofascore is distributed as an App Bundle. If you feed Manager a **base APK**
from a split bundle, the result cannot be installed — Android rejects a base
without its `base__abi` / `base__density` splits, and the message it shows is the
misleading "app isn't compatible with your phone". Merge the splits into a
single APK first (Anti-Split M on device, or APKEditor on desktop), then patch
that.

## Building a release

Go to **Actions → Build Sofascore Font Patch → Run workflow**. The workflow
builds the `.rvp`, publishes it as a GitHub release, and rewrites `patches.json`
to point at it.

## Notes and limitations

- The patch targets fixed resource paths. If a Sofascore update renames its
  fonts, the patch skips the missing ones, and the version list has to be
  updated.
- The default font is fetched at build time from the pinned upstream Google Sans
  Code release and pinned to the weights Sofascore uses. Google Sans Code is
  licensed under the SIL Open Font License 1.1 — see [`fonts/OFL.txt`](fonts/OFL.txt).

## License

GPL-3.0. See [LICENSE](LICENSE).
