param(
    [Parameter(Mandatory=$false)][switch]$cwd
)

$ProjectDir = "C:\Development\CodeBase\Bbc-Projects\MyCode_restAPI"

if ($cwd) {
    $ProjectDir = "$($PWD.Path)"
}

wt --window 0 -p "Windows Powershell" -d "$($ProjectDir)\client" powershell -noExit "npm run dev"
wt --window 0 -p "Windows Powershell" -d "$($ProjectDir)\server" powershell -noExit "& 'C:\Program Files\Docker\Docker\Docker Desktop.exe'"
Start-Sleep 5
Set-Location -Path "$($ProjectDir)\server"
docker compose up -d
Start-Sleep 5
npm start