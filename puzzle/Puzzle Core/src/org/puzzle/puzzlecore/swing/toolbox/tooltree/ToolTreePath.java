/*
 *  Puzzle-GIS - OpenSource mapping program
 *  http://docs.codehaus.org/display/PUZZLEGIS
 *  Copyright (C) 2007-2008 Puzzle-GIS
 *  
 *  GPLv3 + Classpath exception
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.puzzle.puzzlecore.swing.toolbox.tooltree;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 *
 * @author johann sorel
 */
public class ToolTreePath {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("org/geotools/gui/swing/toolbox/tooltree/Bundle");
    private static final String[] EMPTY_STRING_ARRAY = {};
    
    private final List<String> paths = new ArrayList<String>();
    
    ToolTreePath(ToolTreePath father, String i18nKey){
        String myPath = BUNDLE.getString(i18nKey);
        
        if(father != null){
            paths.addAll(father.getInerPath());
        }
        paths.add(myPath);
    }
    
    List<String> getInerPath(){
        return paths;
    }
    
    
    /**
     * get the Sting array of this path
     * @return String[]
     */
    public String[] getPath(){
        return paths.toArray(EMPTY_STRING_ARRAY);        
    }
    
           
}