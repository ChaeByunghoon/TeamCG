import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.modules.*;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by chaebyeonghun on 2018. 2. 25..
 */
public class TreeStructVisitor extends VoidVisitorAdapter<Integer> {

    ArrayList<ParsingNode> parsingNodes = new ArrayList<>();

    public void out(Node n, int indentLevel) {

        parsingNodes.add(new ParsingNode(n.getClass().getSimpleName(), indentLevel));
    }

    public ArrayList<ParsingNode> getParsingNodes() {
        return parsingNodes;
    }

    @Override
    public void visit(final AnnotationDeclaration n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getName().accept(this, arg + 1);
        if (n.getMembers() != null) {
            for (final BodyDeclaration<?> member : n.getMembers()) {
                member.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final AnnotationMemberDeclaration n, final Integer arg) {
        out(n, arg);
        visitAnnotations(n, arg + 1);
        n.getType().accept(this, arg + 1);
        n.getDefaultValue().ifPresent(d -> d.accept(this, arg + 1));
    }

    @Override
    public void visit(final ArrayAccessExpr n, final Integer arg) {
        out(n, arg);
        n.getName().accept(this, arg + 1);
        n.getIndex().accept(this, arg + 1);
    }

    @Override
    public void visit(final ArrayCreationExpr n, final Integer arg) {
        out(n, arg);

        n.getElementType().accept(this, arg + 1);
        for (ArrayCreationLevel level : n.getLevels()) {
            level.accept(this, arg + 1);
        }
        n.getInitializer().ifPresent(i -> i.accept(this, arg + 1));
    }

    @Override
    public void visit(final ArrayInitializerExpr n, final Integer arg) {
        out(n, arg);

        if (n.getValues() != null) {
            for (final Expression expr : n.getValues()) {
                expr.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final AssertStmt n, final Integer arg) {
        out(n, arg);

        n.getCheck().accept(this, arg + 1);
        n.getMessage().ifPresent(m -> m.accept(this, arg + 1));
    }

    @Override
    public void visit(final AssignExpr n, final Integer arg) {
        out(n, arg);
        n.getTarget().accept(this, arg + 1);
        n.getValue().accept(this, arg + 1);
    }

    @Override
    public void visit(final BinaryExpr n, final Integer arg) {
        out(n, arg);
        n.getLeft().accept(this, arg + 1);
        n.getRight().accept(this, arg + 1);
    }


    @Override
    public void visit(final BlockStmt n, final Integer arg) {
        out(n, arg);
        if (n.getStatements() != null) {
            for (final Statement s : n.getStatements()) {
                s.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final BooleanLiteralExpr n, final Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(final BreakStmt n, final Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(final CastExpr n, final Integer arg) {
        out(n, arg);
        n.getType().accept(this, arg + 1);
        n.getExpression().accept(this, arg + 1);
    }

    @Override
    public void visit(final CatchClause n, final Integer arg) {
        out(n, arg);
        n.getParameter().accept(this, arg + 1);
        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final CharLiteralExpr n, final Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(final ClassExpr n, final Integer arg) {
        out(n, arg);
        n.getType().accept(this, arg + 1);
    }

    @Override
    public void visit(final ClassOrInterfaceDeclaration n, final Integer arg) {
        out(n, arg);
        visitAnnotations(n, arg + 1);
        n.getName().accept(this, arg + 1);
        for (final TypeParameter t : n.getTypeParameters()) {
            t.accept(this, arg + 1);
        }
        for (final ClassOrInterfaceType c : n.getExtendedTypes()) {
            c.accept(this, arg + 1);
        }
        for (final ClassOrInterfaceType c : n.getImplementedTypes()) {
            c.accept(this, arg + 1);
        }
        for (final BodyDeclaration<?> member : n.getMembers()) {
            member.accept(this, arg + 1);
        }
    }

    @Override
    public void visit(final ClassOrInterfaceType n, final Integer arg) {
        out(n, arg);
        visitAnnotations(n, arg + 1);
        n.getScope().ifPresent(s -> s.accept(this, arg + 1));
        n.getTypeArguments().ifPresent(tas -> tas.forEach(ta -> ta.accept(this, arg + 1)));
    }

    @Override
    public void visit(final CompilationUnit n, final Integer arg) {
        out(n, arg);

        n.getPackageDeclaration().ifPresent(p -> p.accept(this, arg + 1));
        if (n.getImports() != null) {
            for (final ImportDeclaration i : n.getImports()) {
                i.accept(this, arg + 1);
            }
        }
        if (n.getTypes() != null) {
            for (final TypeDeclaration<?> typeDeclaration : n.getTypes()) {
                typeDeclaration.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final ConditionalExpr n, final Integer arg) {
        out(n, arg);

        n.getCondition().accept(this, arg + 1);
        n.getThenExpr().accept(this, arg + 1);
        n.getElseExpr().accept(this, arg + 1);
    }

    @Override
    public void visit(final ConstructorDeclaration n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        if (n.getTypeParameters() != null) {
            for (final TypeParameter t : n.getTypeParameters()) {
                t.accept(this, arg + 1);
            }
        }
        n.getName().accept(this, arg + 1);
        if (n.getParameters() != null) {
            for (final Parameter p : n.getParameters()) {
                p.accept(this, arg + 1);
            }
        }
        if (n.getThrownExceptions() != null) {
            for (final ReferenceType name : n.getThrownExceptions()) {
                name.accept(this, arg + 1);
            }
        }
        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final ContinueStmt n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final DoStmt n, final Integer arg) {
        out(n, arg);

        n.getBody().accept(this, arg + 1);
        n.getCondition().accept(this, arg + 1);
    }

    @Override
    public void visit(final DoubleLiteralExpr n, final Integer arg) {
        out(n, arg);

    }


    @Override
    public void visit(final EmptyStmt n, final Integer arg) {
        out(n, arg);

    }


    @Override
    public void visit(final EnclosedExpr n, final Integer arg) {
        out(n, arg);

        // n.getInner().isPresent(i -> i.accept(this, arg + 1));
    }

    @Override
    public void visit(final EnumConstantDeclaration n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        if (n.getArguments() != null) {
            for (final Expression e : n.getArguments()) {
                e.accept(this, arg + 1);
            }
        }
        if (n.getClassBody() != null) {
            for (final BodyDeclaration<?> member : n.getClassBody()) {
                member.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final EnumDeclaration n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getName().accept(this, arg + 1);
        if (n.getImplementedTypes() != null) {
            for (final ClassOrInterfaceType c : n.getImplementedTypes()) {
                c.accept(this, arg + 1);
            }
        }
        if (n.getEntries() != null) {
            for (final EnumConstantDeclaration e : n.getEntries()) {
                e.accept(this, arg + 1);
            }
        }
        if (n.getMembers() != null) {
            for (final BodyDeclaration<?> member : n.getMembers()) {
                member.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final ExplicitConstructorInvocationStmt n, final Integer arg) {
        out(n, arg);

        if (!n.isThis() && n.getExpression().isPresent()) {
            n.getExpression().get().accept(this, arg + 1);
        }
        n.getTypeArguments().ifPresent(tas -> tas.forEach(ta -> ta.accept(this, arg + 1)));
        if (n.getArguments() != null) {
            for (final Expression e : n.getArguments()) {
                e.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final ExpressionStmt n, final Integer arg) {
        out(n, arg);

        n.getExpression().accept(this, arg + 1);
    }

    @Override
    public void visit(final FieldAccessExpr n, final Integer arg) {
        out(n, arg);

        n.getScope().accept(this, arg + 1);
        n.getName().accept(this, arg + 1);
    }

    @Override
    public void visit(final FieldDeclaration n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getElementType().accept(this, arg + 1);
        for (final VariableDeclarator var : n.getVariables()) {
            var.accept(this, arg + 1);
        }
    }

    @Override
    public void visit(final ForeachStmt n, final Integer arg) {
        out(n, arg);

        n.getVariable().accept(this, arg + 1);
        n.getIterable().accept(this, arg + 1);
        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final ForStmt n, final Integer arg) {
        out(n, arg);

        for (final Expression e : n.getInitialization()) {
            e.accept(this, arg + 1);
        }
        n.getCompare().ifPresent(c -> c.accept(this, arg + 1));
        for (final Expression e : n.getUpdate()) {
            e.accept(this, arg + 1);
        }
        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final IfStmt n, final Integer arg) {
        out(n, arg);

        n.getCondition().accept(this, arg + 1);
        n.getThenStmt().accept(this, arg + 1);
        n.getElseStmt().ifPresent(es -> es.accept(this, arg + 1));
    }

    @Override
    public void visit(final InitializerDeclaration n, final Integer arg) {
        out(n, arg);

        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final InstanceOfExpr n, final Integer arg) {
        out(n, arg);

        n.getExpression().accept(this, arg + 1);
        n.getType().accept(this, arg + 1);
    }

    @Override
    public void visit(final IntegerLiteralExpr n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final JavadocComment n, final Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(final LabeledStmt n, final Integer arg) {
        out(n, arg);

        n.getStatement().accept(this, arg + 1);
    }

    @Override
    public void visit(final LineComment n, final Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(final LongLiteralExpr n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final MarkerAnnotationExpr n, final Integer arg) {
        out(n, arg);

        n.getName().accept(this, arg + 1);
    }

    @Override
    public void visit(final MemberValuePair n, final Integer arg) {
        out(n, arg);

        n.getValue().accept(this, arg + 1);
    }

    @Override
    public void visit(final MethodCallExpr n, final Integer arg) {
        out(n, arg);

        n.getScope().ifPresent(s -> s.accept(this, arg + 1));
        n.getTypeArguments().ifPresent(tas -> tas.forEach(ta -> ta.accept(this, arg + 1)));
        n.getName().accept(this, arg + 1);
        if (n.getArguments() != null) {
            for (final Expression e : n.getArguments()) {
                e.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final MethodDeclaration n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        if (n.getTypeParameters() != null) {
            for (final TypeParameter t : n.getTypeParameters()) {
                t.accept(this, arg + 1);
            }
        }
        n.getType().accept(this, arg + 1);
        n.getName().accept(this, arg + 1);
        if (n.getParameters() != null) {
            for (final Parameter p : n.getParameters()) {
                p.accept(this, arg + 1);
            }
        }
        if (n.getThrownExceptions() != null) {
            for (final ReferenceType name : n.getThrownExceptions()) {
                name.accept(this, arg + 1);
            }
        }
        n.getBody().ifPresent(b -> b.accept(this, arg + 1));
    }

    @Override
    public void visit(final NameExpr n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final NormalAnnotationExpr n, final Integer arg) {
        out(n, arg);

        n.getName().accept(this, arg + 1);
        if (n.getPairs() != null) {
            for (final MemberValuePair m : n.getPairs()) {
                m.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final NullLiteralExpr n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final ObjectCreationExpr n, final Integer arg) {
        out(n, arg);

        n.getScope().ifPresent(s -> s.accept(this, arg + 1));
        n.getTypeArguments().ifPresent(tas -> tas.forEach(ta -> ta.accept(this, arg + 1)));
        n.getType().accept(this, arg + 1);
        if (n.getArguments() != null) {
            for (final Expression e : n.getArguments()) {
                e.accept(this, arg + 1);
            }
        }
        n.getAnonymousClassBody().ifPresent(acb -> acb.forEach(m -> m.accept(this, arg + 1)));
    }

    @Override
    public void visit(final Parameter n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getType().accept(this, arg + 1);
        //n.getId().accept(this, arg + 1);
        n.getName().accept(this, arg + 1);
    }

    @Override
    public void visit(final PrimitiveType n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
    }

    //ㅇㅣ부분은 고민좀해봐야함
    @Override
    public void visit(Name n, Integer arg) {
        out(n, arg);
        System.out.println(n.asString());


    }

    @Override
    public void visit(SimpleName n, Integer arg) {
        out(n, arg);
        System.out.println(n.asString());
    }


    @Override
    public void visit(ArrayType n, Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getComponentType().accept(this, arg + 1);
    }

    @Override
    public void visit(ArrayCreationLevel n, Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getDimension().ifPresent(d -> d.accept(this, arg + 1));
    }

    @Override
    public void visit(final IntersectionType n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        for (ReferenceType element : n.getElements()) {
            element.accept(this, arg + 1);
        }
    }

    @Override
    public void visit(final UnionType n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        for (ReferenceType element : n.getElements()) {
            element.accept(this, arg + 1);
        }
    }

    @Override
    public void visit(final ReturnStmt n, final Integer arg) {
        out(n, arg);

        n.getExpression().ifPresent(e -> e.accept(this, arg + 1));
    }

    @Override
    public void visit(final SingleMemberAnnotationExpr n, final Integer arg) {
        out(n, arg);

        n.getName().accept(this, arg + 1);
        n.getMemberValue().accept(this, arg + 1);
    }

    @Override
    public void visit(final StringLiteralExpr n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final SuperExpr n, final Integer arg) {
        out(n, arg);

        n.getClassExpr().ifPresent(ce -> ce.accept(this, arg + 1));
    }

    @Override
    public void visit(final SwitchEntryStmt n, final Integer arg) {
        out(n, arg);

        n.getLabel().ifPresent(l -> l.accept(this, arg + 1));
        if (n.getStatements() != null) {
            for (final Statement s : n.getStatements()) {
                s.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final SwitchStmt n, final Integer arg) {
        out(n, arg);

        n.getSelector().accept(this, arg + 1);
        if (n.getEntries() != null) {
            for (final SwitchEntryStmt e : n.getEntries()) {
                e.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final SynchronizedStmt n, final Integer arg) {
        out(n, arg);

        n.getExpression().accept(this, arg + 1);
        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final ThisExpr n, final Integer arg) {
        out(n, arg);

        n.getClassExpr().ifPresent(ce -> ce.accept(this, arg + 1));
    }

    @Override
    public void visit(final ThrowStmt n, final Integer arg) {
        out(n, arg);

        n.getExpression().accept(this, arg + 1);
    }

    @Override
    public void visit(final TryStmt n, final Integer arg) {
        out(n, arg);

        for (final Expression v : n.getResources()) {
            v.accept(this, arg + 1);
        }
        n.getTryBlock().accept(this, arg + 1);
        if (n.getCatchClauses() != null) {
            for (final CatchClause c : n.getCatchClauses()) {
                c.accept(this, arg + 1);
            }
        }
        n.getFinallyBlock().ifPresent(f -> f.accept(this, arg + 1));
    }

    @Override
    public void visit(LocalClassDeclarationStmt n, Integer arg) {
        super.visit(n, arg);
    }

    @Override
    public void visit(final TypeParameter n, final Integer arg) {
        out(n, arg);

        if (n.getTypeBound() != null) {
            for (final ClassOrInterfaceType c : n.getTypeBound()) {
                c.accept(this, arg + 1);
            }
        }
    }

    @Override
    public void visit(final UnaryExpr n, final Integer arg) {
        out(n, arg);

        n.getExpression().accept(this, arg + 1);
    }

    @Override
    public void visit(final UnknownType n, final Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(final VariableDeclarationExpr n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getElementType().accept(this, arg + 1);
        for (final VariableDeclarator v : n.getVariables()) {
            v.accept(this, arg + 1);
        }
    }

    @Override
    public void visit(final VariableDeclarator n, final Integer arg) {
        out(n, arg);

        n.getName().accept(this, arg + 1);
        n.getInitializer().ifPresent(i -> i.accept(this, arg + 1));
    }


    @Override
    public void visit(final VoidType n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);

    }

    @Override
    public void visit(final WhileStmt n, final Integer arg) {
        out(n, arg);

        n.getCondition().accept(this, arg + 1);
        n.getBody().accept(this, arg + 1);
    }

    @Override
    public void visit(final WildcardType n, final Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getExtendedType().ifPresent(e -> e.accept(this, arg + 1));
        n.getSuperType().ifPresent(s -> s.accept(this, arg + 1));
    }

    @Override
    public void visit(LambdaExpr n, final Integer arg) {
        out(n, arg);

        if (n.getParameters() != null) {
            for (final Parameter a : n.getParameters()) {
                a.accept(this, arg + 1);
            }
        }
        if (n.getBody() != null) {
            n.getBody().accept(this, arg + 1);
        }
    }

    @Override
    public void visit(MethodReferenceExpr n, final Integer arg) {
        out(n, arg);

        n.getTypeArguments().ifPresent(tas -> tas.forEach(ta -> ta.accept(this, arg + 1)));
        if (n.getScope() != null) {
            n.getScope().accept(this, arg + 1);
        }
    }

    @Override
    public void visit(TypeExpr n, final Integer arg) {
        out(n, arg);

        if (n.getType() != null) {
            n.getType().accept(this, arg + 1);
        }
    }


    @Override
    public void visit(NodeList n, Integer arg) {
        out(n.getParentNodeForChildren(), arg);
        for (Object node : n) {
            ((Node) node).accept(this, arg + 1);
        }
    }


    @Override
    public void visit(ModuleDeclaration n, Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(ModuleRequiresStmt n, Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(ModuleExportsStmt n, Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(ModuleProvidesStmt n, Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(ModuleUsesStmt n, Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(ModuleOpensStmt n, Integer arg) {
        out(n, arg);
    }

    @Override
    public void visit(UnparsableStmt n, Integer arg) {
        out(n, arg);

    }

    @Override
    public void visit(ReceiverParameter n, Integer arg) {
        out(n, arg);

        visitAnnotations(n, arg + 1);
        n.getType().accept(this, arg + 1);
        n.getName().accept(this, arg + 1);
    }

    @Override
    public void visit(VarType n, Integer arg) {
        out(n, arg);
    }

    /*
    private void visitComment(final Optional<? extends Comment> n, final Integer arg) {
        n.ifPresent(c -> out(c, arg));
        n.ifPresent(c -> c.accept(this, arg + 1));
    }
*/
    private void visitAnnotations(NodeWithAnnotations<?> n, Integer arg) {
        out((Node) n, arg);
        for (AnnotationExpr annotation : n.getAnnotations()) {
            annotation.accept(this, arg + 1);
        }
    }

}