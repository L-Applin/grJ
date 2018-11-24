package main;

import org.graph.*;

import java.util.Comparator;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        MutableGraph<Integer> mg = Graphs.mutable();
        mg.addVertices(1, 2, 3);
        mg.addVertex(3);
        mg.addEdge(1, 2);
        mg.addEdge(1, 3);
        mg.addEdge(1, 4);
        mg.addEdge(12, 125);
        mg.addEdge(3, 4);
        mg.addEdge(3, 4);
        mg.log();
        System.out.println(mg.edges());



    }









    static class Test {

        public static final Comparator<Test> comparator = Comparator.comparingInt(test -> test.val);

        int val;
        String name;


        public Test(int val, String name) {
            this.val = val;
            this.name = name;
        }

        @Override
        public String toString() {
            return "val=" + val +
                    ", name='" + name + '\'';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Test test = (Test) o;
            return val == test.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

    }

    static Test test(int val, String name){
        return new Test(val, name);
    }

}
