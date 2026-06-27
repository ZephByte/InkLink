# Multiple Devices

> _This feature is planned. This page will be updated when multi-device support ships._

---

## Overview

InkLink supports managing more than one CrossPoint reader from a single app. Each device is saved separately and you can switch between them.

---

## Adding a Second Device

_Steps will be documented once the feature ships._

The planned flow:
1. Put the second reader in **Sync mode**
2. Open InkLink; it will detect the new device automatically via mDNS
3. Tap the discovered device to add it
4. Give it a name if you want (optional)

Both devices are now listed in the app.

---

## Switching Between Devices

_To be documented._

The planned flow: a device picker at the top of the main screen (or in a sidebar) lets you switch the active device. All actions (sending books, browsing files, pushing wallpapers) apply to whichever device is selected.

---

## Device Discovery With Multiple Devices

InkLink discovers devices via mDNS. If multiple CrossPoint readers are in Sync mode on the same network at the same time, they will all appear in the device list. Each one is identified by its device name and IP address.

---

## Removing a Device

_To be documented once confirmed._ Removing a device from InkLink deletes the saved connection info from the app. It does not affect the device itself.

---

## Tips

- You can only actively manage one device at a time, but switching is instant as long as both readers are in Sync mode.
- Each device's file browser, fonts, and settings are fully independent.
