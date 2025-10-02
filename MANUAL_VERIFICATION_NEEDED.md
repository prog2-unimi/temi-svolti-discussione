# Manual Verification Required for External Links

## Issue Summary

This document lists external links in the `discussione` directory that could not be automatically verified due to network restrictions in the automated testing environment. **Manual verification is required** to ensure these links are still valid and accessible.

## Links Requiring Manual Verification

### High Priority - Java Documentation

The project uses Java 25 (configured in `sorgenti/build.gradle`), and most documentation links reference Java 25 docs. **Please verify that Java 25 documentation is actually available**, as it may be a pre-release or early access version.

If Java 25 docs are not yet publicly available, consider:
1. Updating project to use latest stable Java version (e.g., Java 21 LTS)
2. Updating all documentation links accordingly

**Affected files (116+ Java 25 URLs):**
- `discussione/src/intro/ifdm/utils.md` (majority of links)
- `discussione/src/intro/ifdm/collections.md`
- `discussione/src/intro/ifdm/io.md`
- `discussione/src/temi/playfy.md`
- `discussione/src/temi/boolvect.md`
- `discussione/src/temi/algebretta.md`
- `discussione/src/temi/bancarelle.md`
- `discussione/src/temi/cambiavalute.md`

**Example Java 25 URLs to verify:**
```
https://docs.oracle.com/en/java/javase/25/docs/api/
https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Objects.html
https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/String.html
```

### Medium Priority - Academic and External Services

#### Zenodo DOI Links
Please verify these DOI badge links are working:
- `https://zenodo.org/badge/413484675.svg`
- `https://zenodo.org/badge/latestdoi/413484675`

**Found in:** `discussione/src/_templates/footer.html`

#### Wikiwand Encyclopedia Links (13 URLs)
Verify these Wikipedia/Wikiwand links for Italian algorithm and data structure concepts:
- `https://www.wikiwand.com/it/Algoritmo_in_loco`
- `https://www.wikiwand.com/it/Copia_di_un_oggetto`
- `https://www.wikiwand.com/it/Hash_table`
- `https://www.wikiwand.com/it/Lista_concatenata`
- `https://www.wikiwand.com/it/RB-Albero`
- `https://www.wikiwand.com/it/Ricerca_dicotomica`
- ... and 7 more

**Found in:** `discussione/src/intro/ifdm/utils.md`, `discussione/src/intro/ifdm/collections.md`

#### Academic Resources
- `https://or-dii.unibs.it/doc/tr/RT_2011-02-66.pdf` (Technical report PDF)
- `https://www.sciencedirect.com/science/article/abs/pii/S0167637707000648` (Research paper)

**Found in:** `discussione/src/intro/ifdm/collections.md`

#### Java Development Resources
- `https://dev.java/learn/api/collections-framework/`
- `https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/Formatter.html#summary`

### Low Priority - Infrastructure Links

These are likely working but couldn't be verified:

#### University Sites
- `https://prog2.di.unimi.it/`
- `https://www.unimi.it/` (upgraded from HTTP ✅)
- `https://informatica.cdl.unimi.it/it`

#### License Badges
- `https://img.shields.io/badge/License-CC%20BY--SA%204.0-blue.svg`
- `https://img.shields.io/badge/License-GPL%20v3-blue.svg`
- `https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Stars`

#### Analytics
- `https://mapio.containers.piwik.pro/`
- `https://mapio.containers.piwik.pro/23cc3eb1-e71f-4331-9b8a-f7b9c90b873a/noscript.html`

## Already Fixed

✅ **HTTP to HTTPS Migration Completed:**
- `http://www.gnu.org/licenses/gpl-3.0` → `https://www.gnu.org/licenses/gpl-3.0`
- `http://creativecommons.org/licenses/by-sa/4.0/` → `https://creativecommons.org/licenses/by-sa/4.0/`
- `http://www.unimi.it/` → `https://www.unimi.it/`

✅ **GitHub Links Verified:**
- `https://github.com/prog2-unimi/jubbiot`
- `https://github.com/prog2-unimi/temi-svolti`
- `https://github.com/prog2-unimi/temi-svolti/blob/master/temi`

## How to Perform Manual Verification

1. **For Java Documentation:**
   ```bash
   # Check if Java 25 docs exist
   curl -I https://docs.oracle.com/en/java/javase/25/docs/api/
   
   # If 404, check what's the latest available version
   curl -I https://docs.oracle.com/en/java/javase/21/docs/api/  # LTS
   curl -I https://docs.oracle.com/en/java/javase/23/docs/api/  # Latest
   ```

2. **For Other Links:**
   ```bash
   # Test individual URLs
   curl -I <URL>
   # or visit in browser
   ```

3. **Report Results:**
   - Create a GitHub issue listing any broken links found
   - Note the HTTP status code or error message
   - Suggest replacements if available

## Next Steps

1. **Immediate:** Verify Java 25 documentation availability
2. **If Java 25 unavailable:** Update project to use stable Java version
3. **Follow-up:** Verify academic and external service links
4. **Ongoing:** Consider setting up automated link checking in CI/CD

## Related Files

- Full link analysis: `LINK_CHECK_REPORT.md`
- Project Java config: `sorgenti/build.gradle` (line 9, line 57)
