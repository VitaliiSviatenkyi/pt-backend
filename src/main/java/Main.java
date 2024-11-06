import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        // 1,
        // 2 -> 3, 4
        // 5 -> 6 -> 7
        List<Node> list = new ArrayList<>();
        list.add(node(1));
        list.add(node(2, new Node[]{node(3), node(4)}));
        list.add(node(5, new Node[]{node(6, new Node[]{node(7)})}));

        // should return 4
        // because (1 + 2 + 3 + 4 + 5 + 6 + 7) / 7 = 4
        System.out.println("Mean value: " + getMeanValue(list));
        // should return 3.62
        // because (1 + 2 + (3 + 4)*0.90 + 5 + 6*0.90 + 7*0.90*0.90) / 7 = 3.62
        System.out.println("Mean value by depth: " + getMeanValueByDepth(list));
    }

    public interface Node {
        double getValue();

        List<Node> getNodes();
    }

    public static double getMeanValue(List<Node> nodes) {
        // please implement algorithm for mean value of all given nodes
        // each node has own value and sub-nodes of the same structure,
        // mean value should be calculated across all values in the tree
        Result result = calculateResult(nodes);
        double meanValue = result.getSum() > 0 ? result.getSum() / result.getCount() : 0.0;
        return roundToHundredths(meanValue);
    }

    public static double getMeanValueByDepth(List<Node> nodes) {
        Result result = calculateResultByDepth(nodes, 0);
        double meanValue = result.getSum() > 0 ? result.getSum() / result.getCount() : 0.0;
        return roundToHundredths(meanValue);
    }

    public static Result calculateResult(List<Node> nodes) {
        double sum = 0;
        double count = 0;
        for (Node node : nodes) {
            sum += node.getValue();
            count++;
            Result currentResult = calculateResult(node.getNodes());
            sum += currentResult.getSum();
            count += currentResult.getCount();
        }
        return new Result(sum, count);
    }

    public static Result calculateResultByDepth(List<Node> nodes, int depth) {
        double sum = 0;
        double count = 0;
        double weight = Math.pow(0.9, depth);
        for (Node node : nodes) {
            sum += node.getValue() * weight;
            count++;
            Result currentResult = calculateResultByDepth(node.getNodes(), depth + 1);
            sum += currentResult.getSum();
            count += currentResult.getCount();
        }
        return new Result(sum, count);
    }

    public static double roundToHundredths(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // builders

    public static Node node(double value) {
        return node(value, new Node[]{});
    }

    public static Node node(double value, Node[] nodes) {
        return new Node() {
            public double getValue() {
                return value;
            }

            public List<Node> getNodes() {
                return Arrays.asList(nodes);
            }
        };
    }

    public static class Result {
        private final double sum;
        private final double count;

        public Result(double sum, double count) {
            this.sum = sum;
            this.count = count;
        }

        public double getSum() {
            return sum;
        }

        public double getCount() {
            return count;
        }
    }
}