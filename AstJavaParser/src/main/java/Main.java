import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chaebyeonghun on 2018. 2. 21..
 */
public class Main {

    public static final String treeOption = "TREE_OPTION";
    public static final String revisedOption = "REVISED_OPTION";
    public static final String representationOption = "REPRESENTATION_OPTION";
    public static final String fourthOption = "FOURTH_OPTION";
    public static final String handCraftOption = "HAND_OPTION";


    public static void main(String[] args) throws FileNotFoundException {
        testRepresentation("/Users/chaebyeonghun/Desktop/tomcat/","/Users/chaebyeonghun/Desktop/tomcat_representation/");


    }

    private static void testAllTree(String inputPath, String outputPath) throws FileNotFoundException {

        FileController fileController = new FileController(inputPath);
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByFullTreeStructure();
        parsingController.printParsingNode();

        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), outputPath);

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testHandCraftTree(String inputPath, String outputPath) throws FileNotFoundException{

        FileController fileController = new FileController(inputPath);
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByHandCraftStructure();
        parsingController.printHandCraftStructParsingNode();

        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), outputPath);

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private static void testRevised(String path, String outputPath) throws FileNotFoundException {
        FileController fileController = new FileController(path);

        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByRevisedStructure();
        parsingController.printRevisedStructParsingNode();
        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), outputPath);

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testRepresentation(String inputPath, String outputPath) throws FileNotFoundException {
        FileController fileController = new FileController(inputPath);
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByRepresentiveTreeStructure();

        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), outputPath);

        parsingController.printRepresentationParsingNode();

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void testFourth(String path,String outputPath) throws FileNotFoundException {
        FileController fileController = new FileController(path);
        ParsingController parsingController = new ParsingController(fileController.getPathFileNames());
        ArrayList<ArrayList<ParsingNode>> parsingNodes = parsingController.parsingNodeByFourthStructure();

        FileOutController fileOutController = new FileOutController(parsingNodes, fileController.getFileNames(), outputPath);

        //parsingController.printFourthStructParsingNode();

        try {
            fileOutController.fileOut();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void printAST(String option, String path) throws FileNotFoundException{
        FileController fileController = new FileController(path);
        ParsingController ps;
        switch (option){
            case treeOption:
                ps = new ParsingController(fileController.getPathFileNames());
                ps.printParsingNode();
                break;
            case revisedOption:
                ps = new ParsingController(fileController.getPathFileNames());
                ps.printRevisedStructParsingNode();
                break;
            case representationOption:
                ps = new ParsingController(fileController.getPathFileNames());
                ps.printRepresentationParsingNode();
                break;
            case fourthOption:
                ps = new ParsingController(fileController.getPathFileNames());
                ps.printFourthStructParsingNode();
                break;
            case handCraftOption:
                ps = new ParsingController(fileController.getPathFileNames());
                ps.printHandCraftStructParsingNode();
                break;
            default:
                System.out.println("Wrong code");

        }

    }
}
