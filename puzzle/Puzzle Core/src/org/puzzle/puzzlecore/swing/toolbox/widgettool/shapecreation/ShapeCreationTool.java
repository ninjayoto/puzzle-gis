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
package org.puzzle.puzzlecore.swing.toolbox.widgettool.shapecreation;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import org.geotools.data.DataUtilities;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.indexed.IndexedShapefileDataStoreFactory;
import org.geotools.feature.SchemaException;
import org.geotools.gui.swing.crschooser.JCRSChooser;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;


/**
 *
 * @author johann sorel
 */
public class ShapeCreationTool extends JPanel {

    
    
    private ShapeAttModel model = new ShapeAttModel();
    private String geotype = "Point";
    private CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84;
    private File file = new File("default.shp");

    /** 
     * Creates new form shapeCreationPanel 
     */
    public ShapeCreationTool() {
        initComponents();
        gui_tab.getSelectionModel().setSelectionMode(gui_tab.getSelectionModel().SINGLE_SELECTION);
        gui_tab.getColumn(1).setCellEditor(new TypeEditor());

        gui_jtf_crs.setText(crs.getName().toString());
        gui_jtf_name.setText(file.getAbsolutePath());
    }

    private void createShape(String name) {
        try {
            // Create the DataStoreFactory
            FileDataStoreFactorySpi factory = new IndexedShapefileDataStoreFactory();

            // Create a Map object used by our DataStore Factory
            // NOTE: file.toURI().toURL() is used because file.toURL() is deprecated
            Map<String, Serializable> map = Collections.singletonMap("url", (Serializable)file.toURI().toURL());

            // Create the ShapefileDataStore from our factory based on our Map object
            ShapefileDataStore myData = (ShapefileDataStore) factory.createNewDataStore(map);
            
            // Tell this shapefile what type of data it will store
            StringBuffer buffer = new StringBuffer();
            buffer.append("geom:");
            buffer.append(geotype);

            Data[] datas = model.getDatas();

            for (Data data : datas) {
                buffer.append("," + data.name);

                switch (data.type) {
                    case INTEGER:
                        buffer.append(":" + Integer.class.getName());
                        break;
                    case LONG:
                        buffer.append(":" + Long.class.getName());
                        break;
                    case DOUBLE:
                        buffer.append(":" + Double.class.getName());
                        break;
                    case STRING:
                        buffer.append(":" + String.class.getName());
                        break;
                    case DATE:
                        buffer.append(":" + Date.class.getName());
                        break;
                }
            }

            System.out.println(buffer);
            
            SimpleFeatureType featureType = DataUtilities.createType(name, buffer.toString());

            // Create the Shapefile (empty at this point)
            myData.createSchema(featureType);
            
            // Tell the DataStore what type of Coordinate Reference System (CRS) to use
            myData.forceSchemaCRS(crs);

            myData.dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Incorrect File : " + e.getMessage());
        } catch (SchemaException se) {
            JOptionPane.showMessageDialog(this, "Incorrect Schema : " + se.getMessage());
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grp_geom = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jXTitledSeparator1 = new org.jdesktop.swingx.JXTitledSeparator();
        gui_jtf_name = new javax.swing.JTextField();
        gui_but_create = new javax.swing.JButton();
        gui_but_file = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gui_tab = new org.jdesktop.swingx.JXTable();
        gui_but_add = new javax.swing.JButton();
        gui_but_up = new javax.swing.JButton();
        gui_but_down = new javax.swing.JButton();
        gui_but_delete = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        gui_jrb_point = new javax.swing.JRadioButton();
        gui_jrb_multipoint = new javax.swing.JRadioButton();
        gui_jrb_multiline = new javax.swing.JRadioButton();
        gui_jrb_multipolygon = new javax.swing.JRadioButton();
        gui_but_crs = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        gui_jtf_crs = new javax.swing.JTextField();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "file")); // NOI18N

