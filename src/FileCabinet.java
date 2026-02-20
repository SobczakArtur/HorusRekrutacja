import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {

    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return traverse(folders).stream()
                .filter(folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return traverse(folders).stream()
                .filter(folder -> folder.getSize().equals(size))
                .toList();
    }

    @Override
    public int count() {
        return traverse(folders).size();
    }


    private List<Folder> traverse(List<Folder> folders) {
        List<Folder> result = new ArrayList<>();

        for (Folder folder : folders) {
            result.add(folder);

            if (folder instanceof MultiFolder multiFolder) {
                result.addAll(traverse(multiFolder.getFolders()));
            }
        }

        return result;
    }
}