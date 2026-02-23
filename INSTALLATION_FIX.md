# Installation Issue - Signature Mismatch

## Problem
App shows "Cannot be installed" toast even after approving installation.

## Root Cause
Each GitHub Actions build generates a **new debug keystore** with a different signature. Android does not allow installing an APK with a different signature over an existing installation.

## Solutions

### Option 1: Manual Uninstall (Recommended)
1. Go to **Settings → Apps**
2. Find **Simple Notepad**
3. Tap **Uninstall**
4. Then install the new APK

### Option 2: ADB (if available)
```bash
adb uninstall com.example.simplenotepad
adb install /path/to/app-debug.apk
```

### Option 3: Root Access
```bash
su
pm uninstall com.example.simplenotepad
pm install /path/to/app-debug.apk
```

### Option 4: Fixed Signing Key (Future)
Configure a fixed debug keystore so all builds use the same signature. This would require:
- Generate a keystore file
- Add it to the repository (encrypted)
- Configure build.gradle to use it
- **Security concern**: Keys in repo are not ideal

## Current APK Location
Download from: https://github.com/travofoz/SimpleNotepad/actions/runs/22276749891

Artifact: `SimpleNotepad-debug`

## Why This Happens
Debug APKs are signed with an auto-generated debug key. GitHub Actions creates a new environment for each build, so each APK has a different signature. Android's security model prevents installing an app with a different signature over an existing one.

## Recommendation
For development, manually uninstall before installing each new version. For production, configure release signing with a fixed keystore.
