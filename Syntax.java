import org.jetbrains.annotations.NotNull;

public class Syntax {

    public String [] file;
    public int token;

    public Syntax(String[] file){
        this.file = file;
        this.token = 0;
    }

    public void match(String tkn){
        if (tkn == file[token]) {
            token++;
        }
        else error();
    }
    public void unMatch(){
        token--;
    }
    public void error(){
        System.out.println("Error");

    }
    public Tree program(){
        return stmt_sequence();
    }

    public Tree stmt_sequence() {
        Tree stmt_sequence = stm();
        Tree current = new Tree();
        current.root = stmt_sequence.root;
        while ( token<file.length &&  file[token] == "SEMICOLON"){
            match("SEMICOLON");
            Tree temp = stm();
            current.root.neighbour=temp.root;
            current.root = temp.root;
        }

        return stmt_sequence;
    }

    public Tree stm(){
        Tree stm = new Tree();
        switch (file[token]){
            case "IF":
                stm = if_stmt();
                break;

            case "REPEAT":
                stm = repeat();
                break;

            case "IDENTIFIER":
                stm =assign_stmt();
                break;
            case "READ":
                stm = read_stmt();
                break;
            case "WRITE":
                stm = write_stmt();
                break;
            default:
                error();

        }
        return stm;
    }

    private Tree write_stmt() {
        Tree write_stmt = new Tree();
        match("WRITE");
        write_stmt.root = new Node("write", 1);
        write_stmt.root.midChild = exp().root;
        return write_stmt;
    }

    private Tree read_stmt() {
        Tree read_stmt = new Tree();
        match("READ");
        read_stmt.root = new Node("read", 1);
        match("IDENTIFIER");
        return read_stmt;
    }


    Tree assign_stmt() {
        Tree assign_stmt = new Tree();
        match("IDENTIFIER");
        assign_stmt.root = new Node("assign", 1);
        match("ASSIGN");
        assign_stmt.root.midChild = exp().root;
        return assign_stmt;

    }

    private Tree repeat() {
        Tree repeat_stmt = new Tree();
        match("REPEAT");
        repeat_stmt.root = new Node("repeat", 1); // creating the root of repeat tree
        repeat_stmt.root.leftChild = stmt_sequence().root;
        match("UNTIL");
        repeat_stmt.root.rightChild = exp().root;
        return repeat_stmt;
    }

    public Tree if_stmt () {
        Tree if_stmt = new Tree();
        match("IF");
        if_stmt.root = new Node("if", 1); // creating the root of if tree
        if_stmt.root.leftChild = exp().root;
        match("THEN");
        if_stmt.root.midChild = stmt_sequence().root;
        if(file[token] == "ELSE"){
            match("ELSE");
            if_stmt.root.rightChild = stmt_sequence().root;
        }
        match("END");
        return if_stmt;
    }

    public Tree exp() {
        Tree exp = simple_exp();
        Tree temp = new Tree();

        if(file[token] == "EQUAL" || file[token] == "LESSTHAN"){
            temp.root = comparison_op().root;
            temp.root.leftChild = exp.root;
            temp.root.rightChild = simple_exp().root;
            exp = temp;
        }

        return exp;
    }

    private Tree comparison_op() {
        Tree comparison_op = new Tree();
        if(file[token] == "EQUAL"){
            match("EQUAL");
            comparison_op.root = new Node("op", 0);
        }
        else if(file[token] == "LESSTHAN") {
            match("LESSTHAN");
            comparison_op.root = new Node("op", 0);
        }
        else error();
        return comparison_op;

    }
    private Tree simple_exp() {
        Tree simple_exp = new Tree();
        Tree temp = new Tree();

        simple_exp = term();
        while (file[token] == "PLUS" || file[token] == "MINUS"){
            temp.root = add_op().root;
            temp.root.leftChild = simple_exp.root;
            temp.root.rightChild = term().root;
            simple_exp = temp;
        }
        return simple_exp;
    }

    private Tree add_op() {
        Tree add_op = new Tree();
        if(file[token] == "PLUS"){
            match("PLUS");
            add_op.root = new Node("op", 0);
        }
        else if(file[token] == "MINUS") {
            match("MINUS");
            add_op.root = new Node("op", 0);
        }
        else error();
        return add_op;
    }

    private Tree term() {
        Tree term = factor();
        Tree temp = new Tree();

        while (file[token] == "MULT" || file[token] == "DIV"){
            temp.root = mul_op().root;
            temp.root.leftChild = term.root;
            temp.root.rightChild = factor().root;
            term = temp;
        }
        return term;
    }

    private Tree factor() {
        Tree factor = new Tree();
        switch (file[token]){
            case "OPENBRACKET":
                match("OPENBRACKET");
                factor.root = exp().root;
                match("CLOSEDBRACKET");
                break;
            case "NUMBER":
                match("NUMBER");
                factor.root = new Node("const", 0);
                break;
            case "IDENTIFIER":
                match("IDENTIFIER");
                factor.root = new Node("id", 0);
                break;
            default:
                error();
        }

        return factor;
    }

    private Tree mul_op() {
        Tree mul_op = new Tree();
        if(file[token] == "MULT") {
            match("MULT");
            mul_op.root = new Node("op", 0);

        }
        else if(file[token] == "DIV"){
            match("DIV");
            mul_op.root = new Node("op", 0);
        }
        else error();
        return mul_op;
    }



}