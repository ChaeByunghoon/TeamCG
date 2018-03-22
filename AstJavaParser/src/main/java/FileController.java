import java.io.File;
import java.util.ArrayList;

/**
 * Created by chaebyeonghun on 2018. 2. 21..
 */
/*
* 특정 디렉토리를 지정한 후 파일리스트를 받아오는 클래스
* */
public class FileController {

    private ArrayList<String> fileNames;
    private ArrayList<String> pathFileNames;
    private String path;


    public FileController(String path){
        //생성자 특정 디렉토리의 파일들의 리스트를 받아온다.
        fileNames = new ArrayList<>();
        pathFileNames = new ArrayList<>();
        this.path = path;
        setFileList();

    }
    private void setFileList(){

        // "/Desktop/AstJavaParser/src/main/source"
        File dirFile = new File(path);
        File[] fileList = dirFile.listFiles();
        for(File tempFile : fileList) {
            if(tempFile.isFile()) {
                if(tempFile.getName().endsWith(".java")){
                    String tempFileName = tempFile.getName();
                        this.fileNames.add(tempFileName);
                        this.pathFileNames.add(path+"/"+tempFileName);
                }

            }
        }
    }
    public ArrayList<String> getFileNames(){
        return this.fileNames;
    }
    public ArrayList<String> getPathFileNames(){
        return this.pathFileNames;
    }
    public void printFileList(){
        System.out.println(fileNames.toString());
    }
    public void printPathFileList(){
        System.out.println(fileNames.toString());
    }


}
