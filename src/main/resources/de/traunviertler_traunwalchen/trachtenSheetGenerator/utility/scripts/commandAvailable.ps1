# Checks whether the given command $cmdName can be found and executed
param([string] $cmdName)

if (Get-Command $cmdName -errorAction SilentlyContinue) {
    Write-Output "yes"
}
else {
    Write-Output "no"
}
