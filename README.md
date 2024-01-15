currently housing all survival plugin code - eventually will turn into shared code between survival/creative plugins

# Get started with development

If you're a complete beginner to programming in general, it's highly recommend reading [Getting Started for Dummies](NOOB.md). Otherwise, continuing to read this assumes that you're acclimated to basic programming concepts.

## Install development tools

Make sure you have git and a Java Development Kit (JDK) installed on your computer. You can check by opening a terminal or command prompt and typing these commands:
```sh
git -v
java -version
```
If you see an error, you can [install git](https://www.git-scm.com/downloads) or [the latest JDK](https://www.oracle.com/java/technologies/downloads/) for your platform respectively.

## Set up a local Minecraft server for testing

Our plugin runs using Paper, which is a mod of Minecraft's vanilla server. More specifically, it's a high-performance fork of Bukkit and Spigot. It provides additional optimizations and features that enhance server performance and gameplay experience.

If you don't need to test the plugin, you can skip this section. Otherwise, follow these steps to create a local Paper server:

Download a Paper server JAR file for the current version of our server, **Minecraft 1.20.2**, from the [PaperMC website](https://papermc.io/downloads/all).

The easiest way to create a development server is to create a `server` directory within the repository. Then, take your downloaded Paper server JAR file place it within that directory. Congratulations! You can now debug the server using `Run and Debug` from the sidebar.

For more flexibility, you can create a `.env` file in the project directory to change the server directory location (rather than using the project directory) or modify the arguments of the Paper server, like so:

```sh
SERVER_DIRECTORY="/Users/Sleuth/Documents/My Server"
SERVER_ARGS="-Xmx3G -Xms3G"
```

If you don't specify server arguments, it will automatically choose `-Xmx1024M -Xms512M`. This will allocate 1024 MB of RAM at maximum and 512 MB at minimum.

## Set up Visual Studio Code

If you haven't already, install [Visual Studio Code](https://code.visualstudio.com/) from Visual Studio's website.

Now, copy the plugin's code onto your system. To clone this repository in Visual Studio Code, open the Command Palette with `Ctrl` + `Shift` + `P` and search for "Git: Clone". Select "Clone from GitHub", and enter `omg-community/regular-place-kit`.

Select a directory to store this repository in, like `Documents/Code`.

[tell the dev to install recommended extensions when prompted]

## Optional tools

### Install Fabric

[AutoReconnect mod](https://modrinth.com/mod/autoreconnect)

## Good luck!

You're ready to start developing!
