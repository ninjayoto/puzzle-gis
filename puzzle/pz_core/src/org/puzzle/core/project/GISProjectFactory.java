/*
 *    Puzzle GIS - Desktop GIS Platform
 *    http://puzzle-gis.codehaus.org
 *
 *    (C) 2007-2009, Johann Sorel
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 3 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.puzzle.core.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ProjectFactory;
import org.netbeans.spi.project.ProjectState;

import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * This class is used by the NetBeans platform to know :
 * <ul>
 *  <li>Which folder represents a {@code GISProject},</li>
 *  <li>How to load a {@code GISProject},</li>
 *  <li>How to save a {@code GISProject}.</li
 * </ul>
 * 
 * @author  Johann Sorel
 * @author  Thomas Bonavia (comments)
 * 
 * @see     org.netbeans.spi.project.ProjectFactory
 */
public class GISProjectFactory implements ProjectFactory{
    
    public static final String PROJECT_DIR = "gisproject";
    public static final String PROJECT_PROPFILE = "project.properties";
    
    /**
     * Specify if a folder on the disk contains a {@code GISProject}.
     * @param   file  A folder on the disk.
     * @return  A {@code boolean}:
     *          <ul>
     *              <li>{@code true} : The folder is a {@code GISProject}</li>
     *              <li>{@code false}: The folder is not a {@code GISProject}</li>
     *          </ul>
     */
    @Override
    public boolean isProject(FileObject file) {
        FileObject folder = file.getFileObject(PROJECT_DIR);
        if(folder == null) return false;
        return folder.getFileObject(PROJECT_PROPFILE) != null;
    }

    /**
     * Load a {@code GISProject} from a folder on the disk.
     * @param   dir     The folder containing the {@code GISFolder}.
     * @param   state   The state of the project (needs to be saved ?).
     * @return  The just loaded {@code GISProject}.
     * @throws  java.io.IOException
     */
    @Override
    public Project loadProject(FileObject dir, ProjectState state) throws IOException {
        return isProject (dir) ? new GISProject (dir, state) : null;
    }

    /**
     * Save a {@code GISProject} on the disk.
     * @param   project   The project to be saved.
     * @throws  java.io.IOException
     * @throws  java.lang.ClassCastException
     */
    @Override
    public void saveProject(Project project) throws IOException, ClassCastException {
        FileObject projectRoot = project.getProjectDirectory();
        if (projectRoot.getFileObject(PROJECT_DIR) == null) {
            throw new IOException ("Project dir " + projectRoot.getPath() + " deleted," +" cannot save project");
        }
        //Force creation of folders if it was deleted
        ((GISProject) project).getMapFolder(true);
        ((GISProject) project).getDocFolder(true);
        ((GISProject) project).getSourceFolder(true);

        //Find the properties file gisproject/project.properties,
        //creating it if necessary
        String propsPath = PROJECT_DIR + File.pathSeparator + PROJECT_PROPFILE;
        FileObject propertiesFile = projectRoot.getFileObject(propsPath);
        if (propertiesFile == null) {
            //Recreate the properties file if needed
            propertiesFile = projectRoot.createData(propsPath);
        }

        Properties properties = project.getLookup().lookup (Properties.class);

        File f = FileUtil.toFile(propertiesFile);
        properties.store(new FileOutputStream(f), "NetBeans GIS Project Properties");
    }
    
}
