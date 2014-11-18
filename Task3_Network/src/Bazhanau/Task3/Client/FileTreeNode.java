package Bazhanau.Task3.Client;

import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class FileTreeNode implements TreeNode {
    private ClientDispatcher dispatcher;
    private FileTreeNode parent;
    private List<FileTreeNode> children;
    private String currentPath;

    private FileTreeNode(ClientDispatcher dispatcher, String currentPath, FileTreeNode parent) {
        this.dispatcher = dispatcher;
        this.currentPath = currentPath;
        this.parent = parent;
    }

    public FileTreeNode(ClientDispatcher dispatcher, String currentPath) {
        this(dispatcher, currentPath, null);
    }

    private static String getName(String path) {
        String[] names = path.split("[/\\\\]");
        return names[names.length - 1];
    }

    private static boolean isDirectory(String path) {
        return !getName(path).contains(".");
    }

    public List<FileTreeNode> getChildren() {
        if (children == null) {
            initChildren();
        }
        return children;
    }

    private void initChildren() {
        try {
            children = new ArrayList<>();
            if (isDirectory(currentPath)) {
                List<String> names = this.dispatcher.sendDirRequest(currentPath);
                children.addAll(names
                        .stream()
                        .map(name -> new FileTreeNode(dispatcher, currentPath + "\\" + name, this))
                        .collect(Collectors.toList()));
            }
        } catch (IOException e) {
            dispatcher.getCatcher().catchException(e);
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return getChildren().get(childIndex);
    }

    @Override
    public int getChildCount() {
        return getChildren().size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return getChildren().indexOf((FileTreeNode) node);
    }

    @Override
    public boolean getAllowsChildren() {
        return isDirectory(currentPath);
    }

    @Override
    public boolean isLeaf() {
        initChildren();
        return getChildren().isEmpty();
    }

    @Override
    public Enumeration children() {
        initChildren();
        return Collections.enumeration(getChildren());
    }

    @Override
    public String toString() {
        return getName(currentPath);
    }

    public String getCurrentPath() {
        return currentPath;
    }
}
