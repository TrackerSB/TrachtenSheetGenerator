# Returns a fully extended path without any tilde-abbreviations (e.g. DOS 8.3 style)
param([string] $path)

(Get-Item "$path").FullName
