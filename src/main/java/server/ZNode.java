package server;

import java.util.List;

public class ZNode {
    Boolean isWatch;
    List<String> watcherList;
    int version;

    String name;
    Object data;
    List<ZNode> children;

    nodetype type;
    int seqflag;

    public enum nodetype{Regular, Ephemeral}

}
