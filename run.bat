@echo off

start "" "%LOCALAPPDATA%\Android\Sdk\emulator\emulator.exe" -avd Pixel_6_API_34

timeout /t 25

call .\gradlew installDebug

"%LOCALAPPDATA%\Android\Sdk\platform-tools\adb.exe" shell monkey -p com.example.tres_en_raya -c android.intent.category.LAUNCHER 1