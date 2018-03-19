import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import jdk.internal.org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by chaebyeonghun on 2018. 2. 20..
 */
public class MethodPrinter {

    public static void main(String[] args) throws Exception{
        File file = new File("/Users/chaebyeonghun/Desktop/AstJavaParser/src/source/");

        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath()+"/"+"File1.java");

        // parse it
        CompilationUnit cu = JavaParser.parse(fileInputStream);

        // visit and print the methods names
        //cu.accept(new MethodVisitor(), null);
        System.out.println(cu.getClassByName("ParsingController"));
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }
}
