# Getting Started for Dummies

## Install git

By installing git, you will be able make changes to the code and collaborate with others, with the ability to push those changes to a remote repository (one that isn't on your local computer). This enables collaboration with other developers, facilitates code review, and ensures that everyone is working on the latest version of the codebase.

Install [git for your platform](https://www.git-scm.com/downloads) from git's website if you don't have it. If you're not sure if you have it, you can check by opening a terminal such as Windows Terminal on Windows, or Terminal on macOS and running this command:

```sh
git version
```

If it shows a version, you have git installed and can move on to the next section. Otherwise, if you see an error, you need to install git.

## Install a JDK

To develop for Java, you need a Java Development Kit (JDK). It enables you to run Java programs, and includes additional tools for compiling, debugging, and testing Java code.

Install [the latest JDK from Oracle](https://www.oracle.com/java/technologies/downloads/) if you don't have it. Similar to before, you can run this command to verify if you already have it installed:

```sh
java -version
```

## Set up Visual Studio Code

If you haven't already, install [Visual Studio Code](https://code.visualstudio.com/) from Visual Studio's website. This is a powerful IDE with many features which we will use to write our code.

Now that you have it installed, we can copy the plugin's code onto your system. To clone this repository in Visual Studio Code, open the Command Palette with `Ctrl` + `Shift` + `P`. You can run many different useful actions from the Command Palette, but for now, search for "Git: Clone".

Select "Clone from GitHub", and enter `omg-community/regular-place-kit`. You may have to log in to GitHub to do this. Select a directory to store this repository in, like `Documents/Code`.

Open the repository you've just cloned. When prompted for recommended extensions (on the bottom right of the window), make sure to install them. You'll need these to develop in Java, as Visual Studio Code doesn't include the these tools by default.

And, VSCode is now all set up! You're almost ready to make your first changes.