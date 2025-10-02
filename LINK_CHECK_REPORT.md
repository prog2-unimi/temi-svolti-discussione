# External Links Check Report

**Date:** $(date +%Y-%m-%d)  
**Scope:** All external links in the `discussione` directory

## Summary

- **Total unique external URLs found:** 165
- **URLs successfully verified:** 3 (GitHub URLs only)
- **URLs unable to verify:** 162 (network restrictions in test environment)

## Verified URLs (Working)

All GitHub URLs are working correctly:

1. ✅ `https://github.com/prog2-unimi/jubbiot`
2. ✅ `https://github.com/prog2-unimi/temi-svolti`
3. ✅ `https://github.com/prog2-unimi/temi-svolti/blob/master/temi`

## URLs That Could Not Be Verified

Due to network restrictions in the automated testing environment, the following domains could not be accessed:

### Oracle Java Documentation (133 URLs)
- **Java 25:** 116 URLs
- **Java 18:** 9 URLs
- **Java 11:** 2 URLs
- **Java 7:** 2 URLs
- **Java Tutorial:** 4 URLs

**Note:** The project is intentionally configured to use Java 25 (see `sorgenti/build.gradle` line 9). However, Java 25 may not be officially released yet. These links should be manually verified to ensure they point to valid documentation.

**Recommendation:** If Java 25 documentation is not yet available, consider updating to the latest stable version (Java 21 LTS or Java 23).

### Other External Services (32 URLs)

#### Zenodo (2 URLs)
- `https://zenodo.org/badge/413484675.svg`
- `https://zenodo.org/badge/latestdoi/413484675`

#### Wikiwand (13 URLs)
- `https://www.wikiwand.com/en/Variadic_function`
- `https://www.wikiwand.com/it/Algoritmo_in_loco`
- `https://www.wikiwand.com/it/Copia_di_un_oggetto`
- `https://www.wikiwand.com/it/Estremo_superiore_e_estremo_inferiore`
- `https://www.wikiwand.com/it/Generatore_lineare_congruenziale`
- `https://www.wikiwand.com/it/Hash_table`
- `https://www.wikiwand.com/it/Lista_concatenata`
- `https://www.wikiwand.com/it/Numeri_pseudo-casuali`
- `https://www.wikiwand.com/it/Ordine_lessicografico`
- `https://www.wikiwand.com/it/RB-Albero`
- `https://www.wikiwand.com/it/Relazione_d%27ordine`
- `https://www.wikiwand.com/it/Ricerca_dicotomica`
- `https://www.wikiwand.com/it/Ricerca_sequenziale`

#### Shields.io (3 URLs)
- `https://img.shields.io/badge/License-CC%20BY--SA%204.0-blue.svg`
- `https://img.shields.io/badge/License-GPL%20v3-blue.svg`
- `https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Stars`

#### University Sites (3 URLs)
- `https://prog2.di.unimi.it/`
- `https://www.unimi.it/` ✅ (upgraded from HTTP)
- `https://informatica.cdl.unimi.it/it`

#### License Sites (2 URLs)
- `https://www.gnu.org/licenses/gpl-3.0` ✅ (upgraded from HTTP)
- `https://creativecommons.org/licenses/by-sa/4.0/` ✅ (upgraded from HTTP)

#### Analytics (2 URLs)
- `https://mapio.containers.piwik.pro/`
- `https://mapio.containers.piwik.pro/23cc3eb1-e71f-4331-9b8a-f7b9c90b873a/noscript.html`

#### Academic Resources (3 URLs)
- `https://dev.java/learn/api/collections-framework/`
- `https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/Formatter.html#summary`
- `https://or-dii.unibs.it/doc/tr/RT_2011-02-66.pdf`
- `https://www.sciencedirect.com/science/article/abs/pii/S0167637707000648`

## Recommendations

### 1. HTTP to HTTPS Migration ✅ COMPLETED
All HTTP URLs have been upgraded to HTTPS:

- `http://www.unimi.it/` → ✅ Updated to `https://www.unimi.it/`
- `http://www.gnu.org/licenses/gpl-3.0` → ✅ Updated to `https://www.gnu.org/licenses/gpl-3.0`
- `http://creativecommons.org/licenses/by-sa/4.0/` → ✅ Updated to `https://creativecommons.org/licenses/by-sa/4.0/`

**Files updated:**
- `discussione/src/index.md` (www.unimi.it)
- `discussione/src/_templates/footer.html` (gnu.org, creativecommons.org)

### 2. Java Documentation Version
Verify that Java 25 documentation is available. If not, update the project configuration and documentation links to use the latest stable Java version:
- Latest LTS: Java 21
- Latest Release: Java 23 (or check current)

**Files to update if changing Java version:**
- `sorgenti/build.gradle` (line 9 and line 57)
- All markdown files in `discussione/src/intro/ifdm/`
- All markdown files in `discussione/src/temi/`

### 3. Manual Verification Needed
The following links should be manually verified by someone with unrestricted internet access:

- All Oracle Java documentation links
- Zenodo DOI links
- Wikiwand encyclopedia links
- University website links
- Academic PDF resources

## Files Containing External Links

1. `discussione/src/_templates/footer.html`
2. `discussione/src/_templates/layout.html`
3. `discussione/src/conf.py`
4. `discussione/src/index.md`
5. `discussione/src/intro/consegne.md`
6. `discussione/src/intro/ifdm/collections.md`
7. `discussione/src/intro/ifdm/io.md`
8. `discussione/src/intro/ifdm/utils.md`
9. `discussione/src/temi/algebretta.md`
10. `discussione/src/temi/bancarelle.md`
11. `discussione/src/temi/boolvect.md`
12. `discussione/src/temi/cambiavalute.md`
13. `discussione/src/temi/filesystem.md`
14. `discussione/src/temi/multiset.md`
15. `discussione/src/temi/piastrelle.md`
16. `discussione/src/temi/playfy.md`
17. `discussione/src/temi/tslib.py`

## Testing Methodology

This report was generated by:
1. Scanning all text files in the `discussione` directory
2. Extracting URLs using regex pattern matching
3. Attempting to verify GitHub URLs (successful)
4. Documenting inability to verify other URLs due to network restrictions

## Next Steps

1. **Manual verification:** Someone with full internet access should verify all non-GitHub URLs
2. **HTTPS migration:** Test and update HTTP URLs to HTTPS where supported
3. **Java version:** Confirm Java 25 documentation availability or downgrade to stable version
4. **Update this report:** Once links are verified, update this document with actual broken links found