        jXTitledSeparator1.setTitle(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "shapefile_creation")); // NOI18N

        gui_jtf_name.setEditable(false);
        gui_jtf_name.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "default")); // NOI18N

        gui_but_create.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "create")); // NOI18N
        gui_but_create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_createActionPerformed(evt);
            }
        });

        gui_but_file.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "...")); // NOI18N
        gui_but_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_fileActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "attributs"))); // NOI18N

        gui_tab.setModel(model);
        jScrollPane1.setViewportView(gui_tab);

        gui_but_add.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "add")); // NOI18N
        gui_but_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_addActionPerformed(evt);
            }
        });

        gui_but_up.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "up")); // NOI18N
        gui_but_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_upActionPerformed(evt);
            }
        });

        gui_but_down.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "down")); // NOI18N
        gui_but_down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_downActionPerformed(evt);
            }
        });

        gui_but_delete.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "delete")); // NOI18N
        gui_but_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_deleteActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(gui_but_add)
                    .add(gui_but_up)
                    .add(gui_but_down)
                    .add(gui_but_delete)))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {gui_but_add, gui_but_delete, gui_but_down, gui_but_up}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(32, 32, 32)
                .add(gui_but_add)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gui_but_delete)
                .add(24, 24, 24)
                .add(gui_but_up)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gui_but_down)
                .addContainerGap(75, Short.MAX_VALUE))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "geometry"))); // NOI18N

        grp_geom.add(gui_jrb_point);
        gui_jrb_point.setSelected(true);
        gui_jrb_point.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "point")); // NOI18N
        gui_jrb_point.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_jrb_pointActionPerformed(evt);
            }
        });

        grp_geom.add(gui_jrb_multipoint);
        gui_jrb_multipoint.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "multipoint")); // NOI18N
        gui_jrb_multipoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_jrb_multipointActionPerformed(evt);
            }
        });

        grp_geom.add(gui_jrb_multiline);
        gui_jrb_multiline.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "multiline")); // NOI18N
        gui_jrb_multiline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_jrb_multilineActionPerformed(evt);
            }
        });

        grp_geom.add(gui_jrb_multipolygon);
        gui_jrb_multipolygon.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "multipolygon")); // NOI18N
        gui_jrb_multipolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_jrb_multipolygonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(gui_jrb_point)
                    .add(gui_jrb_multipoint)
                    .add(gui_jrb_multiline)
                    .add(gui_jrb_multipolygon))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(gui_jrb_point)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gui_jrb_multipoint)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gui_jrb_multiline)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(gui_jrb_multipolygon)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gui_but_crs.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "list")); // NOI18N
        gui_but_crs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gui_but_crsActionPerformed(evt);
            }
        });

        jLabel2.setText(org.openide.util.NbBundle.getMessage(ShapeCreationTool.class, "crs")); // NOI18N

        gui_jtf_crs.setEditable(false);
        gui_jtf_crs.setText("EPSG:4326");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jXTitledSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gui_jtf_name, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gui_but_file))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(gui_but_create)
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gui_jtf_crs, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(gui_but_crs)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jXTitledSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(gui_but_file)
                    .add(gui_jtf_name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(gui_but_crs)
                    .add(gui_jtf_crs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 113, Short.MAX_VALUE)
                        .add(gui_but_create))
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void gui_but_createActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_createActionPerformed
        createShape(gui_jtf_name.getText());
    }//GEN-LAST:event_gui_but_createActionPerformed

    private void gui_but_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_addActionPerformed
        model.addAttribut();
    }//GEN-LAST:event_gui_but_addActionPerformed

    private void gui_but_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_deleteActionPerformed

        int selected = gui_tab.getSelectionModel().getMinSelectionIndex();
        if (selected >= 0) {
            Data data = model.getDataAt(selected);
            model.deleteAttribut(data);
        }
        
    }//GEN-LAST:event_gui_but_deleteActionPerformed

    private void gui_but_upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_upActionPerformed
        int selected = gui_tab.getSelectionModel().getMinSelectionIndex();
        if (selected >= 0) {
            Data data = model.getDataAt(selected);
            model.moveUp(data);
        }
    }//GEN-LAST:event_gui_but_upActionPerformed

    private void gui_but_downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_downActionPerformed
        int selected = gui_tab.getSelectionModel().getMinSelectionIndex();
        if (selected >= 0) {
            Data data = model.getDataAt(selected);
            model.moveDown(data);
        }
    }//GEN-LAST:event_gui_but_downActionPerformed

    private void gui_jrb_pointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_jrb_pointActionPerformed
        geotype = "Point";
    }//GEN-LAST:event_gui_jrb_pointActionPerformed

    private void gui_jrb_multipointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_jrb_multipointActionPerformed
        geotype = "MultiPoint";
    }//GEN-LAST:event_gui_jrb_multipointActionPerformed

    private void gui_jrb_multilineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_jrb_multilineActionPerformed
        geotype = "MultiLineString";
    }//GEN-LAST:event_gui_jrb_multilineActionPerformed

    private void gui_jrb_multipolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_jrb_multipolygonActionPerformed
        geotype = "MultiPolygon";
    }//GEN-LAST:event_gui_jrb_multipolygonActionPerformed

    private void gui_but_crsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_crsActionPerformed
        JCRSChooser jcrs = new JCRSChooser(null, true);
        jcrs.setCRS(crs);
        JCRSChooser.ACTION act = jcrs.showDialog();

        if (act == JCRSChooser.ACTION.APPROVE) {
            crs = jcrs.getCRS();
        }

        gui_jtf_crs.setText(crs.getName().toString());
                
    }//GEN-LAST:event_gui_but_crsActionPerformed

    private void gui_but_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gui_but_fileActionPerformed
        JFileChooser jfc = new JFileChooser(file);
        int act = jfc.showSaveDialog(null);

        if (act == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            
            if (f != null) {
                if (f.getAbsolutePath().endsWith(".shp")) {
                    file = f;
                    gui_jtf_name.setText(file.getAbsolutePath());
                } else {
                    int lastdot = f.getAbsolutePath().lastIndexOf(".");
                    if(lastdot>0){
                        f = new File(f.getAbsolutePath().substring(0,lastdot) +".shp");
                    }else{
                        f = new File(f.getAbsolutePath() +".shp");
                    }
                    
                    file = f;
                    gui_jtf_name.setText(file.getAbsolutePath());
                }
            }

        }
        
    }//GEN-LAST:event_gui_but_fileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grp_geom;
    private javax.swing.JButton gui_but_add;
    private javax.swing.JButton gui_but_create;
    private javax.swing.JButton gui_but_crs;
    private javax.swing.JButton gui_but_delete;
    private javax.swing.JButton gui_but_down;
    private javax.swing.JButton gui_but_file;
    private javax.swing.JButton gui_but_up;
    private javax.swing.JRadioButton gui_jrb_multiline;
    private javax.swing.JRadioButton gui_jrb_multipoint;
    private javax.swing.JRadioButton gui_jrb_multipolygon;
    private javax.swing.JRadioButton gui_jrb_point;
    private javax.swing.JTextField gui_jtf_crs;
    private javax.swing.JTextField gui_jtf_name;
    private org.jdesktop.swingx.JXTable gui_tab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXTitledSeparator jXTitledSeparator1;
    // End of variables declaration//GEN-END:variables
}
