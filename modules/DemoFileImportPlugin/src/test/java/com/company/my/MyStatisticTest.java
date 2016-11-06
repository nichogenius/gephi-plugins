package com.company.my;

import java.io.File;
import java.net.URISyntaxException;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;

public class MyStatisticTest {

    private static final String PATH = "/com/company/my/karate.gml";
    private File file;
    private ProjectController projectController;
    private Container container;

    public MyStatisticTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws URISyntaxException {
        file = new File(getClass().getResource(PATH).toURI());
        if (!file.exists() || !file.canRead()) {
            throw new RuntimeException("file '" + PATH + "' does not exist, or cannot be read");
        } else {
            System.out.println("Confirmed: file '" + PATH + "' exists and can be read.");
        }
        //results are the same with this suggested alternate:
//        file = Utilities.toFile(getClass().getResource(PATH).toURI());

        projectController = Lookup.getDefault().lookup(ProjectController.class);
        projectController.newProject();
        projectController.newWorkspace(projectController.getCurrentProject());
    }

    @After
    public void tearDown() {
        projectController.closeCurrentWorkspace();
        projectController.closeCurrentProject();
    }

    @Test
    public void testFileGettingImportControllerFromLookup() throws Exception {
        FileObject fileObject = FileUtil.toFileObject(file);
        assertNotNull(fileObject);//This was the problem, should not be null
        
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        container = importController.importFile(file);
        assertNotNull(container);
        importController.process(container, new DefaultProcessor(), projectController.getCurrentWorkspace());

    }

}
