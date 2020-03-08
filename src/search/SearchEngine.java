package search;

import java.util.*;

class SearchEngine {

    private List<String> data;
    private Map<String, Set<Integer>> index;
    private SearchStrategy searchStrategy;

    public SearchEngine(List<String> data) {
        this.data = data;
        index = new HashMap<>();
        createIndex();
    }

    private void createIndex() {
        index.clear();
        for (int i = 0; i < data.size(); i++) {
            for (String word : data.get(i).toLowerCase().split("\\s+")) {
                if (index.containsKey(word)) {
                    index.get(word).add(i);
                } else {
                    index.put(word.toLowerCase(), new HashSet<>(Set.of(i)));
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("\n=== List of people ===\n");
        for (String item : data) {
            stringBuilder.append(item).append("\n");
        }
        return stringBuilder.toString();
    }

    public String find(String query) {
        StringBuilder result = new StringBuilder();
        var indexes = searchStrategy.find(index, query, data.size());
        if (indexes == null) {
            result.append("No matching people found.");
        } else {
            result.append(indexes.size()).append(" persons found:").append("\n");
            indexes.forEach(i -> result.append(data.get(i)).append("\n"));
        }
        return result.toString();
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }
}
