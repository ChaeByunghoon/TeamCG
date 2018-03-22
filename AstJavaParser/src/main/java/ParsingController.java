import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by chaebyeonghun on 2018. 2. 20..
 */
// 파싱에 관해 컨트롤 하는 함수
// 노드 파싱과 파싱한 것을 결과로 프린트할 수 있는 함수가 있다.
public class ParsingController {

    ArrayList<CompilationUnit> units = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {


        ArrayList<CompilationUnit> units = new ArrayList<>();
        FileController fileController = new FileController("/Users/chaebyeonghun/Desktop/AstJavaParser/src/source/");


    }

    /*
    * 생성자에서 파일리스트를 받아와서 CompilationUnit 생성 그리고 어레이리스트에 추가
    * */
    public ParsingController(ArrayList<String> fileList) throws FileNotFoundException {

        for (String filename : fileList) {
            CompilationUnit tempCu = JavaParser.parse(new FileInputStream(filename));
            units.add(tempCu);
        }
    }

    //모든 트리
    public ArrayList<ArrayList<ParsingNode>> parsingNodeByFullTreeStructure() {

        TreeStructVisitor rnSelector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            rnSelector = new TreeStructVisitor();
            rnSelector.visit(cu,0);
            nodeDatas.add(rnSelector.getParsingNodes());
        }

        return nodeDatas;
    }
    //일단 대표노드
    public ArrayList<ArrayList<ParsingNode>> parsingNodeByRepresentiveTreeStructure(){
        RepresentationNodeVisitor rnSelector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            rnSelector = new RepresentationNodeVisitor();
            rnSelector.visit(cu,0);
            nodeDatas.add(rnSelector.getParsingNodes());
        }

        return nodeDatas;
    }
    public ArrayList<ArrayList<ParsingNode>> parsingNodeByRevisedStructure(){
        RevisedStructVisitor rsSelector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            rsSelector = new RevisedStructVisitor();
            rsSelector.visit(cu,0);
            nodeDatas.add(rsSelector.getParsingNodes());
        }
        return nodeDatas;
    }
    public ArrayList<ArrayList<ParsingNode>> parsingNodeByFourthStructure(){
        FourthStructVisitor rsSelector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            rsSelector = new FourthStructVisitor();
            rsSelector.visit(cu,0);
            nodeDatas.add(rsSelector.getParsingNodes());
        }
        return nodeDatas;
    }



    public void printParsingNode(){
        TreeStructVisitor rnSelector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            rnSelector = new TreeStructVisitor();
            rnSelector.visit(cu,0);

            nodeDatas.add(rnSelector.getParsingNodes());
        }
        for(int i = 0; i < nodeDatas.size(); i++){
            for(int j = 0 ; j < nodeDatas.get(i).size(); j++){
                System.out.println(nodeDatas.get(i).get(j).getNodeRepresentation());
            }
        }
    }
    public void printRepresentationParsingNode(){
        RepresentationNodeVisitor rnSelector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            rnSelector = new RepresentationNodeVisitor();
            rnSelector.visit(cu,0);

            nodeDatas.add(rnSelector.getParsingNodes());
        }
        for(int i = 0; i < nodeDatas.size(); i++){
            for(int j = 0 ; j < nodeDatas.get(i).size(); j++){
                System.out.println(nodeDatas.get(i).get(j).getNodeRepresentation());
            }
        }
    }
    public void printRevisedStructParsingNode(){
        RevisedStructVisitor selector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            selector = new RevisedStructVisitor();

            selector.visit(cu, 0);
            nodeDatas.add(selector.getParsingNodes());
        }
        for(int i = 0; i < nodeDatas.size(); i++){
            for(int j = 0 ; j < nodeDatas.get(i).size(); j++){
                System.out.println(nodeDatas.get(i).get(j).getNodeRepresentation());
            }
        }
    }
    public void printFourthStructParsingNode(){
        FourthStructVisitor selector;
        ArrayList<ArrayList<ParsingNode>> nodeDatas = new ArrayList<>();
        for(CompilationUnit cu : units){
            selector = new FourthStructVisitor();

            selector.visit(cu, 0);
            nodeDatas.add(selector.getParsingNodes());
        }
        for(int i = 0; i < nodeDatas.size(); i++){
            for(int j = 0 ; j < nodeDatas.get(i).size(); j++){
                System.out.println(nodeDatas.get(i).get(j).getNodeRepresentation());
            }
        }

    }


}

