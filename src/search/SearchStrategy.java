package search;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

interface SearchStrategy {
    Set<Integer> find(Map<String, Set<Integer>> index, String query, int dataSize);

    default String[] transformQuery(String query) {
        return query.trim().toLowerCase().split("\\s+");
    }
}

class SearchAll implements SearchStrategy {
    @Override
    public Set<Integer> find(Map<String, Set<Integer>> index, String query, int dataSize) {
        String[] searchWords = transformQuery(query);
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < dataSize; i++) {
            result.add(i);
        }
        for (String word : searchWords) {
            if (!index.containsKey(word)
                    || result.size() == 0) {
                return null;
            }
            result.retainAll(index.get(word));
        }
        return result;
    }
}

class SearchAny implements SearchStrategy {
    @Override
    public Set<Integer> find(Map<String, Set<Integer>> index, String query, int dataSize) {
        String[] searchWords = transformQuery(query);
        Set<Integer> result = new HashSet<>();
        for (String word : searchWords) {
            if (index.containsKey(word)) {
                result.addAll(index.get(word));
            }
        }
        return result.size() == 0 ? null : result;
    }
}

class SearchNone implements SearchStrategy {
    @Override
    public Set<Integer> find(Map<String, Set<Integer>> index, String query, int dataSize) {
        String[] searchWords = transformQuery(query);
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < dataSize; i++) {
            result.add(i);
        }
        for (String word : searchWords) {
            if (index.containsKey(word)) {
                result.removeAll(index.get(word));
            }
            if (result.size() == 0) {
                return null;
            }
        }
        return result;
    }
}
