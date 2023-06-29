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

---

## On Linux

### To list values currently active env vars
`printenv` <br/>
`printenv | grep SOPS` - To find env variables starting with SOPS

### To reload a profile for changes to take effect
`source ~/.profile'

### To make profile changes for all users
Add a file with `*.sh` extension to the `/etc/profile.d/` folder e.g. `/etc/profile.d/my_env_var.sh` <br/>
In the file with the `*.sh` extension add an export statement e.g. `export THE_ENV_VAR=the_value_of_the_env_var`

