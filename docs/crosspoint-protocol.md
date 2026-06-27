# CrossPoint Protocol Notes

Reference for contributors building the networking and sync layers. Where something is unknown, a TODO marks it.

The CrossPoint firmware is open-source; the authoritative source is the [CrossPoint firmware repo](https://github.com/xteink/crosspoint-reader).

---

## Connection Overview

InkLink connects to CrossPoint devices over the local WiFi network. The device must be in **Sync mode** before InkLink can reach it. Sync mode is enabled from the network menu on the reader.

```
Phone (InkLink) ──── WiFi ────▶ CrossPoint device (Sync mode)
```

No cloud relay or external server is involved. Everything is local.

---

## Device Discovery (mDNS)

CrossPoint advertises itself on the local network via **mDNS** (Multicast DNS, also known as Bonjour/Zeroconf). InkLink discovers devices by listening for the service type CrossPoint broadcasts.

> **TODO:** Confirm the exact mDNS service type string CrossPoint uses (e.g., `_crosspoint._tcp.local.` or `_http._tcp.local.` with a specific name). Check the firmware source.

On Android, mDNS is available via `android.net.nsd.NsdManager`. On iOS, use `Network.framework`'s `NWBrowser`. Both should be wrapped behind an `expect`/`actual` interface in `sharedLogic`.

> **Note:** mDNS may not work on Android emulators; multicast is often blocked. Test discovery on a real device.

---

## File Management (WebDAV)

CrossPoint exposes the device's filesystem over **WebDAV** when in Sync mode. InkLink uses this for all file operations: browsing, uploading books and fonts, and deleting files.

### Base URL

```
http://<device-ip>:<port>/
```

> **TODO:** Confirm the default port CrossPoint uses for its WebDAV server. Check the firmware source.

### Standard WebDAV operations used

| Operation | HTTP Method | Use |
|-----------|-------------|-----|
| List directory | `PROPFIND` | File browser |
| Upload file | `PUT` | Send book / font |
| Delete file | `DELETE` | Remove file |
| Move / rename | `MOVE` | Rename or reorganize |
| Create directory | `MKCOL` | Create folder |

### Book upload path

> **TODO:** Confirm the path CrossPoint expects for books (e.g., `/books/` or `/sdcard/Books/`). This affects where uploads are targeted.

### Font upload path

> **TODO:** Confirm where `.inx` font packs should be placed on the device filesystem.

---

## Device Info (HTTP API)

CrossPoint exposes device metadata (name, firmware version, storage usage) via a simple HTTP API.

> **TODO:** Document the actual endpoints, response format (JSON?), and authentication (if any). Pull from the firmware source or test against a real device.

Example expected shape (to be confirmed):

```json
{
  "device_name": "My X4",
  "firmware_version": "1.2.3",
  "storage_total_bytes": 8589934592,
  "storage_used_bytes": 1234567890
}
```

---

## Firmware Version Check

InkLink will compare the connected device's firmware version against a known latest version to surface an "update available" indicator.

> **TODO:** Determine how CrossPoint communicates firmware version (via the device info API?) and where the latest version is published (GitHub releases? An endpoint in the firmware?).

---

## Authentication

> **TODO:** Does CrossPoint require any authentication for WebDAV or API access? Username/password? A token? Or is it open on the local network when in Sync mode?

---

## BLE Wake (Planned)

A future feature will allow InkLink to wake the reader's network stack via Bluetooth Low Energy, removing the need to manually enable Sync mode on the reader.

> **TODO:** Investigate whether CrossPoint firmware has or plans BLE wake support. This requires BLE hardware and firmware support; confirm against the X4 hardware spec and the firmware roadmap.

---

## Known Devices

| Device | Confirmed working |
|--------|------------------|
| Xteink X4 | Planned (to be confirmed once app is built) |

---

## References

- [CrossPoint firmware source](https://github.com/xteink/crosspoint-reader)
- [WebDAV RFC 4918](https://datatracker.ietf.org/doc/html/rfc4918)
- [mDNS / DNS-SD RFC 6762](https://datatracker.ietf.org/doc/html/rfc6762)
- [Ktor HTTP client docs](https://ktor.io/docs/client-create-new-application.html)
- Android: [`android.net.nsd.NsdManager`](https://developer.android.com/reference/android/net/nsd/NsdManager)
- iOS: [`Network.NWBrowser`](https://developer.apple.com/documentation/network/nwbrowser)
