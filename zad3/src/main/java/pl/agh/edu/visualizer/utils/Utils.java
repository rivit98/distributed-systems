package pl.agh.edu.visualizer.utils;

import java.nio.file.Path;

public class Utils {
    public static boolean isDirectChild(Path child, Path parent) {
        var relPath = child.relativize(parent);
        var res = relPath.toString().equals("..");
        return child.startsWith(parent) && res;
    }
}
