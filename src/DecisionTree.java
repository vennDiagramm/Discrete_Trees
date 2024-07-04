import java.util.*;

class DecisionTree {
    static class Node {
        String attribute;
        Map<String, Node> children;
        String label;

        Node(String attribute) {
            this.attribute = attribute;
            this.children = new HashMap<>();
            this.label = null;
        }

        Node(String attribute, String label) {
            this.attribute = attribute;
            this.label = label;
        }
    }

    Node root;

    public DecisionTree() {
        this.root = null;
    }

    public void buildTree(List<Map<String, String>> data, List<String> attributes) {
        this.root = build(data, attributes);
    }

    private Node build(List<Map<String, String>> data, List<String> attributes) {
        if (data.isEmpty()) return null;

        // Check if all data has the same label
        String firstLabel = data.get(0).get("label");
        boolean allSameLabel = data.stream().allMatch(record -> record.get("label").equals(firstLabel));
        if (allSameLabel) {
            return new Node(null, firstLabel);
        }

        // If attributes are empty, return majority label
        if (attributes.isEmpty()) {
            return new Node(null, majorityLabel(data));
        }

        String bestAttribute = chooseBestAttribute(data, attributes);
        Node node = new Node(bestAttribute);

        Map<String, List<Map<String, String>>> partitions = partitionByAttribute(data, bestAttribute);
        for (String value : partitions.keySet()) {
            List<String> newAttributes = new ArrayList<>(attributes);
            newAttributes.remove(bestAttribute);
            node.children.put(value, build(partitions.get(value), newAttributes));
        }

        return node;
    }

    private String chooseBestAttribute(List<Map<String, String>> data, List<String> attributes) {
        String bestAttribute = null;
        double bestGain = 0;
        double baseEntropy = entropy(data);

        for (String attribute : attributes) {
            double newEntropy = 0;
            Map<String, List<Map<String, String>>> partitions = partitionByAttribute(data, attribute);

            for (List<Map<String, String>> subset : partitions.values()) {
                double subsetEntropy = entropy(subset);
                newEntropy += ((double) subset.size() / data.size()) * subsetEntropy;
            }

            double gain = baseEntropy - newEntropy;
            if (gain > bestGain) {
                bestGain = gain;
                bestAttribute = attribute;
            }
        }

        return bestAttribute;
    }

    private double entropy(List<Map<String, String>> data) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Map<String, String> record : data) {
            String label = record.get("label");
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        double entropy = 0;
        for (String label : labelCounts.keySet()) {
            double p = (double) labelCounts.get(label) / data.size();
            entropy -= p * Math.log(p) / Math.log(2);
        }

        return entropy;
    }

    private String majorityLabel(List<Map<String, String>> data) {
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Map<String, String> record : data) {
            String label = record.get("label");
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }

        return Collections.max(labelCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private Map<String, List<Map<String, String>>> partitionByAttribute(List<Map<String, String>> data, String attribute) {
        Map<String, List<Map<String, String>>> partitions = new HashMap<>();
        for (Map<String, String> record : data) {
            String value = record.get(attribute);
            partitions.computeIfAbsent(value, k -> new ArrayList<>()).add(record);
        }
        return partitions;
    }

    public String classify(Map<String, String> record) {
        return classify(record, root);
    }

    private String classify(Map<String, String> record, Node node) {
        if (node.label != null) {
            return node.label;
        }
        String attributeValue = record.get(node.attribute);
        Node childNode = node.children.get(attributeValue);
        if (childNode == null) {
            return null;
        }
        return classify(record, childNode);
    }

    public static void main(String[] args) {
        // Sample data
        List<Map<String, String>> data = new ArrayList<>();
        data.add(new HashMap<>(Map.of("outlook", "sunny", "temperature", "hot", "humidity", "high", "windy", "false", "label", "no")));
        data.add(new HashMap<>(Map.of("outlook", "sunny", "temperature", "hot", "humidity", "high", "windy", "true", "label", "no")));
        data.add(new HashMap<>(Map.of("outlook", "overcast", "temperature", "hot", "humidity", "high", "windy", "false", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "rainy", "temperature", "mild", "humidity", "high", "windy", "false", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "rainy", "temperature", "cool", "humidity", "normal", "windy", "false", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "rainy", "temperature", "cool", "humidity", "normal", "windy", "true", "label", "no")));
        data.add(new HashMap<>(Map.of("outlook", "overcast", "temperature", "cool", "humidity", "normal", "windy", "true", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "sunny", "temperature", "mild", "humidity", "high", "windy", "false", "label", "no")));
        data.add(new HashMap<>(Map.of("outlook", "sunny", "temperature", "cool", "humidity", "normal", "windy", "false", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "rainy", "temperature", "mild", "humidity", "normal", "windy", "false", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "sunny", "temperature", "mild", "humidity", "normal", "windy", "true", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "overcast", "temperature", "mild", "humidity", "high", "windy", "true", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "overcast", "temperature", "hot", "humidity", "normal", "windy", "false", "label", "yes")));
        data.add(new HashMap<>(Map.of("outlook", "rainy", "temperature", "mild", "humidity", "high", "windy", "true", "label", "no")));

        List<String> attributes = new ArrayList<>(List.of("outlook", "temperature", "humidity", "windy"));

        DecisionTree tree = new DecisionTree();
        tree.buildTree(data, attributes);

        Map<String, String> testRecord = new HashMap<>(Map.of("outlook", "sunny", "temperature", "cool", "humidity", "high", "windy", "true"));
        String result = tree.classify(testRecord);
        System.out.println("The label for the test record is: " + result);
    }
}
