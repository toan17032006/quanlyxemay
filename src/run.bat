@echo off
chcp 65001 >nul
echo ========================================
echo   Compiling Quan Ly Xe May...
echo ========================================
javac -encoding UTF-8 -d out src/Main.java src/model/XeMay.java src/dao/XeMayDAO.java src/ui/XeMayTableModel.java src/ui/XeMayDialog.java src/ui/MainFrame.java
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)
echo [OK] Compilation successful!
echo.
echo ========================================
echo   Launching application...
echo ========================================
java -cp out Main
pause
