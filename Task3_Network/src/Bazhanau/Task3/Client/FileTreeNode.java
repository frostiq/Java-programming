package Bazhanau.Task3.Client;

import Bazhanau.Task3.Dispatchers.ClientDispatcher;

import javax.swing.tree.TreeNode;
import java.io.File;
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
    private File currentPath;

    private FileTreeNode(ClientDispatcher dispatcher, File currentPath, FileTreeNode parent) {
        this.dispatcher = dispatcher;
        this.currentPath = currentPath;
        this.parent = parent;
        initChildren();
    }

    public FileTreeNode(ClientDispatcher dispatcher, File currentPath) {
        this(dispatcher, currentPath, null);
    }

    private void initChildren() {
        try {
            if (currentPath.isDirectory()) {
                List<String> names = this.dispatcher.sendDirRequest(currentPath.getAbsolutePath());
                children = names.stream().map((childName) -> new FileTreeNode(dispatcher, new File(currentPath, childName), this))
                        .collect(Collectors.toList());
            } else {
                children = new ArrayList<>();
            }
        } catch (IOException e) {
            dispatcher.getCatcher().catchException(e);
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return children.indexOf((FileTreeNode) node);
    }

    @Override
    public boolean getAllowsChildren() {
        return currentPath.isDirectory();
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public Enumeration children() {
        return Collections.enumeration(children);
    }

    @Override
    public String toString() {
        return currentPath.getName();
    }
}
