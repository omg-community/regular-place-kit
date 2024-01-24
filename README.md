# regular-place-kit (MC <!--mcversion-->1.20.2<!--mcversion-->)

regular.place kit is meant to be a shared codebase/API for both the survival and creative servers.

However, currently, the main and only focus is on the survival server. All code in this repository should relate to the survival server plugin.

In the future and coming months, the creative server will be launched. As that approaches, the survival portion of this plugin will split off into another repository that depends on this one.

# Get started with development

If you're a complete beginner to programming in general, please follow [Getting Started for Dummies](guides/Beginners.md). It will guide you through setting up the project with extensive explanations, providing all the steps to set everything up from nothing.

## Install development tools

Make sure you have git and a Java Development Kit (JDK) installed on your computer. You can check by opening a terminal and running these commands:
```sh
git -v
java -version
```
If you see any errors, you can [install git](https://www.git-scm.com/downloads) or [the latest JDK](https://www.oracle.com/java/technologies/downloads/) for your platform respectively.

## Set up Visual Studio Code

This repository is set up to use VSCode. If you haven't already, install [Visual Studio Code](https://code.visualstudio.com/) from Visual Studio's website. Clone this repository and open it.

Once you've opened the repository, install any recommended extensions when prompted.

## Set up a local Minecraft server for testing

Our plugin runs using Paper, which is a mod of Minecraft's vanilla server. More specifically, it's a high-performance fork of Bukkit and Spigot. It provides additional optimizations and features that enhance server performance and gameplay experience. If you don't need to test the plugin, you can skip this section.

The easiest way to set up a Paper server is to use the VSCode tooling provided by our project. Open the Command Palette and run `Tasks: Run Task`. Then search for `Start Paper server`. This will help create a Paper server for you which will be stored in the project directory under `./server`. This command runs the server without any debugging and won't automatically build the plugin.

To debug the server, which allows for Hot Code Replacement, you can run the server using `Run and Debug` from the sidebar, or better, use the Command Palette to run `Debug: Start Debugging`.

For more flexibility, you have the option to create a `.env` file in the project directory to change the server path (instead of using the project directory), or modify the arguments of the Paper server, like so:

```sh
SERVER_DIRECTORY="/Users/Mae/Documents/My Server"
SERVER_ARGS="-Xmx3G -Xms3G"
```

If you don't specify server arguments, it will automatically choose `-Xmx1024M -Xms512M`. This will allocate 1024 MB of RAM to your server at maximum, and 512 MB at minimum.

The best way to apply new patches of Minecraft, update to the latest version of Minecraft, or to update Paper to the latest build is by opening the Command Palette, and running any of the `Project: Update..` tasks.

You can also manually modify the `project.properties` file. Change it from Minecraft <!--mcversion-->1.20.2<!--mcversion--> or change the Paper build to whatever you need. Then, run `Replace Paper server JAR with correct build`.

### More tips

In VSCode, you can run any "tasks" from the Command Palette. One task you might find useful is `Tasks: Run Task` `Start Paper server`. 

This will start the server without building the plugin and copying it into the plugin directory, useful if you just want to start the server without the long build process.

While debugging, you can use Hot Code Replacement to apply certain types of changes to the plugin without needing to completely restart the server. You can find this button in the debug menu at the top of the window.

![Hot Code Replacement](<assets/hot-code-replacement.png>)

It may be helpful to bind this to a Keyboard Shortcut like `Alt` + `R`, as this is not bound to anything by default.

In this same menu, you can also restart the debug session, which will consequently restart the server, which is useful in the case that your changes are not Hot Code Replaceable.

![Restart](<assets/restart.png>)

## Optional tools

### Install Fabric

For an even better development experience, you can install the [Fabric mod loader](https://fabricmc.net/use/installer/) and use the AutoReconnect mod to automatically reconnect to the server when you restart it.

Once you install Fabric, add these dependencies into your mods folder in `%appdata%\.minecraft\mods`:

- [Fabric API](https://modrinth.com/mod/fabric-api/versions)
- [Cloth Config API](https://modrinth.com/mod/cloth-config/versions)
- [Mod Menu](https://modrinth.com/mod/modmenu/versions)
- [AutoReconnect](https://modrinth.com/mod/autoreconnect/versions)

## Good luck!

You're ready to start developing!
