# Wallpapers

> _This feature is planned. This page will be updated when wallpaper support ships._

---

## Overview

E-ink displays can only show shades of gray (or a limited palette), so dithering is needed to simulate gradients and detail. InkLink handles this on your phone before sending the image. Pick or design a wallpaper, and InkLink converts and sends it.

---

## How Dithering Works

Dithering converts a full-color image into a pattern of pixels that approximates the original image using only the colors your display supports (typically black and white, sometimes gray shades). InkLink does this on your phone before sending the image, so what arrives on the reader is already optimized for the e-ink panel.

_The specific dithering algorithm (Floyd-Steinberg or similar) and any tunable parameters will be documented here once implemented._

---

## How to Set a Wallpaper

_Steps will be documented once the feature ships._

The planned flow:
1. Go to **Wallpapers** in InkLink
2. Choose **From Photos** to pick an image from your camera roll, or **Create** to design one
3. Preview how the dithered image will look on your reader's panel
4. Tap **Send to Device**
5. The wallpaper is pushed to your reader and set as the sleep/home screen image

---

## Display Specs

For best results, your source image should match your reader's screen resolution.

| Device | Resolution | Display type |
|--------|------------|--------------|
| Xteink X4 | _TBD_ | E-ink (_TBD_ gray levels) |

_Specs will be filled in once confirmed._

---

## Tips

- High-contrast images with clear edges look best on e-ink. Avoid complex gradients or very fine detail.
- The preview in InkLink shows an approximation of how the image will look on the actual display; the reader may render it slightly differently.
- Your reader must be in **Sync mode**. See [Getting Started](Getting-Started.md).
