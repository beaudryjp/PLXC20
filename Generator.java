import java.util.*;

public class Generator{
/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------- Constants --------------------*/

    public static final int LT          = 1;
    public static final int LTOREQ      = 2;
    public static final int GT          = 3;
    public static final int GTOREQ      = 4;
    public static final int EQ          = 5;
    public static final int NOTEQ       = 6;
    public static final int AND         = 7;
    public static final int OR          = 8;
    public static final int NOT         = 9;
    public static final int ADD         = 10;
    public static final int SUB         = 11;
    public static final int MUL         = 12;
    public static final int DIV         = 13;
    public static final int MIN         = 14;
    public static final int MOD         = 15;
    public static final int INCR        = 16;
    public static final int DECR        = 17;
    public static final int INCL        = 18;
    public static final int DECL        = 19;

/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------- Temporals --------------------*/

    public static int num_temp_vars     = -1;
    public static int num_labels        = -1;

/*--------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------- Arrays --------------------*/

    public static int array_position    = -1;
    public static String array_temp     = "";

/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------- Functions --------------------*/

    public static boolean func_implicit = false;
    public static boolean func_prototyp = false;
    public static int param_counter     = 1;
    public static int counter           = 1;
    public static int pointer_counter   = 0;
    public static int func_counter      = 0;
    public static String func_name      = null;
    public static String func_parent    = null;
    public static String func_type      = null;
    public static String tfunc          = null;

/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------- Functions --------------------*/

    public static void command(String cmd, String arg){
        System.out.println("\t" + cmd + " " + arg + ";");
    }

    public static void function_start(String f){
        System.out.println("\nfunction " + f + ":");
    }

    public static void function_end(String f){
        System.out.println("end " + f + ";\n");
    }

    public static void sentence_return(){
        System.out.println("\treturn;");
    }

    public static String function_scope_get_vars(String x, String y, int z){
        return x + "_" + y + "_" + z;
    }

    public static String scope_get_vars(String x, int y){
        return x + "_" + y;
    }

    public static boolean function_is_implicit(){
        return func_implicit;
    }

    public static void function_set_implicit(boolean x){
        func_implicit = x;
    }

    public static boolean function_is_prototype(){
        return func_prototyp;
    }

    public static void function_set_prototype(boolean x){
        func_prototyp = x;
    }
    
    public static String function_get_name(){
		return func_name;
	}

	public static void function_set_name(String x){
		func_name = x;
	}

    public static String function_get_parent(){
		return func_parent;
	}

	public static void function_set_parent(String x){
		func_parent = x;
	}

    public static String function_get_type(){
		return func_type;
	}

	public static void function_set_type(String x){
		func_type = x;
	}

	public static int function_get_counter(){
		return func_counter;
	}

	public static void function_inc_counter(){
		func_counter++;
	}

	public static int params_get_counter(){
		return param_counter;
	}

	public static void params_reset_counter(){
		param_counter = 1;
	}

	public static void params_inc_counter(){
		param_counter++;
	}

    public static void function_data(){
        System.out.printf("%30s %10s %10s %10s %10s %10s\n", "Name", "Type", "Parent", "Counter", "Implicit?", "Param");
        System.out.printf("%30s %10s %10s %10d %10b %10d\n\n", func_name, func_type, func_parent, func_counter, func_implicit, param_counter);
    }

    public static void def_function_first(String id, String type){
        String n = id;
        //System.out.println("is-prototype " + func_prototyp);
        //System.out.println(SymbolTable.table_get_block_index());
        //SymbolTable.dump();
        /*
        System.out.println("id " + id);
        System.out.println("type " + type);
        System.out.println("func_name " + func_name);
        System.out.println("func_parent " + func_parent);
        */
        tfunc = id;
        function_set_type(type);
        function_set_name(id);
        //function_set_parent(id);
        //function_set_prototype(false);
        SymbolTable.table_put(id, type);
        SymbolTable.table_block_start();
        //SymbolTable.dump();
        function_start(id);
        LinkedHashMap<String,String> p = SymbolTable.param_get_list();
        int k = 1;
        for (Map.Entry<String, String> entry : p.entrySet()) {
            if(entry.getKey().startsWith(id)){
                System.out.println("\t" + entry.getKey() + " = param " + k + ";");
                k++;
            }
        }
    }

    public static void def_function_last(String id){
        //function_data();
        //System.out.println(tfunc);
        //System.out.println(func_name + " " + func_parent);
        /*
        String s = "";
        if(func_name == null){
            s = func_parent;
        }
        else{
            s = func_name;
        }
        */
        function_end(tfunc);
        function_set_name(null);
        function_inc_counter();
        //function_set_prototype(true);
        //SymbolTable.reverseParams();
        //SymbolTable.param_clear();
        SymbolTable.table_block_end();
    }

    public static void def_function_par(String id, String type){
        String s = function_scope_get_vars(function_get_name(), id, function_get_counter());
        //System.out.println(s);
        /*
        System.out.println("\nid " + id);
        System.out.println("type " + type);
        System.out.println("s " + s);
        System.out.println("is prototype " + function_is_prototype());
        System.out.println("is implicit  " + func_implicit);
        */
        if(id != null)
            SymbolTable.param_add_new(s, type); 
        //params_inc_counter();
        //param_add_new(s);
    }

