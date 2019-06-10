package com.carlschroedl.gephi.plugin.minimumspanningtree;

import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.ProgressTicket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * See http://wiki.gephi.org/index.php/HowTo_write_a_metric#Create_Statistics
 *
 * @author Carl Schroedl <carlschroedl@gmail.com>
 */
public class MinimumSpanningTree implements Statistics, LongTask {

    private boolean cancel = false;
    private ProgressTicket progressTicket;
    private MinimumSpanningTreeAlgorithm stAlgorithm;
    
    private boolean directed;
    
    public MinimumSpanningTree(){
    }
    
    @Override
    public void execute(GraphModel graphModel){
        this.stAlgorithm = new KruskalsAlgorithm();
        this.stAlgorithm.setMaxSpanningTree(isMaximumSpanningTree());
        stAlgorithm.execute(graphModel);
    }
    /** Only useful if the algorithm takes graph type into account. */

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isMaximumSpanningTree() {
        String[] values = {"Minimum", "Maximum"};
        Object selected = JOptionPane.showInputDialog(null, "Minimum/Maximum Spanning Tree", "Selection", JOptionPane.DEFAULT_OPTION, null, values, "Minimum");
        if (selected.toString() == "Minimum") {
            return false;
        }
        return true;
    }

    /** ----------------------------------------------------------- */

    @Override
    public String getReport() { //delegate
        //Write the report HTML string here
        return stAlgorithm.getReport();
    }

    @Override   //delegate
    public boolean cancel() {
        return stAlgorithm.cancel();
    }

    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progressTicket = progressTicket;
    }
}
