# Author: QuanTuanHuy, Description: Part of FreshFridge Project

Write-Host "Loading environment variables from .env file..." -ForegroundColor Cyan

if (Test-Path .env) {
    Get-Content .env | ForEach-Object {
        if ($_ -match '^\s*([^#][^=]*?)\s*=\s*(.*)$') {
            $name = $matches[1].Trim()
            $value = $matches[2].Trim()
            [System.Environment]::SetEnvironmentVariable($name, $value, [System.EnvironmentVariableTarget]::Process)
            Write-Host "  Loaded: $name" -ForegroundColor Green
        }
    }
} else {
    Write-Host "Warning: .env file not found!" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Starting FreshFridge Service in development mode..." -ForegroundColor Cyan
Write-Host ""

.\mvnw.cmd spring-boot:run
