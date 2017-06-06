package builders;

import components.module.Module;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ModuleBuilder {
    private Module moduleDescription;
    private String dirPath;

    public ModuleBuilder(Module moduleDescription, String dirPath) {
        this.moduleDescription = moduleDescription;
        this.dirPath = dirPath;
    }

    public void build() {
        ExecutorService es = Executors.newCachedThreadPool();

        List<Future<Boolean>> tasks = new LinkedList<>();

        tasks.add(es.submit(new ModuleClassBuilder(moduleDescription, dirPath)));
        tasks.add(es.submit(new VardefBuilder(moduleDescription, dirPath)));
        tasks.add(es.submit(new EditDetailViewBuilder( new String[]{"EDIT", "DELETE"}, dirPath + "\\metadata", moduleDescription, true)));
        tasks.add(es.submit(new EditDetailViewBuilder( new String[]{"SAVE", "CANCEL"}, dirPath + "\\metadata", moduleDescription, false)));

        while (!tasks.isEmpty())
            tasks.removeIf(Future::isDone);

        es.shutdownNow();
    }





}
