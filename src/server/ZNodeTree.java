import java.util.Set;

public class ZNodeTree {
    ZNode root;


    public void create(String path, Object data, Boolean isRegular){}

    public void delete(String path, int version){};

    public boolean exists(String path, boolean setWatch){
        return true;
    }

    public Object getData(String path, boolean setWatch){
        return new Object();
    }

    public void setData(String path, Object data, int version){}

    public Set<ZNode> getChildren(String path, boolean setWatch){
        return null;
    }

//    public void sync(String path)
}
