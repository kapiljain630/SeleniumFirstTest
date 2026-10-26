# Selenium Automation Project

A Java and Maven project for learning Selenium WebDriver.

## Current focus
- Selenium WebDriver fundamentals
- Git version control
- Reliable automated tests

## Run the tests

The suite attaches to Chrome. It does not start a browser, and it does not open hoichoi. Open the site yourself, then run the tests against that window.

1. Close any Chrome window that is already using port 9222.
2. Start Chrome with remote debugging and a dedicated profile:

   macOS:

   ```bash
   /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome \
     --remote-debugging-port=9222 \
     --user-data-dir="$HOME/chrome-hoichoi-debug"
   ```

   Linux:

   ```bash
   google-chrome \
     --remote-debugging-port=9222 \
     --user-data-dir="$HOME/chrome-hoichoi-debug"
   ```

   Windows:

   ```bat
   chrome.exe --remote-debugging-port=9222 --user-data-dir=%USERPROFILE%\chrome-hoichoi-debug
   ```

3. In that window, open `https://hoichoi.tv/` and wait until the Home page has finished loading. Leave that tab focused. The existing Home tray checks expect the catalog those tests were written for: first tray "Women of Authority", last tray "National Award Winners", and the first tray running from "Birangana" to "Jimmi".
4. From this project directory, run:

   ```bash
   mvn test
   ```

`Home` runs first, then `Search`. Search is opened from the side navigation. The suite never calls `driver.get` or `driver.quit`, so the same Chrome window stays open when the run finishes.