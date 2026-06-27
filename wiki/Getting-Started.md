# Getting Started

> _This page will be completed once the app is available for installation. The steps below describe the planned flow._

---

## Requirements

- An iPhone or Android phone
- A device running [CrossPoint firmware](https://github.com/xteink/crosspoint-reader) (e.g., Xteink X4)
- Both your phone and your reader on the **same WiFi network**

---

## Installing InkLink

_Not yet available. Links will be added once the app is submitted to the App Store and Google Play._

| Platform | Install |
|----------|---------|
| Android | Google Play (coming soon) |
| iOS | App Store (coming soon) |

---

## Putting Your Reader in Sync Mode

InkLink connects to your reader over WiFi. The reader needs to be in **Sync mode** before the app can find it.

On your CrossPoint device:
1. Open the **network menu** on the reader
2. Select **Sync mode** (or the equivalent option in your firmware version)
3. The reader will show a network address or a "syncing" indicator

Your reader must stay awake and in Sync mode while you use InkLink.

---

## Connecting Your Device

Once Sync mode is active:

1. Open InkLink on your phone
2. InkLink will automatically scan for CrossPoint devices on your network
3. Your device should appear within a few seconds. Tap it to connect.
4. The app saves the device so you don't need to re-pair on future sessions

> **Tip:** If your device doesn't appear, make sure your phone and reader are on the same WiFi network. mDNS discovery (how InkLink finds your reader) doesn't work across different network segments.

---

## What's Next

Once connected, you can:
- [Send books](Sending-Books.md) to your reader
- [Browse and manage files](Managing-Files.md)
- [Upload fonts](Fonts.md)
- [Push a wallpaper](Wallpapers.md)
- [Check device info and firmware version](Device-Info.md)
