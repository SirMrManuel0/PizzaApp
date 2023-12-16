package de.sirmrmanuel0;

import de.sirmrmanuel0.gui.Start;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {
    private static final Logger logger = Logger.getLogger(Run.class.getName());
    public static void main(String[] args){new Start();}

}
