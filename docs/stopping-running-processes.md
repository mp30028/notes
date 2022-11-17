
## Find port in use and kill the process associated with it
`netstat -aon | findstr 8080`
`kill <process_id>`


## Killing a running java application
1. Find the process id using the `jps` command
2. Kill the process with the `taskkill` command. e.g. `taskkill -f /PID 2164`

`-f` is required with taskkill to force shutdown.
