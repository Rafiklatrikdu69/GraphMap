import junit.framework.TestCase;
import org.junit.Test;

public class LCGrapheTest extends TestCase {
    @Test
    void chercheMaillon() {
        var recherche = new LCGraphe();
        assertNull(recherche.chercherMaillon("S32"));
    }
}