# Sofascore Font ReVanced

A [ReVanced](https://revanced.app) patch that replaces **every font Sofascore
bundles** with one of your choosing from [Google Fonts](https://fonts.google.com).

## What it does

Sofascore ships nine font files inside its APK, under `res/font/`. This patch
overwrites all of them with the weights of the font you pick.

- **Patch name:** `Replace Sofascore fonts`
- **Package:** `com.sofascore.results`
- **Versions:** any — see [Future Sofascore versions](#future-sofascore-versions)
- **Bundle:** `patches-<version>.rvp`

## Choosing a font

**Set it in ReVanced Manager.** The patch has a **Font family** option. Type any
Google Fonts family name into it — `Inter`, `Roboto Slab`, `JetBrains Mono` — and
patch. The font is fetched and applied on your phone while patching, so trying a
different one means changing the option and patching again. No rebuild, no
refreshing the source.

Leave the option blank and the patch falls back to the font bundled inside it,
which is chosen in [`fonts/font.txt`](fonts/font.txt) and baked in by the
workflow. That path works offline and is reproducible.

Details, including the nine resources covered and the condensed caveat, are in
[`fonts/README.md`](fonts/README.md). The current bundled font is **Inter**.

## Future Sofascore versions

**Nothing to do when Sofascore updates.** The patch declares no version list, so
ReVanced Manager offers it for every version, including ones that do not exist
yet. It is safe to leave it that way because the patch only swaps resource files
— it does not depend on any code, class name or fingerprint, which is what
normally ties a ReVanced patch to a specific app version.

It is also written to degrade rather than lie:

- If a future version renames or drops some of its fonts, those are skipped and
  the rest are still replaced.
- If the main body font (`sofascore_sans_regular.otf`) is gone, the patch
  **fails with a clear message** instead of silently doing nothing.

Tested against **26.09.14** and **26.10.12**.

The one thing that can genuinely break is the ReVanced side, not Sofascore: if a
future ReVanced Manager moves to a newer patcher than the one this build targets,
the bundle stops loading. The fix is to bump `TEMPLATE_SHA` at the top of
`.github/workflows/build.yml` to a newer
[revanced-patches-template](https://github.com/ReVanced/revanced-patches-template)
commit and rebuild.

## Installing with ReVanced Manager (2.6.0+)

1. Open ReVanced Manager and go to **Patches**.
2. Add a new patch source and paste:

   ```
   https://raw.githubusercontent.com/KR1901/sofascore-font-revanced/main/patches.json
   ```

3. Refresh sources, select **Sofascore**, enable **Replace Sofascore fonts**, set
   the font you want, and patch as usual.

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

## Notes and limitations

- Google Fonts families have no condensed width, so the three condensed slots
  are filled with the regular width. Text that was narrow becomes full width,
  which can shift some layouts.
- Whatever font you pick is bundled into the patched app, so it has to be
  redistributable. Everything on Google Fonts is.
- Setting a **Font family** needs a network connection while patching.

## Building a release

Only needed for the bundled-font path. Go to
**Actions → Build Sofascore Font Patch → Run workflow**. The workflow builds the
`.rvp`, publishes it as a GitHub release, and rewrites `patches.json` to point at
it.

## License

GPL-3.0. See [LICENSE](LICENSE).
