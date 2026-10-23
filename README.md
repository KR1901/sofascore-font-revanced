# Sofascore Font ReVanced

A [ReVanced](https://revanced.app) patch that replaces Sofascore's bundled sans font with
[Google Sans Code](https://fonts.google.com/specimen/Google+Sans+Code) Regular.

## What it does

Sofascore ships its UI font inside the APK as `res/font/sofascore_sans_regular.otf`.
This patch overwrites that resource with Google Sans Code Regular, so the app renders
its text in that font.

- **Patch name:** `Use Google Sans Code for Sofascore`
- **Package:** `com.sofascore.results`
- **Compatible versions:** `26.09.14`, `26.10.12`
- **Bundle:** `patches-<version>.rvp`

## Installing with ReVanced Manager (2.6.0+)

1. Open ReVanced Manager and go to **Patches**.
2. Add a new patch source and paste:

   ```
   https://raw.githubusercontent.com/KR1901/sofascore-font-revanced/main/patches.json
   ```

3. Refresh sources, select **Sofascore**, enable **Use Google Sans Code for Sofascore**
   and patch as usual.

> ReVanced Manager does not officially support third-party patch sources. If your build
> refuses to load the source, try a manager that allows custom bundles (for example URV
> Manager) or load the `.rvp` from the release page manually.

## Building a release

The bundle is built in CI. Go to **Actions → Build Sofascore Font Patch → Run workflow**.
The workflow builds the `.rvp`, publishes it as a GitHub release, and rewrites
`patches.json` to point at the new release.

## Notes and limitations

- The patch targets a fixed resource path (`res/font/sofascore_sans_regular.otf`).
  If a Sofascore update renames or removes that font, the patch fails and the version
  list in the patch has to be updated. This is why the compatible versions are pinned.
- The font is fetched at build time from the pinned upstream Google Sans Code release
  and pinned to its Regular instance (wght 400, monospace). Google Sans Code is licensed
  under the SIL Open Font License 1.1 — see `fonts/OFL.txt`.

## License

GPL-3.0. See [LICENSE](LICENSE).
