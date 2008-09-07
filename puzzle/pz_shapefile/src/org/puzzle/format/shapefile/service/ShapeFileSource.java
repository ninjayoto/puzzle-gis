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

package org.puzzle.format.shapefile.service;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.swing.JComponent;
import org.apache.commons.collections.map.SingletonMap;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.MapContext;
import org.geotools.map.MapLayer;
import org.geotools.style.MutableStyle;
import org.geotools.style.RandomStyleFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.puzzle.core.context.LayerSource;
import org.puzzle.core.context.RichMapLayer;
import org.puzzle.core.project.GISProject;
import org.puzzle.core.project.source.GISSource;

/**
 *
 * @author  Johann Sorel
 */
public class ShapeFileSource implements GISSource{

    private static final String IMAGE_ICON_BASE = "org/puzzle/format/shapefile/shapefile.png";
    
    private final int id;
    private final Map<String,String> parameters;
    private final String name;
    private final String serviceName;
    private FeatureSource<SimpleFeatureType,SimpleFeature> featureSource = null;
    
    
    ShapeFileSource(File shapefile, String serviceName,int id, Map<String,String> parameters){
        this.id = id;
        this.name = shapefile.getName();
        this.serviceName = serviceName;
        this.parameters = parameters;
        DataStore store = null;
        try {
            store = DataStoreFinder.getDataStore(new SingletonMap("url", shapefile.getPath()));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        try {
            featureSource = store.getFeatureSource(store.getTypeNames()[0]);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
    }
    
    @Override
    public RichMapLayer createLayer(Map<String, String> parameters) {
        MutableStyle style = new RandomStyleFactory().createRandomVectorStyle(featureSource);
        if(parameters == null) parameters = Collections.emptyMap();
        LayerSource source = new LayerSource(id, parameters);
        RichMapLayer layer = new RichMapLayer(featureSource, style,this,source);
        layer.setDescription(CommonFactoryFinder.getStyleFactory(null).createDescription(name,"") );
        
        return layer;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public Image getIcon(int type) {
        return Utilities.loadImage(IMAGE_ICON_BASE);
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public void showLayerWizard(Collection<? extends MapContext> contexts, GISProject project) {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels(contexts, project));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("Create a Shapefile layer from source");
        DialogDisplayer.getDefault().notify(wizardDescriptor);
        
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            MapContext context = ((LayerCreationVisualPanel)panels[0].getComponent()).getContext();
            String title = ((LayerCreationVisualPanel)panels[0].getComponent()).getTitle();
            MapLayer layer = createLayer(null);
            layer.setDescription(CommonFactoryFinder.getStyleFactory(null).createDescription(title,"") );
            context.layers().add(layer);
        }
        
    }

    private WizardDescriptor.Panel[] panels;
    
    private WizardDescriptor.Panel[] getPanels(Collection<? extends MapContext> contexts, GISProject project) {
            panels = new WizardDescriptor.Panel[]{
                        new LayerCreationWizardPanel(contexts,project)
                    };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, new Integer(i));
                    // Sets steps names for a panel
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, Boolean.TRUE);
                }
            }
        return panels;
    }
    
}