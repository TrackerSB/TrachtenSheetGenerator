# Compiles a given TEX file to PDF
param(
    [Parameter(Mandatory = $true)][string] $workingDir,
    [Parameter(Mandatory = $true)][string] $texFileName
)

if (Test-Path -LiteralPath "$workingDir") {
    cd "$workingDir"
    if (Test-Path -LiteralPath "$texFileName") {
        # TODO Implement actual compilation
        Write-Out "TODO Compile \"$texFileName\""
    } else {
        Write-Error "Could not find \"$texFileName\""
    }
} else {
    Write-Error "The working directory \"$workingDir\" does not exist"
}
