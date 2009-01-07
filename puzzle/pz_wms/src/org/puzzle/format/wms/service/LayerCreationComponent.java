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

package org.puzzle.format.wms.service;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.geotools.data.DataStore;
import org.geotools.data.wms.WebMapServer;
import org.geotools.data.wms.backend.AbstractWMSCapabilities;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.map.MapLayer;
import org.jdesktop.swingx.JXTable;
import org.openide.util.NbBundle;
import org.puzzle.core.project.source.JLayerChooser;
import org.puzzle.core.project.source.LayerChooserMonitor;
import org.puzzle.format.wms.ui.DBModel;

/**
 *
 * @author Johann Sorel (Puzzle-GIS)
 */
public class LayerCreationComponent extends JLayerChooser {

    private final WebMapServer server;
    private final WMSSource source;

    /** Creates new form LayerCreationComponent */
    public LayerCreationComponent(final LayerChooserMonitor monitor, WebMapServer store, WMSSource source) {
        super(monitor);
        this.server = store;
        this.source = source;
        initComponents();

        tab_table.setTableHeader(null);
        tab_table.setModel(new DBModel(tab_table));
        
        if (server != null) {
            ((DBModel) tab_table.getModel()).clean();
            try {
                final AbstractWMSCapabilities capa = server.getJaxbCapabilities();
                final List<String> layerNames = new ArrayList<String>();


                if(capa instanceof org.geotools.data.wms.backend.v130.WMSCapabilities){
                    org.geotools.data.wms.backend.v130.WMSCapabilities cp13 =
                            (org.geotools.data.wms.backend.v130.WMSCapabilities) capa;
                    for(org.geotools.data.wms.backend.v130.Layer layer : cp13.getCapability().getLayer().getLayer()){
                        layerNames.add(layer.getName());
                    }
                }else if(capa instanceof org.geotools.data.wms.backend.v111.WMT_MS_Capabilities){
                    org.geotools.data.wms.backend.v111.WMT_MS_Capabilities cp11 =
                            (org.geotools.data.wms.backend.v111.WMT_MS_Capabilities) capa;
                    for(org.geotools.data.wms.backend.v111.Layer layer : cp11.getCapability().getLayer().getLayer()){
                        layerNames.add(layer.getName());
                    }
                }

                ((DBModel) tab_table.getModel()).add(layerNames.toArray(new String[layerNames.size()]));
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        tab_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tab_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[] rows = tab_table.getSelectedRows();
                if(rows.length > 0){
                    String name = "";
                    for(int row : rows){
                        name += tab_table.getValueAt(row, 0).toString();

                        if(row != rows[rows.length-1]){
                            name += ",";
                        }
                    }

                    guiTitle.setText(name);
                    monitor.setReady(true);
                }
            }
        });

    }

    private String getType(){
        int[] rows = tab_table.getSelectedRows();
        if(rows.length > 0){
            String name = "";
            for(int row : rows){
                name += tab_table.getValueAt(row, 0).toString();

                if(row != rows[rows.length-1]){
                    name += ",";
                }
            }
            return name;
        }else{
            return null;
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {


        jLabel2 = new JLabel();
        guiTitle = new JTextField();
        jScrollPane1 = new JScrollPane();
        tab_table = new JXTable();

        jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getStyle() | Font.BOLD));
        jLabel2.setText(NbBundle.getMessage(LayerCreationComponent.class, "title")); // NOI18N
        tab_table.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tab_table);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.TRAILING)
                    .addComponent(jScrollPane1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(guiTitle, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(guiTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * {@inheritDoc }
     */
    @Override
    public MapLayer[] getLayers() {
        String title = guiTitle.getText();
        String type = getType();
        if(type != null){
            MapLayer layer = source.createLayer(Collections.singletonMap(WMSSource.LAYERS_PROP, type));
            layer.setDescription(CommonFactoryFinder.getStyleFactory(null).createDescription(title,"") );
            return new MapLayer[]{layer};
        }

        return new MapLayer[0];
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JTextField guiTitle;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JXTable tab_table;
    // End of variables declaration//GEN-END:variables

}
