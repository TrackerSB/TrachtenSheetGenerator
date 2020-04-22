# Returns a fully extended path without any tilde-abbreviations (e.g. DOS 8.3 style)
param([Parameter(Mandatory=$true)][string] $path)

(Get-Item "$path").FullName