    public static void call_func_par(Tuple n){
        /*
        System.out.println(func_implicit);
        System.out.println(params_get_counter());
        System.out.println("call_func_par expression:n " + n);
        function_data();
        SymbolTable.dump();
        */ 
        Tuple current_parameter = SymbolTable.param_get_by_index(param_counter);
        /*
        System.out.println("parameter in st " + current_parameter);
        System.out.println("parameter passed " + n);
        */
        if(!n.getB().equals(Type.CHAR) && !n.getB().contains("POINTER") && current_parameter != null  && !n.getB().equals(current_parameter.getB())){
            error("The type of the first parameter is '" + current_parameter.getB() + "' and the type of the parameter passed is '" + n.getB() + "'.", true);
        }
        /*
        Set<String> keys = params.keySet();
        for(String k:keys){
            if(k.startsWith(f) && k.endsWith(c)){
                System.out.println(k + " -- " + params.get(k));
            }
            
        }
        */

        if(func_implicit){
            assignment("param " + params_get_counter(), n.getA());
            params_inc_counter();
        }
        else{
            if(params_get_counter() <= SymbolTable.param_get_size()){
                assignment("param " + params_get_counter(), n.getA());
                params_inc_counter();
            }
            else{
                error("The number or the type of the parameters don't match.", true);
            }
        }        
    }

