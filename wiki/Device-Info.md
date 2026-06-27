# Device Info

> _This feature is planned. This page will be updated when the device info screen ships._

---

## Overview

The Device Info screen shows you details about your connected CrossPoint reader: its name, firmware version, storage usage, and whether a firmware update is available.

---

## What's Shown

| Field | Description |
|-------|-------------|
| Device name | The friendly name set on the reader |
| Firmware version | The CrossPoint firmware version currently installed |
| Storage total | Total storage capacity of the reader |
| Storage used | How much storage is currently in use |
| Update available | Whether a newer firmware version exists |

_The exact fields may change depending on what the CrossPoint API exposes._

---

## Firmware Updates

When a newer firmware version is available, the Device Info screen will show a notification. 

> **Note:** In-app firmware flashing is on the roadmap but not in the initial release. The initial version will inform you that an update is available and direct you to the CrossPoint update instructions. OTA updates via InkLink will come later.

---

## Renaming Your Device

_Whether device renaming is supported depends on the CrossPoint API. This section will be updated once confirmed._

---

## Tips

- Your reader must be in **Sync mode** to retrieve device info. See [Getting Started](Getting-Started.md).
- Device info is fetched when you open the screen. Pull to refresh to get the latest values.
