import java.util.*;

public class Main {

    public static void main(String[] args) {
        task12();
    }

    static final boolean SOLVE_PART_2 = true;

    static void task12() {
        Map<String, List<String>> graph = parseInput();
        List<String[]> allRoutes = dfs(graph);
        System.out.println(allRoutes.size());
    }

    static Map<String, List<String>> parseInput() {
        Map<String, List<String>> res = new HashMap<>();
        Scanner in = new Scanner(System.in);
        while (true) {
            String str = in.nextLine();
            if (str.length() < 3) break;
            String[] vv = str.split("-");
            if (!res.containsKey(vv[0])) res.put(vv[0], new ArrayList<>());
            res.get(vv[0]).add(vv[1]);
            if (!res.containsKey(vv[1])) res.put(vv[1], new ArrayList<>());
            res.get(vv[1]).add(vv[0]);
        }
        return res;
    }

    static List<String[]> dfs(Map<String, List<String>> g) {
        List<String[]> res = new LinkedList<>();
        Stack<String> route = new Stack<>();
        Map<String, Boolean> frequencyOfSmallCavesInCurrentRoute = new HashMap<>();
        dfs(g, res, route, frequencyOfSmallCavesInCurrentRoute, "start");
        return res;
    }

    static void dfs(Map<String, List<String>> g, List<String[]> list, Stack<String> route, Map<String, Boolean> frequencyOfSmallCavesInCurrentRoute, String v) {
        route.push(v);
        if (v.equals("end")) {
            list.add(route.toArray(new String[0]));
        } else {
            for (String adj : g.get(v)) {
                if (!adj.equals("start")) {
                    if (!frequencyOfSmallCavesInCurrentRoute.containsKey(adj)) {
                        if (Character.isLowerCase(adj.charAt(0))) {
                            frequencyOfSmallCavesInCurrentRoute.put(adj, false);
                        }
                        dfs(g, list, route, frequencyOfSmallCavesInCurrentRoute, adj);
                        frequencyOfSmallCavesInCurrentRoute.remove(route.pop());
                    } else if (SOLVE_PART_2) {
                        boolean are2EqualsSmall = false;
                        for (Map.Entry<String, Boolean> kv : frequencyOfSmallCavesInCurrentRoute.entrySet()) {
                            are2EqualsSmall = kv.getValue();
                            if (are2EqualsSmall) {
                                break;
                            }
                        }
                        if (!are2EqualsSmall) {
                            frequencyOfSmallCavesInCurrentRoute.put(adj, true);
                            dfs(g, list, route, frequencyOfSmallCavesInCurrentRoute, adj);
                            frequencyOfSmallCavesInCurrentRoute.put(route.pop(), false);
                        }
                    }
                }
            }
        }
    }
}