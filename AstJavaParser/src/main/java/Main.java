import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chaebyeonghun on 2018. 2. 21..
 */
public class Main {


    public static void main(String[] args) throws FileNotFoundException {

        testFourth();
       /* try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private static void testAllTree() throws FileNotFoundException {

        FileController fileController = new FileController("/Users/chaebyeonghun/Desktop/AstJavaParser/src/example/");
        fileController.setFileList();
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByFullTreeStructure();
        parsingController.printParsingNode();
    }

    private static void testRevised() throws FileNotFoundException {
        FileController fileController = new FileController("/Users/chaebyeonghun/Desktop/AstJavaParser/src/example/");
        fileController.setFileList();
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByRevisedStructure();
        parsingController.printRevisedStructParsingNode();
    }

    private static void testRepresentation() throws FileNotFoundException {
        FileController fileController = new FileController("/Users/chaebyeonghun/Desktop/AstJavaParser/src/example/");
        fileController.setFileList();
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByRepresentiveTreeStructure();

        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), "/Users/chaebyeonghun/Desktop/AstJavaParser/src/reTomcatOutput/");

        parsingController.printRepresentationParsingNode();

    }

    private static void testFourth() throws FileNotFoundException {
        FileController fileController = new FileController("/Users/chaebyeonghun/Desktop/AstJavaParser/src/tomcat/");
        fileController.setFileList();
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByFourthStructure();

        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), "/Users/chaebyeonghun/Desktop/AstJavaParser/src/reTomcatOutput/");

        //parsingController.printFourthStructParsingNode();

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
