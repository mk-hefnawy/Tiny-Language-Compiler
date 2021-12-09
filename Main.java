public class Main {

    public static void main(String a[]){

        String [] file = {"READ", "IDENTIFIER", "SEMICOLON",
                "IF", "NUMBER","LESSTHAN","IDENTIFIER", "THEN",
                "IDENTIFIER", "ASSIGN", "NUMBER","SEMICOLON",
                "REPEAT",
                "IDENTIFIER","ASSIGN" ,"IDENTIFIER","MULT","IDENTIFIER","SEMICOLON",
                "IDENTIFIER","ASSIGN" ,"IDENTIFIER","MINUS","NUMBER",
                "UNTIL","IDENTIFIER","EQUAL","NUMBER","SEMICOLON",
                "WRITE", "IDENTIFIER", "END"};

        Syntax syn = new Syntax(file);
        Tree program = syn.program();
        Tree.printPreorder(program.root);

    }
}