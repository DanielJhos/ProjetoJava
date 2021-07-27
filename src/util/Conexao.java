package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    public static Connection conn() {
        Connection c = null;
        try {
            File f = new File("database/projeto_programcao.sqlite");
            if(f.exists()){
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:database/projeto_programcao.sqlite");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return c;
    }

}
