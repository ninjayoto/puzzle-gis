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
package org.puzzle.format.postgis.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import static org.geotoolkit.jdbc.JDBCDataStoreFactory.*;

import org.geotoolkit.data.DataStore;
import org.geotoolkit.storage.DataStoreException;
import org.geotoolkit.data.DataStoreFinder;
import org.jdesktop.swingx.JXTitledPanel;

import org.openide.util.Exceptions;

import org.puzzle.core.project.source.capabilities.SourceCreationPane;
import org.puzzle.core.project.source.GISSourceInfo;
import org.puzzle.format.postgis.resources.PostgisResource;
import org.puzzle.format.postgis.service.PostGISSourceService;

/**
 * PostGIS databaseChooser
 * 
 * @author Johann Sorel
 */
public class JPostGISDataPanel extends SourceCreationPane {

    private DataStore store;
    private final Map<String,Serializable> params = new HashMap<String, Serializable>();

    /** Creates new form DefaultShapeTypeChooser */
    public JPostGISDataPanel() {
        initComponents();

        params.put(DBTYPE.getName().toString(), "postgisng");
        params.put(HOST.getName().toString(), "localhost");
        params.put(PORT.getName().toString(), 5432);
        params.put(SCHEMA.getName().toString(), "public");
        params.put(DATABASE.getName().toString(), "");
        params.put(USER.getName().toString(), "");
        params.put(PASSWD.getName().toString(), "");

        setProperties(params);
    }

    public Map getProperties() {
        return params;
    }

    private void setProperties(Map map) {

        if(map == null){
            throw new NullPointerException();
        }
        
        Object val = null;
        
        val = map.get(HOST.getName().toString());
        jtf_host.setText( (val == null) ? "" : val.toString());
        val = map.get(PORT.getName().toString());
        jtf_port.setValue((val == null) ? "" : val);
        val = map.get(SCHEMA.getName().toString());
        jtf_schema.setText((val == null) ? "" : val.toString());
        val = map.get(DATABASE.getName().toString());
        jtf_database.setText((val == null) ? "" : val.toString());
        val = map.get(USER.getName().toString());
        jtf_user.setText((val == null) ? "" : val.toString());
        val = map.get(PASSWD.getName().toString());
        jtf_password.setText((val == null) ? "" : val.toString());
        
    }

    private void refreshTable() {

        if (store != null) {
            try {
                guiLayerList.setModel(new DefaultComboBoxModel(store.getTypeNames()));
            } catch (DataStoreException ex) {
                System.out.println(ex);
            }
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        but_refresh = new JButton();
        jXTitledPanel1 = new JXTitledPanel();
        jLabel1 = new JLabel();
        jtf_host = new JTextField();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jtf_schema = new JTextField();
        jLabel4 = new JLabel();
        jtf_database = new JTextField();
        jLabel5 = new JLabel();
        jtf_user = new JTextField();
        jLabel6 = new JLabel();
        jtf_password = new JPasswordField();
        jtf_port = new JSpinner();
        jScrollPane1 = new JScrollPane();
        guiLayerList = new JList();

        but_refresh.setText(PostgisResource.getString("test")); // NOI18N
        but_refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                actionRefresh(evt);
            }
        });

        jXTitledPanel1.setBorder(null);







        jXTitledPanel1.setTitle(PostgisResource.getString("connection")); // NOI18N
        jLabel1.setText(PostgisResource.getString("host")); // NOI18N
        jLabel2.setText(PostgisResource.getString("port")); // NOI18N
        jLabel3.setText(PostgisResource.getString("schema")); // NOI18N
        jLabel4.setText(PostgisResource.getString("database")); // NOI18N
        jLabel5.setText(PostgisResource.getString("user")); // NOI18N
        jLabel6.setText(PostgisResource.getString("password")); // NOI18N
        jtf_port.setModel(new SpinnerNumberModel(Integer.valueOf(5432), Integer.valueOf(0), null, Integer.valueOf(1)));

        GroupLayout jXTitledPanel1Layout = new GroupLayout(jXTitledPanel1);
        jXTitledPanel1.setLayout(jXTitledPanel1Layout);
        jXTitledPanel1Layout.setHorizontalGroup(
            jXTitledPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.LEADING, false)
                    .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jtf_host, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jtf_port, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtf_password, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jtf_user, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jtf_database, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(jtf_schema, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
                .addContainerGap())
        );

        jXTitledPanel1Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6});

        jXTitledPanel1Layout.setVerticalGroup(
            jXTitledPanel1Layout.createParallelGroup(Alignment.LEADING)
            .addGroup(jXTitledPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtf_host, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtf_port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtf_schema, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtf_database, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtf_user, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(jXTitledPanel1Layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtf_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(guiLayerList);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jXTitledPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                    .addComponent(but_refresh, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(but_refresh)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                    .addComponent(jXTitledPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void actionRefresh(ActionEvent evt) {//GEN-FIRST:event_actionRefresh

        params.clear();
        params.put(DBTYPE.getName().toString(), "postgisng");
        params.put(HOST.getName().toString(), jtf_host.getText());
        params.put(PORT.getName().toString(), (Integer)jtf_port.getValue());
        params.put(SCHEMA.getName().toString(), jtf_schema.getText());
        params.put(DATABASE.getName().toString(), jtf_database.getText());
        params.put(USER.getName().toString(), jtf_user.getText());
        params.put(PASSWD.getName().toString(), new String(jtf_password.getPassword()));

        but_refresh.setEnabled(false);
        new Thread() {

            @Override
            public void run() {
                try {
                    store = DataStoreFinder.getDataStore(params);
                    refreshTable();
                } catch (DataStoreException ex) {
                    Exceptions.printStackTrace(ex);
                    store = null;
                }

            but_refresh.setEnabled(true);
            }
        }.start();
        
    }//GEN-LAST:event_actionRefresh

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton but_refresh;
    private JList guiLayerList;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JXTitledPanel jXTitledPanel1;
    private JTextField jtf_database;
    private JTextField jtf_host;
    private JPasswordField jtf_password;
    private JSpinner jtf_port;
    private JTextField jtf_schema;
    private JTextField jtf_user;
    // End of variables declaration//GEN-END:variables
    
    public Map<String,GISSourceInfo> createSources() {
        final Map<String,Serializable> params = new HashMap<String,Serializable>();
        params.put(DBTYPE.getName().toString(), "postgisng");
        params.put(HOST.getName().toString(), jtf_host.getText());
        params.put(PORT.getName().toString(), (Integer)jtf_port.getValue());
        params.put(SCHEMA.getName().toString(), jtf_schema.getText());
        params.put(DATABASE.getName().toString(), jtf_database.getText());
        params.put(USER.getName().toString(), jtf_user.getText());
        params.put(PASSWD.getName().toString(), new String(jtf_password.getPassword()));
        final GISSourceInfo info = new GISSourceInfo(-1, PostGISSourceService.SERVICE_ID, params);
        final String name = jtf_host.getText() +"_"+jtf_schema.getText()+"_"+jtf_database.getText();
        final Map<String,GISSourceInfo> sources = new HashMap<String, GISSourceInfo>();
        sources.put(name, info);
        return sources;
    }
}
