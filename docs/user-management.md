## Managing Users on Linux (useradd Command)


### General syntax
`useradd [OPTIONS] USERNAME`. Creates a new user account based on command line options and defaults in `/etc/default/useradd` file.


NB: `useradd` also reads the content of the `/etc/login.defs` file. This file contains configuration for the shadow password suite such as password expiration policy, ranges of user IDs used when creating system and regular users, and more.


## Create User
`sudo useradd mp30028` This creates a new account using the default settings specified in the /etc/default/useradd file.
The command adds an entry to the /etc/passwd, /etc/shadow, /etc/group and /etc/gshadow files.

To use the account password needs to be set with `sudo passwd mp30028`

Use the `-m` or `--create-home` option to create the user home directory as e.g. `/home/mp30028`

`sudo useradd -m mp30028`. The user can read, write and delete files and directories in the home directory



## Groups
Linux groups are organization units that are used to organize and administer user accounts in Linux. The primary purpose of groups is to define a set of privileges such as reading, writing, or executing permission for a given resource that can be shared among the users within the group.

When creating a new user, the default behavior of the useradd command is to create a group with the same name as the username, and same GID as UID.

The -g (--gid) option allows you to create a user with a specific initial login group. You can specify either the group name or the GID number. The group name or GID must already exist.


Example, Create a new user and set the login group

`sudo useradd -g example-user-group mp30028`

Verify the user’s GID, use the id command:

`id -gn example-user-group`



## Creating a User and Assign Multiple Groups
Two types of groups, Primary and Secondary (or supplementary). 
A user belongs to exactly one primary group and zero or more secondary groups.

Add a user to supplementary groups with the `-G` or `--groups` option.

`sudo useradd -g example-user-group -G sudo,developers,admins mp30028`

## Checking the user's groups 

`id mp30028`

## Creating User with Specific Login Shell
By default, login shell is set based on `/etc/default/useradd` file. 

Use the `-s` or `--shell` option to set default the login shell.

## To verify the user’s login shell:
`grep mp30028 /etc/passwd`

## Add comment to a User account
`-c` or `--comment` option allows you to add a short description for the user. The comment is saved in `/etc/passwd` file.

## Inspect the user account with 
`grep mp30028 /etc/passwd`

## Creating a User with an Expiry Date
`sudo useradd -e 2019-01-22 mp30028`

## Inspect account expiry setting:
`sudo chage -l mp30028`

The output will look something like this:
```
Last password change					: Dec 11, 2018
Password expires					: never
Password inactive					: never
Account expires						: Jan 22, 2019
Minimum number of days between password change		: 0
Maximum number of days between password change		: 99999
Number of days of warning before password expires	: 7
```


## Creating a System User
There is no real technical difference between the system and regular (normal) users. Typically system users are created when installing the OS and new packages.

Use the -r (--system) option to create a system user account. For example, to create a new system user named username you would run:

`sudo useradd -r username`. System users are created with no expiry date. Their UIDs are chosen from the range of system user IDs specified in the login.defs file, which is different than the range used for normal users.


## Changing the Default useradd Values
The default useradd options can be viewed and changed using the -D, --defaults option, or by manually editing the values in the /etc/default/useradd file.

To view the current default options type:

`useradd -D`
The output will look something like this:
```
GROUP=100
HOME=/home
INACTIVE=-1
EXPIRE=
SHELL=/bin/sh
SKEL=/etc/skel
CREATE_MAIL_SPOOL=no
```
