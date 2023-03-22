public class TestGraphe {
    private static LCGraphe newGraphe = new LCGraphe();
    public static void main(String [] args ){
        newGraphe.addMain("Lyon", "O");
        newGraphe.addMain("Paris", "M");
        newGraphe.addEdge("Lyon", "Paris", 4, 6, 5);
     newGraphe.afficheBloc();

    }
}
