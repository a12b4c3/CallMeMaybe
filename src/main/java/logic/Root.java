package logic;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Root {
    Path path;
    SourceRoot sourceRoot;
    List<ParseResult<CompilationUnit>> results;
    List<CompilationUnit> compilations;

    public Root(Path path) throws IOException {
        this.path = path;
        this.sourceRoot = new SourceRoot(this.path);
        this.results = this.sourceRoot.tryToParse();
        this.compilations = this.sourceRoot.getCompilationUnits();
    }

    public Path getPath() {
        return path;
    }

    public SourceRoot getSourceRoot() {
        return sourceRoot;
    }


    public List<ParseResult<CompilationUnit>> getResults() {
        return results;
    }

    public List<CompilationUnit> getCompilations() {
        return compilations;
    }

}
