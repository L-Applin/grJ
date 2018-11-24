import org.graph.*;
import org.junit.Test;

public class GraphsTests {


    @Test
    public void testGraph(){

        MutableGraph<Integer> mg = Graphs.mutable();
        mg.addVertices(1, 2, 3);
        mg.addVertex(3);
        mg.addEdge(1, 2);
        mg.addEdge(1, 3);
        mg.addEdge(2, 4);
        mg.addEdge(12, 125);
        mg.addEdge(3, 4);
        mg.addEdge(3, 4);
        mg.log();
        System.out.println(mg.edges());

        System.out.println(mg.dfs().toList(1));

        mg.changeLog()
                .vertices(5, 6, 3, 6, 2, 128)
                .edge(6, 128)
                .commitChanges();
        mg.log(Vertices.vertexComparator(Integer::compareTo));
        System.out.println(mg.dfs().toList(1));

        System.out.println("\nimmutable : ");
        Graph<Integer> imm = Graphs.immutableFrom(mg);
        System.out.println(imm.dfs().toList(1));

    }

}
