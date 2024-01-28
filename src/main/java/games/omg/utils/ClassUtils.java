package games.omg.utils;

import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import games.omg.Main;

public class ClassUtils {
  
  public static List<Class<?>> getClassesInPackage(final String pkgName) {
    try {
      final String pkgPath = pkgName.replace('.', '/');
      final URI pkg = Objects.requireNonNull(Main.class.getClassLoader().getResource(pkgPath)).toURI();
      final ArrayList<Class<?>> allClasses = new ArrayList<Class<?>>();

      Path root;
      if (pkg.toString().startsWith("jar:")) {
        try {
          root = FileSystems.getFileSystem(pkg).getPath(pkgPath);
        } catch (final FileSystemNotFoundException e) {
          root = FileSystems.newFileSystem(pkg, Collections.emptyMap()).getPath(pkgPath);
        }
      } else {
        root = Paths.get(pkg);
      }

      final String extension = ".class";
      try (final Stream<Path> allPaths = Files.walk(root)) {
        allPaths.filter(Files::isRegularFile).forEach(file -> {
          try {
            final String path = file.toString().replace('/', '.');
            final String name = path.substring(path.indexOf(pkgName), path.length() - extension.length());
            allClasses.add(Class.forName(name));
          } catch (final ClassNotFoundException | StringIndexOutOfBoundsException ignored) {
          }
        });
      }
      return allClasses;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> List<Class<? extends T>> getClassesInPackageByType(final String pkgName, final Class<T> type) {
    List<Class<? extends T>> classesByType = new ArrayList<>();
    List<Class<?>> allClasses = getClassesInPackage(pkgName);
    
    if (allClasses != null) {
      for (Class<?> clazz : allClasses) {
        if (type.isAssignableFrom(clazz)) {
          classesByType.add(clazz.asSubclass(type));
        }
      }
    }
    
    return classesByType;
  }

  public static <T> List<T> instantiateClasses(final List<Class<? extends T>> classes) {
    List<T> instances = new ArrayList<>();
    
    if (classes != null) {
      for (Class<? extends T> clazz : classes) {
        try {
          instances.add(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    
    return instances;
  }

  public static List<Object> instantiateClassesInPackage(final String pkgName) {
    List<Class<?>> classes = getClassesInPackage(pkgName);
    return instantiateClasses(classes);
  }

  public static <T> List<T> instantiateClassesInPackageByType(final String pkgName, final Class<T> type) {
    List<Class<? extends T>> classes = getClassesInPackageByType(pkgName, type);
    return instantiateClasses(classes);
  }
}