    public static void return_sentence(Tuple e){
        String f = Generator.function_get_name();
        //String t = SymbolTable.table_get_type(f);
        String t = Generator.function_get_type();
        /*
        System.out.println(f);
        System.out.println(e);
        System.out.println(t);
        System.out.println(SymbolTable.table_get_type(f));
        */
        //SymbolTable.dump();
        if(!e.getB().contains(t)){
            Generator.error("Incorrect type on return sentence.", true);
        }
        else if(f == null){
            Generator.assignment(tfunc, e.getA());
        }
        else{
            Generator.assignment(f, e.getA());
        }
        sentence_return();
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------ Error controling --------------------*/

    public static void warning(String message){
        System.out.println("# WARNING: " + message);
    }

    public static void error(String message, boolean exit){
        if(message.isEmpty())
            System.out.println("\terror;\n\thalt;");
        else
            System.out.println("\t# ERROR: " + message + "\n\terror;\n\thalt;");
        if(exit) 
            System.exit(0);
    }

    public static void var_not_declared(String variable){
        error("# Variable '" + variable + "' not declared.", true);
    }

    public static void var_declared(String variable){
        error("# Variable '" + variable + "' has been declared already.", true);
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------- Labels & temporals --------------------*/

    public static String var_new_temp(){
        num_temp_vars++;
        return "$t" + num_temp_vars;
    }

    public static String label_new(){
        num_labels++;
        return "L" + num_labels;
    }

    public static void label(String label){
        System.out.println(label + ":");
    }

    public static void label_goto(String label){
        System.out.println("\tgoto " + label + ";");
    }
    
    public static String assignment(String var, String exp){
        System.out.println("\t" + var + " = " + exp + ";");
        return var;
    }


/*--------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------- Arrays --------------------*/

    public static void array_init_temp(String name){
        array_temp = name;
    }

    public static String array_new_position(){
        array_position++;
        return array_temp + "[" + array_position + "]";
    }
      
    public static void array_reset_position(){
        array_position = -1;
        array_temp = "";
    }

    public static String array_get_temp(){
        return array_temp;
    }
    
    public static String array_get_position(){
        return Integer.toString(array_position);
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------ Logical conditions ------------*/

    public static Tuple condition(int cond, Tuple arg1, Tuple arg2){
        Tuple result = new Tuple(label_new(), label_new());
        String arg = "";
        String t = "";
        String f = "";
        switch(cond){
            case EQ: 
                arg = arg1.getA() + " == " + arg2.getA();
                t = result.getA();
                f = result.getB();
                break;
            case NOTEQ:
                arg = arg1.getA() + " == " + arg2.getA();
                t = result.getB();
                f = result.getA();
                break;
            case LT:
                arg = arg1.getA() + " < " + arg2.getA();
                t = result.getA();
                f = result.getB();
                break;
            case LTOREQ:
                arg = arg2.getA() + " < " + arg1.getA();
                t = result.getB();
                f = result.getA();
                break;
            case GT:
                arg = arg2.getA() + " < " + arg1.getA();
                t = result.getA();
                f = result.getB();
                break;
            case GTOREQ:
                arg = arg1.getA() + " < " + arg2.getA();
                t = result.getB();
                f = result.getA();
                break;
            default:
                error("Error: code generation failed with arguments: \tcondition:" + cond + "\targ1: " + arg1 + "\targ2: " + arg2, true);
                break;
        }
        System.out.println("\tif (" + arg + ") goto " + t + ";");
        System.out.println("\tgoto " + f + ";");
        return result;
    }

    public static Tuple operator(int cond, Tuple c1, Tuple c2){
        Tuple result = c2;
        switch(cond){
            case NOT:
                result = new Tuple(c1.getB(), c1.getA());
                break;
            case AND:
                label(c1.getB()); 
                label_goto(c2.getA());
                break;
            case OR:
                label(c1.getA()); 
                label_goto(c2.getB());
                break;
            default:
                break;
        }
        return result;
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------- For loops --------------------*/

    public static Tuple for_loop(String arg, String t, String f){
        Tuple result = new Tuple(label_new(), label_new());
        label(result.getA());
        for_loop2(arg, t, f);
        label(result.getB());
        return result;
    }

    public static void for_loop2(String arg, String t, String f){
        for_loop3(arg, t);
        label_goto(f);
    }

    public static void for_loop3(String arg, String t){
        System.out.println("\tif (" + arg + ") goto " + t + ";");
    }

    public static Tuple for_colon_loop(Tuple e1, Tuple e2){
        if((e1.getB().contains(Type.INT) && !e2.getB().contains(Type.INT)) ||(e1.getB().contains(Type.CHAR) && !e2.getB().contains(Type.CHAR))){
            error("Different types in the FOR EACH.", true);
        }

        String temp = var_new_temp();
        assignment(temp, "0");
        Tuple par = new Tuple(label_new(), label_new());
        label(par.getA());
        for_loop3((SymbolTable.array_get_size(e2.getA())-1) + " < " + temp, par.getB());
        assignment(e1.getA(), e2.getA() + "[" + temp + "]");
        assignment(temp, temp + " + 1");
        return par;
    }

    public static Tuple for_in_loop(Tuple e, Tuple a){
        if((e.getB().contains(Type.INT) && a.getB().contains(Type.FLOAT)) || (e.getB().contains(Type.FLOAT) && a.getB().contains(Type.INT))){
            error("Types don't match.", true);
        }

        String t = var_new_temp();
        assignment(t, "-1");
        String inc = label_new();
        label(inc);
        assignment(t, t + " + 1");
        int tam = 0;
        if(array_get_temp() != ""){
            tam = Integer.parseInt(array_get_position()) + 1;
            array_reset_position();
        } 
        else {
            tam = SymbolTable.array_get_size(a.getA());
        }
        String tru = label_new();
        String fals = label_new();
        for_loop2(t + " < " + tam, tru, fals);
        label(tru);
        String temp = var_new_temp();
        assignment(temp, a.getA() +"[" + t + "]");
        assignment(e.getA(), temp);
        return new Tuple(inc, fals);  
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*---------------------------------------------------------------------------------------- Prints --------------------*/

    public static void print_string(String i){
        String s = i.substring(1,i.length()-1);
        String array_temp = var_new_temp();
        int value = 0;
        int size = s.length();
        int special = 0;
        for(int j = 0; j < size; j++){
            value = s.charAt(j);
            if(j>0 && s.charAt(j-1) == 92 && s.charAt(j-2) != 92){
                special--;
                assignment(array_temp + "[" + special + "]", Integer.toString(value));
            }
            else if(value == 92){
            }
            else {
                assignment(array_temp + "[" + special + "]", Integer.toString(value));
            }
            special++;
        }
        assignment(array_temp + "_length", Integer.toString(special));
        String c = var_new_temp();
        assignment(c, "0");
        String tag = label_new();
        String cond_true = label_new();
        String cond_false = label_new();
        label(tag);
        System.out.println("\tif (" + c + " < " + array_temp + "_length) goto " + cond_true + ";");
        System.out.println("\tgoto " + cond_false + ";");
        label(cond_true);
        String aux = var_new_temp();
        assignment(c, array_temp + "[" + c + "]");
        command("writec", aux);
        assignment(c, c + " + 1");
        label_goto(tag);
        label(cond_false);
        command("writec", "10");
    }
    
    public static void print_array(Tuple i){
        StringBuilder temp = new StringBuilder();
        int j = 0;
        while(i.getB().charAt(j) < 58){
            temp.append(i.getB().charAt(j));
            j++;
        }
        int size = Integer.parseInt(temp.toString()) + 1;
        String t = var_new_temp();
        for(int k = 0; k < size; k++){
            System.out.println("\t" + t + " = " + i.getA() + "[" + k + "];");
            if(i.getB().contains(Type.CHAR)){
                command("printc", t);
            }
            else {
                command("print", t);
            }
        }
    }

    public static String print_expression(Tuple e){
        //System.out.println(e);
        String result = "";
        if(!e.getA().contains("[") && e.getB().contains(Type.ARRAY)){
            String t = var_new_temp();
            for(int i = 0; i < SymbolTable.array_get_size(e.getA()); i++){
                System.out.println("\t" + t + " = " + e.getA() + "[" + i + "];");
                if(e.getB().contains(Type.CHAR)){
                    command("printc", t);
                }
                else{
                    command("print", t);
                }
            } 

        }
        else {
            if(e.getB().equals(Type.STRING) && !e.getA().contains("[")){
                String c = var_new_temp();
                assignment(c, "0");
                String tag = label_new();
                String cond_true = label_new();
                String cond_false = label_new();
                label(tag);
                System.out.println("\tif (" + c + " < " + e.getA() + "_length) goto " + cond_true + ";");
                System.out.println("\tgoto " + cond_false + ";");
                label(cond_true);
                String aux = var_new_temp();
                System.out.println("\t" + aux + " = " + e.getA() + "[" + c + "];");
                System.out.println("\twritec " + aux + ";");
                System.out.println("\t" + c + " = " + c + " + 1;");
                label_goto(tag);
                label(cond_false);
                System.out.println("\twritec 10;");

            }
            else if(e.getB().contains(Type.CHAR)){
                if(e.getB().contains(Type.ARRAY)){
                    String t = var_new_temp(); 
                    assignment(t, e.getA());
                    result = t; 
                    command("printc", t);
                }
                else {
                    command("printc", e.getA());
                    result = e.getA();
                }
            }
            else if(e.getB().equals(Type.STRING) && e.getA().contains("[")){

                String i = e.getA().substring(0,e.getA().indexOf("["));
                String current_position = e.getA().substring(e.getA().indexOf("[")+1, e.getA().indexOf("]"));
                Tuple tag = new Tuple(label_new(), label_new());
                System.out.println("# Comprobacion de rango");
                System.out.println("\tif (" + current_position + " < 0) goto " + tag.getA() + ";");
                System.out.println("\tif (" + i + "_length < " + current_position + ") goto " + tag.getA() + ";");
                System.out.println("\tif (" + i + "_length == " + current_position + ") goto " + tag.getA() + ";");
                label_goto(tag.getB());
                label(tag.getA());
                error("Position '" + current_position + "' invalid.", false);
                label(tag.getB());

                String t = var_new_temp(); 
                assignment(t, e.getA());
                result = t; 
                command("printc", t);
            }
            else {
                String t = var_new_temp(); 
                assignment(t, e.getA());
                result = t; 
                command("print", t);
            }    
        }
        return result;
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------- Variable --------------------*/

    public static String var_declaration(String t, String i, String d){
        String result = "";
        String x = scope_get_vars(i, SymbolTable.table_get_block_index());
        /*
            System.out.println(t);
            System.out.println(i);
            System.out.println(d);
            System.out.println(x);
            System.out.println(SymbolTable.table_find(i));
            System.out.println(SymbolTable.table_find(x));        
            SymbolTable.dump();
            */
                
            if(function_get_name() != null){
                if(d == null){
                    System.out.println("\t" + x + " = 0;");
                }
                else{
                    System.out.println("\t" + x + " = " + d + ";");
                }
            }
            else if(d != null && !d.contains("[") && !d.contains("\"")){ 
                
                if(SymbolTable.table_find(i) && (SymbolTable.table_get_block_index() > 0)){
                    System.out.println("\t" + x + " = " + d + ";");
                }
                else {
                    System.out.println("\t" + i + " = " + d + ";");
                }
            } 
            else if(d == null){
                System.out.println("\t" + i + " = 0;");
            }
            
            String tipo = t;
            String var_name = "";
            if(d != null && d.contains("[")){
                t = Type.ARRAY + d + "(" + t + ")";
                result = t;
                System.out.println("\t" + i + "_length = " + d.substring(1, d.length()-1) + ";");
            }

            String typeA = t;
            String typeB = "";
            if(d != null && d.contains("\"")) typeB = Type.STRING;

            if(d != null && typeA.equals(Type.STRING) && typeB.equals(Type.STRING)){

            
                String s = d.substring(1,d.length()-1);
                String array_temp = var_new_temp();
                int value = 0;
                int size = s.length();
                int special = 0;
                for(int j = 0; j < size; j++){
                    value = s.charAt(j);
                    if(j>0 && s.charAt(j-1) == 92 && s.charAt(j-2) != 92){
                        special--;
                        System.out.println("\t" + array_temp + "[" + special + "] = " + value + ";");
                    }
                    else if(value == 92){

                    }
                    else {
                        System.out.println("\t" + array_temp + "[" + special + "] = " + value + ";");
                    }
                    special++;
                }
                System.out.println("\t" + array_temp + "_length = " + special + ";");
                String c = var_new_temp();
                System.out.println("\t" + c + " = 0;");
                String tag = label_new();
                String cond_true = label_new();
                String cond_false = label_new();
                label(tag);
                System.out.println("\tif (" + c + " < " + array_temp + "_length) goto " + cond_true + ";");
                System.out.println("\tgoto " + cond_false + ";");
                label(cond_true);
                String aux = var_new_temp();
                System.out.println("\t" + aux + " = " + array_temp + "[" + c + "];");
                System.out.println("\t" + i + "[" + c + "] = " + aux + ";");
                System.out.println("\t" + c + " = " + c + " + 1;");
                System.out.println("\tgoto " + tag + ";");
                label(cond_false);
                System.out.println("\t" + i + "_length = " + array_temp + "_length;");

            } 

            int p = Integer.parseInt(pointer_get_num());
            if(p > 0){
                pointer_reset();
                String point = "POINTER(";

                for(int j = 0; j < p; j++){
                    t = point + t;
                    t = t + ")";
                }
            }

            if(function_get_name() != null){
                //System.out.println(x + " " + t);
                SymbolTable.table_put(x, t);
            }
            else if(SymbolTable.table_find(i) && (SymbolTable.table_get_block_index() > 0)){
                String s = scope_get_vars(i, SymbolTable.table_get_block_index());
                SymbolTable.table_put(s,t);
                var_name = s;
            }
            else {
                SymbolTable.table_put(i, t);
                var_name = i;
            }

            if(array_get_temp() != ""){ //ARRAY INITIALIZATION
                int tamanoMax = SymbolTable.array_get_size(var_name);
                int size = Integer.parseInt(array_get_position());
                if(size >= tamanoMax){
                    error("Invalid position on '" + var_name + "'", true);
                }
                String array = array_get_temp();
                String temp = var_new_temp();
                for(int j = 0; j <= size ;j++){
                    System.out.println("\t" + temp + " = " + array + "[" + j + "];");
                    System.out.println("\t" + var_name + "[" + j + "] = " + temp + ";");
                }
                array_reset_position();
            }
        return result;
    }

    public static String search_var_block(String i){
        String result = "";
        String x = scope_get_vars(i, SymbolTable.table_get_block_index());
        if(SymbolTable.table_find_in_current_block(x)){
            result = x;
        }
        else {
            int temp = SymbolTable.table_find_block(i); 
            if(temp > 0){
                result = scope_get_vars(i, temp);
            }
            else {
                result = i;
            }
        }
        return result;
    }

    public static void var_check(String i){
        String var_name = i;
        String f = function_get_name();
        String v = function_scope_get_vars(f, i, function_get_counter());
        String v2 = scope_get_vars(i, SymbolTable.table_get_block_index());
        /*
        System.out.println(f);
        System.out.println(i);
        System.out.println(v);
        System.out.println(v2);
        System.out.println(SymbolTable.param_exists(v));
        System.out.println(SymbolTable.table_find(v2));
        SymbolTable.dump();
        */
        if(f != null){
            /*
            if(SymbolTable.param_exists(v)==false || SymbolTable.table_find(v2)==false){
                var_not_declared(i);
            }
            */
        }
        else{
            if(i.contains("*")) 
                var_name = i.replace("*","");
            if(i.contains("[")) 
                var_name = i.substring(0, i.indexOf("["));
            if(!SymbolTable.table_find(var_name)){
                var_not_declared(i);
            } 
        }
    }

    public static void var_check_block(String i){
        if(SymbolTable.table_find_in_current_block(i)){
            var_declared(i);
        } 
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------- Char & Strings -------------*/

    public static String string_assignment(String i, String d){
        String s = d.substring(1,d.length()-1);
        String array_temp = var_new_temp();
        int value = 0;
        int size = s.length();
        int special = 0;
        for(int j = 0; j < size; j++){
            value = s.charAt(j);
            if(j>0 && s.charAt(j-1) == 92 && s.charAt(j-2) != 92){
                special--;
                assignment(array_temp + "[" + special + "]", Integer.toString(value));
            }
            else if(value == 92){
            }
            else {
                assignment(array_temp + "[" + special + "]", Integer.toString(value));
            }
            special++;
        }
        assignment(array_temp + "_length", Integer.toString(special));
        String c = var_new_temp();
        assignment(c, "0");
        String tag = label_new();
        String cond_true = label_new();
        String cond_false = label_new();
        label(tag);
        System.out.println("\tif (" + c + " < " + array_temp + "_length) goto " + cond_true + ";");
        System.out.println("\tgoto " + cond_false + ";");
        label(cond_true);
        String aux = var_new_temp();
        assignment(aux, array_temp + "[" + c + "]");
        assignment(i + "[" + c + "]", aux);
        assignment(c, c + " + 1");
        label_goto(tag);
        label(cond_false);
        assignment(i + "_length", array_temp + "_length");
        return i;
    }

    public static void string_range_check(String i, Tuple e){
        String current_position = e.getA();
        Tuple par = new Tuple(label_new(), label_new());
        System.out.println("# Range check");
        System.out.println("\tif (" + current_position + " < 0) goto " + par.getA() + ";");
        System.out.println("\tif (" + SymbolTable.array_get_size(i) + " < " + current_position + ") goto " + par.getA() + ";");
        System.out.println("\tif (" + SymbolTable.array_get_size(i) + " == " + current_position + ") goto " + par.getA() + ";");
        System.out.println("\tgoto " + par.getB() + ";");
        label(par.getA());
        error("Position '" + current_position + "' invalid.", false);
        label(par.getB());
    }

    public static Tuple init_string_first(String i){
        Tuple result = null;
        String var_name = i;
        if(i.contains("[")) 
            var_name = i.substring(0,i.indexOf("["));

        if(SymbolTable.table_get_type(var_name).equals(Type.STRING)){
            String size = i + "_length";
            result = new Tuple(size, var_name);
        }
        else {
            int size = SymbolTable.array_get_size(var_name);
            result = new Tuple(size + "", var_name);
        }
        return result;
    }

    public static Tuple init_string_last(String e){
        String s = e.substring(1,e.length()-1);
        String arrayTemp1 = var_new_temp();
        int value = 0;
        int tam = s.length();
        int especial = 0;
        for(int j = 0; j < tam; j++){
            value = s.charAt(j);
            if(j>0 && s.charAt(j-1) == 92 && s.charAt(j-2) != 92){
                especial--;
                System.out.println("\t" + arrayTemp1 + "[" + especial + "] = " + value + ";");
            }
            else if(value == 92){
            } 
            else {
                System.out.println("\t" + arrayTemp1 + "[" + especial + "] = " + value + ";");
            }
            especial++;
        }
        System.out.println("\t" + arrayTemp1 + "_length = " + especial + ";");
        return new Tuple(arrayTemp1,Type.STRING);
    }

    public static Tuple not_expression(Tuple e){
        String label = label_new();
        String temp = var_new_temp();
        System.out.println("\t" + temp + " = " + e.getA() + ";");
        System.out.println("\tif (" + e.getA() + " < 97) goto " + label + ";");
        System.out.println("\tif (122 < " + e.getA() + ") goto " + label + ";");
        System.out.println("\t" + temp + " = " + e.getA() + " - 32;");
        System.out.println(label + ":");
        return new Tuple(temp, Type.CHAR);
    }

    public static Tuple tilde_expression(Tuple e){
        String label0 = label_new();
        String temp = var_new_temp();
        String label1 = label_new();
        System.out.println("\t" + temp + " = " + e.getA() + ";");
        System.out.println("\tif (" + e.getA() + " < 65) goto " + label0 + ";");
        System.out.println("\tif (122 < " + e.getA() + ") goto " + label0 + ";");
        System.out.println("\tif (96 < " + e.getA() + ") goto " + label1 + ";");
        System.out.println("\tif (90 < " + e.getA() + ") goto " + label0 + ";");
        System.out.println("\t" + temp + " = " + e.getA() + " + 32;");
        System.out.println("\tgoto " + label0 + ";");
        System.out.println(label1 + ":");
        System.out.println("\t" + temp + " = " + e.getA() + " - 32;");       
        System.out.println(label0 + ":");   
        return new Tuple(temp, Type.CHAR);
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------- Casting --------------------*/

    public static Tuple cast(String type, Tuple e){
        Tuple result = null;
        if(e.getB() != "checked"){
            String t = var_new_temp();
            System.out.println("\t" + t + " = (" + type + ") " + e.getA() + ";");
            result = new Tuple(t,"");
        }
        else {
            result = e;
        }
        result.setB(type);
        return result;
    }

    public static Tuple cast_char(Tuple e){
        Tuple result = e;
        result.setB(e.getB().replace(Type.INT, Type.CHAR));
        return result;
    }

    public static Tuple cast_string(Tuple e){
        Tuple result = null;
        if(e.getB().contains(Type.ARRAY)){
            result = new Tuple(e.getA(), Type.STRING);
        }
        else {
            String array_temp = var_new_temp();
            System.out.println("\t" + array_temp + "[0] = " + e.getA() + ";");
            System.out.println("\t" + array_temp + "_length = 1;");
            result = new Tuple(array_temp, Type.STRING);
        }
        return result;
    }
/*--------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------ Arithmetic operations ---------*/

    public static Tuple arithmetic(int a, String op, Tuple e1, Tuple e2){
        Tuple result = null;
        String t = var_new_temp(); 
        String type = "";
        switch(a){
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                /*
                System.out.println("a " + a);
                System.out.println("op " + op);
                System.out.println("e1 " + e1);
                System.out.println("e2 " + e2);
                */
                if(e1.getB().contains("POINTER") || e2.getB().contains("POINTER")){
                    error("Operations with pointers.", true);                   
                }
                if(e1.getB().equals(Type.STRING) && e2.getB().equals(Type.STRING)){

                    String counter = var_new_temp();
                    System.out.println("\t" + t + "_length = " + e1.getA() + "_length + " + e1.getA() + "_length;");
                    String labelini = label_new();
                    String cond_true = label_new();
                    String cond_false = label_new();
                    System.out.println(labelini + ":");
                    System.out.println("\tif (" + counter + " < " + e1.getA() + "_length) goto " + cond_true + ";");
                    System.out.println("\tgoto " + cond_false + ";");
                    System.out.println(cond_true + ":");
                    String aux = var_new_temp();
                    System.out.println("\t" + aux + " = " + e1.getA() + "[" + counter + "];");
                    System.out.println("\t" + t + "[" + counter + "] = " + aux + ";");
                    System.out.println("\t" + counter + " = " + counter + " + 1;");
                    System.out.println("\tgoto " + labelini + ";");
                    System.out.println(cond_false + ":");
                    
                    String counter2 = var_new_temp();
                    String labelini2 = label_new();
                    String condTrue2 = label_new();
                    String condFalse2 = label_new();
                    System.out.println(labelini2 + ":");
                    System.out.println("\tif (" + counter2 + " < " + e2.getA() + "_length) goto " + condTrue2 + ";");
                    System.out.println("\tgoto " + condFalse2 + ";");
                    System.out.println(condTrue2 + ":");
                    String aux2 = var_new_temp();
                    System.out.println("\t" + aux2 + " = " + e2.getA() + "[" + counter2 + "];");
                    System.out.println("\t" + t + "[" + counter + "] = " + aux2 + ";");
                    System.out.println("\t" + counter + " = " + counter + " + 1;");
                    System.out.println("\t" + counter2 + " = " + counter2 + " + 1;");
                    System.out.println("\tgoto " + labelini2 + ";");
                    System.out.println(condFalse2 + ":");
                    
                    type = Type.STRING;
                } 
                else if(e1.getB() == Type.FLOAT || e2.getB() == Type.FLOAT){
                    if(e1.getB().contains(Type.INT)){
                        String temp = var_new_temp();
                        String first_operand = var_new_temp();
                        System.out.println("\t" + first_operand + " = " + e1.getA() + ";");
                        System.out.println("\t" + temp + " = (float) " + first_operand + ";");
                        t = var_new_temp();
                        System.out.println("\t" + t + " = " + temp + " " + op + "r " + e2.getA() + ";");
                    } 
                    else if(e2.getB().contains(Type.INT)){
                        String temp = var_new_temp();
                        String second_operand = var_new_temp();
                        System.out.println("\t" + second_operand + " = " + e2.getA() + ";");
                        System.out.println("\t" + temp + " = (float) " + second_operand + ";");
                        t = var_new_temp();
                        System.out.println("\t" + t + " = " + e1.getA() + " " + op + "r " + temp + ";");
                    } 
                    else if(!(e1.getB()).contains(Type.ARRAY) && !(e2.getB()).contains(Type.ARRAY)){
                        t = var_new_temp();
                        System.out.println("\t" + t + " = " + e1.getA() + " " + op + "r " + e2.getA() + ";");
                    } 
                    else {
                        if(e1.getB().contains(Type.ARRAY)){
                            t = var_new_temp();
                            String first_operand = var_new_temp();
                            System.out.println("\t" + first_operand + " = " + e1.getA() + ";");
                            System.out.println("\t" + t + " = " + first_operand + " " + op + "r " + e2.getA() + ";");
                        } 
                        else {
                            t = var_new_temp();
                            String second_operand = var_new_temp();
                            System.out.println("\t" + second_operand + " = " + e2.getA() + ";");
                            System.out.println("\t" + t + " = " + e1.getA() + " " + op + "r " + second_operand + ";");
                        }
                    }

                    type = Type.FLOAT;
                } 
                else if(e1.getB().equals(Type.CHAR) && !e2.getB().equals(Type.CHAR)) {
                    t = var_new_temp();
                    System.out.println("\t" + t + " = " + e1.getA() + " " + op + " " + e2.getA() + ";");
                    type = Type.INT;         
                } 
                else if(!e1.getB().equals(Type.CHAR) && e2.getB().equals(Type.CHAR)) {
                    t = var_new_temp();
                    System.out.println("\t" + t + " = " + e1.getA() + " " + op + " " + e2.getA() + ";");
                    type = Type.INT;         
                } 
                else if(e1.getB().equals(Type.CHAR) && e2.getB().equals(Type.CHAR)) {
                    error("CHAR addition.", true);         
                } 
                else if(!(e1.getB()).contains(Type.ARRAY) && !(e2.getB()).contains(Type.ARRAY)){
                    t = var_new_temp();
                    System.out.println("\t" + t + " = " + e1.getA() + " " + op + " " + e2.getA() + ";");
                    type = Type.INT; 
                } 
                else {
                    /** 1st is FLOAT */
                    if((e1.getB()).contains(Type.ARRAY) && (e1.getB()).contains(Type.FLOAT)){  
                        String first_operand = var_new_temp();
                        System.out.println("\t" + first_operand + " = " + e1.getA() + ";");
                        if((e2.getB()).equals(Type.INT) || ((e2.getB()).contains(Type.ARRAY) && (e2.getB()).contains(Type.INT))){
                            String temp_cast = var_new_temp();
                            String temp = var_new_temp();
                            System.out.println("\t" + temp_cast + " = " + e2.getA() + ";");
                            System.out.println("\t" + temp + " = (float) " + temp_cast + ";");
                            t = var_new_temp();
                            System.out.println("\t" + t + " = " + first_operand + " " + op + "r " + temp + ";");
                        } else {
                            String second_operand = var_new_temp();
                            System.out.println("\t" + second_operand + " = " + e2.getA() + ";");
                            t = var_new_temp();
                            System.out.println("\t" + t + " = " + first_operand + " " + op + "r " + second_operand + ";");
                        }
                        type = Type.FLOAT;
                    }
                    /** 2nd is FLOAT */
                    else if((e2.getB()).contains(Type.ARRAY) && (e2.getB()).contains(Type.FLOAT)){   
                        String second_operand = var_new_temp();
                        System.out.println("\t" + second_operand + " = " + e2.getA() + ";");
                        if((e1.getB()).equals(Type.INT) || ((e1.getB()).contains(Type.ARRAY) && (e1.getB()).contains(Type.INT))){
                            String temp_cast = var_new_temp();
                            String temp = var_new_temp();
                            System.out.println("\t" + temp_cast + " = " + e1.getA() + ";");
                            System.out.println("\t" + temp + " = (float) " + temp_cast + ";");
                            t = var_new_temp();
                            System.out.println("\t" + t + " = " + temp + " " + op + "r " + second_operand + ";");
                        }
                        else {
                            String first_operand = var_new_temp();
                            System.out.println("\t" + first_operand + " = " + e1.getA() + ";");
                            t = var_new_temp();
                            System.out.println("\t" + t + " = " + first_operand + " " + op + "r " + second_operand + ";");
                        }
                        type = Type.FLOAT;
                    } 
                    /** Both are INT */
                    else { 
                        String first_operand = var_new_temp();
                        System.out.println("\t" + first_operand + " = " + e1.getA() + ";");
                        String second_operand = var_new_temp();
                        System.out.println("\t" + second_operand + " = " + e2.getA() + ";");
                        t = var_new_temp();
                        System.out.println("\t" + t + " = " + first_operand + " " + op + " " + second_operand + ";");
                        type = Type.INT;
                    }

                }
                result = new Tuple(t, type);
                break;
            case MIN:
                System.out.println("\t" + t + " = -" + e1.getA() + ";");
                result = new Tuple(t, e1.getB());
                break;
            case MOD:
                String temp1 = var_new_temp();
                String temp2 = var_new_temp();
                System.out.println("\t" + t + " = " + e1.getA() + " / " + e2.getA() + ";");
                System.out.println("\t" + temp1 + " = " + t + " * " + e2.getA() + ";");
                System.out.println("\t" + temp2 + " = " + e1.getA() + " - " + temp1 + ";");
                result = new Tuple(temp2, "int");
                break;
            default:
                error("Error: code generation failed with arguments: \toperation:" + op + "\tnumber 1: " + e1 + "\tnumber 2: " + e2, true);
                break;
        }
        
        return result;
    }

    public static Tuple increment(int op, String var){
        if(!SymbolTable.table_find(var))
            var_not_declared(var); 

        Tuple temp = new Tuple(var_new_temp(), SymbolTable.table_get_type(var));
        switch(op){
            case INCL:
                System.out.println("\t" + var + " = " + var + " + 1;");
                temp.setA(var);
                break;
            case DECL:
                System.out.println("\t" + var + " = " + var + " - 1;");
                temp.setA(var);
                break;
            case INCR:
                assignment(temp.getA(), var);
                System.out.println("\t" + var + " = " + var + " + 1;");
                break;
            case DECR:
                assignment(temp.getA(), var);
                System.out.println("\t" + var + " = " + var + " - 1;");
                break;   
            default:
                error("Error: code generation failed with arguments: \toperation:" + op + "\tvar: " + var, true);
                break;
        }
        return temp;
    }

/*--------------------------------------------------------------------------------------------------------------------*/
/*-------------------------------------------------------------------------------------- Pointers --------------------*/

    public static void pointer_inc_counter(){
        pointer_counter++;
    }

    public static void pointer_reset(){
        pointer_counter = 0;
    }

    public static String pointer_get_num(){
        return Integer.toString(pointer_counter);
    }

    public static Tuple pointer_access(String e){
        Tuple result = null;
        String temp = var_new_temp();
        String f = function_get_name();
        String v = function_scope_get_vars(f, e, function_get_counter());
        String v2 = scope_get_vars(e, SymbolTable.table_get_block_index());
        String t = "";
        /*
        System.out.println("temp " + temp);
        System.out.println("f " + f);
        System.out.println("v " + v);
        System.out.println("v2 " + v2);
        System.out.println("e " + e);
        */
        if(f != null){
            if(SymbolTable.param_exists(v)){
                e = v;
            }
            else if(SymbolTable.table_find(v2)){
                e = v2;
            }
        }
        if(SymbolTable.param_exists(v)){
            t = SymbolTable.param_get_with_name(v).getB();
        }
        else if(SymbolTable.table_find(v2)){
            t = SymbolTable.table_get_type(v2);
        }
        else{
            t = SymbolTable.table_get_type(e);
        }
        System.out.println("\t" + temp + " = *" + e + ";");
        //System.out.println(t);
        t = "POINTER(" + t + ")";
        /** We remove POINTER( & ) */
        String type = t.substring(8,t.length()-1);
        String temp1;
        int levels = Integer.parseInt(pointer_get_num());
        //System.out.println(levels);
        for(int i = 0; i < levels; i++){
            temp1 = var_new_temp();
            System.out.println("\t" + temp1 + " = *" + temp + ";");
            temp = temp1;
            //type = type.substring(8,type.length()-1);
        }
        result = new Tuple(temp, type);
        pointer_reset();
        return result;
    }

    public static Tuple pointer_asig_access(String i){
        String var_name = i;
        String f = function_get_name();
        String v = function_scope_get_vars(f, i, function_get_counter());
        String v2 = scope_get_vars(i, SymbolTable.table_get_block_index());
        /*
        System.out.println(f);
        System.out.println(i);
        System.out.println(v);
        System.out.println(v2);
        System.out.println(SymbolTable.param_exists(v));
        System.out.println(SymbolTable.table_find(v2));
        SymbolTable.dump();
        */
        if(f != null){
            if(SymbolTable.param_exists(v)){
                i = v;
            }
            else if(SymbolTable.table_find(v2)){
                i = v2;
            }
        }
        if(i.contains("[")) var_name = i.substring(0, i.indexOf("["));
        if(i.contains("*")) var_name = i.replace("*","");
        if(f == null && !SymbolTable.table_find(var_name)){
            var_not_declared(i);
        }
        String t = "";
        if(SymbolTable.param_exists(v)){
            t = SymbolTable.param_get_with_name(v).getB();
        }
        else if(SymbolTable.table_find(v2)){
            t = SymbolTable.table_get_type(v2);
        }
        else{
            t = SymbolTable.table_get_type(i);
        }
        int p = Integer.parseInt(pointer_get_num()) +1;
        if(p > 0){
            pointer_reset();
            String point = "POINTER(";
            for(int j = 0; j < p; j++){
                t = point + t;
                t = t + ")";
                i = "*" + i;
            }
        }

        return new Tuple(i,t);
    }

    public static void pointer_asig_expression(Tuple result, String i, Tuple e){
        /*
        System.out.println(i);
        System.out.println(e);
        System.out.println(result);
        */
        if(result.getA().contains("*")) {
            int pointers_num = result.getA().length() - result.getA().replace("*", "").length();
            int levels_assigned = (e.getB().length() - e.getB().replace("POINTER", "").length());
            String f = function_get_name();
            String v = function_scope_get_vars(f, i, function_get_counter());
            String v2 = scope_get_vars(i, SymbolTable.table_get_block_index());
            String typeA = "";
            if(SymbolTable.param_exists(v)){
                typeA = SymbolTable.param_get_with_name(v).getB();
            }
            else if(SymbolTable.table_find(v2)){
                typeA = SymbolTable.table_get_type(v2);
            }
            else{
                typeA = SymbolTable.table_get_type(i);
            }
            typeA = typeA.replace("*","");
            int levels = (typeA.length() - typeA.replace("POINTER", "").length());
                
            if(pointers_num > 1){
                String asd = result.getA().replace("*","");
                String das = "";
                int j = 0;
                /*
                do{
                    das = var_new_temp();
                    System.out.println("\t" + das + " = *" + asd + ";");
                    asd = das;
                    j++;
                } while(j < pointers_num-1);
                */
                System.out.println("\t*" + result.getA().replace("*","") + " = "  + e.getA() + ";");
            }
            else {
                System.out.println("\t" + result.getA() + " = " + e.getA() + ";");
            }

        } 
        else if(SymbolTable.table_find(i) && SymbolTable.table_get_type(i).contains("POINTER") || e.getB().contains("POINTER")) {
            int levels_assigned = (e.getB().length() - e.getB().replace("POINTER", "").length());
            String typeA = SymbolTable.table_get_type(i.replace("*",""));
            int levels = (typeA.length() - typeA.replace("POINTER", "").length());
            if(levels != levels_assigned){
                error("Types mismatch on POINTERS.", true);
            }
            //System.out.println("\t" + result.getA() + " = " + e.getA() + ";");

        }
    }

    public static Tuple pointer_reference(Tuple e){
        if(!SymbolTable.table_find(e.getA())){
            error("Variable mush be declared to table_find a pointer.", true);
        } 
        String temp = var_new_temp();
        System.out.println("\t" + temp + " = &" + e.getA() + ";");
        String newEF = "POINTER(" + e.getB() + ")";
        return new Tuple(temp,newEF);
    }

}
