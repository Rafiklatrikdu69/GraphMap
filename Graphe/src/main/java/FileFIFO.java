import java.util.LinkedList;

public class FileFIFO<T> {
    private LinkedList<T> file;

    public FileFIFO() {
        file = new LinkedList<>();
    }

    public void enfiler(T element) {
        file.addLast(element);
    }

    public T defiler() {
        if (estVide()) {
            throw new IllegalStateException("La file est vide.");
        }
        return file.removeFirst();
    }

    public T premier() {
        if (estVide()) {
            throw new IllegalStateException("La file est vide.");
        }
        return file.getFirst();
    }

    public boolean estVide() {
        return file.isEmpty();
    }

    public int taille() {
        return file.size();
    }
}
