# regular-place-kit

regular.place kit is meant to be a shared codebase/API for both the survival and creative servers.

However, currently, the main and only focus is on the survival server. All code in this repository should relate to the survival server plugin.

In the future and coming months, the creative server will be launched. As that approaches, the survival portion of this plugin will split off into another repository that depends on this one.

# Get started with development

If you're a complete beginner to programming in general, please follow [Getting Started for Dummies](NOOB.md). It will guide you through setting up the project with extensive explanations, providing all the steps to set everything up from nothing.

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

Our plugin runs using Paper, which is a mod of Minecraft's vanilla server. More specifically, it's a high-performance fork of Bukkit and Spigot. It provides additional optimizations and features that enhance server performance and gameplay experience.

If you don't need to test the plugin, you can skip this section. Otherwise, follow these steps to create a local Paper server:

Download a Paper server JAR file for the current version of our plugin and server, **Minecraft <!--mcversion-->1.20.2<!--mcversion-->**, from the [PaperMC website](https://papermc.io/downloads/all).

The easiest way to create a development server is to create a `server` directory within the repository. Take your downloaded Paper server JAR file place it within that directory. And, you're done!

You can now debug the server using `Run and Debug` from the sidebar, or better, use the Command Palette to run `Debug: Start Debugging`.

For more flexibility, you have the option to create a `.env` file in the project directory to change the server path (instead of using the project directory), or modify the arguments of the Paper server, like so:

```sh
SERVER_DIRECTORY="/Users/Mae/Documents/My Server"
SERVER_ARGS="-Xmx3G -Xms3G"
```

If you don't specify server arguments, it will automatically choose `-Xmx1024M -Xms512M`. This will allocate 1024 MB of RAM to your server at maximum, and 512 MB at minimum.

## Optional tools

### Install Fabric

[AutoReconnect mod](https://modrinth.com/mod/autoreconnect)

## Good luck!

You're ready to start developing!
