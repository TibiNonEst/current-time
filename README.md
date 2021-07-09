# Current Time ![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/tibinonest/current-time/gradle-ci/main) ![GitHub](https://img.shields.io/github/license/tibinonest/current-time) ![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/tibinonest/current-time) ![Spiget tested server versions](https://img.shields.io/spiget/tested-versions/93971)
**A spigot plugin to set your world(s) to the current irl time.**

## Commands and Permissions
The plugin currently only has one command: /currenttime.
This command is available with the permission currenttime.reload and reloads the plugin's config.yml file.
You can also run this command with the alias /ct.

## Default Configuration:
```yaml
# Time zone, please use TZBD names or UTC offsets
# A list of available names can be viewed at https://en.wikipedia.org/wiki/List_of_tz_database_time_zones
# Will use system default if left empty
time-zone: 'America/New_York'

# A list of worlds to apply the time zone to
# Will apply to all overworlds if left empty
worlds: []

# Plugin messages
messages:
  zone-not-found: 'The requested time zone could not be found, please try entering a valid one.'
  world-not-found: 'The requested world or worlds could not be found, please try entering a valid one.'
```
