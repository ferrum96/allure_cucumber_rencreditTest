package ru.appline.autotests.glue.plugin;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import cucumber.api.event.TestSourceRead;
import gherkin.*;
import gherkin.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

final class CucumberSourceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ru.appline.autotests.glue.plugin.CucumberSourceUtils.class);
    private final Map<String, TestSourceRead> pathToReadEventMap = new HashMap();
    private final Map<String, GherkinDocument> pathToAstMap = new HashMap();
    private final Map<String, Map<Integer, AstNode>> pathToNodeMap = new HashMap();

    CucumberSourceUtils() {
    }

    public void addTestSourceReadEvent(String path, TestSourceRead event) {
        this.pathToReadEventMap.put(path, event);
    }

    public Feature getFeature(String path) {
        if (!this.pathToAstMap.containsKey(path)) {
            this.parseGherkinSource(path);
        }

        return this.pathToAstMap.containsKey(path) ? ((GherkinDocument)this.pathToAstMap.get(path)).getFeature() : null;
    }

    private void parseGherkinSource(String path) {
        if (this.pathToReadEventMap.containsKey(path)) {
            Parser<GherkinDocument> parser = new Parser(new AstBuilder());
            TokenMatcher matcher = new TokenMatcher();

            try {
                GherkinDocument gherkinDocument = (GherkinDocument)parser.parse(((TestSourceRead)this.pathToReadEventMap.get(path)).source, matcher);
                this.pathToAstMap.put(path, gherkinDocument);
                Map<Integer, AstNode> nodeMap = new HashMap();
                ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode currentParent = new ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode(gherkinDocument.getFeature(), (ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode)null);
                Iterator var7 = gherkinDocument.getFeature().getChildren().iterator();

                while(var7.hasNext()) {
                    ScenarioDefinition child = (ScenarioDefinition)var7.next();
                    this.processScenarioDefinition(nodeMap, child, currentParent);
                }

                this.pathToNodeMap.put(path, nodeMap);
            } catch (ParserException var9) {
                LOGGER.trace(var9.getMessage(), var9);
            }

        }
    }

    private void processScenarioDefinition(Map<Integer, AstNode> nodeMap, ScenarioDefinition child, ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode currentParent) {
        ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode childNode = new ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode(child, currentParent);
        nodeMap.put(child.getLocation().getLine(), childNode);
        child.getSteps().forEach((step) -> {
            ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode var10000 = (ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode)nodeMap.put(step.getLocation().getLine(), new ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode(step, childNode));
        });
        if (child instanceof ScenarioOutline) {
            this.processScenarioOutlineExamples(nodeMap, (ScenarioOutline)child, childNode);
        }

    }

    private void processScenarioOutlineExamples(Map<Integer, AstNode> nodeMap, ScenarioOutline scenarioOutline, ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode childNode) {
        scenarioOutline.getExamples().forEach((examples) -> {
            ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode examplesNode = new ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode(examples, childNode);
            TableRow headerRow = examples.getTableHeader();
            ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode headerNode = new ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode(headerRow, examplesNode);
            nodeMap.put(headerRow.getLocation().getLine(), headerNode);
            IntStream.range(0, examples.getTableBody().size()).forEach((i) -> {
                TableRow examplesRow = (TableRow)examples.getTableBody().get(i);
                Node rowNode = new ru.appline.autotests.glue.plugin.CucumberSourceUtils.ExamplesRowWrapperNode(examplesRow, i);
                ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode expandedScenarioNode = new ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode(rowNode, examplesNode);
                nodeMap.put(examplesRow.getLocation().getLine(), expandedScenarioNode);
            });
        });
    }

    private ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode getAstNode(String path, int line) {
        if (!this.pathToNodeMap.containsKey(path)) {
            this.parseGherkinSource(path);
        }

        return this.pathToNodeMap.containsKey(path) ? (ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode)((Map)this.pathToNodeMap.get(path)).get(line) : null;
    }

    public ScenarioDefinition getScenarioDefinition(String path, int line) {
        return this.getScenarioDefinition(this.getAstNode(path, line));
    }

    private ScenarioDefinition getScenarioDefinition(ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode astNode) {
        return astNode.getNode() instanceof ScenarioDefinition ? (ScenarioDefinition)astNode.getNode() : (ScenarioDefinition)astNode.getParent().getParent().getNode();
    }

    public String getKeywordFromSource(String uri, int stepLine) {
        Feature feature = this.getFeature(uri);
        if (feature != null) {
            TestSourceRead event = this.getTestSourceReadEvent(uri);
            String trimmedSourceLine = event.source.split("\n")[stepLine - 1].trim();
            GherkinDialect dialect = (new GherkinDialectProvider(feature.getLanguage())).getDefaultDialect();
            Iterator var7 = dialect.getStepKeywords().iterator();

            while(var7.hasNext()) {
                String keyword = (String)var7.next();
                if (trimmedSourceLine.startsWith(keyword)) {
                    return keyword;
                }
            }
        }

        return "";
    }

    private TestSourceRead getTestSourceReadEvent(String uri) {
        return this.pathToReadEventMap.containsKey(uri) ? (TestSourceRead)this.pathToReadEventMap.get(uri) : null;
    }

    private static class AstNode {
        private final Node node;
        private final ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode parent;

        AstNode(Node node, ru.appline.autotests.glue.plugin.CucumberSourceUtils.AstNode parent) {
            this.node = node;
            this.parent = parent;
        }

        public Node getNode() {
            return this.node;
        }

        public CucumberSourceUtils.AstNode getParent() {
            return this.parent;
        }
    }

    private static class ExamplesRowWrapperNode extends Node {
        private final int bodyRowIndex;

        ExamplesRowWrapperNode(Node examplesRow, int bodyRowIndex) {
            super(examplesRow.getLocation());
            this.bodyRowIndex = bodyRowIndex;
        }

        public int getBodyRowIndex() {
            return this.bodyRowIndex;
        }
    }
}

