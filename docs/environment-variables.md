# Handling Environment Variables

## On windows (powershell)

### To Set
`[Environment]::SetEnvironmentVariable('VAR_1','value-1')` 
<br/><br/>
To make a permnanent change<br/>
`[Environment]::SetEnvironmentVariable('VAR_1','value-1', 'Machine')` <br/>
<br/><br/>
To unset or remove, pass an empty string<br/>
`[Environment]::SetEnvironmentVariable('VAR_1','', 'Machine')` <br/>

### To Get
`[Environment]::GetEnvironmentVariable('VAR_1')` 

### More info
[Powershell Docs](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_environment_variables?view=powershell-7.3)
