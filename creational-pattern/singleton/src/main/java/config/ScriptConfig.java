package config;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class ScriptConfig {

    private String path;
    private final DatabaseConnection db;

    public ScriptConfig(String path, DatabaseConnection db) {
        this.path = path;
        this.db = db;
    }

    public void runScript() throws FileNotFoundException {
        ScriptRunner sr = new ScriptRunner(this.db.getConnection());
        Reader reader = new BufferedReader(new FileReader(this.path));
        sr.runScript(reader);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
