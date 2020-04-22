# Checks whether the given command $cmdName can be found and executed
param([Parameter(Mandatory=$true)][string] $cmdName)

if (Get-Command $cmdName -errorAction SilentlyContinue) {
    Write-Output "yes"
}
else {
    Write-Output "no"
}
