# Troubleshooting

> _This page will expand as the app ships and real issues are reported. Check back for updates._

---

## Device Not Found

InkLink uses mDNS to discover CrossPoint devices on your local network. If your device doesn't appear:

**Check these first:**
- Your reader is in **Sync mode** (network menu → Sync mode)
- Your phone and your reader are on the **same WiFi network**. Different bands (2.4 GHz vs. 5 GHz) or different network segments can block mDNS.
- Your router doesn't block multicast traffic. Some routers (especially guest networks) do this by default.

**Android specific:**
- mDNS discovery can be unreliable on Android emulators. Test on a real device.
- Some Android devices aggressively restrict background network access. Make sure InkLink has network permissions and isn't being killed by battery optimization.

**iOS specific:**
- iOS 14+ requires apps to declare local network usage. InkLink will prompt you to allow local network access; tap Allow. If you denied it, go to Settings → InkLink → Local Network and enable it.

**If nothing works:**
- Try putting the reader back into Sync mode (disable and re-enable it)
- Restart your phone's WiFi (toggle it off and back on)
- Restart your router if the above doesn't help

---

## Connection Drops During Transfer

If InkLink loses the connection mid-transfer:

- The reader may have exited Sync mode. Some firmware versions have a timeout; re-enable Sync mode and retry.
- The transfer will need to be restarted from scratch. InkLink does not currently resume interrupted transfers.
- Keep your phone screen on and InkLink in the foreground during large transfers.

---

## Upload Fails

If a book, font, or wallpaper fails to upload:

- Check that your reader is still in Sync mode and appears connected in the app
- Try the upload again; transient network errors are common on WiFi
- Make sure the file is a valid format (`.epub`, `.pdf`, `.inx`)
- Check that the reader has enough free storage. See [Device Info](Device-Info.md).

---

## File Browser Shows Empty or Wrong Content

- The file browser reflects what's on the reader's filesystem at the time you open it. Pull to refresh to reload.
- If the connection dropped while the browser was open, close and reopen it after re-entering Sync mode.

---

## App Crashes or Freezes

- Force-quit and reopen the app
- If the crash is reproducible, [open an issue](https://github.com/ZephByte/InkLink/issues) with steps to reproduce, your device model, and your OS version

---

## Getting Help

If your issue isn't covered here:

- [Open a GitHub issue](https://github.com/ZephByte/InkLink/issues): include your phone model, OS version, reader model, and firmware version
- Browse or ask in the CrossPoint community; the [CrossPoint repo](https://github.com/xteink/crosspoint-reader) may have discussions relevant to your device
