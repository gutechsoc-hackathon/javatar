/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package GephiGraph;

import java.io.File;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.Estimator;
import org.gephi.data.attributes.type.DynamicInteger;
import org.gephi.data.attributes.type.Interval;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.attribute.AttributeRangeBuilder;
import org.gephi.filters.plugin.dynamic.DynamicRangeBuilder;
import org.gephi.filters.plugin.dynamic.DynamicRangeBuilder.DynamicRangeFilter;
import org.gephi.filters.spi.FilterBuilder;
import org.gephi.graph.api.*;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DynamicProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

/**
 * This demo shows how to create a dynamic network from a set of 'static' files
 * using the <code>DynamicProcessor</code> (Time Frame Import). It also shows
 * how to read and filter dynamic attributes.
 * <p>
 * It uses a simple dataset of three GEXF files. The graph has a 'price' attribute,
 * which will therefore be set a <code>DYNAMIC_INTEGER</code> type automatically.
 * <p>
 * It does the following steps:
 * <ul>
 * <li>Create a project and a workspace, it is mandatory.</li>
 * <li>Import <code>timeframe1.gexf</code> into an import container.</li>
 * <li>Create an configure the <code>DynamicProcessor</code>.</li>
 * <li>Append the container for the time '2007'.</li>
 * <li>Import <code>timeframe2.gexf</code> and append it for '2008'.</li>
 * <li>Import <code>timeframe3.gexf</code> and append it for '2009'.</li>
 * <li>Read the 'price' value for each node, one can see the value for each interval.</li>
 * <li>Use <code>AVERAGE</code> and <code>MAX</code> evaluators to see how to get a
 * <code>Integer</code> result from a <code>DynamicInteger</code> for any time interval.</li>
 * <li>Create a dynamic range and attribute range filter queries. The aim is to
 * keep the graph [2007-2008] with average price superior or equal to seven.</li>
 * <li>Add the filter query to the filter model and execute it. Get a
 * <code>GraphView</code> as a result.</li>
 * <li>Verify the node '4' doesn't belong to the result, as it's average price is
 * lower than seven.</li></ul>
 * The <code>DynamicProcessor</code> is able to import <code>DOUBLE</code> or
 * <code>DATE</code> values. Default is <code>DOUBLE</code>.
 * <p>
 * When filtering the graph using a <code>DynamicRangeFilter</code>, parent filter
 * queries (e.g executed after) are using the visible interval to get a simple
 * result from a dynamic value. In this example that means the
 * <code>AttributeRangeFilter</code> is getting the 'price' from the [2007, 2008]
 * interval only. The filter uses the default <code>Estimator</code> set in the
 * <code>DynamicModel</code>. One can set these default estimators from the
 * <code>DynamicController</code>.
 * @author Mathieu Bastian
 */
public class ImportDynamic {

    public void script() {
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        //Import first file
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        Container container;
        try {
            File file = new File(getClass().getResource("/org/gephi/toolkit/demos/resources/timeframe1.gexf").toURI());
            container = importController.importFile(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Initialize the DynamicProcessor - which will append the container to the workspace
        DynamicProcessor dynamicProcessor = new DynamicProcessor();
        dynamicProcessor.setDateMode(false);    //Set 'true' if you set real dates (ex: yyyy-mm-dd), it's double otherwise
        dynamicProcessor.setLabelmatching(true);   //Set 'true' if node matching is done on labels instead of ids

        //Set date for this file
        dynamicProcessor.setDate("2007");

        //Process the container using the DynamicProcessor
        importController.process(container, dynamicProcessor, workspace);

        //Import second file
        try {
            File file = new File(getClass().getResource("/org/gephi/toolkit/demos/resources/timeframe2.gexf").toURI());
            container = importController.importFile(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Set date for this file
        dynamicProcessor.setDate("2008");

        //Process the second container
        importController.process(container, dynamicProcessor, workspace);

        //Import second file
        try {
            File file = new File(getClass().getResource("/org/gephi/toolkit/demos/resources/timeframe3.gexf").toURI());
            container = importController.importFile(file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //Set date for this file
        dynamicProcessor.setDate("2009");

        //Process the second container
        importController.process(container, dynamicProcessor, workspace);

        //Get the price attribute
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        Graph graph = graphModel.getGraph();
        for (Node n : graph.getNodes()) {
            DynamicInteger value = (DynamicInteger) n.getNodeData().getAttributes().getValue("price");
            System.out.println("'" + n.getNodeData().getLabel() + "': " + value.toString());
        }

        //Get the price attribute in average - learn more about ESTIMATOR
        for (Node n : graph.getNodes()) {
            DynamicInteger value = (DynamicInteger) n.getNodeData().getAttributes().getValue("price");

            Integer priceFrom2007to2009Avg = value.getValue(new Interval(2007, 2009), Estimator.AVERAGE);
            System.out.println("With AVERAGE estimator: '" + n.getNodeData().getLabel() + "': " + priceFrom2007to2009Avg);

            Integer priceFrom2007to2009Max = value.getValue(new Interval(2007, 2009), Estimator.MAX);
            System.out.println("With MAX estimator: '" + n.getNodeData().getLabel() + "': " + priceFrom2007to2009Max);
        }

        //Create a dynamic range filter query
        FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
        FilterBuilder[] builders = Lookup.getDefault().lookup(DynamicRangeBuilder.class).getBuilders();
        DynamicRangeFilter dynamicRangeFilter = (DynamicRangeFilter) builders[0].getFilter();     //There is only one TIME_INTERVAL column, so it's always the [0] builder
        Query dynamicQuery = filterController.createQuery(dynamicRangeFilter);

        //Create a attribute range filter query - on the price column
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        AttributeColumn priceCol = attributeModel.getNodeTable().getColumn("price");
        AttributeRangeBuilder.AttributeRangeFilter attributeRangeFilter = new AttributeRangeBuilder.AttributeRangeFilter(priceCol);
        Query priceQuery = filterController.createQuery(attributeRangeFilter);

        //Set dynamic query as child of price query
        filterController.add(priceQuery);
        filterController.add(dynamicQuery);
        filterController.setSubQuery(priceQuery, dynamicQuery);

        //Set the filters parameters - Keep nodes between 2007-2008 which have average price >= 7
        dynamicRangeFilter.setRange(new Range(2007.0, 2008.0));
        attributeRangeFilter.setRange(new Range(7, Integer.MAX_VALUE));

        //Execute the filter query
        GraphView view = filterController.filter(priceQuery);
        Graph filteredGraph = graphModel.getGraph(view);

        //Node 3 shoudln't be in this graph
        System.out.println("Node 3 in the filtered graph: " + filteredGraph.contains(graph.getNode("3")));
    }
}
