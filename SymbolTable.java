import java.util.*;

class SymbolTable {

    private static ArrayList<HashMap<String,String>> table;
    private static LinkedHashMap<String,String> params;
    static int table_get_block_index;

    static {
        table = new ArrayList<HashMap<String,String>>();
        params = new LinkedHashMap<String,String>();
        table.add(new HashMap<String,String>());
        table_get_block_index = 0;
    }

    public static void table_put(String s, String e){
        HashMap<String, String> temp = table.get(table_get_block_index);
        temp.put(s,e);
        //TablaSimbolos.dump();
    }

    public static String table_get_type(String s){
        int i = 0;
        while((table_get_block_index >= i) && !table.get(table_get_block_index - i).containsKey(s)){
            i++;
        }
        return table.get(table_get_block_index - i).get(s);
    }

    public static void table_block_start(){
        table_get_block_index++;
        table.add(table_get_block_index, new HashMap<String,String>());    
    }
    
    public static void table_block_end(){
        table.remove(table_get_block_index);
        table_get_block_index--; 
    }

    public static boolean table_find(String s){
        for(int k = 0; k < table.size(); k++){
            if(table.get(k).containsKey(s)){
                return true;
            }
        }
        /*
        while((table_get_block_index >= i) && !table.get(table_get_block_index - i).containsKey(s)){
            i++;
        }

        if(table_get_block_index < i){
            return false;
        } else {
            return true;
        }
        */
        return false;
    }

    public static boolean table_find_in_current_block(String s){
        return table.get(table_get_block_index).containsKey(s);
    }
    
    public static int table_get_block_index(){
        return table_get_block_index;
    }

    public static int table_find_block(String s){
        int i = 0;
        int temp = table_get_block_index;
        String var = Generator.scope_get_vars(s, temp);
        while((table_get_block_index > i) && !table.get(table_get_block_index - i).containsKey(var)){
            i++;
            temp--;
        } 
        if(table_get_block_index == i) return 0;
        return temp;
    }

    public static int array_get_size(String s){
        String tipo = table_get_type(s);
        int index_start = tipo.indexOf("[");
        int index_end = tipo.indexOf("]");
        String tam = tipo.substring(index_start + 1, index_end);
        return Integer.parseInt(tam);
    }

    public static LinkedHashMap<String,String> param_get_list(){
        return params;
    }

    public static String param_get_type(String s){
        return params.get(s);
    }

    public static boolean param_exists(String s){
        return params.containsKey(s);
    }

    public static void param_add_new(String s, String t){
        if(!param_exists(s))
            params.put(s, t);
    }

    public static Tuple param_get_with_name(String s){
        return new Tuple(s, params.get(s));
    }

    public static int param_get_size(){
        return params.size();
    }

    public static void param_clear(){
        params.clear();
    }

    public static Tuple param_get_by_index(int index){
        int i = 1;
        Set<String> keys = params.keySet();
        for(String k:keys){
            if(i == index){
                return new Tuple(k, params.get(k));
            }
            i++;
        }
        return null;
    }

    public static void dump(){
        System.out.println("---------------- list of the symbols table --------------------");
        System.out.println("--------------------------- variables -------------------------------");
        for(int k = 0; k < table.size(); k++){
            table.get(k).forEach((key,value) -> System.out.printf("%30s %10s\n", key, value));
        }
        if(params.size() > 0){
            System.out.println("--------------------------- parameters ------------------------------");
            params.forEach((key,value) -> System.out.printf("%30s %10s\n", key, value));
        }
        System.out.println("---------------------------------------------------------------------");
    }
}
