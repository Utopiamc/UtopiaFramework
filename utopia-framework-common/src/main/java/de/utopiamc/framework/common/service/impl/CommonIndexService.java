package de.utopiamc.framework.common.service.impl;

import com.google.inject.Inject;
import de.utopiamc.framework.common.models.JarFileConfig;
import de.utopiamc.framework.common.models.JarFileIndex;
import de.utopiamc.framework.common.service.IndexService;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommonIndexService implements IndexService {

    @Inject
    private Logger logger;

    @Override
    public Set<JarFileIndex> indexJarFiles(Set<File> jarFiles) {
        return jarFiles.stream()
                .map(this::indexJarFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private JarFileIndex indexJarFile(File file) {
        try {
            URL url = new URL("jar:file:" + file.getAbsolutePath() + "!/");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url}, getClass().getClassLoader());

            InputStream resource = classLoader.getResourceAsStream("config.xml");
            JarFileConfig config = new JarFileConfig();

            if (resource == null) {
                config.setSearchPoints(List.of(""));
            }else {
                SAXBuilder builder = new SAXBuilder();
                builder.setReuseParser(false);
                Document document = builder.build(resource);
                Element rootElement = document.getRootElement();

                if (!rootElement.getName().equalsIgnoreCase("drop-in"))
                    return null;

                config = new JarFileConfig();

                for (Element child : rootElement.getChildren()) {
                    System.out.println(child);
                    switch (child.getName().toLowerCase()) {
                        case "modules" -> {
                            List<String> modules = new ArrayList<>();
                            for (Element module : child.getChildren()) {
                                System.out.println(module);
                                if (!module.getName().equalsIgnoreCase("module"))
                                    continue;
                                for (Element moduleChild : module.getChildren()) {
                                    System.out.println(moduleChild);
                                    if (!moduleChild.getName().equalsIgnoreCase("class"))
                                        continue;
                                    modules.add(moduleChild.getText());
                                }
                            }
                            config.getModules().addAll(modules);
                        }
                        case "sources" -> {
                            List<String> searchPoints = new ArrayList<>();
                            for (Element childChild : child.getChildren()) {
                                if (!childChild.getName().equalsIgnoreCase("source"))
                                    continue;

                                for (Element elements : childChild.getChildren()) {
                                    if (elements.getName().equalsIgnoreCase("root")) {
                                        searchPoints.add(elements.getText());
                                    }
                                }
                            }
                            config.getSearchPoints().addAll(searchPoints);
                        }
                    }
                }
            }

            JarFileIndex jarFileIndex = new JarFileIndex(file.getName().substring(0, file.getName().length()-3), classLoader, scanClasses(classLoader, config.getSearchPoints()), config);

            logger.info(String.format("Indexed '%s' successfully. Found %s modules and %s classes.", jarFileIndex.name(), config.getModules().size(), jarFileIndex.classes().size()));

            return jarFileIndex;
        } catch (Throwable e) {
            logger.warning(String.format("Failed to index drop-in '%s'! %s", file.getName(), e.getMessage()));
            e.printStackTrace();
        }
        return null;
    }

    private Set<Class<?>> scanClasses(ClassLoader classLoader, List<String> searchPoints) {
        ClassGraph classGraph = new ClassGraph()
                .overrideClassLoaders(classLoader)
                .ignoreParentClassLoaders();
        searchPoints.forEach(classGraph::acceptPackages);

        try (ScanResult scanResult = classGraph.scan()) {
            return new HashSet<>(scanResult.getAllClasses().loadClasses());
        }
    }
}
